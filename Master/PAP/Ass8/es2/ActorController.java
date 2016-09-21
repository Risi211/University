/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass08.es2;

import akka.actor.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luca
 */
public class ActorController extends UntypedActor {
    
        FlagMonitor flag;
        ActorRef master;    
        ActorRef echo;
        
        public void preStart() 
        {

        }

        @Override
        public void onReceive(Object msg) 
        {                            
           if(msg instanceof MsgLoad) //sarebbe un prestart
           {
               log("inizializzazione");
               MsgLoad ml = (MsgLoad)msg;
               flag = ml.flag;
               //creo attore master
               master = getContext().actorOf(Props.create(ActorMaster.class), "ActorMaster");
               master.tell(ml.g, getSelf()); //inizializza master
               //creo attore echo
               echo = getContext().actorOf(Props.create(ActorEcho.class), "ActorEcho");
           }
           //parte il master per la prima volta
           if(msg instanceof MsgStart)
           {               
               log("va in stand by");
                echo.tell(new MasterResponse(), getSelf());
           }
            //il master ha finito un ciclo del gioco della vita
           if(msg instanceof MasterResponse)
           {
               try 
               {
                   //si sospende per 200ms
                   log("si sospende per 200ms");
                   Thread.sleep(200);                   
               } 
               catch (InterruptedException ex) 
               {
                   Logger.getLogger(ActorController.class.getName()).log(Level.SEVERE, null, ex);
               }
               //se la flag è true, fa ripartire il master
               if(flag.isStarted())
               {
                   master.tell(new MsgStart(), getSelf());
                   log("Avvia il master per un ciclo del gioco della vita");
               }
               else
               {
                   //altrimenti manda un messaggio a se stesso
                   //la getSender restituisce questo stesso attore
                   //perchè il Master ha barato, ha messo il controller come sender
                   echo.tell(new MasterResponse(), getSelf());
                   log("manda la MasterResponse a se stesso");
               }               
           }
        }        
        
        private void log(String msg)
        {
                System.out.println("[ActorController] "+msg);
        }    
    
}
