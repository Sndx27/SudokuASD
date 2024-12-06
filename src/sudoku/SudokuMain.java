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
   public SudokuMain(String playerName, int difficulty) {
      Container cp = getContentPane();
      cp.setLayout(new BorderLayout());

      cp.add(board, BorderLayout.CENTER);

      // Add a button to the south to re-start the game via board.newGame()
      btnNewGame.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        board.newGame(difficulty);
    	    }
    	});
      cp.add(btnNewGame, BorderLayout.SOUTH);  // Menambahkan tombol ke bagian bawah

      // Initialize the game board to start the game
      board.newGame(difficulty);
      setJMenuBar(createMenuBar(difficulty));

      pack();     // Pack the UI components, instead of using setSize()
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
      setTitle("Sudoku");
      setVisible(true);
   }
   // Method to create the menu bar
   private JMenuBar createMenuBar(int difficulty) {
      JMenuBar menuBar = new JMenuBar();

      // File menu
      JMenu fileMenu = new JMenu("File");
      JMenuItem newGameItem = new JMenuItem("New Game");
      JMenuItem resetGameItem = new JMenuItem("Reset Game");
      JMenuItem exitItem = new JMenuItem("Exit");

      // Add action listeners for File menu items
      newGameItem.addActionListener(e -> board.newGame(difficulty));
      resetGameItem.addActionListener(e -> resetGame());
      exitItem.addActionListener(e -> System.exit(0));

      fileMenu.add(newGameItem);
      fileMenu.add(resetGameItem);
      fileMenu.addSeparator(); // Add a separator line
      fileMenu.add(exitItem);

      // Options menu (placeholder for now)
      JMenu optionsMenu = new JMenu("Options");
      JMenuItem optionsItem = new JMenuItem("Settings");
      optionsItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mohon bersabar, setting belum ada karena deadline mepet"));
      optionsMenu.add(optionsItem);

      // Help menu
      JMenu helpMenu = new JMenu("Help");
      JMenuItem aboutItem = new JMenuItem("About");
      aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this, "Dibuat Oleh :\nBurju  \nClay \nSandy \nSEMOGA DAPAT A! \n\n\nThanks Sir!!!"));
      helpMenu.add(aboutItem);

      // Add menus to the menu bar
      menuBar.add(fileMenu);
      menuBar.add(optionsMenu);
      menuBar.add(helpMenu);

      return menuBar;
   }

   // Method to reset the game (clears the board but keeps the puzzle)
   private void resetGame() {
      for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            Cell cell = board.cells[row][col];
            if (cell.status == CellStatus.TO_GUESS || cell.status == CellStatus.WRONG_GUESS) {
               cell.newGame(0, false); // Reset the cell
            }
         }
      }
   }
}