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
public class Facade {
    private SystemA sa = new SystemA();
    private SystemB sb = new SystemB();
    public void Op1(){
        sa.OpA1();
        sb.OpB1();
    }
    public void Op2(){
        sa.OpA2();
        sb.OpB2();
    }    
}
