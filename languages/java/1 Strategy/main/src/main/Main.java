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
        Context c1 = new Context(new Addition());
        Context c2 = new Context(new Substraction());
        System.out.println(c1.execOp(5, 3));
        System.out.println(c2.execOp(5, 3));
    }
    
}
