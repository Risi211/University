/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pap.ass4;

import java.util.Random;

/**
 *
 * @author luca.parisi2
 */
public class ThreadGuesser extends Thread {
    
    Secret s;
    long min = 0;
	long max = 0;
    boolean exe = true;
    String name;
    long start_clock;
    
    public ThreadGuesser(Secret to_guess, long min, long max, String name, long start_clock)
    {
            s = to_guess;
            this.min = min;
			this.max = max;
            this.name = name;
            this.start_clock = start_clock;
    }    
    
    public void run()
    {
            while (exe)
            {
              try_guess();	
            }		
    }
        
    protected void try_guess()
    {
		for(long i = min; i <= max; i++)
		{
			
			if(s.guess(i))
			{
				long end_clock = System.currentTimeMillis();
				exe = false;
				System.out.println("Sono il thread: " + name + "\r\n" +
						"numero indovinato: " + i + "\r\n" + 
						"tempo totale = " + (end_clock - start_clock) + " ms"
				);
				System.exit(0);
			}
		}
    }        
        
}
