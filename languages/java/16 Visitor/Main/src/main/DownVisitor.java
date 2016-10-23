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
public class DownVisitor implements Visitor{

    @Override
    public void visit(This t) {
        System.out.println("do down on" + t.tthis());
    }

    @Override
    public void visit(That t) {
        System.out.println("do down on" + t.tthat());
    }

    @Override
    public void visit(TheOther t) {
        System.out.println("do down on" + t.other());
    }
    
}
