package main.ResLoading;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

    private static Clip play;  // Keeping your original variable name. 

    public static void playMenuSound() {
        try {
            AudioInputStream menuSound = AudioSystem.getAudioInputStream(new File("res/Select Button.wav"));
            play = AudioSystem.getClip();
            play.open(menuSound);

            // Start playing the sound off at a lower volume
            setVolume(0.4f); // Example: start with 20% volume.

            play.start();
            while (!play.isRunning()) {
                Thread.sleep(10);
            }
            while (play.isRunning()) {
                Thread.sleep(10);
            }
            play.close();
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void playGameSound() {
        try {
            AudioInputStream gameSound = AudioSystem.getAudioInputStream(new File("res/Guns N Knife.wav"));
            play = AudioSystem.getClip();
            play.open(gameSound);

            setVolume(.6f); // Example: full volume by default.

            play.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (play != null) {
            play.stop();
            play.close();
        }
    }

    // New method to change the volume.
    public static void setVolume(float value) {
        if (play != null && play.isOpen()) {
            FloatControl volumeControl = (FloatControl) play.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            float dB = min + (max - min) * value;  // Convert linear scale to decibel value.
            volumeControl.setValue(dB);
        }
    }
}
