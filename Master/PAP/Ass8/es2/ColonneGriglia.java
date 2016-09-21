/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pap.ass08.es2;

import javax.swing.JTextField;

/**
 *
 * @author luca.parisi2
 */
public class ColonneGriglia {
    
    ColonnaGriglia c;
    ColonnaGriglia prec;
    ColonnaGriglia succ;
    JTextField[] matrix; //eventuale riga di JTextField che rappresentano le celle visualizzate nella form
    int cell_rows;
    int cell_columns;
    int matrix_rows;
    int matrix_columns;
    
    public ColonneGriglia(ColonnaGriglia c, ColonnaGriglia prec, ColonnaGriglia succ, int cell_rows, int cell_columns, int matrix_rows, int matrix_columns, JTextField[] matrix)
    {
        this.c = c;
        this.prec = prec;
        this.succ = succ;
        this.cell_rows = cell_rows;
        this.cell_columns = cell_columns;
        this.matrix_rows = matrix_rows;
        this.matrix_columns = matrix_columns;
        this.matrix = matrix;
    }
    
}
