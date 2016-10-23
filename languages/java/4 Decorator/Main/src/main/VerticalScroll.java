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
public class VerticalScroll extends WindowDecorator {
    
    public VerticalScroll(Window win) {
        super(win);
    }
    public void drawVerticalScroll(){
        System.out.println("draw vertical scroll\r\n");
    }
    public void draw() {
	super.draw(); //super.draw() in java
	drawVerticalScroll();
    }    
    
}
