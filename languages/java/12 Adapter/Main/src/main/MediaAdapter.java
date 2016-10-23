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
public class MediaAdapter implements MediaPlayer{

    AdvancedMediaPlayer adv;
    @Override
    public void play(String path, String format) {
        if(format.equals("vlc")){
            adv = new VlcMediaPlayer();
            adv.playVlc(path);
            return;
        }
        if(format.equals("mp4")){
            adv = new Mp4MediaPlayer();
            adv.playMp4(path);
            return;
        }
    }
    
}
