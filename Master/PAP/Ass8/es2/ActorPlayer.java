/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass08.es2;

import akka.actor.*;
import java.util.List;
import java.util.Random;

import javax.swing.JTextField;
import javax.swing.*;

/**
 *
 * @author luca
 */
public class ActorPlayer extends UntypedActor {
    
        private ColonnaGriglia cg;
        private ColonnaGriglia precedente;
        private ColonnaGriglia successiva;
        private JTextField[] matrix; //eventuale riga di JTextField che rappresentano le celle visualizzate nella form
        private int cell_rows;
        private int cell_columns;
        private int matrix_rows;
        private int matrix_columns;
        private int cont_vivi = 0;
        
        public void preStart() 
        {

        }
	
        @Override
        public void onReceive(Object msg) 
        {
            if(msg instanceof ColonneGriglia) //inizializzazione
            {
                log("ricevute colonne della griglia");
                ColonneGriglia cc = (ColonneGriglia)msg;
                cg = cc.c;
                cell_rows = cc.cell_rows;
                cell_columns = cc.cell_columns;
                matrix_rows = cc.matrix_rows;
                matrix_columns = cc.matrix_columns;
                precedente = cc.prec;
                successiva = cc.succ;
                matrix = cc.matrix;
            }
            if(msg instanceof ComputeLife) //aggiorna stato delle celle
            {
                compute();
                //restituisce al master la risposta
                getSender().tell(new PlayerLifeComputed(cont_vivi), getSelf());
            }
            if(msg instanceof UpdateLife) //aggiorna nuovo stato di vita delle celle e le disegna nella form
            {
                update();
                //restituisce al master la risposta
                getSender().tell(new PlayerLifeUpdated(), getSelf());
            }                                    
        }
        
        private void compute()
        {
            cont_vivi = 0; //azzera contatore celle vive
            //per ogni cella della riga aggiorna lo stato di vita della cella
            for(int i = 0; i < cell_columns; i++)
            {
                /*
                una cella m[i,j] che nello stato s(t) è live e ha zero o al più una cella vicina live (e le altre dead), nello stato s(t+1) diventa dead (“muore di solitudine”)
                una cella m[i,j] che nello stato s(t) è live e ha quattro o più celle vicine live, nello stato s(t+1) diventa dead (“muore di sovrappopolamento”)
                una cella m[i,j] che nello stato s(t) è live e ha due o tre celle vicine live, nello stato s(t+1) rimane live (“sopravvive”)
                una cella m[i,j] che nello stato s(t) è dead e ha tre celle vicine live, nello stato s(t+1) diventa live         
                */        
                Point c = cg.cells[i];
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
        }
        
        private void update()
        {
            //per ogni cella della riga aggiorna lo stato di vita della cella
            for(int i = 0; i < cell_columns; i++)
            { 
                Point c = cg.cells[i];
                c.UpdateLife();
                //le celle possono essere di più della griglia vista sul form
                //se la cella è visualizzata nella form, viene aggiornata
                if(cg.index < matrix_rows  && i < matrix_columns) 
                {
                    if(c.getNewLife() == Life.Dead)
                    {
                        final int jj = i;
                        SwingUtilities.invokeLater( () -> matrix[jj].setBackground(java.awt.Color.WHITE));                
                    }
                    else //live
                    {
                        final int jj = i;                    
                        SwingUtilities.invokeLater( () -> matrix[jj].setBackground(java.awt.Color.BLACK));                                
                    }                    
                }
                
            }             
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
        int i = current.getX(); //riga
        int j = current.getY(); //colonna
        Point output = null;
        
        switch(d)
        {
            case NordOvest:
            {
                if(j == 0)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    //return  rowPoints[points_rows - 1][points_columns -1];
                    return precedente.cells[cell_columns - 1];
                }
                //in tutti gli altri casi:
                //return rowPoints[i - 1][j -1];
                return precedente.cells[j - 1];
            }
            case Nord:
            {
                //in tutti gli altri casi:
                //return rowPoints[i - 1][j];
                return precedente.cells[j];
            }
            case NordEst:
            {
                if(j == cell_columns - 1)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    //return rowPoints[points_rows - 1][0];
                    return precedente.cells[0];
                }
                //in tutti gli altri casi:
                //return rowPoints[i - 1][j +1];
                return precedente.cells[j + 1];
            }
            case Est:
            {
                if(j == cell_columns - 1)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    //return rowPoints[i][0];
                    return cg.cells[0];
                }
                //in tutti gli altri casi:
                //return rowPoints[i][j +1];
                return cg.cells[j + 1];
            }
            case SudEst:
            {
                if(j == cell_columns - 1)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    //return rowPoints[0][0];
                    return successiva.cells[0];
                }
                //in tutti gli altri casi:
                //return rowPoints[i + 1][j +1];
                return successiva.cells[j + 1];
            }
            case Sud:
            {
                //in tutti gli altri casi:
                //return rowPoints[i + 1][j];
                return successiva.cells[j];
            }
            case SudOvest:
            {
                if(j == 0)
                {
                    //in questo caso il suo vicino è l'altro estremo
                    //return rowPoints[0][points_columns -1];
                    return successiva.cells[cell_columns - 1];
                }
                //in tutti gli altri casi:
                //return rowPoints[i + 1][j -1];
                return successiva.cells[j - 1];
            }
            case Ovest:
            {
                if(j == 0)
                {
                    //in questo caso il suo vicino è nell'ultima colonna
                    //return rowPoints[i][points_columns -1];
                    return cg.cells[cell_columns - 1];
                }
                //in tutti gli altri casi:
                //return rowPoints[i][j -1];
                return cg.cells[j - 1];
            }
        }
        return output;       
    }    
        
        
        private void log(String msg){
                System.out.println("[ActorPlayer-"+getSelf()+"] "+msg);
        }
        
}