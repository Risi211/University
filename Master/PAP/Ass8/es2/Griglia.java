/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass08.es2;

import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author luca
 */
public class Griglia {
    
    JTextField[][] matrix;
    JLabel lbl;
    Point[][] cells;
    int cell_rows;
    int cell_columns;
    int matrix_rows;
    int matrix_columns;    
    
    public Griglia(JTextField[][] matrix, Point[][] cells, int cell_rows, int cell_columns, int matrix_rows, int matrix_columns, JLabel lbl)
    {
        this.matrix = matrix;
        this.cells = cells;
        this.matrix_rows = matrix_rows;
        this.matrix_columns = matrix_columns;
        this.cell_columns = cell_columns;
        this.cell_rows = cell_rows;
        this.lbl = lbl;
    }
    
}
