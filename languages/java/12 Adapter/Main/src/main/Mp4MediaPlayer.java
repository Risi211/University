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
public class Mp4MediaPlayer implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String path) {
        throw new UnsupportedOperationException("Not supported Vlc."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playMp4(String path) {
        System.out.println("play mp4: " + path);
    }
    
}
