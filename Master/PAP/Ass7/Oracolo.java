/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass07;

import akka.actor.*;
import java.util.*;
import java.util.Random;

/**
 *
 * @author luca
 */
public class Oracolo extends UntypedActor {
    
        private int num_to_guess;
        private int nRisposte;
        private int nPlayers;
        private int turno;
        private List<ActorRef> players;
        private int index;

        public void preStart() 
        {
                log("starting Oracolo");
                Random r = new Random(System.currentTimeMillis());
                num_to_guess = r.nextInt(1000);
                log("creato numero da indovinare: (" + num_to_guess + ")");
                
                System.out.print("Immetti il numero di players: ");
                String input = System.console().readLine();                
                nPlayers = Integer.parseInt(input);
                
                players = new ArrayList<ActorRef>();
                for (int i = 0; i < nPlayers; i++)
                {
                    ActorRef peer = getContext().actorOf(Props.create(Player.class), "player-"+i);
                    players.add(peer);
                }
                log("Players created.");
                log("Turno 1 starting");
               index = 0;
               players.get(index).tell(new TurnMsg(), getSelf());
               
               nRisposte = 0;
               turno = 1;
        }

        @Override
        public void onReceive(Object msg) 
        {                            
                //controllo se il giocatore che ha risposto ha vinto
                if(msg instanceof GuessMsg)
                {
                    nRisposte++;
                    GuessMsg m = (GuessMsg) msg;
                    if(m.getNumGuessed() == num_to_guess)
                    {
                        //il giocatore ha vinto
                        ActorRef winner = getContext().sender();
                        winner.tell(new WinMsg(), getSelf());
                        
                        //gli altri giocatori hanno perso
                        for (ActorRef p: players)
                        {
                            if(p.toString() != winner.toString())
                            {
                                p.tell(new SobMsg(), getSelf());  
                            }
                        }                        
                        
                        //si ferma l'oracolo
                        log("Fine gioco");
                        getContext().stop(getSelf()); //ferma attore
                    }
                    
                    //se il giocatore non ha indovinato, restituisce un suggerimento
                    if(m.getNumGuessed() < num_to_guess)
                    {
                        ActorRef player = getContext().sender();
                        player.tell(new AdviceMsg(true), getSelf());
                    }

                    //se il giocatore non ha indovinato, restituisce un suggerimento
                    if(m.getNumGuessed() > num_to_guess)
                    {
                        ActorRef player = getContext().sender();
                        player.tell(new AdviceMsg(false), getSelf());
                    }

                     //tocca al giocatore successivo
                    if (nRisposte == nPlayers)
                    {
                        index = 0;
                        nRisposte = 0;
                    }
                    else
                    {
                        index++;
                    }
                    
                    log("Turno " + turno + " finished");
                    //nuovo turno
                    turno++;

                    //avviso tutti i giocatori che devono reindovinare
                     log("Turno " + turno + " starting");
                     players.get(index).tell(new TurnMsg(), getSelf());                    
                }                
        }

        private void log(String msg)
        {
                System.out.println("[Oracolo] "+msg);
        }


}
