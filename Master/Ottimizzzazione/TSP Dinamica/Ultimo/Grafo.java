/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author luca
 */
public class Grafo {
    
    //List<Integer> V = new ArrayList<Integer>();
    //List<Arco> E = new ArrayList<Arco>(); //gli archi hanno un verso, il costo per andare/tornare potrebbe essere diverso    
    //contiene il grado per ogni vertice, nel grafo dei lower bound
    int[] gradi_lb; //l'indice del vettore è l'id del vertice
    int NUM_VERTICI;
    
    public Grafo(int num_vertici)
    {
        this.NUM_VERTICI = num_vertici;
        gradi_lb = new int[num_vertici];
        //inizializza gradi
        for(int i = 0; i < num_vertici; i++)
        {
            gradi_lb[i] = 0;
        }
    }
    
    /*
    public Optional<Arco> getArco(int i, int j)
    {
        return E.stream().filter((Arco a) -> a.i == i && a.j == j).findFirst();
    }
    */
    /*
    public List<Integer> getPredecessori(int v)
    {
        //trova la lista di tutti gli archi in entrata al vertice v
        List<Arco> archi_in_entrata = E.stream().filter((Arco a) -> a.j == v).collect(Collectors.toList());
        //ritorna la lista di tutti i predecessori di v, che sono cioè alla partenza degli archi in entrata a v
        return archi_in_entrata.stream().map((Arco a) -> a.i).collect(Collectors.toList());
    }
    */
    
    /*
    public List<Integer> getSuccessori(int v)
    {
        //trova la lista di tutti gli archi in uscita dal vertice v
        List<Arco> archi_in_uscita = E.stream().filter((Arco a) -> a.i == v).collect(Collectors.toList());
        //ritorna la lista di tutti i successori di v, che sono cioè in arrivo degli archi in uscita da v
        return archi_in_uscita.stream().map((Arco a) -> a.j).collect(Collectors.toList());
    }      
    */
    /*
    public void buildArchi(double[][] c, int rows, int columns)
    {
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                if(i != j) //non c'è l'arco che va da un vertice a se stesso
                {
                    Arco a = new Arco();
                    a.i = i;
                    a.j = j;
                    a.cij = c[i][j];
                    E.add(a);
                }
            }
        }
    }
    */
    public void Add_Grado(int v)
    {
        gradi_lb[v]++;
    }
    
    public void resetGradi()
    {
        for(int i = 0; i < gradi_lb.length; i++)
        {
            gradi_lb[i] = 0;
        }
    }
    
    public boolean checkOttimo()
    {
        for(int i = 0; i < gradi_lb.length; i++)
        {
            if(gradi_lb[i] != 2)
            {
                return false;
            }
        }
        return true;
    }
    
    public int getSommaGradi()
    {
        int somma = 0;
        for(int i = 0; i < gradi_lb.length; i++)
        {
            somma += gradi_lb[i];
        }
        return somma;
    }
    
}
