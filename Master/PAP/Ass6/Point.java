/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass06;

//import java.util.concurrent.Semaphore;

/**
 *
 * @author luca
 */
public class Point {
    
    private int i;
    private int j;
    private Life s_old;
    private Life s_new;
    //private Semaphore lock;

    public Point(int x, int y, Life stato)
    {
        i = x;
        j = y;
        s_old = stato;
        s_new = stato;
        //lock = new Semaphore(1);
    }
    
    public void setX(int x) throws InterruptedException
    {        
        i = x;        
    }
    
    public void setY(int y)
    {        
        j = y;        
    }
    
    public void setLife(Life l)
    {        
        s_new = l;        
    }    
    
    public int getX()
    {        
        return i;
    }    
    
    public int getY()
    {
        return j;
    }        
    
    public Life getOldLife()
    {
        return s_old;
    }
    
    public Life getNewLife()
    {
        return s_old;
    }    
    
    public void UpdateLife()
    {
        s_old = s_new;
    }
    
}
