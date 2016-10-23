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
        Originator<String> orig = new Originator<>();
        Caretaker<String> care = new Caretaker<>();

        orig.setState("state 0");
        care.saveState(orig); //save state of the originator
        orig.showState();

        orig.setState("state 1");
        care.saveState(orig); //save state of the originator
        orig.showState();

        orig.setState("state 2");
        care.saveState(orig); //save state of the originator
        orig.showState();

        //restore state of the originator
        care.restoreState(orig, 0);
        orig.showState();  //shows state0        
    }
    
}
