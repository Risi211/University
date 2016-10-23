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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AbstractFactory a1 = FactoryProducer.getFactory("shape");
        AbstractFactory a2 = FactoryProducer.getFactory("colour");
 
        Shape s1 = a1.getShape("r");
        Shape s2 = a1.getShape("c");

        Colour c1 = a2.getColour("r");
        Colour c2 = a2.getColour("b");

        s1.draw();
        s2.draw();
        c1.fill();
        c2.fill();

    }
    
}
