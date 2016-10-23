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
public class Leaf implements Component {

    private String name;
    public Leaf(String n){
        name = n;
    }
    @Override
    public void traverse() {
        System.out.println(name);
    }
    
}
