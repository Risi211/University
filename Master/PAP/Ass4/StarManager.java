/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass4;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author luca
 */
public class StarManager extends Thread{
    
    private int width;
    private int height;
    private Star s;
    private Random generator;
    
    //Logica di Controllo
    //per 20 volte chiama sempre lo stesso metodo per spostare l'asterisco, 
    //ad ogni chiamata aumenta il count. Quando arriva a 8, genero un nuovo numero random da 1 a 8, 
    //a cui associo ad ogni numero un metodo di move per l'asterisco
    private int count = 0; 
    private int num = 0;
    private TextLib instance;
    
    public StarManager(TextLib instance, int width, int height, long seed)
    {
        this.instance = instance;
        this.width = width;
        this.height = height;
        s = new Star((width / 2), (height / 2), instance);        
        //inizializza sequenza random pseudo-casuale
        generator = new Random(seed);
        num = generator.nextInt(8) + 1; //inizializza il numero random che indica il metodo di spostamento per le prime 20 iterazioni
    }
    
    public void run()
    {
        //scrive l'asterisco nel centro delle coordinate passate (inizializzazione)
        instance.writeAt((width / 2), (height / 2), "*");
        while(true)
        {
            moveStar();
            try 
            {
				//sposta l'asterisco 10 volte al secondo
                sleep(100);
            } 
            catch (InterruptedException ex) 
            {
                Logger.getLogger(StarManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void moveStar()
    {   
        //controllo se l'aserisco si trova nel confine:
        if(isInBoundaries())
        {
            //l'azione è già stata fatta dal metodo isInBoundaries
            count++;
            return;
        }
        if(count == 8)
        {
            //genero un numero tra 1 e 8
            num = generator.nextInt(8) + 1;            
            count = 0;
        }
        //esegue l'azione di spostamento dell'asterisco
        chooseAction(num);
        count++;
    }
    
	//in base al numero passato in input,
	//sceglie quale metodo chiamare e spostare l'asterisco di conseguenza
    private void chooseAction(int num)
    {
        switch(num)
        {
            case 1:
            {
                s.MoveLeft();
                break;
            }
            case 2:
            {
                s.MoveRight();
                break;
            }
            case 3:
            {
                s.MoveUp();
                break;
            }
            case 4:
            {
                s.MoveDown();
                break;
            }
            case 5:
            {
                s.MoveDiagonalPosPlus();
                break;
            }
            case 6:
            {
                s.MoveDiagonalPosMinus();
                break;
            }
            case 7:
            {
                s.MoveDiagonalNegPlus();
                break;
            }
            case 8:
            {
                s.MoveDiagonalNegMinus();
                break;
            }    
        }
    }

	//quando l'asterisco arriva al confine destro, deve andare a destra
	//sceglie quale metodo chiamare e spostare l'asterisco di conseguenza
    private void BackLeft()
    {
		num = 1; //moveLeft();
                s.MoveLeft();
		count = 0;
    }
	
	//quando l'asterisco arriva al confine sinistro, deve andare a destra
	//sceglie quale metodo chiamare e spostare l'asterisco di conseguenza
    private void BackRight()
    {
            num = 2; //moveRight();
            s.MoveRight();
            count = 0;
    }

	//quando l'asterisco arriva al confine in basso, deve andare a destra
	//sceglie quale metodo chiamare e spostare l'asterisco di conseguenza
    private void BackUp()
    {
            num = 3; //moveUp();
            s.MoveUp();
            count = 0;
    }	

	//quando l'asterisco arriva al confine in alto, deve andare a destra
	//sceglie quale metodo chiamare e spostare l'asterisco di conseguenza	
    private void BackDown()
    {
            num = 4; //moveDown();
            s.MoveDown();
            count = 0;
    }
	
	//controlla se l'asterisco è attualmente vicino al confine
	//nel caso lo rimanda indietro e restituisce true
    private boolean isInBoundaries()
    {
        if(s.x == 1)
        {
            BackRight();
            return true;
        }
        if(s.x == width - 1)
        {
            BackLeft();
            return true;
        }
        if(s.y == 1)
        {
            BackDown();
            return true;
        }
        if(s.y == height - 1)
        {
            BackUp();
            return true;
        }  
        return false;
    }
}
