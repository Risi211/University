/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lowerbound;

import javax.swing.JTextArea;

/**
 *
 * @author luca
 */
public class LogWriter extends Thread{
    
    JTextArea log;
    LogBuffer buffer;
    
    public LogWriter(JTextArea log, LogBuffer buffer)
    {
        this.log = log;
        this.buffer = buffer;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(200); //ogni 200ms scrive                
            }
            catch(Exception ex)
            {
                
            }           
            
            //svuota tutto il buffer
            while(!buffer.isEmpty())
            {
                log.setText(log.getText() + "\r\n" + buffer.read());
            }

            //controlla se deve fermarsi
            if(buffer.isStopped())
            {
                break; //finisce la run() questo thread
            }            
            
        }
    }
    
}
