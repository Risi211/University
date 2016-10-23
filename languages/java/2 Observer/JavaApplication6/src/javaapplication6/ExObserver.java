/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

/**
 *
 * @author lupin
 */
public class ExObserver<T> implements Observer<T> {

    @Override
    public void update(T value) {
        System.out.println("new value: " + value);
    }
    
}
