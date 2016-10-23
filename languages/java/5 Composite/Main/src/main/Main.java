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
        Composite root = new Composite("root");
        Composite sub = new Composite("sub");
        sub.insertChild(new Leaf("s1"));
        sub.insertChild(new Leaf("s2"));
        sub.insertChild(new Leaf("s3"));
        root.insertChild(sub);
        root.insertChild(new Leaf("c2"));
        root.insertChild(new Leaf("c3"));
        root.insertChild(new Leaf("c4"));

        root.traverse();
    }
    
}
