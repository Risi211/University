/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lowerbound;

import java.util.*;

/**
 *
 * @author luca.parisi2
 */
public class PoolStati {
    
    LinkedList<Stato> pool;
    int NUM_VERTICI;
    int init_dimension;
    
    public PoolStati(int init_dimension, int NUM_VERTICI)
    {
        this.NUM_VERTICI = NUM_VERTICI;
        this.init_dimension = init_dimension;
        pool = new LinkedList<Stato>();
        AumentaStati(init_dimension);
    }
    
    private void AumentaStati(int size)
    {
        for(int i = 0; i < size; i++)
        {
            pool.add(new Stato(NUM_VERTICI));
        }        
    }
    
    public Stato GetStato()
    {
        if(pool.isEmpty())
        {
            AumentaStati(init_dimension);
        }
        return pool.remove();
    }
    
    public void ReleaseStato(Stato s)
    {
        pool.add(s);
    }
    
}
