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
public class Car {
    Wheel[] w = new Wheel[4];
    Engine e = new Engine();
    Body b = new Body();
    
    public void specifications(){
        System.out.println("engine: " + e.getHp());
        System.out.println("body: " + b.getShape());
        System.out.println("wheel: " + w[0].getSize());
    }
}
