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
public class Caretaker<T> {
    private List<Memento<T>> list = new ArrayList<>();
    public void saveState(Originator<T> orig){
        list.add(orig.createMemento());
    }
    public void restoreState(Originator<T> orig, int stateNumber){
        orig.setMemento(list.get(stateNumber));
    }
}
