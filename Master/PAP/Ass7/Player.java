/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass07;

import akka.actor.*;
import java.util.List;
import java.util.Random;

/**
 *
 * @author luca
 */
public class Player extends UntypedActor {
    
        private int num_guessed; 
        private int min;  //il numero da indovinare è maggiore del minimo
        private int max; //il numero da indovinare è minore del  massimo
	  
        public void preStart() 
        {
            //inizializzazione del player
            min = 0; //numero minimo di partenza
            max = 1000; //numero massimo di partenza
            num_guessed = 0;
        }
	
        @Override
        public void onReceive(Object msg) 
        {
            if (msg instanceof TurnMsg) //l'oracolo dice al player che può provare ad indovinare
            {
                    ActorRef oracolo = getContext().sender();

                    Random r = new Random(System.currentTimeMillis());
                    num_guessed = r.nextInt(max - min + 1) + min; //genero un numero nel range plausibile
                    
                    oracolo.tell(new GuessMsg(num_guessed), getSelf());
                    
                    log("has sent num to guess to Oracolo");

            }
            
            if(msg instanceof AdviceMsg) //suggerimento
            {
                AdviceMsg a = (AdviceMsg) msg;
                if(a.isSuperiore())
                {
                    min = num_guessed;
                }
                else //è inferiore
                {
                    max = num_guessed;
                }
            }
            
            if(msg instanceof WinMsg) 
            {
                log("won!");
                getContext().stop(getSelf());
            }
            
            if(msg instanceof SobMsg)
            {
                log("sob!");
                getContext().stop(getSelf());
            }
        }
        
        private void log(String msg){
                System.out.println("[Player-"+getSelf()+"] "+msg);
        }
        
}
