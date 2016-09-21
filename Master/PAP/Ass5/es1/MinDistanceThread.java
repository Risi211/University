/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass5;

/**
 *
 * @author luca
 */
public class MinDistanceThread extends Thread{
    
    Punto[] punti;
    int min_index;
    int max_index;
    Punto C;
    Punto min; //minimo visto dal main
    Punto min_locale; //minimo locale, quando tutti i punti sono stati controllati,
    //si controlla se il minimo locale è < del minimo visto dal main, in tal caso lo sovrascrive
    
    public MinDistanceThread(Punto[] punti, int min_i, int max_i, Punto C, Punto min)
    {
        this.punti = punti;
        min_index = min_i;
        max_index = max_i;
        this.C = C;
        this.min = min; //stesso riferimento
        min_locale = new Punto(min.getX(), min.getY(), min.getZ()); //riferimento locale
    }
    
    public void run()
    {
        for(int i = min_index; i < max_index; i++)
        {
            if(Punto.getDistance(punti[i], C) < Punto.getDistance(min_locale, C))
            {
                min_locale.setX(punti[i].getX());
                min_locale.setY(punti[i].getY());
                min_locale.setZ(punti[i].getZ());
            }
        }
        
        //controllo con una check&act atomica e sincronizzata
        //se il minimo locale < minimo
        synchronized(min)
        {
            if(Punto.getDistance(min_locale, C) < Punto.getDistance(min, C))
            {
                min.setX(min_locale.getX());
                min.setY(min_locale.getY());
                min.setZ(min_locale.getZ());
            }
        }
        
    }    
    
}
