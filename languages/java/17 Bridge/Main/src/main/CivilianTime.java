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
public class CivilianTime extends Time{
    public CivilianTime(int h, int m, boolean p){
        ti = new CivilianTimeImpl(h, m, p);
    }
}
