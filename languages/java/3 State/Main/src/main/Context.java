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
public class Context {
    private State currentState;
    public Context(){
        currentState = new StateAppend1();
    }
    public void setState(State s){
        currentState = s;
    }
    public void write(String s){
        currentState.write(this, s);
    }
}
