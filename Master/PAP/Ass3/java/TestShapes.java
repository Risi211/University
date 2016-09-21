/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass03;

import java.util.*;
import java.util.function.*;

/**
 *
 * @author luca
 */
public class TestShapes {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        
        ArrayList<Shape> semplici = new ArrayList<Shape>();
        semplici.add(new Line(new P2d(0, 0), new P2d(2, 0)));
        semplici.add(new Line(new P2d(1, 1), new P2d(1, 4)));
        semplici.add(new Line(new P2d(2, 2), new P2d(5, 5)));
        semplici.add(new Rect(new P2d(10, 5), new P2d(15, 10)));
        semplici.add(new Rect(new P2d(10, 2), new P2d(20, 4)));
        semplici.add(new Circle(new P2d(7, 7), 5));
        semplici.add(new Circle(new P2d(2, 2), 3));      
        
        ArrayList<Shape> tmp = new ArrayList<Shape>();
        tmp.add(new Line(new P2d(20, 20), new P2d(30, 30)));
        tmp.add(new Rect(new P2d(30, 30), new P2d(40, 40)));
        tmp.add(new Circle(new P2d(35, 35), 5));
        
        semplici.add(new Combo(tmp));                               
        
        //-------------------------------------------------------
        
        //test 1a: moveShapes figure semplici
        System.out.println("-----test 1: stampo figure semplici------------");
        Utils.logAll(semplici);
        Utils.moveShapes(semplici, new V2d(1,1));
        System.out.println("-------------Applico la moveShapes alle figure semplici------------");
        Utils.logAll(semplici);
        System.out.println("--------end Test 1------------------");
                
        //-------------------------------------------------------
        
        //test 2a: inBBox figure semplici (in questo caso contiene solo i rettangoli)
        BBox bbox = new BBox(new P2d(0, 0),new P2d(30, 30));
        System.out.println("--------test 2: stampo le figure semplici comprese nel box: "+ bbox.getP1().toString() + "," + bbox.getP4().toString() + "------------------");
        Utils.logAll(Utils.inBBox(semplici, bbox));
        System.out.println("--------end test 2----------------");
                
        //-------------------------------------------------------
                
        //test 3a: maxPerim figure semplici
        System.out.println("--------test 3----------");
        System.out.println("Max perimetro = " + Utils.maxPerim(semplici));
                        
        //-------------------------------------------------------

        //test 4a: shapeWithMaxPerim figure semplici
        System.out.println("--------test 4----------");
        Shape s = Utils.shapeWithMaxPerim(semplici);
        if(s instanceof Line)
        {
            Line l = (Line) s;
            System.out.println("Linea: " + l.getP1().toString() + ", " + l.getP2().toString());
        }
        if(s instanceof Rect)
        {
            Rect r = (Rect) s;
            System.out.println("Rettangolo: " + r.getP1().toString() + ", " + r.getP4().toString());
        }
        if(s instanceof Circle)
        {
            Circle r = (Circle) s;
            System.out.println("Cerchio: " + r.getCentro().toString() + ", " + r.getRaggio());
        }
        if(s instanceof Combo)
        {
            Combo c = (Combo) s;
            System.out.println("Combo: Inizio");
            Utils.logAll(c.getShapes());
            System.out.println("Combo: Fine");
        }
                
        //-------------------------------------------------------
        
        //test 5a: sortShapesByX figure semplici        
        System.out.println("Test 5");
        Utils.sortShapesByX(semplici);
        Utils.logAll(semplici); //stampa le figure ordinate (NB: la x minima del cerchio è la x del centro meno la lunghezza del raggio)
                
        //-------------------------------------------------------
        
        //test 6a: contains figure semplici
        System.out.println("Test 6");
        P2d p = new P2d(3, 3);
        if(Utils.contains(semplici, p))
        {
            System.out.println("il punto " + p.toString() + " è contenuto in almeno una figura");
        }
        else
        {
            System.out.println("il punto " + p.toString() + "NON è contenuto in almeno una figura");
        }
                
        //-------------------------------------------------------
        
        //test 7a: getContaining figure semplici
        System.out.println("Test 7:");
        p = new P2d(3, 3);
        System.out.println("figure che contengono il punto " + p.toString() + ":");
        Utils.logAll(Utils.getContaining(semplici, p));                
        
        
    }
    
}
