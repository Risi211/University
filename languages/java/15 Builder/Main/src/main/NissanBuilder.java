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
public class NissanBuilder implements Builder {

    @Override
    public Wheel getWheel() {
        Wheel w = new Wheel();
        w.setSize(24);
        return w;
    }

    @Override
    public Body getBody() {
        Body b = new Body();
        b.setShape("nissan");
        return b;
    }

    @Override
    public Engine getEngine() {
        Engine e = new Engine();
        e.setHp(70);
        return e;
    }
    
}
