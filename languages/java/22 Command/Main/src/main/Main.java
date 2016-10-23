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
	//init
	Calculator calculator = new Calculator(30,20);
	AddCommand addCmd = new AddCommand(calculator);
	SubCommand subCmd = new SubCommand(calculator);
	//command
	ACommand command; //This will be used to invoke commands

	//simulate user behavior
	//press +
	command = addCmd;
	//execute command
	int result = command.execute();
        System.out.println("res: " + result);        
	//press -
	command = subCmd;
	result = command.execute();
        System.out.println("res: " + result);

    }
    
}
