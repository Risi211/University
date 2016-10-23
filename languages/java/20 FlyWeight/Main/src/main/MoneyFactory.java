/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author lupin
 */
public class MoneyFactory {
    Money[] m = new Money[]{new MetallicMoney(), new PaperMoney()};
    public MoneyFactory(){
    
    }
    public Money getMoneyToDisplay(MoneyType type){
        switch(type){
            case Metallic:{
                return m[0];
            }
            case Paper:{
                return m[1];
            }
        }
        return null;
    }
}
