package it.unibs.pajc.ClientServer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundClip  {

    private Clip clip;
    private boolean playing = false;

    /**
     * Costruttore della classe per la riproduzione di file audio
     * @param s Filepath dell'audio che vogliamo usare
     */
    public SoundClip(String s) {
        try {
            File soundFile = new File(s + ".wav");
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

    /**
     * Metodo che se invocato riproduce il file audio
     * Continua all'infinito
     */
    public void start() {
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-25);
        clip.loop(50);
        clip.start();
    }

    /**
     * Metodo che esegue SOLO UNA VOLTA l'audio
     */
    public void startSound(){
        FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volume.setValue(-6);
        clip.start();
    }

    /**
     * Metodo usato per stoppare la riproduzione dell'audio
     */
    public void stop() {
        clip.stop();
    }
}
