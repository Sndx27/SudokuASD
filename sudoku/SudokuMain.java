package sudoku;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    	    }
    	});
      cp.add(btnNewGame, BorderLayout.SOUTH);  // Menambahkan tombol ke bagian bawah

      // Initialize the game board to start the game
      board.newGame();

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
      setVisible(true);
   }

   
}