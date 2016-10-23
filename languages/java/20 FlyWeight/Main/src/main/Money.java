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
public abstract class Money {
    public abstract MoneyType getMoneyType(); //intrinsic state
    public final void getDisplayOfMoneyFalling(int moneyValue){ //extrinsic state
        System.out.println("display " + getMoneyType() + " of value " + moneyValue);
    }
}
