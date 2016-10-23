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
public class CivilianTimeImpl extends TimeImp{
    
    protected String wm;
    public CivilianTimeImpl(int h, int m, boolean pm) {
        super(h, m);
        if(pm){
            wm = "PM";
        }
        else{
            wm = "AM";
        }
    }
    
}
