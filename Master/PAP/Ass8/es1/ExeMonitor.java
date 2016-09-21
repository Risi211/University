/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ppp;

/**
 *
 * @author luca.parisi2
 */
public class ExeMonitor {
    
    private boolean start;
    
    	public ExeMonitor()
        {
		start = false;
	}
        
	public synchronized void set(boolean b){
		start = b;
	}
	
	public synchronized boolean isSet(){
		return start;
	}	
}
