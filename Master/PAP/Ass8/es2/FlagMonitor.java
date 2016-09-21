/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass08.es2;

/**
 *
 * @author luca
 */
public class FlagMonitor {
  
    private boolean isRunning;

    public FlagMonitor(){
            isRunning = false;
    }

    public synchronized void start(){
            isRunning = true;
    }

    public synchronized boolean isStarted(){
            return isRunning;
    }

    public synchronized void stop(){
            isRunning = false;
    }
        
    
}
