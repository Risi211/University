/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass4;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author luca
 */
public class Main {
    
    public static void main(String[] args) 
    {
        int n = Integer.parseInt(args[0]); //leggo il parametro
        TextLib lib = TextLibFactory.getInstance();
        lib.cls();
        
        //stampa i confini del quadrato di console in cui gli asterischi si muovono:
        int width = 50;
            int height  = 20;

            for(int i = 0; i <= height; i++)
            {			
                    lib.writeAt(0, i, "+");
            }
          for(int i = 0; i <= width; i++)
            {
                    lib.writeAt(i, 0, "+");
            }
          for(int i = 0; i <= width; i++)
            {
                    lib.writeAt(i, height, "+");
            }
            for(int i = 0; i <= height; i++)
            {
                    lib.writeAt(width, i, "+");
            }		

            //inizializzo il seed per la generazione dei numeri casuali nei sotto-thread:
            long seed = System.currentTimeMillis();
		
        //creo n thread, dove ogni thread gestisce un asterisco
        List<StarManager> stars = new ArrayList<StarManager>();
        for(int i = 0; i < n; i++)
        {
            StarManager sm = new StarManager(lib, width, height, seed * (i + 1));
            sm.start();
            stars.add(sm);
        }
        //dorme
        while(true)
        {
            try
            {
                Thread.sleep(10000);
            }
            catch(InterruptedException ex)
            {

            }
        }        
    }    
    
}
