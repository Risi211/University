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
public class Colleague<T> implements IColleague<T>{

    private String name;
    public Colleague(String n){
        name = n;
    }
    @Override
    public void send(IMediator<T> im, T s) {
        im.distributeMsg(s);
    }

    @Override
    public void receive(T s) {
        System.out.println(name + " received " + s);
    }
    
}
