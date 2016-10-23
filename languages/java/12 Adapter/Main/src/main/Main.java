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
        SimplePlayer player = new SimplePlayer();
        player.play("aaa", "mp3");
        player.play("bbb", "vlc");
        player.play("ccc", "mp4");
    }
    
}
