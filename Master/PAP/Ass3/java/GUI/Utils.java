/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass03;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
/**
 *
 * @author luca
 */
public final class Utils {
    
    //il costruttore è privato, è una classe statica
    private Utils()
    {
        
    }
    
    public static void moveShapes(List<Shape> figure, V2d v)
    {        
        //il parametro è passato per riferimento
           figure.forEach((Shape s) -> s.move(v));
    }
    
    public static List<Shape> inBBox(List<Shape> figure, P2d p0, P2d p1)
    {
           return figure.stream().filter((Shape s) -> s.isInside(p0, p1)).collect(Collectors.toList());
    }
    
    public static double maxPerim(List<Shape> figure)
    {                       
        OptionalDouble od = figure.stream().mapToDouble(Shape::getPerim).max();
/*si poteva fare anche così:*/        
//OptionalDouble od = figure.stream().mapToDouble((Shape s) -> s.getPerim()).max();
        if(od.isPresent())
        {
            return od.getAsDouble();
        }
        else //se la lista è vuota
        {
            return 0;
        }
    }
    
    public static Shape shapeWithMaxPerim(List<Shape> figure)
    {                       
        //implemento un metodo come la compareTo
        Comparator<Shape> cs = (Shape s1, Shape s2) -> 
        {
            if (s1.getPerim() <= s2.getPerim())
            {
                return -1;
            }
            else
            {
                return 1;
            }
        };
        
        Optional<Shape> os = figure.stream().max(cs);
        if(os.isPresent())
        {
            return os.get();
        }
        else //se la lista è vuota
        {
            return null;
        }
    }
    
    //void perchè è per riferimento
    public static void sortShapesByX(List<Shape> figure)
    {        
        //implemento un metodo come la compareTo
        Comparator<Shape> cs = (Shape s1, Shape s2) -> 
        {
            int minXShape1 = s1.getBBox().getP1().getX();
            int minXShape2 = s2.getBBox().getP1().getX();

            if (minXShape1 <= minXShape2)
            {
                return -1;
            }
            else
            {
                return 1;
            }
        };
                
        //il parametro è passato per riferimento
        figure.sort(cs);
    }
    
    public static boolean contains(List<Shape> figure, P2d p)
    {
        Optional<Shape> os = figure.stream().filter((Shape s) -> s.contains(p)).findAny();
        return os.isPresent();            
    }

    public static List<Shape> getContaining(List<Shape> figure, P2d p)
    {
        return figure.stream().filter((Shape s) -> s.contains(p)).collect(Collectors.toList());
    }    

    public static void logAll(List<Shape> figure)
    {
 //stampa coordinate shape su console le figure semplici, senza combo
        Consumer<Shape> drawComposta = (Shape s) -> 
        {
            if(s instanceof Line)
            {
                Line l = (Line) s;
                System.out.println("Line: " + l.getP1().toString() + " " + l.getP2().toString());
            }
            if(s instanceof Rect)
            {
                Rect r = (Rect) s;
                System.out.println("Rect: " + r.getP1().toString() + " " + r.getP4().toString());
            }
            if(s instanceof Circle)
            {
                Circle r = (Circle) s;
                System.out.println("Circle: " + r.getCentro().toString() + ", raggio = " + r.getRaggio());
            }
        };

        //stampa su console la composizione di figure (combo)
        Consumer<Shape> drawShapes = (Shape s) -> 
        {
            if(s instanceof Combo)
            {
                Combo c = (Combo) s;
                System.out.println("Inizio Combo:");
                c.getShapes().forEach(drawComposta);
                System.out.println("Fine Combo:");
            }
            //la lista potrebeb contenere anche figure semplici:
            if(s instanceof Line)
            {
                Line l = (Line) s;
                System.out.println("Line: " + l.getP1().toString() + " " + l.getP2().toString());
            }
            if(s instanceof Rect)
            {
                Rect r = (Rect) s;
                System.out.println("Rect: " + r.getP1().toString() + " " + r.getP4().toString());
            }
            if(s instanceof Circle)
            {
                Circle r = (Circle) s;
                System.out.println("Circle: " + r.getCentro().toString() + ", raggio = " + r.getRaggio());
            }            
        };                
        
        figure.forEach(drawShapes);
        
    }    
    
}
