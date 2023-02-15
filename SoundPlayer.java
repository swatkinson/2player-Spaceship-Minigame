import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
    public void play(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
                    clip.open(inputStream);
                    clip.start();
                    Thread.sleep(clip.getMicrosecondLength() / 1000); // convert microseconds to milliseconds?
                } catch (Exception e) {
                    System.out.println("Play sound error: " + e.getMessage() + " for " + fileName);
                }
            }
        }).start();
    }
}
