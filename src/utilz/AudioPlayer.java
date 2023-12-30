package utilz;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class AudioPlayer {
    private Clip clip;

    public AudioPlayer(String filePath) {
    	 try {
             File audioFile = new File(filePath);
             AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
             clip = AudioSystem.getClip();
             clip.open(audioInputStream);
         } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
             e.printStackTrace();
         }
    }

    public void play() {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }
}

