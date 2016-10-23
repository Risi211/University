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
public class SimplePlayer implements MediaPlayer{

    @Override
    public void play(String path, String format) {
        if(format.equals("mp3")){
            System.out.println("play mp3: " + path);
        }
        else{
            MediaAdapter adapter = new MediaAdapter();
            adapter.play(path, format);
        }
    }
    
}
