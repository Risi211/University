/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pap.ass4;

/**
 *
 * @author luca
 */
public class Star {
    
    int x;
    int y;
    private TextLib controller;
    
    public Star(int x, int y, TextLib controller)
    {
        this.x = x;
        this.y = y;
        this.controller = controller;
    }
    
    public void MoveLeft()
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x - 1, y, "*");
        x = x - 1;
    }
    
    public void MoveRight()
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x + 1, y, "*");
        x = x + 1;        
    }
    
    public void MoveUp()
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x, y - 1, "*");
        y = y - 1;        
    }    
    
    public void MoveDown()
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x, y + 1, "*");
        y = y + 1;                
    }    
    
    public void MoveDiagonalPosPlus() //pos indica il coefficiente angolare della diagonale
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x + 1, y - 1, "*");
        x = x + 1;
        y = y - 1;                
    }
    
    public void MoveDiagonalPosMinus() //pos indica il coefficiente angolare della diagonale
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x - 1, y + 1, "*");
        x = x - 1;
        y = y + 1;                
    }    
    
    public void MoveDiagonalNegPlus() //pos indica il coefficiente angolare della diagonale
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x + 1, y + 1, "*");
        x = x + 1;
        y = y + 1;                
    }
    
    public void MoveDiagonalNegMinus() //pos indica il coefficiente angolare della diagonale
    {
        controller.writeAt(x, y, " ");
        controller.writeAt(x - 1, y - 1, "*");
        x = x - 1;
        y = y - 1;                
    }    
    
}
