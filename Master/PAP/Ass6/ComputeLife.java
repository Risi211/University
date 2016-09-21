/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass06;

import javafx.scene.paint.Color;
import javax.swing.JTextField;
import javax.swing.*;
import java.util.concurrent.Callable;
/**
 *
 * @author luca
 */
 
 //questo è il codice di un task
 //il suo compito è quello di far evolvere la vita della
 //riga iesima a cui è stato assegnato.
public class ComputeLife implements Callable<Integer>{

    private int idTask;
    private final int row;   //riga di questo task
    private Point[][] rowPoints;
    private int points_rows; //#righe matrice
    private int points_columns;    //#colonne matrice    
    
    public ComputeLife(int idTask, Point[][] rowPoints, int row, int points_rows, int points_columns) 
    {
        this.idTask = idTask;
        this.row = row;
        this.points_rows = points_rows;
        this.points_columns = points_columns;        
        this.rowPoints = rowPoints;
    }    
    
    private void log(String msg) 
    {
            System.out.println(msg);
    }

    @Override
    public Integer call() 
    {     
        int cont_vivi = 0;
    //per ogni cella della riga aggiorna lo stato di vita della cella
            for(int i = 0; i < points_columns; i++)
            {
                /*
                una cella m[i,j] che nello stato s(t) è live e ha zero o al più una cella vicina live (e le altre dead), nello stato s(t+1) diventa dead (“muore di solitudine”)
                una cella m[i,j] che nello stato s(t) è live e ha quattro o più celle vicine live, nello stato s(t+1) diventa dead (“muore di sovrappopolamento”)
                una cella m[i,j] che nello stato s(t) è live e ha due o tre celle vicine live, nello stato s(t+1) rimane live (“sopravvive”)
                una cella m[i,j] che nello stato s(t) è dead e ha tre celle vicine live, nello stato s(t+1) diventa live         
                */        
                Point c = rowPoints[row][i];
                int num = getNumNeighboursLive(c);
                if(c.getOldLife()== Life.Dead && num == 3) //diventa live
                {
                    c.setLife(Life.Live);
                    cont_vivi++;
                    continue;
                }

                if((c.getOldLife() == Life.Live && num > 3) || (c.getOldLife() == Life.Live && num < 2)) //diventa dead
                {
                    c.setLife(Life.Dead);
                    continue;
                }            
                //nessun cambiamento se sono qui
                if(c.getOldLife() == Life.Live)
                {
                    cont_vivi++;
                    continue;
                }
            } 
        return cont_vivi;
    }

    private int getNumNeighboursLive(Point c)
    {
        int num_vicini_vivi = 0;
        
        if(getVicino(Direzione.Est, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        if(getVicino(Direzione.Nord, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        if(getVicino(Direzione.NordEst, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        if(getVicino(Direzione.NordOvest, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        if(getVicino(Direzione.Ovest, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        if(getVicino(Direzione.Sud, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        if(getVicino(Direzione.SudEst, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        if(getVicino(Direzione.SudOvest, c).getOldLife() == Life.Live)
        {
            num_vicini_vivi++;
        }
        
        return num_vicini_vivi;
    }
    
    private Point getVicino(Direzione d, Point current)
    {
        int i = current.getX();
        int j = current.getY();
        Point output = null;
        
        switch(d)
        {
            case NordOvest:
            {
                if(i == 0 && j == 0)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    return  rowPoints[points_rows - 1][points_columns -1];
                }
                if(i == 0)
                {
                    //in questo caso il suo vicino è nell'ultima riga
                    return rowPoints[points_rows - 1][j -1];
                }
                if(j == 0)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    return rowPoints[i - 1][points_columns -1];
                }
                //in tutti gli altri casi:
                return rowPoints[i - 1][j -1];
            }
            case Nord:
            {
                if(i == 0)
                {
                    //in questo caso il suo vicino è nell'ultima riga
                    return rowPoints[points_rows - 1][j];
                }
                //in tutti gli altri casi:
                return rowPoints[i - 1][j];
            }
            case NordEst:
            {
                if(i == 0 && j == points_columns - 1)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    return rowPoints[points_rows - 1][0];
                }
                if(i == 0)
                {
                    //in questo caso il suo vicino è nell'ultima riga
                    return rowPoints[points_rows - 1][j +1];
                }
                if(j == points_columns - 1)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    return rowPoints[i - 1][0];
                }
                //in tutti gli altri casi:
                return rowPoints[i - 1][j +1];
            }
            case Est:
            {
                if(j == points_columns - 1)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    return rowPoints[i][0];
                }
                //in tutti gli altri casi:
                return rowPoints[i][j +1];
            }
            case SudEst:
            {
                if(i == points_rows - 1 && j == points_columns - 1)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    return rowPoints[0][0];
                }
                if(i == points_rows - 1)
                {
                    //in questo caso il suo vicino è nell'ultima riga
                    return rowPoints[0][j +1];
                }
                if(j == points_columns - 1)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    return rowPoints[i + 1][0];
                }
                //in tutti gli altri casi:
                return rowPoints[i + 1][j +1];
            }
            case Sud:
            {
                if(i == points_rows - 1)
                {
                    //in questo caso il suo vicino è nell'ultima riga
                    return rowPoints[0][j];
                }
                //in tutti gli altri casi:
                return rowPoints[i + 1][j];
            }
            case SudOvest:
            {
                if(i == points_rows - 1 && j == 0)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    return rowPoints[0][points_columns -1];
                }
                if(i == points_rows - 1)
                {
                    //in questo caso il suo vicino è nell'ultima riga
                    return rowPoints[0][j -1];
                }
                if(j == 0)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    return rowPoints[i + 1][points_columns -1];
                }
                //in tutti gli altri casi:
                return rowPoints[i + 1][j -1];
            }
            case Ovest:
            {
                if(j == 0)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    return rowPoints[i][points_columns -1];
                }
                //in tutti gli altri casi:
                return rowPoints[i][j -1];
            }
        }
        return output;       
    }    

}
