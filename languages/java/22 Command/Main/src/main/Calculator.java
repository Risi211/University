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
public class Calculator implements IReceiver{

    private int x;
    private int y;
    private Action a;
    @Override
    public void setAction(Action a) {
        this.a = a;
    }

    @Override
    public int getResult() {
        switch(a){
            case Add:{
                return x + y;
            }
            case Sub:{
                return x - y;
            }
        }
        return -1;
    }
    public Calculator(int x_, int y_){
        x = x_;
        y = y_;
    }
}
