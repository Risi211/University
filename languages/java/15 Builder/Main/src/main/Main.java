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
        Director d = new Director();
        d.setBuilder(new JeepBuilder());
        d.getCar().specifications();
        d.setBuilder(new NissanBuilder());
        d.getCar().specifications();
    }
    
}
