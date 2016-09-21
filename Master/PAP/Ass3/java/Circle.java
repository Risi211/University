/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass03;

/**
 *
 * @author luca
 */
public class Circle implements Shape {
    
    //p1 è il punto in alto a sinistra
    //p4 il punto in basso a destra
    private P2d centro;
    private int raggio = 0;
    
    public Circle(P2d centro, int raggio)
    {
        this.centro = centro;
        this.raggio = raggio;
    }
    
    public void move(V2d v) 
    {
        centro = centro.sum(v);
    }

   
    public double getPerim() 
    {
            return 2*Math.PI*raggio;
    }

    
    public boolean isInside(BBox bbox) 
    {
        if(getBBox().getP1().getX() >= bbox.getP1().getX() && getBBox().getP1().getY() >= bbox.getP1().getY()
                && getBBox().getP4().getX() <= bbox.getP4().getX() && getBBox().getP4().getY() <= bbox.getP4().getY())
        {
            return true;
        }            
        else
        {
            return false;
        }
            
    }

    
    public boolean contains(P2d p) 
    {
        //controllo se il punto appartiene al cerchio, cioè se la sua distanza
        //dal centro è <= del raggio, altrimenti non appartiene
        if(P2d.distance(p, centro) <= raggio)
        {            
                return true;
        }
        else
        {
            return false;
        }
                                 
    }
        
    public int getMinX()
    {
        return (centro.getX() - raggio);
    }
    
   public P2d getCentro()
   {
       return centro;
   }    

   public int getRaggio()
   {
       return raggio;
   }   

    public BBox getBBox() {
        return new BBox(new P2d(centro.getX() - raggio, centro.getY() - raggio), new P2d(centro.getX() + raggio, centro.getY() + raggio));
    }
   
}
