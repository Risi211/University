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
public class StateAppend2 implements State {
    public void write(Context c, String s){
        System.out.println(s + "-2\r\n");
	//change state
	c.setState(new StateAppend1());        
    }     
}
