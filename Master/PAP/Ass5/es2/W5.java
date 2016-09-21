
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
public class W5 extends Thread{
        
    UnsafeCounter c3;
    Semaphore waitW3;
    Semaphore waitW4;
    Semaphore w5Done;
    int ntimes;
    
    public W5(UnsafeCounter c3, int ntimes, Semaphore waitW3, Semaphore waitW4, Semaphore w5Done)
    {
        this.c3 = c3;      
        this.waitW3 = waitW3;
        this.waitW4 = waitW4;
        this.w5Done = w5Done;
        this.ntimes = ntimes;
    }
    
    public void run()
    {
        for(int i = 0; i < ntimes; i++)
        {            
            try 
            {
                //aspetta che w3 e w4 abbiano stampato
                waitW3.acquire();
                waitW4.acquire();                
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(W3.class.getName()).log(Level.SEVERE, null, ex);
            }
            //stampa c1
            System.out.println("W5: c3 = " + c3.getValue());
            
            //da 2 permessi, uno per w1 e l'altro per w2
            w5Done.release(2); 
            //serve il rendevouz a causa di questa istruzione, altrimenti uno
            //fra W1 e W2 potrebbe fare l'incremento 2 volte di fila
        }
    }
    
    
}
