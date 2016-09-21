/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca
 */
public class Flag {
    
    private boolean is_high = false; //stato della flag
    
    public Flag()
    {
        
    }
    
    // - cambia lo stato in alzata
    public synchronized void setHigh()
    {
        is_high = true;
    }

    //    - cambia lo stato in abbassata
    public synchronized void setLow()
    {
        is_high = false;
    }

    // - restituisce true se la bandiera è alzata, false altrimenti    
    public synchronized boolean capture()
    {
        if(is_high)
        {
            return true;
        }
        return false;
    }
    
}
