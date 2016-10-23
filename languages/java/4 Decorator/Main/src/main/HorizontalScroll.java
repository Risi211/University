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
public class HorizontalScroll extends WindowDecorator {
    
    public HorizontalScroll(Window win) {
        super(win);
    }
    public void drawHorizontalScroll(){
        System.out.println("draw horizontal scroll\r\n");
    }
    public void draw() {
	super.draw(); //super.draw() in java
	drawHorizontalScroll();
    }    
    
    
}
