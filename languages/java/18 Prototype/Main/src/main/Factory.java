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
public final class Factory {
    private static Stooge[] prototypes = new Stooge[]{null, new Carl(), new Larry(), new Moe()};
    public static Stooge makeStooge(int choice){
        return prototypes[choice].clone();
    }
}
