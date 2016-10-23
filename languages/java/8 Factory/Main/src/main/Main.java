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
        Currency c1 = CurrencyFactory.getCurrency("D");
        Currency c2 = CurrencyFactory.getCurrency("E");
        System.out.println(c1.getSymbol());
        System.out.println(c2.getSymbol());
    }
    
}
