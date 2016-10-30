/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorial.spring;

/**
 *
 * @author lupin
 */
public abstract class HelloWorld {
    
    protected String message = "World";   
    
    public void setMessage(String msg){
        message = msg;
    }
    public String getMessage(){
        return message;
    }    
    public void printMessage(){
        System.out.println(message);
    }
    
    public void init(){
        System.out.println("Init Hello World");
    }
    public void destroy(){
        System.out.println("Destroy Hello World");
    }    
    
}
