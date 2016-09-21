/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass06;

import javax.swing.JTextField;
import javax.swing.*;

/**
 *
 * @author luca
 */
 
 //task che aggiorna lo stato di vita di ogni singola cella
 //se la cellla è visualizzata nella form, allorna aggiorna anche quella.
public class UpdateLife implements Runnable{

    private int idTask;
    private final int row;   //riga di questo task
    private Point[][] rowPoints;
    private int points_rows; //#righe matrice
    private int points_columns;    //#colonne matrice    
    private JTextField[][] matrix;
    private int matrix_rows = 0;
    private int matrix_columns = 0;
    
    public UpdateLife(int idTask, Point[][] rowPoints, int row, int points_rows, int points_columns, JTextField[][] matrix, int matrix_rows, int matrix_columns) 
    {
        this.idTask = idTask;
        this.row = row;
        this.points_rows = points_rows;
        this.points_columns = points_columns;        
        this.rowPoints = rowPoints;
        this.matrix = matrix;
        this.matrix_rows = matrix_rows;
        this.matrix_columns = matrix_columns;
    }    
    
    private void log(String msg) 
    {
            System.out.println(msg);
    }

    @Override
    public void run() 
    {     
               
        //AGGIORNA LO STATO DI VITA DEI PUNTI DI QUESTA RIGA E L'EVENTUALE CELLA DELLA 
        //MATRICE DELLA FORM
        
//per ogni cella della riga aggiorna lo stato di vita della cella
            for(int i = 0; i < points_columns; i++)
            { 
                Point c = rowPoints[row][i];
                c.UpdateLife();
                if(row < matrix_rows && i < matrix_columns)
                {
                    if(rowPoints[row][i].getNewLife() == Life.Dead)
                    {
                        final int ii = row;
                        final int jj = i;
                        SwingUtilities.invokeLater( () -> matrix[ii][jj].setBackground(java.awt.Color.WHITE));                
                    }
                    else //live
                    {
                        final int ii = row;
                        final int jj = i;                    
                        SwingUtilities.invokeLater( () -> matrix[ii][jj].setBackground(java.awt.Color.BLACK));                                
                    }                    
                }
                
            }      
    }

}
