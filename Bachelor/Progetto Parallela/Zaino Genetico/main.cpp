/* 
 * File:   main.cpp
 * Author: luca.parisi2
 *
 * Created on June 4, 2014, 10:16 AM
 */


#include <cstdlib>
#include <mpi.h>
#include <vector>
#include <fstream>  //legge massimo peso zaino e cibi da file
#include <algorithm> //per la find(elemento esiste in vettore)

#ifdef WINDOWS
    #include <windows.h> //serve per lo sleep in windows
    #define TIME 1000 //millisecondi
#else //linux
    #include <unistd.h> //serve per lo sleep in linux
    #define TIME 1 //secondi
#endif 



using namespace std;

#define LOOP 300 //numero di iterazioni dell'algoritmo genetico per ogni processo

#define OFFSET_TAG 20 //offset per il tag della send, prima la size col my_id poi gli indici col tag my_id + offset_tag

//struct cibi:
struct cibo
{
    string nome;
    int volume;       
    int calorie;
    int id;    
};

int max_volume; //massimo volume dello zaino
vector<cibo> cibi;
vector< vector<cibo> > popolazione_old;
MPI_Comm slave_comm;
int size_procs;
int size_slave;
int cibi_size;



//signature Master:
void ReadParameters(); //legge massimo volume zaino e cibi da file "cibi"
bool Check();           //controlla se somma totale volume cibi > massimo volume zaino
void Crea_Comunicatore_Slave(int);
void SendRandomSeeds(); //master manda seed diversi agli slave per generare numeri random
void WaitAllResponses(); //master aspetta la risposta col cromosoma migliore di ogni slave
void Ricevi_Cromosomi(vector< vector<cibo> >&); //riceve da tutti gli slave il loro cromosoma migliore
void Stampa_Risultato(vector< vector<cibo> >&); //stampa a video il cromosoma di ogni slave

//signature Slave:
void Initialize(); //genera una prima popolazione formata da 8 cromosomi generati a random
int Fitness(vector<cibo>); //dato un cromosoma, restituisce il suo valore di fitness
void CalcolaFitness(vector< vector<cibo> >, vector<int>&); //restituisce il valore di fitness per ogni cromosoma della popolazione passata come input
void Find2Bigs(vector<int>, int, int*); //si passano vettore valori di fitness, size del vettore, ritorna vettore con 2 indici migliori della popolazione
void Find2Random(int*, int, int*); //size sono il numero di cibi del cromosoma, ritorna 2 cromosomi random da mantenere anche per la prossima popolazione
void Crossover(vector<cibo>, vector<cibo>, vector<cibo>&, vector<cibo>&); //ritorna i 2 cromosomi figli risultato del crossover fra i 2 cromosomi genitori
void Normalize(vector<cibo>&); //ritorna cromosoma che rispetta vincolo somma volumi < zaino e ogni cibo nello zaino è unico, non si ripete
void TrovaIndici(vector<cibo>, int* , int); //trova indici dei cibi del cromosoma nel vettore globale di tutti i cibi
void MPI_Send_Cromosoma(vector<cibo>,int, vector< vector<cibo> >&); //spedisce cromosoma e riceve altri cromosomi dagli altri slave in popolazione new (ultimo parametro)
void Ricostruisci_Cromosoma(vector<cibo>&, int*, int); //ricostruisce il cromosoma partendo dagli indici del vettore cibi
void Mutazione(vector<cibo>&); //fa la mutazione di un cromosoma, sostituisce un cibo a caso con un altro non presente nello zaino
void Sveglia_Master(int); //slave manda la sua sveglia al master
void Comunica_Cromosoma_Migliore(int); //slave spedisce al master il suo cromosoma migliore
void MPI_Send_Cromosoma(vector<cibo>&, int); //...chiamata dalla funzione prima...

//signature debug
void printPopolazione(int); //int = my_id del processo, stampa tutta la popolazione, cioè per ogni cromosoma stampa i cibi che contiene

int main(int argc, char** argv) {

    ReadParameters();

    if(!Check())
    {
        printf("ERRORE: i cibi stanno tutti dentro allo zaino, aggiungi più cibi al file\r\n");
        exit(1);
    }    
    
  int my_id, size;       
  
  MPI_Init (&argc, &argv);      /* starts MPI */
  MPI_Comm_rank (MPI_COMM_WORLD, &my_id);        /* get current process id */
  MPI_Comm_size (MPI_COMM_WORLD, &size);        /* get number of processes */    
  
  //variabili globali  
  size_procs = size;     //numero di processi slave + master
  size_slave = size - 1; //numero di processi slave
 
  //creo nuovo comunicatore che contiene solo i processi slave:
  Crea_Comunicatore_Slave(my_id);      

  if(my_id == 0) //master
  {
    //master:
    //il master assegna ai processi slave il loro seme utilizzato per la
    //generazione dei numeri random
      SendRandomSeeds();
               
     //il master fa un ciclo while e rimane in sleep finchè tutti i processi
     //slave non gli danno il loro cromosoma migliore
      WaitAllResponses();
      
      vector< vector<cibo> > best_cromosomi;
      Ricevi_Cromosomi(best_cromosomi);
                   
      //stampo cromosomi:
      Stampa_Risultato(best_cromosomi);

  }
  else //slave
  {
      MPI_Status tmp_status;
      //riceve dal master il seme per generare numeri casuali:
      int seme = 0;      
      MPI_Recv(&seme,1,MPI_INT,0,my_id,MPI_COMM_WORLD,&tmp_status);
      
      //inizializza numeri random per lo slave
      srand(seme);
      
      //fa l'inizializzazione della popolazione con 8 cromosomi random            
      Initialize();         
      
      //debug stampa popolazione random generata da ogni slave  
      //printPopolazione(my_id);      
      
      //inizio loop algoritmo genetico:
      for(int i = 0; i < LOOP; i++)
      {      
          const int popolazione_size = popolazione_old.size();
          vector<int> val_fitness;
          
          //per ogni cromosoma della popolazione, calcola la funzione di fitness 
          CalcolaFitness(popolazione_old, val_fitness);           
          
          //tra i cromosomi della popolazione ne sceglie 4 che sopravvivono
          //al prossimo loop, più quelli che emigrano dagli altri slave
          
          //trova i 2 cromosomi con il valore di fitness più alto (2 indici del vector popolazione)
          int bigTwo [2]; 
          Find2Bigs(val_fitness, popolazione_size, bigTwo);
          //prende altri 2 cromosomi random, diversi da quelli di prima (memorizza sempre gli indici)
          int ranTwo [2];
          Find2Random(bigTwo, popolazione_size, ranTwo);
          
            //a = cromosoma migliore (valore massimo funzione di fitness) = popolazione.at(bigTwo[0])
            //b = secondo cromosoma migliore                              = popolazione.at(bigTwo[1])
            //c = random scelto tra gli altri disponibili                 = popolazione.at(ranTwo[0])
            //d = random scelto tra gli altri disponibili                 = popolazione.at(ranTwo[1])

            //crossover1 = prima metà del cromosoma 1 + seconda metà del cromosoma2
            //crossover2 = prima metà del cromosoma2 + seconda metà del cromosoma1
          
            //fai crossover:
            //e1 = a (crosover 1) c
            //e2 = a (crosover 2) c
            //f1 = b (crossover1) d
            //f2 = b (crossover2) d          
          

          
          vector<cibo> cromosoma_e1;
          vector<cibo> cromosoma_e2;
          vector<cibo> cromosoma_f1;
          vector<cibo> cromosoma_f2;                  
                    
          Crossover(popolazione_old.at(bigTwo[0]), popolazione_old.at(ranTwo[0]), cromosoma_e1, cromosoma_e2);
          Crossover(popolazione_old.at(bigTwo[1]), popolazione_old.at(ranTwo[1]), cromosoma_f1, cromosoma_f2);

          //nuova popolazione
          vector< vector<cibo> > popolazione_new;
                              
                   
          //spedisco agli altri slave il mio cromosoma migliore (senza considerare i nuovi cromosomi trovati con il crossover))
          vector<cibo> cromosoma_da_spedire = popolazione_old.at(bigTwo[0]);
          
          //spedisce agli altri slave il cromosoma migliore
          //e riceve cromosomi degli altri slave nel vettore della nuova popolazione
          MPI_Send_Cromosoma(cromosoma_da_spedire, my_id, popolazione_new);
                    
        //fa la mutazione del suo cromosoma migliore (prima di fare il crossover)
        Mutazione(popolazione_old.at(bigTwo[0]));        

        
          //aggiungo i 2 big e i 2 random alla nuova popolazione
         //formata per adesso solo dai cromosomi emigrati dagli altri slave
          popolazione_new.push_back(popolazione_old.at(bigTwo[0]));
          popolazione_new.push_back(popolazione_old.at(bigTwo[1]));
          popolazione_new.push_back(popolazione_old.at(ranTwo[0]));
          popolazione_new.push_back(popolazione_old.at(ranTwo[1]));
          
          popolazione_new.push_back(cromosoma_e1);
          popolazione_new.push_back(cromosoma_e2);
          popolazione_new.push_back(cromosoma_f1);
          popolazione_new.push_back(cromosoma_f2);        
        
        //scope per deallocare memoria di popolazione old
        {
            //rilascio memoria occupata dalla popolazione vecchia
            //cambia riferimento globale della popolazione old
            //con quello di un vettore temporaneo vuoto            
            vector< vector<cibo> >().swap(popolazione_old);             
        //quando esco da questo scope, il vettore temporaneo viene eliminato
        //quindi tutta la memoria referenziata da popolazione_old viene liberata
        }
          
        //nuova popolazione, cambia riferimento della popolazione new con questo
        popolazione_old.swap(popolazione_new);
        //la memoria puntata dalla popolazione_new viene deallocata quando il for reincomincia
        //perchè la dichiarazione della popolazione new è dentro al ciclo for
      }    
            
      //da la sveglia al master che nel frattempo dorme e aspetta le risposte degli slave
      Sveglia_Master(my_id);
                
      //comunica al master il cromosoma migliore
      //la popolazione è un vector globale
      Comunica_Cromosoma_Migliore(my_id);
      
  }          
  
  MPI_Finalize();
  
  return 0;
}

//legge dal file cibi (contenuto nella stessa cartella dell'eseguibile) l'elenco dei cibi disponibili e il massimo volume dello zaino
void ReadParameters()
{
    fstream file;
    string line;
    string line2;
    int valore;
    char enter;
    file.open("cibi");
    if(file.is_open())
    {
        //leggo volume massimo zaino:
        getline(file,line,',');  //legge stringa, non mi serve
        getline(file,line,','); //legge valore intero
        max_volume = atoi(line.c_str());
        //evito invio
        enter = file.get();         
        //leggo tutti i cibi
        while(file.good())
        {
            cibo c;                                    
            getline(file,line,','); //identificativo
            c.id = atoi(line.c_str());            
            getline(file,c.nome,','); //nome cibo           
            getline(file,line,','); //calorie
            c.calorie = atoi(line.c_str());
            getline(file,line,','); //volume
            c.volume = atoi(line.c_str());            
            cibi.push_back(c);
            //evito invio
            enter = file.get();       
        }    
        file.close();
    }
    cibi_size = cibi.size();    
}

//controlla se la somma dei volumi di tutti i cibi letti è < max volume zaino
/* Parametri:
 * 
 * output:
 * --true se il volume totale dei cibi è > del volume massimo dello zaino
 * --false altrimenti, non avrebbe senso applicare l'algoritmo di massimizzazione se tutti i cibi stanno dentro allo zaino
 */
bool Check()
{
    int somma_volumi = 0;
    int cont = cibi.size();   
    for(int i = 0; i < cont; i++)
    {
        somma_volumi += cibi.at(i).volume;
    }
    
    if(somma_volumi < max_volume)
    {
        return false;
    }
        
    return true;
}

/*
 * Crea il nuovo comunicatore MPI formato solo dai processi slave
 * Parametri:
 * 
 * Input:
 * int my_id = identificativo del processo (master o slave)
 */
void Crea_Comunicatore_Slave(int my_id)
{
  int color = 0;    
    if(my_id != 0) //se è processo slave appartengono al color 1    
    {
        color = 1;
    }
    MPI_Comm_split(MPI_COMM_WORLD,color,my_id,&slave_comm); //definisco nuovo comunicatore    
}

//Master spedisce agli slave il loro seme per la generazione di numeri random
void SendRandomSeeds()
{
    // initialize random seed: 
    srand (time(NULL));      
    int seeds [size_slave];
    for(int i = 0; i < size_slave; i++)
    {
        seeds[i] = rand();
        //trasmette seme allo slave i + 1 (gli slave partono da 1, il master è 0)
        MPI_Send(&seeds[i], 1, MPI_INT, i + 1, i+1, MPI_COMM_WORLD);
    }     
}

//Master va in sleep finchè gli slave non riceve tutte le sveglie dagli slave
void WaitAllResponses()
{
    MPI_Status tmp_status;
    
    for(int i = 0; i < size_slave; i++)
    {                
        int flag = 0;
        int current_slave = i + 1;
        int ccc = 0;
        
        MPI_Iprobe(current_slave,current_slave,MPI_COMM_WORLD,&flag,&tmp_status); 
        if(flag) //non dormo neanche
        {
            printf("\r\nMaster: ricevo sveglia da slave: %d\r\n", current_slave);
            MPI_Recv(&ccc,1,MPI_INT,current_slave,current_slave,MPI_COMM_WORLD,&tmp_status);            
            continue;
        }
        //dorme finchè non gli arriva la sveglia dal current slave
        do
        {
            //sleep
            printf("\r\nMaster: dormo\r\n");
            sleep(TIME); //1 secondo   
            MPI_Iprobe(current_slave,current_slave,MPI_COMM_WORLD,&flag,&tmp_status); 
        }    
        while(!flag);
        printf("\r\nMaster: ricevo sveglia da slave: %d\r\n", current_slave);        
        MPI_Recv(&ccc,1,MPI_INT,current_slave,current_slave,MPI_COMM_WORLD,&tmp_status);            
    }    
    
      printf("\r\nMaster: Mi sono svegliato sono pronto per ricevere i cromosomi migliori dai miei slave\r\n");    
    
}

//crea una prima popolazione random, scegliendo che cosa mettere dentro allo zaino
//prendendo cibi a random finchè il volume totale non supera quello dello zaino
void Initialize()
{
    //creo numero random tra 0 e num. elementi del vettore cibi.
    //poi aggiungo il cibo al i-esimo cromosoma
    //finchè la somma totale dei volumi < max volume zaino
    //N.B.i numeri random sono tutti diversi, non si può inserire 2 volte lo stesso cibo
    
    //genera 8 cromosomi ranom
    for(int i = 0; i < 8; i++)
    {
        vector<int> generati; //contiene numeri random già estratti
        int somma_volumi = 0;
        vector<cibo> cromosoma;
        do
        {
            //genera indice finchè non ne pesca uno diverso da quelli già generati
            int indice = 0;
            do
            {
                indice = rand() % cibi.size(); //numero tra 0 e cibi.size() - 1
            }
            while(find(generati.begin(), generati.end(), indice) != generati.end());                
            somma_volumi += cibi.at(indice).volume;
            if(somma_volumi < max_volume)
            {
                //aggiungo cibo all'indice nell'attuale cromosoma
                cromosoma.push_back(cibi.at(indice));
                //aggiungo indice ai numero random già generati
                generati.push_back(indice);                 
            }
            else
            {
                break;
            }    
        }
        while(1);        
        popolazione_old.push_back(cromosoma);
    }    
}

//LA FUNZIONE DI FITNESS tiene conto del numero dei cibi e delle calorie
//secondo la seguente funzione:
//
//fitness = somma(calorie) * num cibi cromosoma / num cibi totali letti da file
//
//quindi un cromosoma è meglio se ha la funzione di fitness più alta degli altri.
int Fitness(vector<cibo> cromosoma)
{
    const int cromosoma_size = cromosoma.size(); //numero cibi del cromosoma
    //const int cibi_size = cibi.size();          //nmero totale dei cibi a disposizione
    int calorie = 0;
    //somma calorie
    for(int i = 0; i < cromosoma_size; i++)
    {
        calorie += cromosoma.at(i).calorie;
    }    
    //calcolo fitness
    return calorie * cromosoma_size / cibi_size;
}

//data una popolazione, calcola il fitness di ogni cromosoma
void CalcolaFitness(vector< vector<cibo> > popolazione, vector<int>& val_fitness)
{
    const int popolazione_size = popolazione.size();
    //per ogni cromosoma della popolazione, calcola la funzione di fitness 
    for(int k = 0; k < popolazione_size; k++)
    {
        vector<cibo> cromosoma = popolazione.at(k);
        val_fitness.push_back(Fitness(cromosoma));
    }    
}

//restituisce indici del vettore con valore di fitness più grande
//output[0] = valore massimo
//output[1] = secondo valore più grande
void Find2Bigs(vector<int> val_fitness, int size, int* output)
{        
    int big = 0;
    int big2 = 0;
    
    //trova massimo
    for(int i = 0; i < size; i++)
    {
        //per il massimo
        if(val_fitness.at(i) > big)
        {
            //setta il massimo
            big = val_fitness.at(i);
            output[0] = i;
        }    
    }

    //trova secondo massimo
    for(int i = 0; i < size; i++)
    {
        //per il secondo massimo
        if(val_fitness.at(i) > big2 && val_fitness.at(i) != big)
        {
            //setta il massimo
            big2 = val_fitness.at(i);
            output[1] = i;
        }     
    }    
    
    return;
}

//restituisce 2 indici del vettore fitness random, diversi dal valore di fitness più grande
//output[0] = primo random
//output[i] = secondo random
void Find2Random(int* bigTwo, int size, int* output) //size sono il numero di cibi del cromosoma
{        
    int ran1 = 0;
    int ran2 = 0;
    
    //primo random
    do
    {
        ran1 = rand() % size;
    }    
    //finchè l'indice random generato è uguale ad uno dei 2 più grani già presi
    //estrai un altro numero random
    while(ran1 == bigTwo[0] || ran1 == bigTwo[1]);          
    
    //secondo random
    do
    {
        ran2 = rand() % size;
    }    
    //finchè l'indice random generato è uguale ad uno dei 2 più grani già presi
    //estrai un altro numero random
    while(ran2 == bigTwo[0] || ran2 == bigTwo[1] || ran2 == ran1);      
    
    output[0] = ran1;
    output[1] = ran2;
    
    return;
}

//crea 2 figli:
//il primo figlio prende la prima meta dei geni del primo cromosoma e la seconda metà del secondo
//il secondi figlio il contrario
void Crossover(vector<cibo> cromosoma_a, vector<cibo> cromosoma_b, vector<cibo>& figlio1, vector<cibo>& figlio2)
{   
    int size_a = cromosoma_a.size();
    int size_b = cromosoma_b.size();
    
    //creo primo figlio:
    for(int i = 0; i < size_a / 2; i++)
    {
        figlio1.push_back(cromosoma_a.at(i));
    }    
    for(int i = size_b / 2; i < size_b; i++)
    {
        figlio1.push_back(cromosoma_b.at(i));
    }

    //creo secondo figlio:
    for(int i = 0; i < size_b / 2; i++)
    {
        figlio2.push_back(cromosoma_b.at(i));
    }     
    for(int i = size_a / 2; i < size_a; i++)
    {
        figlio2.push_back(cromosoma_a.at(i));
    }       
           
    //check e modifica dei 2 cromosomi, se non rispettano più i vincoli
    //es somma volume cibi > volume zaino
    //cava cibi dallo zaino finchè il volume non torna < dello zaino
    Normalize(figlio1);
    Normalize(figlio2);
    
    return;
}

//normalizza cromosoma:
//se ha 2 volte lo stesso cibo lo cava
//e se ha troppo cibi ne cava qualcuno
void Normalize(vector<cibo>& cromosoma)
{        
    //controllo se uno stesso cibo c'è 2 volte, in questo caso lo elimino
    for(int i = 0; i < cromosoma.size(); i++)
    {
        cibo da_controllare = cromosoma.at(i);
        //controllo se l'id di questo cibo si ripete
        for(int k = 0; k < cromosoma.size(); k++)
        {
            //non controllo se stesso, solo gli altri cibi
            if(k != i && da_controllare.id == cromosoma.at(k).id)
            {
                //cancello cibo k-esimo
                cromosoma.erase(cromosoma.begin() + k);
            }    
        }    
    }    
    
    int cromosoma_size = cromosoma.size();
    int somma_volumi = 0;        
    //calcola somma volumi
    for(int i = 0; i < cromosoma_size; i++)
    {
        somma_volumi += cromosoma.at(i).volume;
    }    
    if(somma_volumi > max_volume)
    {
        //cavo a random i cibi finchè il volume non torna < dello zaino        
        do
        {
            int indice = rand() % cromosoma_size; //fra 0 e size - 1
            somma_volumi -= cromosoma.at(indice).volume;
            cromosoma.erase(cromosoma.begin() + indice);
            cromosoma_size--;             
        }
        while(somma_volumi > max_volume);
    }
    return;
}

//dato un cromosoma, trova, per ogni cibo, l'indice del suo corrispettivo nel vettore che contiene i cibi totali
void TrovaIndici(vector<cibo> cromosoma, int* indici, int length)
{
    //int cibi_size = cibi.size();
    //per ogni cibo del cromosoma da spedire, 
    //trova l'indice del vettore cibi
    for(int ind = 0; ind < length; ind++)
    {
        cibo c = cromosoma.at(ind);
        //trova c in cibi
        for(int k = 0; k < cibi_size; k++)
        {
            cibo tmpc = cibi.at(k);
            if(c.id == tmpc.id)
            {
                //salvo indice del cibo del cromosomada spedire che fa riferimento allo stesso cibo nel vettore cibi
                indici[ind] = k;
                break;
            }    
        }    
    }    
}

//spedisce il cromosoma a tutti gli altri slave
void MPI_Send_Cromosoma(vector<cibo> cromosoma, int my_id, vector< vector<cibo> >& popolazione_new)
{
        int length = cromosoma.size();          
        //indici del vettore
        int indici [length];
        TrovaIndici(cromosoma, indici, length);   

        //ricevo size del vettore degli indici (fanno riferimento al vector cibi)
        int rec_size [size_slave];
        MPI_Allgather (&length,1,MPI_INT,rec_size,1,MPI_INT,slave_comm);

        //calcolo displs, recv_counts per ogni processo e somma size totale
          int tmp = 0;
          int displs [size_slave];
          int rec_count[size_slave];
          //per ogni processo
          for(int ind = 0; ind < size_slave; ind++)
          {
               displs[ind] = tmp; //offset a partire da 0 per salvare int del processo nel buffer di ricezione
               tmp += rec_size[ind]; //serve per la somma finale per allocare il buffer di ricezione
               rec_count[ind] = rec_size[ind]; //int che il processo i speisce
          }    
          const int somma = tmp;
          int rec_buf [somma];          

      /*
      int MPI_Allgatherv(void * sendbuff, int sendcount, MPI_Datatype sendtype, 
                 void * recvbuf, int * recvcounts, int * displs, 
                 MPI_Datatype recvtype, MPI_Comm comm)
      */            
      MPI_Allgatherv(indici, length, MPI_INT, 
                 rec_buf, rec_count, displs, 
                 MPI_INT, slave_comm);
        
        
      
          //per ogni processo con id != dall'attuale
    //ricostruisce cromosoma ricevuto dagli altri processi
    //e lo aggiunge alla popolazione
    for(int ind = 0; ind < size_slave; ind++)
    {
        int id_slave = ind + 1;
        if(id_slave != my_id)
        {
            //ricostruisce cromosoma
            vector<cibo> cromosoma_ricostruito;
            
            
            int to_read = rec_count[ind];
            for(int kkk = 0; kkk < to_read; kkk++)
            {
                int offset = displs[ind];
                int index = rec_buf[offset + kkk];
                cibo c = cibi.at(index);
                cromosoma_ricostruito.push_back(c);
            } 
            
            //aggiungo il cromosoma dello slave ind alla mia popolazione
            popolazione_new.push_back(cromosoma_ricostruito);
        }   
    } 
      
        
}

void Ricostruisci_Cromosoma(vector<cibo>& cromosoma_ricostruito, int* indici, int length)
{
    //ricostruisce cromosoma    
    for(int i = 0; i < length; i++)
    {
        cibo c = cibi.at(indici[i]);
        cromosoma_ricostruito.push_back(c);
    }    
}

//fa lamutazione di un cromosoma, modificando a caso un cibo con un altro
//poi lo si rinormalizza per il vincolo volume cibi < volume zaino
void Mutazione(vector<cibo>& cromosoma)
{
    //estraggo cibo a random:
    int size = cromosoma.size();
    //indice del cibo da estrarre
    int indice = rand() % size; //tra 0 e size - 1
    //prendo cibo nuovo, scorro tutto il vettore dei cibi
    //finchè non trovo il primo disponibile che non è già presente nello zaino
    cibo nuovo;
    //int cibi_size = cibi.size();
    for(int i = 0; i < cibi_size; i++)
    {
        cibo current = cibi.at(i);        
        //controllo se cibo current p già nello zaino
        bool available = true;
        for(int k = 0; k < size; k++)
        {
            cibo zaino = cromosoma.at(k);            
            if(zaino.id == current.id)
            {
                //questo cibo current è già dentro allo zaino
                //prendo il successivo
                available = false;
                break;
            }    
        }
        if(available)
        {
            //il cibo current non è nello zaino, sostituisco il cibo estratto prima
            //con questo
            cromosoma.erase(cromosoma.begin() + indice);
            cromosoma.push_back(current);
            //normalizza cromosoma perfargli avere una soluzione accettabile
            //controlla vincoli di volume
            Normalize(cromosoma);
            return;
        }    
    }        
}

void Sveglia_Master(int my_id)
{
      //sveglia master:
      //trasmette risposta fine lavoro al master
      int ccc = 1;
      //MPI_Send(&ccc, 1, MPI_INT, 0, my_id, MPI_COMM_WORLD);
      //ho fatto l'ISENT così il processo può terminare completament, il master poi lo leggerà il suo messaggio
      //MPI_Isend(&ccc, 1, MPI_INT, 0, my_id, MPI_COMM_WORLD, &send_reqs[my_id - 1]);
      
      for(int ind = 0; ind < size_slave; ind++)
      {
          int id_slave = ind + 1;
          if(id_slave == my_id)
          {
            MPI_Send(&ccc, 1, MPI_INT, 0, my_id, MPI_COMM_WORLD);
            printf("sono lo slave %d e Send eseguita \r\n", my_id);              
          }    
        //per svegliarsi il master deve aspettare tutte le risposte dagli slave
        MPI_Barrier(slave_comm);          
      }    
    
}

void Comunica_Cromosoma_Migliore(int my_id)
{
    const int popolazione_size = popolazione_old.size();
    vector<int> val_fitness;
          
    CalcolaFitness(popolazione_old, val_fitness);
      
    int bigTwo[2]; //[0] è il massimo
    Find2Bigs(val_fitness, popolazione_size, bigTwo);
      
    vector<cibo> cromosoma_max = popolazione_old.at(bigTwo[0]);
      
    //il master si ricostruirà il cromosoma, quindi spedisco solo gli indici dei cibi che fanno parte del cromosoma
    MPI_Send_Cromosoma(cromosoma_max, my_id);  
    
    
}

void MPI_Send_Cromosoma(vector<cibo>& cromosoma_max, int my_id)
{
      //scompone cromosoma in indici cibi
      int length = cromosoma_max.size();          
        //indici del vettore
        int indici [length];
        TrovaIndici(cromosoma_max, indici, length);
        
        //spedisco al master la size del vettore, così sa quanti int ricevere
        //MPI_Send(void * buffer, count, MPI_TYPE, dest, tag, MPI_COMM_WORLD);              
        
      for(int i = 0; i < size_slave; i++)
      {
          int id_slave = i + 1;
          if(my_id == id_slave)
          {
                MPI_Send(&length, 1, MPI_INT, 0, my_id, MPI_COMM_WORLD);
              //per svegliarsi il master deve aspettare tutte le risposte dagli slave              
          }    
          MPI_Barrier(slave_comm);          
      }    
    
      for(int i = 0; i < size_slave; i++)
      {
          int id_slave = i + 1;
          if(my_id == id_slave)
          {
            //spedisco al master vettore di indici
            //cambia il tag: my_id + 20
            MPI_Send(indici, length, MPI_INT, 0, my_id + OFFSET_TAG, MPI_COMM_WORLD);  

              //per svegliarsi il master deve aspettare tutte le risposte dagli slave              
          }    
          MPI_Barrier(slave_comm);          
      }        
        
}

void Ricevi_Cromosomi(vector< vector<cibo> >& best_cromosomi)
{
    MPI_Status tmp_status;
      int cromosomi_size [size_slave];
      //per ogni slave, ricevo size indici che mi spediscono
      for(int i = 0; i < size_slave; i++)
      {
          //salva la size nell'indice dello slave giusto
          //MPI_Recv(cromosomi_size + i - 1, 1, MPI_INT, i, i, MPI_COMM_WORLD,&tmp_status);          
          int id_slave = i + 1; //tag = id slave
          MPI_Recv(cromosomi_size + i, 1, MPI_INT, id_slave, id_slave, MPI_COMM_WORLD,&tmp_status);          
      }
      
      //vector< vector<cibo> > best_cromosomi;  
      //per ogni slave, leggo indici, ricostruisco cromosoma e lo salvo nella lista dei Best
      for(int i = 0; i < size_slave; i++)
      {
          
          int id_slave = i + 1;
          
          //alloco vettore di indici della size letta prima per lo stesso slave
          const int size_cromosoma_slave = cromosomi_size [i];
          int indici [size_cromosoma_slave]; 
          MPI_Recv(indici, size_cromosoma_slave, MPI_INT, id_slave, id_slave + OFFSET_TAG, MPI_COMM_WORLD,&tmp_status);                    
          
          //ricostruisco cromosoma
          vector<cibo> cromosoma_ricostruito;
          Ricostruisci_Cromosoma(cromosoma_ricostruito, indici, size_cromosoma_slave);
          best_cromosomi.push_back(cromosoma_ricostruito);
      }
      
}

void Stampa_Risultato(vector< vector<cibo> >& best_cromosomi)
{
    printf("Master: volume zaino = %d\r\n\r\n",max_volume);
    const int best_cromosomi_size = best_cromosomi.size();
    for(int i = 0; i < best_cromosomi_size; i++)
    {
        vector<cibo> cromosoma = best_cromosomi.at(i);
        int fit = Fitness(cromosoma);          
        printf("Master: cromosoma %d fitness %d slave %d:\r\n",i,fit, i );          
        const int cromosoma_size = cromosoma.size();
        for(int k = 0; k < cromosoma_size; k++)
        {
            cibo c = cromosoma.at(k);
            printf("--cibo %s volume %d calorie %d\r\n",c.nome.c_str(), c.volume, c.calorie);
        }    
        printf("\r\n");
    }    
}

//debug stampa popolazione di uno slave
void printPopolazione(int my_id)
{
      const int popolazione_size = popolazione_old.size();
      for(int i = 0; i < popolazione_size; i++)
      {
          vector<cibo> cromosoma = popolazione_old.at(i);
          int fit = Fitness(cromosoma);          
          printf("cromosoma %d fitness %d processo %d:\r\n",i,fit, my_id);          
          const int cromosoma_size = cromosoma.size();
          for(int k = 0; k < cromosoma_size; k++)
          {
              cibo c = cromosoma.at(k);
              printf("--cibo %s volume %d processo %d\r\n",c.nome.c_str(), c.volume, my_id);
          }    
          printf("\r\n");
      }    
}


