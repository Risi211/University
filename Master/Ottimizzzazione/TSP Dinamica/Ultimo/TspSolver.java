/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.*;
import javax.swing.SwingUtilities;
import javax.swing.*;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author luca
 */
public class TspSolver  extends Thread{
       
    final static int INFINITE = 100000000; //è un numero abbastanza grande da poter essere considerato infinito
    //ma più piccolo di Integer.MaxValue, altrimenti se si somma ad un altro numero va in overflow e diventa negativo    
    //dimensioni per disegnare grafo su schermo
    int VERTEX_WIDTH = 20;
    int VERTEX_HEIGHT = 20;    
    int PARTENZA, NUM_VERTICI;
    Grafo g;
    double upperBound;
    double userUpperBound;
    Vertex[] vertici;
    JTextArea TxtLog;
    int[][] cost;
    JPanel PnlLB, PnlTsp, PnlNN;
    int num_iterazioni_LB;
    int num_iterazioni_Alfa;
    double alfaStart;
    double alfaMin;
    JLabel LblLB, LblNN, LblTSP;    
    boolean debugLB;
    LogBuffer buffer;
    int niter_debug;
    int init_dimension;
    PoolStati stati_precaricati;
    int tetto_stati = 0;
    
    public TspSolver(double userUpperBound, int NUM_VERTICI, int PARTENZA, Vertex[] vertici, int[][] cost, JTextArea TxtLog, JPanel PnlLB, JPanel PnlTsp, int num_iterazioni_LB, int num_iterazioni_Alfa, double alfaStart, double alfaMin, JLabel LblLB, JLabel LblNN, JLabel LblTSP, JPanel PnlNN, boolean debugLB, int niter_debug, int init_dimension, int tetto_stati)
    {        
        this.userUpperBound = userUpperBound;
        this.NUM_VERTICI = NUM_VERTICI;
        this.PARTENZA = PARTENZA;
        this.vertici = vertici;
        this.cost = cost;
        this.TxtLog = TxtLog;
        this.PnlLB = PnlLB;
        this.PnlTsp = PnlTsp;
        this.num_iterazioni_LB = num_iterazioni_LB;
        this.alfaStart = alfaStart;
        this.alfaMin = alfaMin;
        this.LblLB = LblLB;
        this.LblNN = LblNN;
        this.LblTSP = LblTSP;
        this.PnlNN = PnlNN;
        this.debugLB = debugLB;
        this.num_iterazioni_Alfa = num_iterazioni_Alfa;
        this.niter_debug = niter_debug;
        this.init_dimension = init_dimension;
        this.tetto_stati = tetto_stati;
        
        DefaultCaret dc = (DefaultCaret) this.TxtLog.getCaret();
        dc.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        buffer = new LogBuffer();
        //fa partire il thread che scrive nel log
        LogWriter lw = new LogWriter(TxtLog, buffer);
        lw.start();
    }
    
    
    
    @Override
    public void run() 
    {   
        //inizializza stati precaricati
        log("Genera pool di stati di dimensione: " + init_dimension + "\r\n");
        stati_precaricati = new PoolStati(init_dimension, NUM_VERTICI);        
        
        //inizializzazione grafo
        g = new Grafo(NUM_VERTICI);
        //g.buildVertici(num_vertici);
        //g.buildArchi(cost, NUM_VERTICI, NUM_VERTICI);                         

        //prendo l'upperbound migliore fra quelli calcolati dal triottimale
        //mi serve per applicare l'ascent lagrangiano nel calcolo del lower bound
        upperBound = GetBestUpperBound() + 0.5;
        
        log("\r\nCalcolo lower bound\r\n");
        LowerBoundSolution lb_sol = getLowerBound(g);
        
            print(LblLB, "\r\nLowerBound, costo = " + lb_sol.lowerBoundValue_stati);                

            DrawSolution(PnlLB, lb_sol.solution);        
        
        //controlla se si deve fermare qui, cioè se si è in modalità debug
        if(!debugLB)
        {            
            //calcola soluzione ottima
            if(lb_sol.isOptimal)
            {
                //il lower bound ha trovato la soluzione ottima
                DrawSolution(PnlTsp, lb_sol.solution);
                print(LblTSP, "TSP, costo = " + lb_sol.stati_lb[NUM_VERTICI - 1].getCosto1(PARTENZA));        
            }
            else
            {
                //controllo se usare la dinamica normale o la variante con un tetto massimo di stati
                TSP_Solution soluzione_ottima;
                if(tetto_stati == 0) //dinamica normale
                {
                    soluzione_ottima = getTSPSolution(g, lb_sol, upperBound);        
                }
                else //tetto stati
                {
                    soluzione_ottima = getTSPSolutionTettoStati(g, lb_sol, upperBound);        
                }
                DrawSolution(PnlTsp, soluzione_ottima.sol);
                print(LblTSP, "TSP, costo = " + soluzione_ottima.cost);        
            }
            
        }                
        
        //ferma il thread che gestisce il log
        buffer.stop();
    }    
    
    public double GetBestUpperBound()
    {
        if(userUpperBound > 0)
        {
            log("Upper bound = " + userUpperBound);
            return userUpperBound;
        }
        
        double ub = 0;                
        
        //log("Calcolo Nearest Neighbour Upper bound\r\n");
        //calcolo upperbound con l'algoritmo del nearest neighbour
        TSP_Solution nn_sol = Nearest_Neighbour(g, cost, NUM_VERTICI, NUM_VERTICI);            
        //upperBound = nn_sol.cost;
        log("Nearest Neighbour Upper bound = " + nn_sol.cost);    
        //print(LblNN, "Nearest Neighbour, costo = " + upperBound);
        //ricerca locale: 3-opt del nearest neighbour
        //log("Calcolo 3-Opt Nearest Neighbour Upper bound\r\n");
        TSP_Solution triOpt_nn_sol = TriOttimale(cost, NUM_VERTICI, NUM_VERTICI, nn_sol, 100);
        log("3-Opt Nearest Neighbour Upper bound = " + triOpt_nn_sol.cost);    
        
        
        //calcolo upperbound naive: vertici da 0 a n-1 in ordine
        //log("\r\nCalcolo Naive1 Upper Bound (vertici da 0 a n) = ");
        TSP_Solution naive1_sol = Naive1(cost);
        //upperBound = nn_sol.cost;
        log("\r\nNaive1 Upper bound = " + naive1_sol.cost);    
        //ricerca locale: 3-opt naive1
        //log("\r\nCalcolo 3-Opt Naive1 Upper Bound (vertici da 0 a n) = ");
        TSP_Solution triOpt_naive1_sol = TriOttimale(cost, NUM_VERTICI, NUM_VERTICI, naive1_sol, 100);
        log("3-Opt Naive1 Upper bound = " + triOpt_naive1_sol.cost);            
        
        //log("\r\nCalcolo 3-Opt Naive2 Upper Bound:\r\ngenera 10 istanze a random, apllica il 3-opt a ognuna di esse \r\ne restituisce l'upper bound migliore trovato  = ");
        //calcolo 10 soluzioni naive a random        
        Random r = new Random(System.currentTimeMillis());        
        //calcolo upperbound naive: vertici da 0 a n-1 in ordine        
        TSP_Solution naive2_sol = Naive2(cost, r);
        //upperBound = nn_sol.cost;  
        //ricerca locale: 3-opt naive2
        TSP_Solution triOpt_naive2_sol = TriOttimale(cost, NUM_VERTICI, NUM_VERTICI, naive2_sol, 100);
        double naive2ub = 0;
        for(int i = 0; i < 10; i++)
        {
            naive2_sol = Naive2(cost, r);
            TSP_Solution triOpt_tmp = TriOttimale(cost, NUM_VERTICI, NUM_VERTICI, naive2_sol, 100);
            //controllo se ho trovato un upperbound migliore
            if(triOpt_tmp.cost < triOpt_naive2_sol.cost)
            {
                triOpt_naive2_sol = triOpt_tmp;
                naive2ub = naive2_sol.cost;
            }
        }                       
        log("\r\nNaive2 Upper bound = " + naive2ub);  
        log("3-Opt Naive2 Upper bound = " + triOpt_naive2_sol.cost); 
        log(" ");
        
        if(triOpt_nn_sol.cost < triOpt_naive1_sol.cost)
        {
            if(triOpt_nn_sol.cost < triOpt_naive2_sol.cost) //nearest neighbour wins
            {
                //il nearest neighbour ha trovato l'upper bound migliore
                ub = triOpt_nn_sol.cost;
                print(LblNN, "Nearest Neighbour, costo = " + ub);
                DrawSolution(PnlNN, triOpt_nn_sol.sol);   
            }
            else //naive2 wins
            {
                //il naive2 ha trovato l'upper bound migliore
                ub = triOpt_naive2_sol.cost;
                print(LblNN, "Naive2, costo = " + ub);
                DrawSolution(PnlNN, triOpt_naive2_sol.sol);   
            }
        }
        else
        {
            if(triOpt_naive1_sol.cost < triOpt_naive2_sol.cost) //naive1 wins
            {
                //il naive2 ha trovato l'upper bound migliore
                ub = triOpt_naive1_sol.cost;
                print(LblNN, "Naive1, costo = " + ub);
                DrawSolution(PnlNN, triOpt_naive1_sol.sol);                   
            }
            else //naive2 wins
            {
                //il naive2 ha trovato l'upper bound migliore
                ub = triOpt_naive2_sol.cost;
                print(LblNN, "Naive2, costo = " + ub);
                DrawSolution(PnlNN, triOpt_naive2_sol.sol);                   
            }
        }
        
        return ub;
    }
    
     public TSP_Solution getTSPSolution(Grafo g, LowerBoundSolution lb_sol, double upper_bound)
    { 
        //Stati_nPath[] lower_bounds
        TSP_Solution tsp_sol = new TSP_Solution();
        tsp_sol.sol = new Vertex[NUM_VERTICI + 1];
        
            long start = System.currentTimeMillis();        
        
        Stadio[] stadi = new Stadio[NUM_VERTICI];
        
        //inizializzazione
        Stadio std = new Stadio(NUM_VERTICI);
        
        Stato stt = new Stato(NUM_VERTICI);
        stt.costo = 0;
        stt.predecessore = null;
        stt.addVertice(PARTENZA);
        stt.end = PARTENZA;
        stt.id = 0;
        
        std.addStato(stt);
        stadi[0] = std;
        
        long id_stato = 1;
        
        
        long tmpStart = 0;
        long tmpEnd = 0;
        long startd = System.currentTimeMillis();
        long hashd = 0;
        long getStato = 0;
        long streamd = 0;
        long gestioneStatod = 0;
        long completamentod = 0;
        long setContainsd = 0;
        long initStatod = 0;
        long released = 0;
        long logd = 0;
        
        
        //ricorsione forward
        for(int k = 1; k < NUM_VERTICI; k++)
        {
            Stadio sk = new Stadio(NUM_VERTICI);      
            long cont_stati = 0; //conta quanti stati genero alla cardinalità k
            long stati_scartati = 0; //numero stati scartati
            //espansione degli stati dello stadio k-1
            for(int bucket = 0; bucket < NUM_VERTICI; bucket++)
            {
                tmpStart = System.currentTimeMillis();
                Iterator<Long> keys = stadi[k-1].stati.get(bucket).keySet().iterator();                
                tmpEnd = System.currentTimeMillis();
                hashd += (tmpEnd - tmpStart)/10;
                
                while(keys.hasNext()) //per ogni chiave
                {
                    tmpStart = System.currentTimeMillis();
                    long key = keys.next(); //considero la chiave corrente
                    //prendo tutti i valori di questa chiave
                    List<Stato> toCheck = stadi[k-1].stati.get(bucket).get(key);                    
                    tmpEnd = System.currentTimeMillis();
                    getStato += (tmpEnd - tmpStart);
                    
                    for(Stato s:toCheck) //per ogni stato si questo sottoinsieme di stati
                    {
                        //genera-aggiorna gli stati di Sk raggiungibili da (S,i)
                        int i = s.end;

                        for(int j = 1; j < NUM_VERTICI; j++)
                        {
                            tmpStart = System.currentTimeMillis();
                            if(s.setContains(j))
                            {
                                tmpEnd = System.currentTimeMillis();
                                setContainsd += (tmpEnd - tmpStart);
                                //il vertice appartiene ai successori di i meno s
                                continue;
                            }
                            tmpEnd = System.currentTimeMillis();
                            setContainsd += (tmpEnd - tmpStart);                            
                            //considera lo stato (S',j)
                            tmpStart = System.currentTimeMillis();
                            Stato s2 = stati_precaricati.GetStato();
                            s2.end = j;                    
                            s2.costo = s.costo + cost[i][j];
                            s2.setSet(s.set);
                            s2.addVertice(j);
                            s2.predecessore = s;
                            tmpEnd = System.currentTimeMillis();
                            initStatod += (tmpEnd - tmpStart);
                            //controllo se la somma del costo di questo stato che finisce nel vertice j
                            ///e il costo del lower bound che inizia nel vertice j di cardinalità n - k
                            //è > dell'upper_bound
                            //Stati_nPath lb = lower_bounds[NUM_VERTICI - s2.getCardinality()];
                            tmpStart = System.currentTimeMillis();
                            Stati_nPath lb = lb_sol.stati_lb[NUM_VERTICI - k - 1];
                            double completamento = getCompletamentoLB(s2, lb, j, lb_sol.lambda);
                            //if(s2.costo + lb.getCosto1(j) > upper_bound)                            
                            tmpEnd = System.currentTimeMillis();
                            completamentod += (tmpEnd - tmpStart);
                            
                            if(completamento > upper_bound)
                            {
                                //rigetta lo stato
                                stati_scartati++;
                                tmpStart = System.currentTimeMillis();
                                stati_precaricati.ReleaseStato(s2);
                                tmpEnd = System.currentTimeMillis();
                                released += (tmpEnd - tmpStart);
                                continue;
                            }
                            //else
                            tmpStart = System.currentTimeMillis();
                            cont_stati++;
                            //si hanno i seguenti casi:                    
                            //se (S',j) non appartiene a Sk:
                            Stato target = sk.getStato(s2);
                            if(target != null) //lo stato s2 è contenuto nello stadio Sk
                            {
                                //controllo se il costo dello stato già contenuto in Sk è maggiore del nuovo costo di s2
                                if(target.costo > s2.costo)
                                {
                                    sk.updateStato(s2, target);
                                    //l'updateStato modifica lo stato vecchio, quindi si può liberare s2 nel pool di stati
                                    stati_precaricati.ReleaseStato(s2);
                                }
                            }
                            else //non contenuto
                            {
                                s2.id = id_stato++;
                                sk.addStato(s2);                        
                            }
                            
                            tmpEnd = System.currentTimeMillis();
                            gestioneStatod += (tmpEnd - tmpStart);
                            
                        }
                    }
                }
                
            }
            tmpStart = System.currentTimeMillis();
            stadi[k] = sk; //aggiungo lo stadio alla lista
            //stampo numero di stati generati
            log("TSP: cardinalità " + k + ":");
            log("-- numero stati utili = " + cont_stati);
            log("-- numero stati scartati = " + stati_scartati);
            tmpEnd = System.currentTimeMillis();
            logd += (tmpEnd - tmpStart);            
        }
        
        long endd = System.currentTimeMillis();
        long totald = (endd - startd);
        log("totald = " + totald);
        log("hashd = " + hashd);
        log("getStato = " + getStato);
        log("streamd = " + streamd);
        log("gestioneStatod = " + gestioneStatod);
        log("completamentod = " + completamentod);
        log("setContainsd = " + setContainsd);
        log("initStatod = " + initStatod);
        log("released = " + released);
        log("logd = " + released);

        
        //calcolo costo ottimo
        int z_min = Integer.MAX_VALUE;
        Stato ultimo_stato = new Stato(NUM_VERTICI); //l'ultimo stato del TSP, serve per ricostruire il percorso minimo con i predecessori
        for(int bucket = 0; bucket < NUM_VERTICI; bucket++)
        {
                //Set<Long> keys = stadi[k-1].stati.get(bucket).keySet();
                Iterator<Long> keys = stadi[NUM_VERTICI-1].stati.get(bucket).keySet().iterator();
                while(keys.hasNext()) //per ogni chiave
                {
                    long key = keys.next(); //considero la chiave corrente
                    List<Stato> toCheck = stadi[NUM_VERTICI-1].stati.get(bucket).get(key);
                    for(Stato s:toCheck)
                    {
                        //se esiste l'arco che fa dal vertice finale al vertice di partenza:
                        //Optional<Arco> a = g.getArco(s.end, PARTENZA);
                        //if(a.isPresent())
                        //{
                            //se il costo è minore del minimo attuale, lo aggiorna
                            //double new_cost = s.costo + a.get().cij;
                            int new_cost = s.costo + cost[s.end][PARTENZA];
                            if(new_cost < z_min)
                            {
                                z_min = new_cost;
                                ultimo_stato.predecessore = s;                    
                            }
                        //}
                    }                    
                }                    
        }

        
        ultimo_stato.costo = z_min;
        ultimo_stato.end = PARTENZA;
        ultimo_stato.id = id_stato++;
        //ultimo_stato.set è il vettore con tutti i bit = 1, contiene tutti i vertici del grafo
        
        TSP_Result res = new TSP_Result();
        res.stadi = stadi;
        res.ultimo_stato = ultimo_stato;
        
        long end = System.currentTimeMillis();        
        log("\r\nCosto soluzione ottima = " + res.ultimo_stato.costo);
        tsp_sol.cost = res.ultimo_stato.costo;
        log("Percorso inverso:");
        log("Id: " + res.ultimo_stato.end);
        Stato pred = res.ultimo_stato;
        int cont = 0;
        tsp_sol.sol[cont++] = vertici[pred.end];
        while(pred.predecessore != null)
        {
            pred = pred.predecessore;
            tsp_sol.sol[cont++] = vertici[pred.end];
            log("Id: " + pred.end);
        }

        log("tempo totale esecuzione TSP: " + ((end - start) / 1000) + " secondi");     
        
        
        return tsp_sol;                                
    }            

     public TSP_Solution getTSPSolutionTettoStati(Grafo g, LowerBoundSolution lb_sol, double upper_bound)
    { 
        //Stati_nPath[] lower_bounds
        TSP_Solution tsp_sol = new TSP_Solution();
        tsp_sol.sol = new Vertex[NUM_VERTICI + 1];
        
            long start = System.currentTimeMillis();        
        
        Stadio[] stadi = new Stadio[NUM_VERTICI];
        
        //inizializzazione
        Stadio std = new Stadio(NUM_VERTICI);
        
        Stato stt = new Stato(NUM_VERTICI);
        stt.costo = 0;
        stt.predecessore = null;
        stt.addVertice(PARTENZA);
        stt.end = PARTENZA;
        stt.id = 0;
        
        std.addStato(stt);
        stadi[0] = std;
        
        long id_stato = 1;                       
        
        //dato che questa variante è probabile che trovi un upper bound,
        //se la cardinalità è > della metà del numero dei vertici
        //e il numero degli stati utili + il numero degli stati scartati < tetto massimo
        //rigenera tutti gli stati, e non ne scarta più nemmeno uno
        boolean da_scartare = true;
        //ricorsione forward
        for(int k = 1; k < NUM_VERTICI; k++)
        {
            Stadio sk = new Stadio(NUM_VERTICI);      
            long cont_stati = 0; //conta quanti stati genero alla cardinalità k
            long stati_scartati = 0; //numero stati scartati
            List<Stato> selected = new ArrayList<>(); //contiene tutti gli stati, saranno da ordinare in modo crescente sul completamento al lower bound per prendere es i primi 20k stati da espandere            
            //espansione degli stati dello stadio k-1
            for(int bucket = 0; bucket < NUM_VERTICI; bucket++)
            {
                Iterator<Long> keys = stadi[k-1].stati.get(bucket).keySet().iterator();                                
                while(keys.hasNext()) //per ogni chiave
                {
                    long key = keys.next(); //considero la chiave corrente
                    //prendo tutti i valori di questa chiave
                    List<Stato> toCheck = stadi[k-1].stati.get(bucket).get(key);       
                    //aggiungo tutti questi stati alla lista degli stati totali
                    Iterator<Stato> is = toCheck.iterator();
                    while(is.hasNext())
                    {
                        selected.add(is.next());
                    }
                }
            }
                //ordino la lista selected sulla base del completamento, in ordine crescente
                selected.sort((Stato s1, Stato s2) -> s1.completamento < s2.completamento ? -1 : 1);
                //prende il minimo fra il tetto stati e il numero di stati totali da espandere
                int listSize = selected.size();
                int minCount = tetto_stati < listSize ? tetto_stati : listSize;
                    for(int t = 0; t < minCount; t++) //per ogni stato si questo sottoinsieme di stati
                    {
                        Stato s = selected.get(t);
                        //genera-aggiorna gli stati di Sk raggiungibili da (S,i)
                        int i = s.end;

                        for(int j = 1; j < NUM_VERTICI; j++)
                        {
                            if(s.setContains(j))
                            {
                                //il vertice appartiene ai successori di i meno s
                                continue;
                            }
                            //considera lo stato (S',j)
                            Stato s2 = stati_precaricati.GetStato();
                            s2.end = j;                    
                            s2.costo = s.costo + cost[i][j];
                            s2.setSet(s.set);
                            s2.addVertice(j);
                            s2.predecessore = s;
                            //controllo se la somma del costo di questo stato che finisce nel vertice j
                            ///e il costo del lower bound che inizia nel vertice j di cardinalità n - k
                            //è > dell'upper_bound
                            //Stati_nPath lb = lower_bounds[NUM_VERTICI - s2.getCardinality()];
                            Stati_nPath lb = lb_sol.stati_lb[NUM_VERTICI - k - 1];
                            s2.completamento = getCompletamentoLB(s2, lb, j, lb_sol.lambda);
                                                        
                            //dato che è probabile che trovi un upper bound, non scarta gli stati 
                            //delle ultime 5 cardinalità
                            if(da_scartare)
                            {
                                if(s2.completamento > upper_bound)
                                {
                                    //rigetta lo stato
                                    stati_scartati++;
                                    stati_precaricati.ReleaseStato(s2);
                                    continue;
                                }
                            }
                            //else, non scartato
                            cont_stati++;
                            //si hanno i seguenti casi:                    
                            //se (S',j) non appartiene a Sk:
                            Stato target = sk.getStato(s2);
                            if(target != null) //lo stato s2 è contenuto nello stadio Sk
                            {
                                //controllo se il costo dello stato già contenuto in Sk è maggiore del nuovo costo di s2
                                if(target.costo > s2.costo)
                                {
                                    sk.updateStato(s2, target);
                                    //l'updateStato modifica lo stato vecchio, quindi si può liberare s2 nel pool di stati
                                    stati_precaricati.ReleaseStato(s2);
                                }
                            }
                            else //non contenuto
                            {
                                s2.id = id_stato++;
                                sk.addStato(s2);                        
                            }                           
                            
                        }
                    }
                
            //controllo se ho superato la metà dei vertici con la cardinalità
            //e se il numero degli stati utili + numero stati scartati < tetto massimo
            //in questo caso non si scartano più gli stati fino alla fine delle cardinalità
            //e rigenero anche gli stati scartati in questo stadio
            //rifacendo l'iterazione
            if( da_scartare && ((cont_stati + stati_scartati) < tetto_stati) && (k > (NUM_VERTICI / 2)))
            {
                da_scartare = false;
                log("Rigenero stati dello stadio alla cardinalità " + k + ""
                        + "\r\nPerchè la somma fra il numero di stati utili"
                        + "e il numero di stati scartati è < tetto massimo.\r\n"
                        + "Da adesso in avanti non si scartano più gli stati.");
                k--;
                continue;
            }
            stadi[k] = sk; //aggiungo lo stadio alla lista
            //stampo numero di stati generati
            log("TSP: cardinalità " + k + ":");
            log("-- numero stati utili = " + cont_stati);
            log("-- numero stati scartati = " + stati_scartati);
        }
        
        //calcolo costo ottimo
        int z_min = Integer.MAX_VALUE;
        Stato ultimo_stato = new Stato(NUM_VERTICI); //l'ultimo stato del TSP, serve per ricostruire il percorso minimo con i predecessori
        for(int bucket = 0; bucket < NUM_VERTICI; bucket++)
        {
                //Set<Long> keys = stadi[k-1].stati.get(bucket).keySet();
                Iterator<Long> keys = stadi[NUM_VERTICI-1].stati.get(bucket).keySet().iterator();
                while(keys.hasNext()) //per ogni chiave
                {
                    long key = keys.next(); //considero la chiave corrente
                    List<Stato> toCheck = stadi[NUM_VERTICI-1].stati.get(bucket).get(key);
                    for(Stato s:toCheck)
                    {
                        //se esiste l'arco che fa dal vertice finale al vertice di partenza:
                            //se il costo è minore del minimo attuale, lo aggiorna
                            //double new_cost = s.costo + a.get().cij;
                            int new_cost = s.costo + cost[s.end][PARTENZA];
                            if(new_cost < z_min)
                            {
                                z_min = new_cost;
                                ultimo_stato.predecessore = s;                    
                            }
                    }                    
                }                    
        }

        
        ultimo_stato.costo = z_min;
        ultimo_stato.end = PARTENZA;
        ultimo_stato.id = id_stato++;
        //ultimo_stato.set è il vettore con tutti i bit = 1, contiene tutti i vertici del grafo
        
        TSP_Result res = new TSP_Result();
        res.stadi = stadi;
        res.ultimo_stato = ultimo_stato;
        
        long end = System.currentTimeMillis();        
        log("\r\nCosto soluzione = " + res.ultimo_stato.costo);
        tsp_sol.cost = res.ultimo_stato.costo;
        log("Percorso inverso:");
        log("Id: " + res.ultimo_stato.end);
        Stato pred = res.ultimo_stato;
        int cont = 0;
        tsp_sol.sol[cont++] = vertici[pred.end];
        while(pred.predecessore != null)
        {
            pred = pred.predecessore;
            tsp_sol.sol[cont++] = vertici[pred.end];
            log("Id: " + pred.end);
        }

        log("tempo totale esecuzione TSP: " + ((end - start) / 1000) + " secondi");     
        
        
        return tsp_sol;                                
    }            
     
     
     private double getCompletamentoLB(Stato s, Stati_nPath lb, int j, double[] lambda)
     {         
         //controllo se il pigreco(j) (il predecessore1 del cammino del lower bound)
         //non appartiene all'insieme S:
         //completamento = costo(S,j) + f^-1(n-k, j) se il pigreco non appartiene a S
         //completamento = costo(S,j) + g^-1(n-k, j) se il pigreco appartiene a S
         double completamento = s.costo + lb.getCosto1(j);
         //double completamento = 0;
         
         /*
         if(s.setContains(lb.getPredecessore1(j)))
         {
             completamento = s.costo + lb.getCosto2(j);
         }
         else
         {
             completamento = s.costo + lb.getCosto1(j);
         }
         */
         
         //sommo le penalità lagrangiane al completamento:
         completamento += lambda[j]; //solo una volta lambda j
         //2 volte tutte quelle che non compaiono nel set di vertici di s
         completamento += s.sommaPenalita(lambda);
         return completamento;
     }
     
    private LowerBoundSolution getLowerBound(Grafo g)
    {
        LowerBoundSolution sol = new LowerBoundSolution();
        
        //Stati_nPath[] lower_bounds = getReverseFunctionLagrangiana(cost, g);
        LowerBoundSolution lb_sol = getReverseFunctionLagrangiana(cost, g);
        //Stati_nPath[] lower_bounds = RelaxationNPath(cost, g);
        
        //double lb = lower_bounds[NUM_VERTICI - 1].getCosto1(PARTENZA);
        //double lb = getCostoLBFromStati(lb_sol.stati_lb, cost);
        
        //sol.lowerBoundValue_stati = lb;
        log("Lower Bound, costi lagrangiani: " + lb_sol.stati_lb[NUM_VERTICI - 1].getCosto1(PARTENZA));
        //log("Lower Bound, costi originali: " + lb_sol.lowerBoundValue_stati);
         
        log("Gradi del grafo lower bound:");
        for(int i = 0; i < g.gradi_lb.length; i++)
        {
            log("--id: " + i + " grado = " + g.gradi_lb[i]);
        }                
        
        if(g.checkOttimo()) //ho trovato la soluzione ottima direttamente con il lower bound
        {
            log("Il lower bound ha trovato la soluzione ottima");
            sol.isOptimal = true;
        }
        else
        {
            sol.isOptimal = false;
        }

        LowerBoundSolution lbs = getCamminoLB(lb_sol.stati_lb);
        log("Percorso normale del grafo lower bound:");
        //int j = NUM_VERTICI-1;
        //log("--id: " + PARTENZA + ", costo = " + lower_bounds[j].getCosto1(PARTENZA));
        for(int i = 0; i < NUM_VERTICI; i++)
        {
            log("--id: " + lbs.cammino[i]);
            log("---costo archi= " + lbs.costo_archi[i]);
            log("---costo stati= " + lbs.costo_stati[i]);
        }
        log("--id: " + PARTENZA);
        //log("somma archi = " + lbs.lowerBoundValue_archi);
        
        //preparo vertici da disegnare
        sol.solution = new Vertex[NUM_VERTICI + 1];
        for(int i = 0; i < NUM_VERTICI; i++)
        {
            sol.solution[i] = vertici[lbs.cammino[i]];
        }
        sol.solution[NUM_VERTICI] = vertici[PARTENZA];
        sol.stati_lb = lb_sol.stati_lb;
        sol.lambda = lb_sol.lambda;
        sol.lowerBoundValue_archi = lb_sol.lowerBoundValue_archi;
        sol.lowerBoundValue_stati = lb_sol.lowerBoundValue_stati;
        return sol;        
    }
    
     
    //calcola un valido Lower Bound per il TSP utilizzando il rilassamento degli stati n-path
    //senza generare i loop di 2 vertici
    //Parametri:
    //--c matrice dei costi arco da vertice i a vertice j (viene usata la trasposta
        //per indicare il vertice di partenza nello stato, es:)
        //s = (3,i) è il lower bound dello stato di cardinalità 3 che parte dal vertice i
        //e arriva fino alla destinazione del tsp
    //--g = grafo dei vertici / archi
    public LowerBoundSolution getReverseFunctionLagrangiana(int[][] costi_originali, Grafo g) //basato su n_path
    {
        LowerBoundSolution lb_sol = new LowerBoundSolution();
//1) inizializzazione
        double[] lambda = new double[NUM_VERTICI]; 
        initArrayToZero(lambda, NUM_VERTICI);
        
        lb_sol.lambda = new double[NUM_VERTICI]; 
        initArrayToZero(lb_sol.lambda, NUM_VERTICI);
        
        double[][] costi_lagrangiani = copiaMatrice(costi_originali, NUM_VERTICI, NUM_VERTICI);
        
        double LB = Integer.MIN_VALUE; // -infinito        

        int cont_alfa = 0; //se faccio n interazioni senza aggiornare le penalità lagrangiane, cambio alfa
        
//2) ciclo ascent lagrangiano
        for(int i = 0; i < num_iterazioni_LB; i++) //fa 100 iterazioni per trovare le migliori penalità lagrangiane
        {            
            
            //controllo se devo decrementare alfa:
            if(cont_alfa == num_iterazioni_Alfa)
            {
                cont_alfa = 0;
                if(alfaStart > alfaMin) // è il valore minimo di alfa
                {
                    alfaStart *= 0.75; //prendo il 90% di alfa attuale
                }
            }
            
            lb_sol.stati_lb = RelaxationNPath(costi_lagrangiani, g);
            calcolaGradiVerticiLB(lb_sol.stati_lb, g);
            //calcolo LB, come il minimo fra la somma del costo dell'arco
            //dalla partenza al primo vertice, la reverse function con cardinalità NUM_VERTICI - 1
            //e la sommatoria delle penalità lagrangiane
            double LB_nuovo = getLB(costi_lagrangiani, lb_sol.stati_lb, lambda);
            
            //se il nuovo LB è > del vecchio, allora aggiorno le penalità lagrangiane ottime
            //e LB
            if(LB_nuovo > LB)
            {
                //aggiorna penalità lagrangiane lambda_star (src, dest, length)
                copiaVettore(lambda, lb_sol.lambda, NUM_VERTICI);
                LB = LB_nuovo;
                cont_alfa = 0;
                //controllo se ho trovato la soluzione ottima (cioè se tutti i gradi dei vertici sono = 2)
                calcolaGradiVerticiLB(lb_sol.stati_lb, g);
                if(g.checkOttimo())
                {
                    log("Debug: LowerBound = " + LB_nuovo + ", iterazione numero: " + (i+1));
                    //stampa gradi vertici e relativa penalità lagrangiana
                    for(int k = 0; k < NUM_VERTICI; k++)
                    {
                        log("--Vertice: " + k + ", gradi = " + g.gradi_lb[k] + ", lambda = " + lambda[k]);
                    }
                    log(" ");                    
                    return lb_sol;
                    //break;
                }                
            }           
            
            //aggiorno penalità lagrangiane
            updateLambda(lambda, NUM_VERTICI, LB_nuovo, g);
            //aggiorno matrice dei costi in base alle nuove penalità lagrangiane
            updateMatrice(costi_originali, costi_lagrangiani, lambda, NUM_VERTICI, NUM_VERTICI);
            
            cont_alfa++;
            
            //ogni n iterazioni stampa le informazioni del lower bound attuale.
            if(debugLB && (i % niter_debug == 0))
            {
                log("Debug: LowerBound Attuale = " + LB_nuovo + ", iterazione numero: " + i);
                //stampa alfa
                log("Debug: Alfa = " + alfaStart);
                //stampa bound best
                log("Debug: Best LowerBound = " + LB);
                //stampa somma gradi
                log("Debug: Somma gradi = " + g.getSommaGradi());
                //stampa somma lambda                
                log("Debug: Somma lambda = " + getSomma(lambda));
                log("--");
//stampa gradi vertici e relativa penalità lagrangiana
                for(int k = 0; k < NUM_VERTICI; k++)
                {
                    log("--Vertice: " + k + ", gradi = " + g.gradi_lb[k] + ", lambda = " + lambda[k]);
                }
                log(" ");
            }            
        }     
        
//3) calcola reverse function con le lambda_star migliori trovate        
        updateMatrice(costi_originali, costi_lagrangiani, lb_sol.lambda, NUM_VERTICI, NUM_VERTICI);
        lb_sol.stati_lb = RelaxationNPath(costi_lagrangiani, g);
        calcolaGradiVerticiLB(lb_sol.stati_lb, g);
        //debug: LB DIVERSO
        lb_sol.lowerBoundValue_archi = getCostoLBFromStati(lb_sol.stati_lb, costi_lagrangiani);
        lb_sol.lowerBoundValue_stati = getCostoLBFromStatiInt(lb_sol.stati_lb, cost); 
        return lb_sol;
    }        
 
    public double getSomma(double[] lambda)
    {
        double somma = 0;
        for(int i = 0;i < lambda.length; i++)
        {
            somma += lambda[i];
        }
        return somma;
    }
    
    //ricostruisco lower bound con costi lagrangiani
    public double getCostoLBFromStati(Stati_nPath[] stati, double[][] c)
    {
        LowerBoundSolution lbs = getCamminoLB(stati);
        double lb = 0;
        int i,j = 0;
        for(int v = 1; v < lbs.cammino.length; v++)
        {
            i = lbs.cammino[v - 1];
            j = lbs.cammino[v];
            //double ccc = c[i][j];
            lb += c[i][j];
        }
        i = lbs.cammino[lbs.cammino.length - 1];
        j = PARTENZA;
        lb +=  c[i][j];
        return lb;
        
    }
    
    //ricostruisco lower bound con costi originali
    public double getCostoLBFromStatiInt(Stati_nPath[] stati, int[][] c)
    {
        LowerBoundSolution lbs = getCamminoLB(stati);
        double lb = 0;
        int i,j = 0;
        for(int v = 1; v < lbs.cammino.length; v++)
        {
            i = lbs.cammino[v - 1];
            j = lbs.cammino[v];
            //double ccc = c[i][j];
            lb += c[i][j];
        }
        i = lbs.cammino[lbs.cammino.length - 1];
        j = PARTENZA;
        lb +=  c[i][j];
        return lb;
        
    }    

   public double getLB(double[][]c, Stati_nPath[] stati, double[] lambda)
    {
        //double[] lambda_sum = new double[NUM_VERTICI];
        //precalcolo la sommatoria delle penalità lagrangiane, 
        //(per ogni vertice tranne l'origine)
        //lambda_sum[i] = 0;
        double lambda_sum = 0;
        for(int i = 1; i < NUM_VERTICI; i++)
        {
            lambda_sum += lambda[i];
            /*
            for(int j = 1; j < NUM_VERTICI; j++)
            {
                //la sommatoria è 2*(lambda[j]) + lambda[i]
                lambda_sum[i] += (j != i) ? 2*lambda[j] : lambda[i];
            } 
                    */
        }
        lambda_sum *= 2;
        
        //calcolo il LB minimo da usare nell'ascent lagrangiano
        double min = Integer.MAX_VALUE;
        for(int i = 1; i < NUM_VERTICI; i++)
        {
            //cost sono i costi originali
            //double costo = c[PARTENZA][i] + stati[NUM_VERTICI - 2].getCosto1(i) + lambda_sum[i];
            double costo = c[PARTENZA][i] + stati[NUM_VERTICI - 2].getCosto1(i) + lambda_sum;
            if(costo < min)
            {
                min = costo;
            }
        }
        return min;
    }    

   
    //calcola un lower bound per il TSP utilizzando il rilassamento degli stati nPath, 
    //senza loop di 2 vertici, utilizzando il cammino inverso, quindi la matrice dei costi trasposta
    public Stati_nPath[] RelaxationNPath(double[][] c, Grafo g) //basato su n_path
    {
 
        Stati_nPath[] stati = new Stati_nPath[NUM_VERTICI]; //l'indice di questo vettore indica la cardinalità k        
        
        //INIZIALIZZAZIONE:
        //
        //f(PARTENZA, i) = c[i][PARTENZA],         
        stati[0] = new Stati_nPath(NUM_VERTICI);
        for(int i = 1; i < NUM_VERTICI; i++)
        {

            //stati[0].setMin1(i, c[PARTENZA][i]); //costi simmetrici
            stati[0].setMin1(i, c[i][PARTENZA]);
            stati[0].setPredecessore1(i, PARTENZA);
            stati[0].setMin2(i, INFINITE);
            stati[0].setPredecessore2(i, PARTENZA);                

        }
        
        //RICORSIONE FORWARD
        for(int k = 1; k < NUM_VERTICI - 1; k++) //k è la cardinalità
        {
            stati[k] = new Stati_nPath(NUM_VERTICI);
            for(int i = 1; i < NUM_VERTICI; i++) //i indica il vertice corrente
            {
                    //per ogni stato (k,i) calcolo il suo costo e il suo predecessore
                    double[] hi = new double[NUM_VERTICI];
                    List<Integer> loop2v = new ArrayList<Integer>();
                    for(int j = 1; j < NUM_VERTICI; j++) //j è il successore di i
                    {
                        if(i != j) //non si considera il vertice di partenza e l'arco da i a se stesso
                        {
                            if(stati[k - 1].getPredecessore1(j) != i)
                            {
                                //hi[j] = stati[k - 1].getCosto1(j) + c[j][i]; //costi simmetrici
                                hi[j] = stati[k - 1].getCosto1(j) + c[i][j];
                            }
                            else
                            {
                                //hi[j] = stati[k - 1].getCosto2(j) + c[j][i];    //costi simmetrici
                                hi[j] = stati[k - 1].getCosto2(j) + c[i][j];   
                                loop2v.add(j);
                            }
                        }
                    }                   
                    
                    double min1 = Double.MAX_VALUE;
                    double min2 = Double.MAX_VALUE;
                    int predecessore1 = -1;
                    int predecessore2 = -1;
                    //trovo minimo secondo il controllo del loop di 2 vertici
                    for(int j = 1; j < NUM_VERTICI; j++)
                    {
                        if(i != j)
                        {
                            if(hi[j] < min1)
                            {
                                min1 = hi[j];
                                predecessore1 = j;
                            }                            
                        }
                    }
                    //trovo secondo cammino minimo e predecessore
                    for(int j = 1; j < NUM_VERTICI; j++)
                    {
                        if(i != j && j != predecessore1)
                        {
                            if(hi[j] < min2)
                            {
                                min2 = hi[j];
                                predecessore2 = j;
                            }                            
                        }
                    }                    
                    
                    //se il predecessore di cammino minimo
                    //appartiene alla lista dei dei vertici tali per cui
                    //è stato preso il secondo cammino minimo
                    //per evitare un loop di 2 vertici, setta il flag a true
                    //così quando sono nello stato k-1 mi ricordo che devo prendere
                    //il predecessore2 per ricostruire il cammino del lower bound
                    if(loop2v.contains(predecessore1))
                    {
                        stati[k].setFlagPred1(i, true);
                    }
                    else
                    {
                        stati[k].setFlagPred1(i, false);
                    }
                    //stessa cosa per il predecessore2
                    if(loop2v.contains(predecessore2))
                    {
                        stati[k].setFlagPred2(i, true);
                    }
                    else
                    {
                        stati[k].setFlagPred2(i, false);
                    }                    
                    
                    //aggiungo stato al vettore
                    stati[k].setMin1(i, min1);
                    stati[k].setPredecessore1(i, predecessore1);
                    stati[k].setMin2(i, min2);
                    stati[k].setPredecessore2(i, predecessore2);                    
            }
        }
        
        //calcolo lower bound di z* (del TSP con |S|)
        //con il minimo fra la somma dei lower bounds degli stati con cardinalità |S| - 1
        //e vertice finale i
        //e il costo dell'arco dal vertice i al vertice di partenza
        //calcolo lower bound di z* (del TSP con |S|)
        //con il minimo fra la somma dei lower bounds degli stati con cardinalità |S| - 1
        //e vertice finale i
        //e il costo dell'arco dal vertice i al vertice di partenza   
        //(mi serve pèer sapere qual è l'ultimo vertice visitato dal cammino
        //del lower bound, non per il costo totale del LB)
        computeLastEdgeLB(stati, c);                
        
        return stati;
    }            

    public void computeLastEdgeLB(Stati_nPath[] stati, double[][] c)
    {
            //per ogni stato (k,i) calcolo il suo costo e il suo predecessore
            double min = Integer.MAX_VALUE;
            int predecessore = -1;            
            int k = NUM_VERTICI - 1;
            stati[k] = new Stati_nPath(NUM_VERTICI);
            
            for(int j = 1; j < NUM_VERTICI; j++) //j è il successore di i
            {
                double costo = stati[k - 1].getCosto1(j) + c[j][PARTENZA];
                if(costo < min)
                {
                    min = costo;
                    predecessore = j;                                
                }
            }
            
            //aggiungo stato al vettore
            stati[k].setMin1(PARTENZA, min);
            stati[k].setPredecessore1(PARTENZA, predecessore);  
            stati[k].setFlagPred1(PARTENZA, false);
            stati[k].setFlagPred2(PARTENZA, false);
    }    

    public void calcolaGradiVerticiLB(Stati_nPath[] stati, Grafo g)
    {      
        g.resetGradi();
        //aggiunge 2 grado al vertice di partenza (e destinazione)
        g.Add_Grado(PARTENZA);
        g.Add_Grado(PARTENZA);
        
        //ricostruisco vertici visitati nel lower bound e calcolo il grado per ogni vertice
        int i = PARTENZA;
        int pred = stati[NUM_VERTICI-1].getPredecessore1(i);

        boolean usato_pred1 = true;
        for(int k = NUM_VERTICI-2; k > 0; k--)
        {      
            g.Add_Grado(pred);
            g.Add_Grado(pred);
                int old_pred = pred;
                if(usato_pred1)
                {
                    if(stati[k + 1].getFlagPred1(i)) //se la flag è true, devo usare il predecessore2, perchè ho usato la g nel calcolo della f
                    {
                        //prendo il predecessore2
                        pred = stati[k].getPredecessore2(old_pred);
                        usato_pred1 = false;
                    }
                    else
                    {
                        pred = stati[k].getPredecessore1(old_pred);
                        usato_pred1 = true;
                    }                    
                }
                else //ho usato il predecessore2
                {
                    if(stati[k + 1].getFlagPred2(i)) //se la flag è true, devo usare il predecessore2, perchè ho usato la g nel calcolo della f
                    {
                        //prendo il predecessore2
                        pred = stati[k].getPredecessore2(old_pred);
                        usato_pred1 = false;
                    }
                    else
                    {
                        pred = stati[k].getPredecessore1(old_pred);
                        usato_pred1 = true;
                    }                    
                }
                i = old_pred; 
        }        
            g.Add_Grado(pred);
            g.Add_Grado(pred);        
        
    }         

    public LowerBoundSolution getCamminoLB(Stati_nPath[] stati)
    {
        int cont = 0;
        LowerBoundSolution lbs = new LowerBoundSolution();
        lbs.cammino = new int[NUM_VERTICI]; //la partenza non c'è alla fine
        //E' IL CONTRARIO, E' DA INVERTIRE L'ORDINE DEI VERTICI
        lbs.costo_archi = new double[NUM_VERTICI]; //costo ad ogni cardinalità del lowerbound
        lbs.costo_stati = new double[NUM_VERTICI]; //costo ad ogni cardinalità del lowerbound
        
//ricostruisco vertici visitati nel lower bound e calcolo il grado per ogni vertice
        int i = PARTENZA;
        int pred = stati[NUM_VERTICI-1].getPredecessore1(i);
        boolean usato_pred1 = true;
        lbs.cammino[cont++] = PARTENZA;    
        lbs.costo_archi[NUM_VERTICI-1] = cost[PARTENZA][pred];
        lbs.costo_stati[NUM_VERTICI-1] = stati[NUM_VERTICI - 1].getCosto1(i);
        lbs.lowerBoundValue_stati = lbs.costo_stati[NUM_VERTICI-1];
        double somma_archi = lbs.costo_archi[NUM_VERTICI-1];

        for(int k = NUM_VERTICI-2; k > 0; k--)
        {            
                lbs.cammino[cont++] = pred;
                int old_pred = pred;
                if(usato_pred1)
                {
                    if(stati[k + 1].getFlagPred1(i)) //se la flag è true, devo usare il predecessore2, perchè ho usato la g nel calcolo della f
                    {
                        //prendo il predecessore2
                        pred = stati[k].getPredecessore2(old_pred);
                        lbs.costo_stati[k] = stati[k].getCosto2(old_pred);
                        usato_pred1 = false;
                    }
                    else
                    {
                        pred = stati[k].getPredecessore1(old_pred);
                        lbs.costo_stati[k] = stati[k].getCosto1(old_pred);
                        usato_pred1 = true;
                    }                    
                }
                else //ho usato il predecessore2
                {
                    if(stati[k + 1].getFlagPred2(i)) //se la flag è true, devo usare il predecessore2, perchè ho usato la g nel calcolo della f
                    {
                        //prendo il predecessore2
                        pred = stati[k].getPredecessore2(old_pred);
                        lbs.costo_stati[k] = stati[k].getCosto2(old_pred);
                        usato_pred1 = false;
                    }
                    else
                    {
                        pred = stati[k].getPredecessore1(old_pred);
                        lbs.costo_stati[k] = stati[k].getCosto1(old_pred);
                        usato_pred1 = true;
                    }                    
                }
                i = old_pred;
                                        
                    lbs.costo_archi[k] = cost[old_pred][pred]; 
                    somma_archi += lbs.costo_archi[k];                
        }
        lbs.cammino[cont++] = pred;        
        lbs.costo_archi[0] = cost[pred][PARTENZA]; 
        lbs.costo_stati[0] = stati[0].getCosto1(pred);
        somma_archi += lbs.costo_archi[0];
        lbs.lowerBoundValue_archi = somma_archi;
        return lbs;
    }             
    
    public void updateLambda(double[] lambda, int length, double LB, Grafo g)
    {
        //precalcolo il denominatore
        double d = 0;
        for(int i = 1; i < NUM_VERTICI; i++)
        {
            d += Math.pow((g.gradi_lb[i] - 2), 2);
        }
        //d = Math.sqrt(d);
        //d /= 2;
        
        //aggiorno le penalità lagrangiane per ogni vertice, tranne l'origine
        for(int i = 1; i < NUM_VERTICI; i++)
        {
            double step = ( (alfaStart * (upperBound - LB) / d) * (g.gradi_lb[i] - 2));
            lambda[i] -= step;
        }
    }    
    
    public double[][] copiaMatrice(int[][] m, int rows, int columns)
    {
        double[][] c = new double[rows][columns];
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                c[i][j] = m[i][j];
            }
        }
        return c;
    }    
    
    public void updateMatrice(int[][] src, double[][] dest, double[] lambda, int rows, int columns)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                if(i != j) //non si modifica l'arco che va da un vertice a se stesso, rimane NULL_EDGE
                {
                    dest[i][j] = src[i][j] - lambda[i] - lambda[j];
                }                
            }
        }
    }    
    
    public void copiaVettore(double[] src, double[] dest, int length)
    {    
        for(int i = 0; i < length; i++)
        {
            dest[i] = src[i];
        }
    }
    
    public void copiaVettoreInt(int[] src, int[] dest, int length)
    {    
        for(int i = 0; i < length; i++)
        {
            dest[i] = src[i];
        }
    }    
    
    public void initArrayToZero(double[] v, int length)
    {
        for(int i = 0; i < length; i++)
        {
            v[i] = 0;
        }
    }    

    private void DrawSolution(JPanel p, Vertex[] vs)
    {
        
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try
        {
            Object partenza = graph.insertVertex(parent, null, vs[0].getID(),  vs[0].getX(),  vs[0].getY(),  VERTEX_WIDTH,  VERTEX_HEIGHT); 	                  
            Object succ = partenza;
            for(int i = 1; i < vs.length; i++)
            {
                Object v = graph.insertVertex(parent, null, vs[i].getID(),  vs[i].getX(),  vs[i].getY(),  VERTEX_WIDTH,  VERTEX_HEIGHT); 	                  
                graph.insertEdge(parent, null, "", v, succ);
                succ = v;
            }
        }
        finally
        {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);       
        
        p.removeAll();
        p.setLayout(new BorderLayout());
        p.add(graphComponent,BorderLayout.CENTER);
        p.revalidate();
        

    }
    
    
    //restituisce un upper bound al TSP, è un algoritmo greedy
    public TSP_Solution Nearest_Neighbour(Grafo g, int[][] c, int rows, int columns)
    {
        TSP_Solution nn_sol = new TSP_Solution();
        nn_sol.sol = new Vertex[NUM_VERTICI + 1];
        nn_sol.cammino = new int[NUM_VERTICI]; //non rimette l'origine alla fine
        
        boolean[] visited = new boolean[NUM_VERTICI];
        //all'inizio nessun vertice è visitato
        for(int i = 0; i < NUM_VERTICI; i++)
        {
            visited[i] = false;
        }
        
        //parto dal vertice di partenza
        int current_vertex = PARTENZA;
        nn_sol.sol[0] = vertici[PARTENZA];
        nn_sol.cammino[0] = PARTENZA;
        double upper_bound = 0;
        int next = 0;
        visited[PARTENZA] = true;
        
        int cont = 1;
        while(!All_Visited(visited)) //finchè non visita tuti i vertici continua
        {
            double min = Integer.MAX_VALUE;
            //trovo il vertice più vicino al vertice corrente che non è stato visitato
            for(int i = 0; i < NUM_VERTICI; i++)
            {
                if(c[current_vertex][i] < min && !visited[i])
                {
                    min = c[current_vertex][i];
                    next = i;
                }
            }
            current_vertex = next;
            nn_sol.sol[cont] = vertici[current_vertex];
            nn_sol.cammino[cont++] = current_vertex;
            upper_bound += min;            
            visited[current_vertex] = true;
        }
        
        //devo aggiungere il costo per tornare alla partenza
        upper_bound += c[current_vertex][PARTENZA];
        nn_sol.sol[cont] = vertici[PARTENZA];
        nn_sol.cost = upper_bound;
        
        return nn_sol; 
    }
    
    public boolean All_Visited(boolean[] visited)
    {
        for(Boolean b:visited)
        {
            if(b.booleanValue() == false)
            {
                return false;
            }
        }
        return true;
    }    
    
    //prende vertici in ordine e restituisce questa soluzione
    public TSP_Solution Naive1(int[][] c)
    {
        TSP_Solution sol = new TSP_Solution();
        sol.cammino = new int[NUM_VERTICI];
        sol.sol = new Vertex[NUM_VERTICI + 1];        
        double cost = 0;
        sol.cammino[0] = 0;
                
        for(int i = 1; i < NUM_VERTICI; i++)
        {
            sol.cammino[i] = i;
            cost += c[i-1][i];
        }
        cost += c[sol.cammino.length - 1][0];
        
        sol.cost = cost;
        sol.sol = VerticiDaCammino(sol.cammino);
        
        return sol;
    }
    
    //prende vertici a random
    public TSP_Solution Naive2(int[][] c, Random r)
    {
        TSP_Solution sol = new TSP_Solution();
        sol.cammino = new int[NUM_VERTICI];
        sol.sol = new Vertex[NUM_VERTICI + 1];        
        double cost = 0;
        sol.cammino[0] = 0;
                
        List<Integer> generati = new ArrayList<Integer>();
        int gen = 0;
        
        for(int i = 1; i < NUM_VERTICI; i++)
        {
            do
            {
                gen = r.nextInt(NUM_VERTICI - 1) + 1;
            }
            while(generati.contains(gen));
            sol.cammino[i] = gen;
            cost += c[i-1][i];
            generati.add(gen);
        }
        cost += c[sol.cammino.length - 1][0];
        
        sol.cost = cost;
        sol.sol = VerticiDaCammino(sol.cammino);
        
        return sol;
    }
    
    //3-opt, ricerca locale per migliorare la soluzione del nearest neighbour
    public TSP_Solution TriOttimale(int[][] cost, int rows, int columns, TSP_Solution sol_partenza, int num_iterazioni)
    {
        int length = sol_partenza.cammino.length;
        TSP_Solution sol = new TSP_Solution();
        sol.cammino = new int[length];
        copiaVettoreInt(sol_partenza.cammino, sol.cammino, length);
        sol.cost = sol_partenza.cost;
        
        boolean migliorata = false;
        
        //ripassa tutti gli archi per num_iterazioni, provando a migliiorare la sol col 3-opt        
        for(int i = 0; i < num_iterazioni; i++)
        {
            //primo ciclo, considera AB
            for(int a = 0; a < NUM_VERTICI - 3; a++)
            {
                //secondo ciclo, considera CD
                for(int c = a + 1; c < NUM_VERTICI - 2; c++)
                {
                    //terzo ciclo, considera EF
                    for(int e = c+1; e < NUM_VERTICI - 1; e++)
                    {
                        //provo tutte e 7 le combinazioni
                        for(int type = 1; type <= 7; type++)
                        {
                            int[] cammino_nuovo = change(sol.cammino, type, a, c, e);
                            double costo_nuovo = getCostoFromCammino(cammino_nuovo);
                            if(costo_nuovo < sol.cost)
                            {
                                sol.cost = costo_nuovo;
                                copiaVettoreInt(cammino_nuovo, sol.cammino, length);
                                migliorata = true;
                            }                              
                        }                  
                    }
                }
            }
            if(!migliorata)
            {
                break;
            }
            else
            {
                migliorata = false;
            }
        }
        
        //costruisce vertici dal cammino
        sol.sol = VerticiDaCammino(sol.cammino);
        
        return sol;
    }
    
    public Vertex[] VerticiDaCammino(int[] cammino)
    {
        Vertex[] v = new Vertex[NUM_VERTICI + 1];
        for(int i = 0; i < cammino.length; i++)
        {
            v[i] = vertici[cammino[i]];
        }
        v[NUM_VERTICI] = vertici[PARTENZA];
        return v;
    }
    
    private double getCostoFromCammino(int[] cammino)
    {
        double costo = 0;
        for(int i = 1; i < cammino.length; i++)
        {
            costo += cost[cammino[i-1]][cammino[i]];
        }
        //aggiungo ultimo costo
        costo += cost[cammino[cammino.length - 1]][PARTENZA];
        return costo;
    }
    
    private int[] change(int[] cammino, int type, int a, int c, int e)
    {
        int b = a+1;
        int d = c+1;
        int f = e+1;        
        int[] nuovo_cammino = new int[cammino.length];
        
        //il nuovo cammino contiene tutti i vertici fino ad A compreso
        int index = makeA(cammino, nuovo_cammino, a);
        
        switch(type)
        {
            case 1: //AB CD EF diventano AD EC BF
            {
                //prendo vertici da B a C compresi, invertiti
                Stack<Integer> bc = new Stack<Integer>();
                for(int i = b; i <= c; i++)
                {
                    bc.push(cammino[i]);
                }
                //inserisco vertici da D ad E compresi dopo A
                for(int i = d; i <= e; i++)
                {
                    nuovo_cammino[index++] = cammino[i];
                }
                //inserisco vertici da c a b compresi (bc invertiti)
                while(!bc.empty())
                {
                    nuovo_cammino[index++] = bc.pop();
                }
                break;
            }            
            case 2: //AB CD EF diventano AD EB CF
            {
                //taglio vertici da B a C compresi
                Queue<Integer> bc = new LinkedList<Integer>();
                for(int i = b; i <= c; i++)
                {
                    bc.add(cammino[i]);
                }
                //inserisco vertici da D ad E compresi dopo A
                for(int i = d; i <= e; i++)
                {
                    nuovo_cammino[index++] = cammino[i];
                }
                //inserisco vertici da B a C compresi
                while(!bc.isEmpty())
                {
                    nuovo_cammino[index++] = bc.remove();
                }
                break;
            }
            case 3: //AB CD EF diventano AB, CE, DF
            {
                //inverte i vertici da D ad E compresi
                Stack<Integer> de = new Stack<Integer>();
                for(int i = d; i <= e; i++)
                {
                    de.push(cammino[i]);
                }
                //copio vertici fino a C compreso
                for(; index <= c; index++) //l'index indicava già il prossimo elemento dopo A
                {
                    nuovo_cammino[index] = cammino[index];
                }
                //inserisco vertici da E a D
                while(!de.empty())
                {
                    nuovo_cammino[index++] = de.pop();
                }                
                break;
            }
            case 4: //AB CD EF diventano AE, DC, BF
            {
                //taglia i vertici da B ad E compresi e li inverte
                Stack<Integer> be = new Stack<Integer>();
                for(int i = b; i <= e; i++)
                {
                    be.push(cammino[i]);
                }
                //inserisce dopo A i vertici da E a B compresi
                while(!be.empty())
                {
                    nuovo_cammino[index++] = be.pop();
                }
                break;
            }
            case 5: //AB CD EF diventano AC, BD, EF
            {
                //taglia e inverte i vertici da B a C compresi                
                Stack<Integer> bc = new Stack<Integer>();
                for(int i = b; i <= c; i++)
                {
                    bc.push(cammino[i]);
                }     
                //scrivo vertici da C a B dopo A
                while(!bc.empty())
                {
                    nuovo_cammino[index++] = bc.pop();
                }
                //aggiungo i vertici da D ad E compresi com'erano prima                
                for(int i = d; i <= e; i++)
                {
                    nuovo_cammino[index++] = cammino[i];
                }
                break;
            }
            case 6: //AB CD EF diventano AC, BE, DF
            {
                //taglia e inverte da B a C compresi
                Stack<Integer> bc = new Stack<Integer>();
                for(int i = b; i <= c; i++)
                {
                    bc.push(cammino[i]);
                }
                //taglia e inverte i vertici da D ad E compresi
                Stack<Integer> de = new Stack<Integer>();
                for(int i = d; i <= e; i++)
                {
                    de.push(cammino[i]);
                }      
                //inserisco dopo A i vertici da C a B
                while(!bc.empty())
                {
                    nuovo_cammino[index++] = bc.pop();
                }            
                //inserisco dopo B i vertici da E a D
                while(!de.empty())
                {
                    nuovo_cammino[index++] = de.pop();
                }                
                break;
            }
            case 7: //AB CD EF diventano AE, DB, CF
            {
                //taglio vertici da B a C compresi
                Queue<Integer> bc = new LinkedList<Integer>();
                for(int i = b; i <= c; i++)
                {
                    bc.add(cammino[i]);
                }
                //taglia e inverte i vertici da D ad E compresi
                Stack<Integer> de = new Stack<Integer>();
                for(int i = d; i <= e; i++)
                {
                    de.push(cammino[i]);
                }     
                //inserisco dopo A i vertici da E a D
                while(!de.empty())
                {
                    nuovo_cammino[index++] = de.pop();
                }       
                //inserisco dopo D i vertici da B a C
                while(!bc.isEmpty())
                {
                    nuovo_cammino[index++] = bc.remove();
                }                
                break;
            }
            default:
            {
                break;
            }
        }
        
        //inserisco F e gli altri vertici rimanenti
        makeF(cammino, nuovo_cammino, f, index);        
        
        return nuovo_cammino;
    }
    
    //copia tutti i vertici dall'origine fino ad A compreso nel nuovo cammino
    //ritorna l'indice del prossimo elemento vuoto del cammino nuovo
    private int makeA(int[] originale, int[] nuovo, int a)
    {
        int index = 0;
        for(; index <= a; index++)
        {
            nuovo[index] = originale[index];
        }
        return index;
    }
    
    //copia i verti da F compreso fino alla fine, index è il prossimo elemento dove bisogna scrivere
    //nel nuovo cammino
    private void makeF(int[] originale, int[] nuovo, int f, int index)
    {
        for(int i = f; i < originale.length; i++)
        {
            nuovo[index++] = originale[i];
        }
    }
            
    private void log(String s)
    {
        //SwingUtilities.invokeLater(() -> TxtLog.setText(TxtLog.getText() + "\r\n" + s));
        buffer.write(s);
    }
    
    private void print(JLabel lbl, String s)
    {
        SwingUtilities.invokeLater(() -> lbl.setText(s));
    }    
    
}
