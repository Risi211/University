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
public class ColourFactory implements AbstractFactory{

    @Override
    public Shape getShape(String s) {
        throw new UnsupportedOperationException("Not supported Shape."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Colour getColour(String s) {
        if(s.equals("r")){
            return new Red();
        }        
        if(s.equals("b")){
            return new Blue();
        }        
        return null;
    }
    
}
