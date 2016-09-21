/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

/**
 *
 * @author luca
 */
public class Stati_nPath {
    
    
    private class Stato_nPath
    {
        double costo1; //costo cammino minimo che finisce nel vertice end, indice del bucket
        int predecessore1; //vertice successore (di arrivo) nello stato attuale (per la reverse function)
        double costo2;//costo cammino minimo che finisce nel vertice end, con predecessore diverso dal primo
        int predecessore2;//vertice successore (di arrivo) nello stato attuale (per la reverse function), del secondo cammino minimo
        boolean flag_pred1; //se true significa che il costo di cammino minimo è stato calcolato prendendo il secondo costo di cammino minimo del predecessore1, false il minimo
        boolean flag_pred2; //se true significa che il secodno costo di cammino minimo è stato calcolato prendendo il secondo costo di cammino minimo del predecessore2, false il minimo
    }
    
    //l'indice i del bucket indica il vertice di partenza dello stato nPath di cardinalità k
    //il valore dell'elemento indica il suo costo f(k,i)
    Stato_nPath[] bucket;
    
    public Stati_nPath(int num_vertici)
    {
        bucket = new Stato_nPath[num_vertici];
        for(int i = 0; i < num_vertici; i++)
        {
            bucket[i] = new Stato_nPath();
        }
    }
    
    //end = vertice finale
    //setta f(k,end) = costo
    public void setMin1(int start, double costo)
    {
        bucket[start].costo1 = costo;
    }
    
    public double getCosto1(int start)
    {
        return bucket[start].costo1;
    }
       
    public int getPredecessore1(int start)
    {
        return bucket[start].predecessore1;
    }
        
    public void setPredecessore1(int start, int predecessore)
    {
        bucket[start].predecessore1 = predecessore;
    }
    
    //end = vertice finale
    //setta f(k,end) = costo
    public void setMin2(int start, double costo)
    {
        bucket[start].costo2 = costo;
    }
    
    public double getCosto2(int start)
    {
        return bucket[start].costo2;
    }
       
    public int getPredecessore2(int start)
    {
        return bucket[start].predecessore2;
    }
        
    public void setPredecessore2(int start, int predecessore)
    {
        bucket[start].predecessore2 = predecessore;
    }    
    
    public boolean getFlagPred1(int start)
    {
        return bucket[start].flag_pred1;
    }
        
    public void setFlagPred1(int start, boolean flag)
    {
        bucket[start].flag_pred1 = flag;
    }        
    
    public boolean getFlagPred2(int start)
    {
        return bucket[start].flag_pred2;
    }
        
    public void setFlagPred2(int start, boolean flag)
    {
        bucket[start].flag_pred2 = flag;
    }            
        
}
