/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca
 */

import java.util.concurrent.locks.*;

public class Sync {
    
    private Lock mutex;
    private Condition thread_turn;
    private int current_turn;
    private int num_thread;
    private boolean winner = false;
    
    public Sync(int num_thread)
    {
        mutex = new ReentrantLock(); 
        thread_turn = mutex.newCondition();
        this.num_thread = num_thread;
        current_turn = 1; //parte per primo il primo thread 
    }
    
    // - chiamata dal player i-esimo, sospende il player fin quando non è il suo turno
    public void waitForTurn(int turn) throws InterruptedException
    {
        try
        {
            mutex.lock();
            if(current_turn != turn)
            {
                 thread_turn.await(); //aspetta il suo turno
            }
        }
        finally
        {
            mutex.unlock();
        }
        
    }

    // - cede il turno al player successivo, dopo ke il thread corrente ha fallito la cattura della flag
    public void next()
    {
        try
        {
            mutex.lock();
            if(current_turn == num_thread)
            {
                current_turn = 1;
            }
            else
            {
                current_turn++;
            }
            thread_turn.signalAll(); //sveglia tutti i thread che controllano se è il loro turno
        }                        
        finally
        {
            mutex.unlock();
        }        
    }
    
    public boolean Exist_Winner()
    {
        try
        {
            mutex.lock();
            return winner;
        }
        finally
        {
            mutex.unlock();
        }
        
    }
    
}
