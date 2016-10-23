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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Time[] t = new Time[]{new Time(14,30), new CivilianTime(2, 30, true), new ZuluTime(14,30,6)};
        for(int i = 0; i < 3; i++){
            t[i].tell();
        }
    }
    
}
