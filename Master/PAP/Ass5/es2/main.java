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

public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        
            int ntimes = 100;
            UnsafeCounter c1 = new UnsafeCounter(0);
            UnsafeCounter c2 = new UnsafeCounter(0);
            UnsafeCounter c3 = new UnsafeCounter(0);
            
            Semaphore waitW1 = new Semaphore(0);
            Semaphore waitW2 = new Semaphore(0);
            Semaphore waitW3 = new Semaphore(0);
            Semaphore waitW4 = new Semaphore(0);
            Semaphore waitW5 = new Semaphore(0);
            
            Semaphore sem_c1 = new Semaphore(1);
            Semaphore sem_c2 = new Semaphore(1);
            Semaphore sem_c3 = new Semaphore(1);
            
            //mi serve il rendevouz per sincronizzare w1 e w2 fra di loro,
            //altrimenti uno dei 2 thread potrebbe incrementare il proprio contatore 2 volte di fila
            //mandando tutto in deadlock
            Semaphore rendevouz_w1 = new Semaphore(1);
            Semaphore rendevouz_w2 = new Semaphore(1);

            W1 w1 = new W1(c1, ntimes, waitW5, waitW1, rendevouz_w2, rendevouz_w1);
            W2 w2 = new W2(c2, ntimes, waitW5, waitW2, rendevouz_w1, rendevouz_w2);
            W3 w3 = new W3(c1, c3, ntimes, waitW1, waitW3, sem_c3);
            W4 w4 = new W4(c2, c3, ntimes, waitW2, waitW4, sem_c3);
            W5 w5 = new W5(c3, ntimes, waitW3, waitW4, waitW5);
            w1.start();
            w2.start();
            w3.start();
            w4.start();
            w5.start();
            w1.join();
            w2.join();
            w3.join();
            w4.join();
            w5.join();
        
            System.out.println("\r\nFine Main");
    }
    
}
