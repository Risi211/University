/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca
 */

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class W1 extends Thread{
    
    UnsafeCounter c1;
    Semaphore waitW5;
    Semaphore w1Done;
    Semaphore rendevouz_w1;
    Semaphore rendevouz_w2;
    int ntimes;
    
    public W1(UnsafeCounter c1, int ntimes, Semaphore waitW5, Semaphore w1Done, Semaphore rendevouz_w2, Semaphore rendevouz_w1)
    {
        this.c1 = c1;
        this.waitW5 = waitW5;
        this.w1Done = w1Done;
        this.ntimes = ntimes;
        this.rendevouz_w1 = rendevouz_w1;
        this.rendevouz_w2 = rendevouz_w2;
    }
    
    public void run()
    {
        for(int i = 0; i < ntimes; i++)
        {
            try 
            {
                rendevouz_w2.acquire(); //controllo se w2 ha fatto l'incremento
                //incrementa contatore
                c1.inc();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W1.class.getName()).log(Level.SEVERE, null, ex);
            }
            //incrementa 
            w1Done.release();
            try 
            {
                //prima di poter reincrementarlo, deve aspettare
                waitW5.acquire();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W1.class.getName()).log(Level.SEVERE, null, ex);
            }
            rendevouz_w1.release(); //w2 può andare una volta
        }
    }
    
}
