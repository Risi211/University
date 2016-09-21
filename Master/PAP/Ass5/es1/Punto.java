/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass5;



/**
 *
 * @author luca
 */
public class Punto {
    
    private int x;
    private int y;
    private int z;
    
    public Punto(int x, int y, int z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getZ()
    {
        return z;
    }
    
    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setZ(int z)
    {
        this.z = z;
    }

    public static double getDistance(Punto p1, Punto p2)
    {
        return Math.sqrt( Math.pow((p1.getX() - p2.getX()),2) + Math.pow((p1.getY() - p2.getY()),2) + Math.pow((p1.getZ() - p2.getZ()),2) );
    }
    
}
