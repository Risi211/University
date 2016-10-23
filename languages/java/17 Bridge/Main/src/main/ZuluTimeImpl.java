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
public class ZuluTimeImpl extends TimeImp{
    
    protected String zone;
    public ZuluTimeImpl(int h, int m, int pm) {
        super(h, m);
        if(pm == 5){
            zone = "Eastern";
        }
        else{
            zone = "Center";
        }
    }
    public void tell(){
        System.out.println("time is " + hr + ":" + min + ", " + zone);
    }
}
