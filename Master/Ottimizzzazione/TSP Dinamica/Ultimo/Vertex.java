package lowerbound;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca.parisi2
 */
public class Vertex {
    
    //coordinate
    private int x;
    private int y;
    //nome vertice
    private int id;
    //x e y double
    double xD;
    double yD;
    
    public Vertex(int x, int y, int id)
    {
        this.x = x;
        this.y = y;
        this.id = id;
    }
    
    public Vertex(double x, double y, int id)
    {
        xD = x;
        yD = y;
        this.id = id;
    }    
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }
    
    public int getID()
    {
        return id;
    }    
    
    public static int getDistance(Vertex v1, Vertex v2)
    {
        double res = (v1.getX() - v2.getX()) * (v1.getX() - v2.getX()) + (v1.getY() - v2.getY()) * (v1.getY() - v2.getY());
        return (int)Math.sqrt(res);
    }
    
    public static double getDistanceDouble(Vertex v1, Vertex v2)
    {
        double res = (v1.xD - v2.xD) * (v1.xD - v2.xD) + (v1.yD - v2.yD) * (v1.yD - v2.yD);
        return Math.sqrt(res);
    }    
    
}
