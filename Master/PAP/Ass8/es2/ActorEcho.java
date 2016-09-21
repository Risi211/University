/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass08.es2;

import akka.actor.*;

/**
 *
 * @author luca
 */
public class ActorEcho extends UntypedActor {
    
        public void preStart() 
        {

        }
        
        @Override
        public void onReceive(Object msg) 
        {
            //ritorna il messaggio al sender
            getSender().tell(msg, getSelf());
        }        
    
}
