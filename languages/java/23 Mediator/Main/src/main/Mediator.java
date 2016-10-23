/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lupin
 */
public class Mediator<T> implements IMediator<T> {

    private List<IColleague<T>> cols = new ArrayList<>();
    @Override
    public void distributeMsg(T s) {
        for(IColleague c : cols){
            c.receive(s);
        }
    }

    @Override
    public void registerColleague(IColleague<T> c) {
        cols.add(c);
    }
    
}
