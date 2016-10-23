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
        Injector inj = new EmailInjector();
        App a1 = inj.getApp();
        a1.processMsg("a1");
        Injector inj2 = new SmsInjector();
        App a2 = inj2.getApp();
        a2.processMsg("a2");
    }
    
}
