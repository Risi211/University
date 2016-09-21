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
public class Rect implements Shape {
    
    //p1 è il punto in alto a sinistra
    //p4 il punto in basso a destra
    private P2d p1;
    private P2d p4;
    
    public Rect(P2d p1, P2d p4)
    {
        this.p1 = p1;
        this.p4 = p4;
    }
    
    public void move(V2d v) 
    {
        p1 = p1.sum(v);
        p4 = p4.sum(v);
    }

   
    public double getPerim() 
    {
        P2d p2 = new P2d(p4.getX(), p1.getY());
        P2d p3 = new P2d(p1.getX(), p4.getY());
        return P2d.distance(p1, p2) +
                P2d.distance(p1, p3) +
                P2d.distance(p3, p4) +
                P2d.distance(p2, p4);
    }

    
    public boolean isInside(P2d p1, P2d p2) 
    {
        if(p1.getX() <= this.p1.getX() && p1.getY() <= this.p1.getY()
                && p2.getX() >= this.p4.getX() && p2.getY() >= this.p4.getY())
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
        //considero la linea solo verticale o solo orizzontale, fissando quindi la x o la y
        if(p.getX() >= p1.getX() && p.getX() <= p4.getX() && p.getY() >= p1.getY() && p.getY() <= p4.getY())
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
        return p1.getX();
    }
    
    public P2d getP1()
   {
       return p1;
   }

   public P2d getP4()
   {
       return p4;
   }   

    @Override
    public BBox getBBox() {
        return new BBox(new P2d(p1.getX(), p1.getY()), new P2d(p4.getX(), p4.getY()));
    }
    
}
