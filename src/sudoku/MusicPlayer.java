/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #7
 * 1 - 5026231066 - Burju Ferdinand Harianja
 * 2 - 5026231132 - Clay Amsal Sebastian Hutabarat
 * 3 - 5026213181 - Sandythia Lova Ramadhani Krisnaprana
 */
package sudoku;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class MusicPlayer {
    private static Clip clip; // Make clip static so it persists across screens

    // Start playing music (loop continuously)
    public static void playMusic(String musicPath) {
        if (clip != null && clip.isRunning()) {
            return; // Don't restart the music if it's already playing
        }

        try {
            File audioFile = new File(musicPath); // Path to your music file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Stop the music when the application ends or when you want to stop it
    public static void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
