package edu.virginia.engine.util;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.*;


public class Sound
{
    private Clip clip;


    public Sound (String filename) {
        try {
            String filepath = ("resources" + File.separator + filename);
            File file = new File(filepath);
            AudioInputStream input = AudioSystem.getAudioInputStream(file);
            if (input == null) {
                System.err.println("[DisplayObject.setImage] ERROR: file does not exist!");
            }
            clip = AudioSystem.getClip();
            clip.open(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            if (clip != null) {
                clip.stop();
                clip.setFramePosition(0);
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if(clip == null) return;
        clip.stop();
    }

    public void loop() {
        try {
            if(clip != null) {

                clip.stop();
                clip.setFramePosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isActive() {
        return clip.isActive();
    }


}
