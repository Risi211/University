/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass5;

import java.util.*;
import java.util.Random;

/**
 *
 * @author luca
 */
public class Ass5 {    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException 
    {        
        int howMany = Runtime.getRuntime().availableProcessors();
        
        int num_punti = 1000;
        Punto[] punti = new Punto[num_punti];
        Punto C = new Punto(1000, 5000, 3000); //punto di riferimento per distanza minima
        Punto min = new Punto(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE); //ogni thread calcola il punto minimo e lo scrive qui se la distanza è minore di quella attuale
        
        Random generator;
        long seed = 11;
        int max = 10000;
        generator = new Random(seed);
        
        for(int i = 0; i < num_punti; i++)
        {
            punti[i] = new Punto(generator.nextInt(max), generator.nextInt(max), generator.nextInt(max));
        }
        
        
        System.out.println("Calcolo minimo con i thread");
        long start = System.currentTimeMillis();
        
        MinDistanceThread[] t = new MinDistanceThread[howMany];
        for(int i = 0; i < howMany; i++)
        {
            t[i] = new MinDistanceThread(punti, (num_punti / howMany * i), (num_punti / howMany * (i + 1)), C, min);
            t[i].start();
        }
        
        //aspetto fine thread
        for(int i = 0; i < howMany; i++)
        {
            t[i].join();
        }        
        
        long end = System.currentTimeMillis();        
        
        System.out.println("punto minimo = (" + min.getX() + "," + min.getY() + "," + min.getZ() + ")");
        System.out.println("distanza euclidea = " + Punto.getDistance(C, min));
        System.out.println("tempo totale = " + (end - start) + " ms");
        
        //confronto con gli stream
        System.out.println("\r\nConfronto con gli stream");
        start = System.currentTimeMillis();
        
        Punto min_stream = Arrays.stream(punti).min((Punto p1, Punto p2) ->
        {
            if(Punto.getDistance(p1, C) < Punto.getDistance(p2, C))
            {
                return -1;
            }
            else
            {
                return 1;
            }
        }).get();
                
        end = System.currentTimeMillis();        
        
        System.out.println("punto minimo = (" + min_stream.getX() + "," + min_stream.getY() + "," + min_stream.getZ() + ")");
        System.out.println("distanza euclidea = " + Punto.getDistance(C, min));
        System.out.println("tempo totale = " + (end - start) + " ms");
        
        
    }
    
}
