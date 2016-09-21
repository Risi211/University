/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass08.es2;

import akka.actor.*;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author luca
 */
public class ActorMaster extends UntypedActor {
    
        private Griglia g;
        private List<ActorRef> players;
        private int num_players;
        private int cont_response = 0;
        private ActorRef controller;
        private JTextField[][] matrix;
        private int num_celle_vive;
        private JLabel lbl;
        
        public void preStart() 
        {

        }

        @Override
        public void onReceive(Object msg) 
        {   
           if(msg instanceof Griglia)
           {
               g = (Griglia)msg;
               log("Griglia ricevuta");
               controller = getSender(); //si salva il riferimento del controller
               num_players = g.cell_rows; //un giocatore per ogni riga
               matrix = g.matrix;
               lbl = g.lbl;
               //inizializza gli attori che lavorano su ogni riga della matrice
               players = new ArrayList<ActorRef>();
               for(int i = 0; i < num_players; i++)
               {
                   ActorRef p = getContext().actorOf(Props.create(ActorPlayer.class), "player-"+i);
                   players.add(p);
               }  
           }
           //risposta degli attori, hanno computato una riga di celle
           if(msg instanceof PlayerLifeComputed)
           {
               cont_response++;
               PlayerLifeComputed plc = (PlayerLifeComputed)msg;
               num_celle_vive += plc.num_vivi;
               if(cont_response == num_players)
               {
                   //ho ricevuto le risposte da tutti gli attori
                   cont_response = 0;
                    log("Tutti i player aggiornano lo stato delle celle ");
                    for(int i = 0; i < num_players; i++)
                    {
                        ActorRef p = players.get(i);
                        p.tell(new UpdateLife(), getSelf());
                    }
               }
           }
           if(msg instanceof PlayerLifeUpdated)
           {
               cont_response++;
               if(cont_response == num_players)
               {
                   //ho ricevuto le risposte da tutti gli attori
                   cont_response = 0;
                   //aggiorna label contatore celle vive
                    final int n = num_celle_vive;
                    SwingUtilities.invokeLater( () -> lbl.setText("Num Vivi = " + n));
                    log("ritorna il controllo all' ActorController");
                    controller.tell(new MasterResponse(), getSelf()); 
               }               
           }      
           //esegue il gioco della vita
           if(msg instanceof MsgStart)
           {
                StartCycle();
           }

        }

        private void StartCycle() 
        {
            log("inizo ciclo gioco della vita");
            cont_response = 0;
            num_celle_vive = 0;
            //fa partire la computazione a tutti i giocatori
            for(int i = 0; i < num_players; i++)
            {
                ActorRef p = players.get(i);
                //ogni player controlla una colonna
                ColonnaGriglia c = new ColonnaGriglia(g.cells[i], i);
                ColonnaGriglia prec;
                ColonnaGriglia succ;
                if(i == 0)
                {
                    prec = new ColonnaGriglia(g.cells[g.cell_rows - 1], g.cell_rows - 1);
                    succ = new ColonnaGriglia(g.cells[i + 1], i + 1);
                }
                else
                {
                     if(i == g.cell_rows - 1)
                     {
                         prec = new ColonnaGriglia(g.cells[i - 1], i - 1);
                         succ = new ColonnaGriglia(g.cells[0], 0);
                     }
                     else
                     {
                         prec = new ColonnaGriglia(g.cells[i - 1], i - 1);
                         succ = new ColonnaGriglia(g.cells[i + 1], i + 1);
                     }                       
                }
                //invia al player le colonne a lui necessarie
                JTextField[] matrix_row = null;
                if(i < g.matrix_rows)
                {
                    matrix_row = matrix[i];
                }
                p.tell(new ColonneGriglia(c, prec, succ, g.cell_rows, g.cell_columns, g.matrix_rows, g.matrix_columns, matrix_row), getSelf());
                //fapartire la computazione
                p.tell(new ComputeLife(), getSelf()); 
            }            
        }                
        
        private void log(String msg)
        {
                System.out.println("[ActorMaster] "+msg);
        }    
    
    
}
