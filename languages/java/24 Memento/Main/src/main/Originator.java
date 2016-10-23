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
public class Originator<T> {
    private T state;
    public Memento createMemento(){
        Memento<T> m = new Memento<>();
        m.setState(state);
        return m;
    }
    public void setMemento(Memento<T> m){
        state = m.getState();
    }
    public void setState(T st){
        state = st;
    }
    public void showState(){
        System.out.println("state: " + state);
    }
}
