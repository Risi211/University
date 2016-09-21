/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author luca
 */
public class LogBuffer {
    
    Queue<String> buffer;
    boolean stop = false;
    
    public LogBuffer()
    {
        buffer = new LinkedList<String>();
    }
    
    public synchronized void write(String s)
    {
        buffer.add(s);
    }
    
    public synchronized String read()
    {
        return buffer.remove();
    }
     
    public synchronized boolean isEmpty()
    {
        return buffer.isEmpty();
    }
    
    public synchronized void stop()
    {
        stop = true;
    }
    
    public synchronized boolean isStopped()
    {
        return stop;
    }    
    
}
