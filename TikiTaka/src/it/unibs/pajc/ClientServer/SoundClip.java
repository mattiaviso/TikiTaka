package it.unibs.pajc.ClientServer;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundClip  implements  Runnable{

    private Clip clip;


    private long lastSoundPlayTime = 0;
    private static final long MIN_SOUND_PLAY_INTERVAL = 10000; // Intervallo minimo tra due riproduzioni in millisecondi

    /**
     * Costruttore della classe per la riproduzione di file audio
     *
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

    }

    /**
     * Metodo che esegue SOLO UNA VOLTA l'audio
     */

    public void startSound()  {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSoundPlayTime >= MIN_SOUND_PLAY_INTERVAL) {
            FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volume.setValue(-6);
            clip.start();
            lastSoundPlayTime = currentTime;
        }
    }

    /**
     * Metodo usato per stoppare la riproduzione dell'audio
     */
    public void stop() {

    }

    @Override
    public void run() {
        startSound();
    }

    public void play() {
        Thread audioThread = new Thread(this);
        audioThread.start();
        clip.drain();
        clip.stop();
    }
}
