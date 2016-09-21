/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pap.ass06;

import java.util.concurrent.*;
import javax.swing.JTextField;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author luca.parisi2
 */
 
//GameOfLife è il master che viene chiamato ad ogni iterazione
//del gioco della vita dal Controller.
//Crea n task, uno per ogni riga della matrice dei punti
//dopo che ogni cella è stata computata, aggiorna lo stato di vita
//di ogni punto della matrice rowPoints e le celle viste nella form.
public class GameOfLife extends Thread {

    //private ScheduledExecutorService executor;
    private ExecutorService executor;
    private JTextField[][] matrix;
    private int matrix_rows = 0;
    private int matrix_columns = 0;
    private int poolSize;
    private Point[][] rowPoints;
    private int points_rows = 0;
    private int points_columns = 0;    
    private JLabel lbl;
    
    public GameOfLife (int poolSize, JTextField[][] matrix, int matrix_rows, int matrix_columns, Point[][] rowPoints, int points_rows, int points_columns, JLabel lbl)
    {		
        this.matrix = matrix;
        this.matrix_rows = matrix_rows;
        this.matrix_columns = matrix_columns;
        this.points_rows = points_rows;
        this.points_columns = points_columns;        
        //executor = Executors.newScheduledThreadPool(poolSize);
        executor = Executors.newFixedThreadPool(poolSize);
        this.poolSize = poolSize;
        this.rowPoints = rowPoints;
        this.lbl = lbl;
    }

    public void run()
    { 


                try
                {
                    Set<Future<Integer>> resultSet = new HashSet<Future<Integer>>();
                    int idTask = 0;	
                    for (int i=0; i<points_rows; i++) 
                    {
                        //crea il task per la riga i di celle
                        //executor.scheduleAtFixedRate(new ComputeLife(idTask, flag, matrix, matrix_rows, matrix_columns, cells, i, points_rows, points_columns), 1, 500, TimeUnit.MILLISECONDS);
                        //executor.execute(new ComputeLife(idTask, rowPoints, i, points_rows, points_columns, matrix, matrix_rows, matrix_columns));
                        Future<Integer> res = executor.submit(new ComputeLife(idTask, rowPoints, i, points_rows, points_columns));
                        resultSet.add(res);
                        log("submitted task " + idTask++);           
                    }	

                    executor.shutdown();
                    executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
                    
                    //sommo le celle vive:
                    int sum = 0;
                    for (Future<Integer> future : resultSet) {
                        try 
                        {
                            sum += future.get();
                        } 
                        catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }                    
                    final int sumf = sum;
                    SwingUtilities.invokeLater( () -> lbl.setText("Num Vivi = " + sumf));                    
                    
                    //genera altri task per aggiornare lo stato di vita dei punti e le celle della matrice della form
                    ExecutorService executor2 = Executors.newFixedThreadPool(poolSize);
                    idTask = 0;	
                    for (int i=0; i<points_rows; i++) 
                    {
                        //crea il task per la riga i di celle
                        //executor.scheduleAtFixedRate(new ComputeLife(idTask, flag, matrix, matrix_rows, matrix_columns, cells, i, points_rows, points_columns), 1, 500, TimeUnit.MILLISECONDS);
                        executor2.execute(new UpdateLife(idTask, rowPoints, i, points_rows, points_columns, matrix, matrix_rows, matrix_columns));
                        log("submitted second task " + idTask++);           
                    }	

                    executor2.shutdown();
                    executor2.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);                    
                    
                    
                }
                catch(Exception ex)
                {
                    ex.printStackTrace();
                }
        
    }        
    
    private void log(String msg)
    {
        System.out.println("[SERVICE] "+msg);
    }
  
}
