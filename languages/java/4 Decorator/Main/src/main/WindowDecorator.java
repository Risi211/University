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
public class WindowDecorator implements Window{

    protected Window w;
    public WindowDecorator(Window win){
        w = win;
    }
    @Override
    public void draw() {
        w.draw();
    }    
}
