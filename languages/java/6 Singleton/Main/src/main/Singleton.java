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
public class Singleton {
    private int value;
    private static Singleton instance;
    private Singleton(int v){
        value = v;
    }
    public static Singleton getInstance(){
        if(instance == null){
            instance = new Singleton(0);
        }
        return instance;
    }
    public void setV(int v){
        value = v;
    }
    public int getV(){
        return value;
    }
    
}
