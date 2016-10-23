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
public class Handler1 extends Base{
    public void handle(int i){
        if(i % 2 > 0){
            System.out.println("H1 passed " + i);
            next.handle(i);
        }
        else{
            System.out.println("H1 managed " + i);
        }
    }
}
