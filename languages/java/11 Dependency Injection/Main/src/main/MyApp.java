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
public class MyApp implements App{

    MsgService service;
    public MyApp(MsgService ms){
        service = ms;
    }
    @Override
    public void processMsg(String msg) {
        service.send(msg);
    }
    
}
