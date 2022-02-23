package it.unibs.pajc;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


public class SoundClip {
	
    private Clip clip;
    private boolean playing = false;
    
    public SoundClip(String s) {      
        try {      
            File soundFile = new File(s+".wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);              
            clip = AudioSystem.getClip();
            clip.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public void start(){
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-15);
        clip.loop(50);
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
}
