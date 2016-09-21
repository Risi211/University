/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pap.ass4;

import java.util.*;

/**
 *
 * @author luca.parisi2
 */
public class TestCruncher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        long max = Long.parseLong(args[0]);
        //inizializza il segreto
        Secret s = new Secret(max);                
        //restituisce il numero di processori logici del sistema
	int howMany = Runtime.getRuntime().availableProcessors();
        System.out.println("Numero di processori = " + howMany);
        //lista di thread
        List<ThreadGuesser> guessers = new ArrayList<ThreadGuesser>();
        long start_clock = System.currentTimeMillis();
        //inizializza i thread
        for(int i = 0; i < howMany; i++)
        {
			long range_min = (max / howMany) * i;
			long range_max = (max / howMany) * (i + 1);
            ThreadGuesser tg = new ThreadGuesser(s,range_min, range_max,"Thread Id: " + i,start_clock);
            tg.start();
            guessers.add(tg);
        }
        while(true)
        {
            try
            {
                Thread.sleep(1000);
            }
            catch(InterruptedException ex)
            {

            }
        }

        
    }
    
}
