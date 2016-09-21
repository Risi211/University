/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass03;

import java.util.*;

/**
 *
 * @author luca
 */
public class Combo implements Shape{
    
    private ArrayList<Shape> figure;
    
    public Combo(List<Shape> s)
    {
        figure = new ArrayList<Shape>();
        ListIterator<Shape> li = s.listIterator();
        while(li.hasNext())
        {
            figure.add(li.next());
        }
    }
    
    public void move(V2d v) 
    {
          for(Shape f:figure)
          {
              f.move(v);
          }                
    }

    public double getPerim() 
    {
        /*
            double p = 0; //perimetro totale
            for(Shape f:figure)
            {
                p += f.getPerim();
            }
            return p;
                */
        return figure.stream().mapToDouble((Shape s) -> s.getPerim()).reduce(0, (double a, double b) -> a + b);
    }

    //tutte le figure devono appartenere al box
    public boolean isInside(P2d p1, P2d p2) 
    {        
        /*
            for(Shape f:figure)
            {
                if(!f.isInside(p1, p2))
                {
                    return false;
                }
            }
            return true;
                */        
        //se trova almeno una figura che non appartiene al box, isPresent() è true        
        return !(figure.stream().filter((Shape s) -> !s.isInside(p1, p2)).findAny().isPresent());
    }

    //il punto basta che appartenga ad una figura della combo
    public boolean contains(P2d p) 
    {
        /*
            for(Shape f:figure)
            {
                if(f.contains(p))
                {
                    return true;
                }
            }
            return false;        
          */
        //se trovo almeno una figura che contiene il punto, ritorna true (cioè isPresent())
        return figure.stream().filter((Shape s) -> s.contains(p)).findAny().isPresent();
    }
    
    public int getMinX()
    {        
        return this.getBBox().getP1().getX();
        /*
        for(Shape f:figure)
        {
            if(f instanceof Line)
            {
                Line l = (Line) f;
                if(l.getMinX() < min)
                {
                    min = l.getMinX();
                }
            }
            if(f instanceof Circle)
            {
                Circle c = (Circle) f;
                if(c.getMinX() < min)
                {
                    min = c.getMinX();
                }
            }
            if(f instanceof Rect)
            {
                Rect r = (Rect) f;
                if(r.getMinX() < min)
                {
                    min = r.getMinX();
                }
            }            
        }
                
        return min;
                */
    }
    
   public List<Shape> getShapes()
   {
       return figure;
   }       

    @Override
    public BBox getBBox() {
        //nella foldr, il primo bbox è uguale a quello della prima figura
        BBox b = figure.get(0).getBBox();
        for(Shape s:figure)
        {
            b = BBox.mergeBBox(s.getBBox(), b);
        }
        return b;
    }
    
}
