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
public class Context {
    Strategy s;
    public Context(Strategy str){
        s = str;
    }
    public int execOp(int n1, int n2){
        return s.doOp(n1, n2);
    }
}
