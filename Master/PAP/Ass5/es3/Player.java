
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luca
 */
public class Player extends Thread{
    
    private Flag f;
    private Sync s;
    private int turn;
    
    public Player(Flag f, Sync s, int turn)
    {
        this.f = f;
        this.s = s;
        this.turn = turn;
    }
    
    public void run()
    {
        while(true)
        {
            try 
            {
                s.waitForTurn(turn); //aspetta il proprio turno
                //controlla se c'è già stato un vincitore
                if(s.Exist_Winner())
                {
                    System.out.println("Thread n. " + turn + ": SOB");
                    return;
                }
                if(f.capture()) //prova a catturare la flag
                {
                    System.out.println("Thread n. " + turn + ": WON!");
                    return;
                }
                //passa il turno al player successivo
                s.next();
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }        
    }    
    
}
