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
public class Composite implements Component {

    private String name;
    List<Component> children = new ArrayList<>();
    public Composite(String n){
        name = n;
    }
    public void insertChild(Component c){
        children.add(c);
    }
    @Override
    public void traverse() {
        System.out.println(name);
        int length = children.size();
        for(int i = 0; i < length; ++i){
            children.get(i).traverse();
        }
    }
    
}
