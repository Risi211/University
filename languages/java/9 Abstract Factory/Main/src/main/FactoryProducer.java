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
public final class FactoryProducer {
    public static AbstractFactory getFactory(String s){
        if(s.equals("shape")){
            return new ShapeFactory();
        }
        if(s.equals("colour")){
            return new ColourFactory();
        }                
        return null;
    }
}
