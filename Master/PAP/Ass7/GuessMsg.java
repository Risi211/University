/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass07;

/**
 *
 * @author luca
 */
public class GuessMsg {
    
    private int num_guessed;
    
    public GuessMsg(int num)
    {
        num_guessed = num;
    }
    
    public int getNumGuessed()
    {
        return num_guessed;
    }
    
}
