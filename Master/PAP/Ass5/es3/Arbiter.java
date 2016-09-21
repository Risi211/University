/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca
 */

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Arbiter extends Thread {
    
    private Random generatore;
    private Flag f;
    
    public Arbiter(Flag f)
    {
        generatore = new Random(System.currentTimeMillis());
        this.f = f;
    }
    
    public void run()
    {
        while(true)
        {
            try 
            {
                sleep(100); //per 100ms la bandiera rimane abbassata
                f.setHigh(); //la alza
                sleep(generatore.nextInt(10)); //rimane alzata per max 10 ms
                f.setLow(); //ritorna bassa
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Arbiter.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
