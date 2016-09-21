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
public class Stato {
    
    int end;          //vertice di arrivo 
    int costo;              //f(S,i), il costo dello stato
    double completamento; //costo + completamento, serve per ordinare gli stati nella variante con il tetto di stati
    Stato predecessore; //predecessore di questo stato
    long id; //id dello stato, univoco per tutti gli stati, serve per contare gli stati totali
    
    long set; //maschera che indica quali vertici contiene il set di questo stato
    //il bit in posizione j se è 1 significa che il vertice j è contenuto nel set, se è 0 no
    
    int num_vertici;
    
    public Stato(int num_vertici)
    {
        set = 0;
        this.num_vertici = num_vertici;
    }
    
    public void addVertice(int v)
    {
        set += (1L << v); //l'id del vertice è l'iesimo bit a partire da destra
    }
    
    public void setSet(long set)
    {
        this.set = set;
    }
    
    public boolean setContains(int v)
    {
        //return set[v];
        //return set.get(v);
        long flag = (1L << v); //bit da controllare se è a 1
        return (set & flag) == flag;
    }   
    
    public double sommaPenalita(double[] lambda)
    {
        double somma = 0;
        //per ogni vertice, se non appartiene al set, somma le penalità lagrangiane
        for(int i = 0; i < num_vertici; i++)
        {
            if(!setContains(i))
            {
                somma += (lambda[i] * 2);
            }
        }
        return somma;
    }
    
        //serve per definire la equals
    @Override
    public boolean equals(Object other) 
    {
        if (!(other instanceof Stato)) 
        {
            return false;
        }

        Stato toCheck = (Stato)other;

        // Custom equality check here.
        if(this.end == toCheck.end && this.set == toCheck.set)
        {
            return true;
        }
        return false;
    }    
    
}
