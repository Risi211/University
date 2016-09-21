/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass03;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.*;
import java.util.function.*;
import java.awt.geom.*;

/**
 *
 * @author luca
 */
public class Viewer implements ShapeViewer
{    

    private JPanel panel;
    
    public Viewer(JPanel jp)
    {
        panel = jp;
    }
        
    @Override
    public void update(List<Shape> shapes) 
    {
        Graphics2D g2 = (Graphics2D) panel.getGraphics();
        g2.clearRect(0, 0, panel.getWidth(), panel.getHeight());        
        if(shapes == null)
        {
            return;
        }
        
        //stampa tutte le figure semplici che fanno parte di una figura composta
        Consumer<Shape> drawComposta = (Shape s) -> 
        {
            if(s instanceof Line)
            {
                Line l = (Line) s;
                g2.drawLine(l.getP1().getX(), l.getP1().getY(), l.getP2().getX(), l.getP2().getY());
            }
            if(s instanceof Rect)
            {
                Rect r = (Rect) s;
                int width = (int)P2d.distance(r.getP1(), new P2d(r.getP4().getX(), r.getP1().getY()));
                int height = (int)P2d.distance(r.getP1(), new P2d(r.getP1().getX(), r.getP4().getY()));
                g2.drawRect(r.getP1().getX(), r.getP1().getY(), width, height);
            }
            if(s instanceof Circle)
            {
                Circle c = (Circle) s;
                java.awt.Shape theCircle = new Ellipse2D.Double(c.getCentro().getX() - c.getRaggio(), c.getCentro().getY() - c.getRaggio(), 2.0 * c.getRaggio(), 2.0 * c.getRaggio());
                g2.draw(theCircle);
            }
        };                
        
        //stampa tutte le figure (semplici + composte)
        Consumer<Shape> drawShapes = (Shape s) -> 
        {
            if(s instanceof Line)
            {
                Line l = (Line) s;
                g2.drawLine(l.getP1().getX(), l.getP1().getY(), l.getP2().getX(), l.getP2().getY());
            }
            if(s instanceof Rect)
            {
                Rect r = (Rect) s;
                int width = (int)P2d.distance(r.getP1(), new P2d(r.getP4().getX(), r.getP1().getY()));
                int height = (int)P2d.distance(r.getP1(), new P2d(r.getP1().getX(), r.getP4().getY()));
                g2.drawRect(r.getP1().getX(), r.getP1().getY(), width, height);
            }
            if(s instanceof Circle)
            {
                Circle c = (Circle) s;
                java.awt.Shape theCircle = new Ellipse2D.Double(c.getCentro().getX() - c.getRaggio(), c.getCentro().getY() - c.getRaggio(), 2.0 * c.getRaggio(), 2.0 * c.getRaggio());
                g2.draw(theCircle);
            }
            if(s instanceof Combo)
            {
                Combo c = (Combo) s;
                c.getShapes().forEach(drawComposta);
            }            
        };
               
        shapes.forEach(drawShapes);       
        
    }    
    
}
