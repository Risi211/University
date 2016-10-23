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
public abstract class Game {
    
    public abstract void begin();
    public abstract void middle();
    public abstract void end();
    
    public final void play(){
        begin();
        middle();
        end();
    }
}
