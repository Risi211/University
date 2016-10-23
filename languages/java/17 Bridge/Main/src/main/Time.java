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
public class Time {
    TimeImp ti;
    public Time(){
    }
    public Time(int h, int m){
        ti = new TimeImp(h, m);
    }
    public void tell(){
        ti.tell();
    }
}
