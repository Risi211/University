
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca
 */
public class W2 extends Thread {
    
    UnsafeCounter c2;
    Semaphore waitW5;
    Semaphore w2Done;
    Semaphore rendevouz_w1;
    Semaphore rendevouz_w2;
    int ntimes;
    
    public W2(UnsafeCounter c2, int ntimes, Semaphore waitW5, Semaphore w2Done, Semaphore rendevouz_w1, Semaphore rendevouz_w2)
    {
        this.c2 = c2;
        this.waitW5 = waitW5;
        this.w2Done = w2Done;
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
                rendevouz_w1.acquire();
                //incrementa contatore
                c2.inc();      
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W2.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //incrementa 
            w2Done.release();
            try 
            {
                //prima di poter reincrementarlo, deve aspettare
                waitW5.acquire();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W2.class.getName()).log(Level.SEVERE, null, ex);
            }
            rendevouz_w2.release();
        }
    }
    
    
}
