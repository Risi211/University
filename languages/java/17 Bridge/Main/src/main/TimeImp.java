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
public class TimeImp {
    protected int hr;
    protected int min;
    public TimeImp(int h, int m){
        hr = h;
        min = m;
    }
    public void tell(){
        System.out.println("time is " + hr + ":" + min);
    }
}
