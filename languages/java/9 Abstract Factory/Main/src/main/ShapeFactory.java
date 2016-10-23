/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author lupin
 */
public class ShapeFactory implements AbstractFactory {

    @Override
    public Shape getShape(String s) {
        if(s.equals("r")){
            return new Rectangle();
        }
        if(s.equals("c")){
            return new Circle();
        }        
        return null;
    }

    @Override
    public Colour getColour(String s) {
        throw new UnsupportedOperationException("Not supported Colour."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
