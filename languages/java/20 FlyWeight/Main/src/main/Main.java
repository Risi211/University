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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	final int SUP=100; // <--- Suppose this is one million :)
	MoneyFactory factory = new MoneyFactory();
	for (int i = 0; i < SUP; ++i)
	{
		Money graphicalMoney;
		if (i < 10)
		  graphicalMoney = factory.getMoneyToDisplay(MoneyType.Metallic);
		else
		  graphicalMoney = factory.getMoneyToDisplay(MoneyType.Paper);
		
		graphicalMoney.getDisplayOfMoneyFalling(i);
	}  
    }
    
}
