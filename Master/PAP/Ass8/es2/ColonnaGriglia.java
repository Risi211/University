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
public class ColonnaGriglia {
  
    Point[] cells;
    int index; //indice della riga della cella
    
    public ColonnaGriglia(Point[] cells, int index)
    {
        this.cells = cells;
        this.index = index;
    }        
}
