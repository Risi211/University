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
public class EmailServiceImpl implements MsgService{

    @Override
    public void send(String msg) {
        System.out.println("send email: " + msg);
    }
    
}
