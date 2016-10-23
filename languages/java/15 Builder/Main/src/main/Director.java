package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author lupin
 */
public class Director {
    private Builder b;
    public void setBuilder(Builder bu){
        b = bu;
    }
    public Car getCar(){
        Car c = new Car();
        c.b = b.getBody();
        c.e = b.getEngine();
        c.w[0] = b.getWheel();
        c.w[1] = b.getWheel();
        c.w[2] = b.getWheel();
        c.w[3] = b.getWheel();
        return c;
    }
    
}
