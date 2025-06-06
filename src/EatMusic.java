import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class EatMusic {
    private static EatMusic instance = null;
    private Clip clip;

    private EatMusic() {}

    public static EatMusic getInstance2() {
        if (instance == null) {
            instance = new EatMusic();
        }
        return instance;
    }

    public void playMusic(String filePath) {
        try {
            if (clip != null && clip.isRunning()) {
                return; // Music is already playing
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

            // Start playing the sound
            clip.start();

            // Optionally, add a listener to handle the completion of the sound
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close(); // Close the clip when the sound finishes
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopMusic() {
        if (clip != null) {
            clip.stop();
            clip.close();
        }
    }

    public void pauseMusic() {
        if (clip != null) {
            clip.stop();
        }
    }

    public void resumeMusic() {
        if (clip != null) {
            clip.start();
        }
    }


}
