
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
public class W3 extends Thread {
    
    UnsafeCounter c1;
    UnsafeCounter c3;
    Semaphore waitW1;
    Semaphore w3Done;
    Semaphore sem_c3;  
    int ntimes;
    
    public W3(UnsafeCounter c1, UnsafeCounter c3, int ntimes, Semaphore waitW1, Semaphore w3Done, Semaphore sem_c3)
    {
        this.c1 = c1;
        this.c3 = c3;
        this.waitW1 = waitW1;
        this.w3Done = w3Done;
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
                waitW1.acquire();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W3.class.getName()).log(Level.SEVERE, null, ex);
            }
            

            //stampa c1
                System.out.println("W3: c1 = " + c1.getValue());

            
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
            w3Done.release();
        }
    }
    
    
}
