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
public final class CurrencyFactory {
    public static Currency getCurrency(String n){
        if(n.equals("D")){
            return new Dollar();
        }
        if(n.equals("E")){
            return new Euro();
        }
        return null;        
    }
}
