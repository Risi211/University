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
public class TheOther implements Element{

    @Override
    public void accept(Visitor v) {
        v.visit(this);
    }
    public String other(){
        return "other";
    }
    
}
