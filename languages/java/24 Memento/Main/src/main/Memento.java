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
public class Memento<T> {
    private T state;
    public T getState(){
        return state;
    }
    public void setState(T s){
        state = s;
    }
}
