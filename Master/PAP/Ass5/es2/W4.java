
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
public class W4 extends Thread{
    
    UnsafeCounter c2;
    UnsafeCounter c3;
    Semaphore waitW2;
    Semaphore w4Done;
    Semaphore sem_c3;
    int ntimes;
    
    public W4(UnsafeCounter c2, UnsafeCounter c3, int ntimes, Semaphore waitW2, Semaphore w4Done, Semaphore sem_c3)
    {
        this.c2 = c2;
        this.c3 = c3;
        this.waitW2 = waitW2;
        this.w4Done = w4Done;
        this.ntimes = ntimes;
        this.sem_c3 = sem_c3;
    }
    
    public void run()
    {
        for(int i = 0; i < ntimes; i++)
        {            
            try 
            {
                //aspetta che w1 incrementi c1
                waitW2.acquire();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W4.class.getName()).log(Level.SEVERE, null, ex);
            }
            

                //stampa c2
                System.out.println("W4: c2 = " + c2.getValue());

                
            try 
            {
                //incrementa c3
                sem_c3.acquire();
                c3.inc();
                sem_c3.release();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W4.class.getName()).log(Level.SEVERE, null, ex);
            }

            //incrementa 
            w4Done.release();
        }
    }
    
    
}
