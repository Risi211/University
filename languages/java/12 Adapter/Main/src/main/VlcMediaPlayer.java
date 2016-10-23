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
public class VlcMediaPlayer implements AdvancedMediaPlayer{

    @Override
    public void playVlc(String path) {
        System.out.println("Play vlc: " + path);
    }

    @Override
    public void playMp4(String path) {
        throw new UnsupportedOperationException("Not supported Mp4."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
