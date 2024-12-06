package sudoku;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
/**
 * The main Sudoku program
 */
public class SudokuMain extends JFrame {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // private variables
   GameBoardPanel board = new GameBoardPanel();
   JButton btnNewGame = new JButton("New Game");

   // Constructor
   public SudokuMain() {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);

      // Add a button to the south to re-start the game via board.newGame()
      btnNewGame.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	         board.newGame();
               playSound("D:/download/cozy-lofi-beat-split-memmories-248205.wav");
            }
         });
         cp.add(btnNewGame, BorderLayout.SOUTH);  // Menambahkan tombol ke bagian bawah
         
         // Initialize the game board to start the game
         board.newGame();
         playSound("D:/download/cozy-lofi-beat-split-memmories-248205.wav");

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku Kelompok 7");
      setVisible(true);
   }
   private void playSound(String filePath) {
        try {
            // Load the audio file (make sure the path is correct)
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            // Get a Clip object for playback
            Clip clip = AudioSystem.getClip();
            // Open the clip with the audio stream
            clip.open(audioStream);
            // Start playing the audio
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
   
   }
}