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
public class AddCommand extends ACommand{

    public AddCommand(IReceiver ir) {
        super(ir);
    }

    @Override
    public int execute() {
        ir.setAction(Action.Add);
        return ir.getResult();
    }
    
}
