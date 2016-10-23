/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lupin
 */
public class Fuel<T> implements Observable{

    List<Observer<T>> observers = new ArrayList<>();
    T value;

    @Override
    public void registerObserver(Observer ob) {
        observers.add(ob);
    }

    @Override
    public void notifyObservers() {
        for(Observer<T> ob : observers){
            ob.update(value);
        }
    }
    
    public void setValue(T val){
        value = val;
        notifyObservers();
    }    
    

    
}
