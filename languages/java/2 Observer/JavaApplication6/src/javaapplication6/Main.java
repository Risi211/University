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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Observer<Float> ob1 = new ExObserver<Float>();
        Fuel<Float> f = new Fuel<Float>();
        f.registerObserver(ob1);
        f.setValue(2.2f);
    }
    
}
