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
public class Larry implements Stooge{

    @Override
    public Stooge clone() {
        return new Larry();
    }

    @Override
    public void slap_stick() {
        System.out.println("Larry");
    }
    
}
