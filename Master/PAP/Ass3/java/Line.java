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
public class Line implements Shape{

    //p1 è il punto più a sinistra, p2 il punto più a destra
    private P2d p1;
    private P2d p2;
    //se p1 è più in basso di p2, il coefficiente angolare è positivo
    //altrimenti è negativo
    //cioè se la y di p1 è > della y di p2 (graficamente con gli assi rovesciati) è positivo
    private boolean pos = false;
    
    public Line(P2d p1, P2d p2)
    {
        this.p1 = p1;
        this.p2 = p2;
        if(this.p1.getY() >= this.p2.getY())
        {
            pos = true;
        }
    }
    
    public void move(V2d v) 
    {
        p1 = p1.sum(v);
        p2 = p2.sum(v);
    }

   
    public double getPerim() 
    {
        return P2d.distance(p1, p2);
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
        //considero la linea solo verticale o solo orizzontale, fissando quindi la x o la y
        //non posso applicare la formula sotto sennò il denominatore diventa 0
        
        //se la x è costante
        if(p1.getX() == p2.getX())
        {
            if(p.getX() != p1.getX())
            {
                return false;
            }
            else
            {
                //la y deve essere compresa tra i 2 punti
                //se il coefficiente angolare è positivo:
                if(pos)
                {
                    if(p.getY() <= p1.getY() && p.getY() >= p2.getY())
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }                
                }
                else //coefficiente angolare negativo
                {
                    if(p.getY() <= p2.getY() && p.getY() >= p1.getY())
                    {
                        return true;
                    }
                    else
                    {
                        return false;
                    }
                }                
            }
        }

        //se la y è costante
        if(p1.getY() == p2.getY())
        {
            if(p.getY() != p1.getY())
            {
                return false;
            }
            else
            {
                //la x deve essere compresa tra i 2 punti
                if(p.getX() <= p2.getX() && p.getX() >= p1.getX())
                {
                    return true;
                }
                else
                {
                    return false;
                }                
            }
        }
        
        //arriva qui se la linea è obliqua
        //controllo se il punto appartiene alla retta passante per i 2 punti
        //in caso affermativo, controllo anche che il punto sia compreso tra i 2 punti della linea
        //uso la formula: (x - x1)/(x2 - x1) = (y - y1)/(y2 - y1)
        if((p.getX() - p1.getX()) / (p2.getX() - p1.getX()) == (p.getY() - p1.getY()) / (p2.getY() - p1.getY()))
        {
            //se il punto appartiene alla retta, controllo anche che sia sempre compreso tra i 2 punti
            //della linea
            if(pos)
            {
                if(p.getX() >= p1.getX() && p.getX() <= p2.getX() && p.getY() <= p1.getY() && p.getY() >= p2.getY())
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else //negativo
            {
                if(p.getX() >= p1.getX() && p.getX() <= p2.getX() && p.getY() >= p1.getY() && p.getY() <= p2.getY())
                {
                    return true;
                }
                else
                {
                    return false;
                }                
            }
        }
        
        return false;
    }
    
   public int getMinX()
   {
       return p1.getX();
   }
   
   public P2d getP1()
   {
       return p1;
   }

   public P2d getP2()
   {
       return p2;
   }   

    @Override
    public BBox getBBox() 
    {
         if(pos)
        {
            return new BBox(new P2d(p1.getX(), p2.getY()), new P2d(p2.getX(), p1.getY()));
        }
        else
        {
            return new BBox(new P2d(p1.getX(), p1.getY()), new P2d(p2.getX(), p2.getY()));
        }

    }
   
}
