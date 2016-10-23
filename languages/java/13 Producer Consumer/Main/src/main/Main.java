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
        List<Integer> sharedQueue = new ArrayList<>();
        int size = 4;
        Thread prodThread = new Thread(new Producer(sharedQueue, size), "Producer1");
        Thread prodThread2 = new Thread(new Producer(sharedQueue, size), "Producer2");
        Thread consThread = new Thread(new Consumer(sharedQueue, size), "Consumer");
        prodThread.start();
        prodThread2.start();
        consThread.start();
    }
    
}
