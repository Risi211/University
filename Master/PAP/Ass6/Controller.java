/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass06;

import javax.swing.JTextField;
import javax.swing.JLabel;

/**
 *
 * @author luca
 */
 
//il controller è un thread che ogni 200ms fa ripartire i task
//per far evolvere il gioco della vita 
public class Controller extends Thread {
    
    private JTextField[][] matrix;
    private int matrix_rows = 0;
    private int matrix_columns = 0;
    private FlagMonitor flag;
    private int poolSize;
    private Point[][] cells;
    private int points_rows = 0;
    private int points_columns = 0;  
    private JLabel lbl;
    
    public Controller (int poolSize, JTextField[][] matrix, int matrix_rows, int matrix_columns, Point[][] cells, int points_rows, int points_columns, FlagMonitor flag, JLabel lbl)
    {		
        this.matrix = matrix;
        this.matrix_rows = matrix_rows;
        this.matrix_columns = matrix_columns;
        this.points_rows = points_rows;
        this.points_columns = points_columns;        
        this.poolSize = poolSize;
        this.cells = cells;
        this.flag = flag;
        this.lbl = lbl;
    }


    public void run() 
    {
            while(true)
            {
                if(flag.isStarted())
                {
                    new GameOfLife(poolSize, matrix, matrix_rows, matrix_columns, cells, points_rows, points_columns, lbl).start();                    
                }
                try 
                {
                    Thread.sleep(200);
                } 
                catch (InterruptedException ex) 
                {
                    return;
                }
            }

    } 
    
}
