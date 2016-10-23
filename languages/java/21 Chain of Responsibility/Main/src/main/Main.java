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
        Handler1 root = new Handler1();
        Handler2 h2 = new Handler2();
        root.add(h2);
        h2.setNext(root);
        for(int i = 0; i < 10; i++){
            root.handle(i);
        }
    }
    
}
