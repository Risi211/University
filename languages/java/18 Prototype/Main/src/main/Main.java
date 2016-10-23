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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<Stooge> s = new ArrayList<>();
        s.add(Factory.makeStooge(1));
        s.add(Factory.makeStooge(2));
        s.add(Factory.makeStooge(3));
        
        for (int i=0; i < s.size(); ++i)
           s.get(i).slap_stick();
    }
    
}
