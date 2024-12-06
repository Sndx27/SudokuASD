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

    private MusicPlayer musicPlayer;

   // private variables
        GameBoardPanel board = new GameBoardPanel();
        JButton btnNewGame = new JButton("New Game");
        JTextField statusBar = new JTextField("Welcome to Sudoku! by Kelompok 7");
        JProgressBar progressBar = new JProgressBar(0, SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE);
        Timer timer;
        int elapsedTime = 0; // in seconds
        int score = 0;


   // Constructor
   public SudokuMain(String playerName, int difficulty) {

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        cp.add(board, BorderLayout.CENTER);

        // Set icon for taskbar
        try {
            // Pastikan path gambar sesuai dengan lokasi file Anda
            ImageIcon icon = new ImageIcon("src/iamges/astolfoKAWAI.jpg");  // Ganti dengan path gambar Anda
            setIconImage(icon.getImage()); // Set ikon untuk aplikasi di taskbar
        } catch (Exception e) {
            System.out.println("Icon not found: " + e.getMessage());
        }

        // Start playing background music
        musicPlayer = new MusicPlayer();
        musicPlayer.playMusic("src\\audio\\The Notebooks - Stray Game OST (Original Soundtrack) (1).wav"); // Replace with your file path

        // The rest of your constructor logic...
        // Setup your game UI, buttons, etc.
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(playerName + "'s Sudoku");
        setVisible(true);

      // Add a button to the south to re-start the game via board.newGame()
      btnNewGame.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            board.newGame(difficulty);
            resetGame();
            updateStatusBar();
         }
      }); 


      JPanel southPanel = new JPanel(new BorderLayout());
      southPanel.add(btnNewGame, BorderLayout.NORTH); // button at the top
      southPanel.add(statusBar, BorderLayout.SOUTH);  // status bar 
      cp.add(southPanel, BorderLayout.SOUTH);  // Add the south panel to the main container

      // Initialize the game board to start the game
      board.newGame(difficulty);
      setJMenuBar(createMenuBar(difficulty));
      updateStatusBar();

      progressBar.setStringPainted(true);
      cp.add(progressBar, BorderLayout.NORTH);
      timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            elapsedTime++;
            updateStatusBar();
        }
      });
        timer.start();


        pack();     // Pack the UI components, instead of using setSize()
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // to handle window-closing
        setTitle(playerName+"'s Sudoku");
        setVisible(true);
   }
    public class ImagePanel extends JPanel {
        private Image backgroundImage;

        // Constructor untuk memuat gambar
        public ImagePanel(String imagePath) {
            try {
                backgroundImage = new ImageIcon(imagePath).getImage();
            } catch (Exception e) {
                System.out.println("Background image not found: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
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

    // Method to update the status bar with the number of cells remaining
    public void updateStatusBar() {
    int remainingCells = 0;
    for (int row = 0; row < SudokuConstants.GRID_SIZE; row++) {
        for (int col = 0; col < SudokuConstants.GRID_SIZE; col++) {
            Cell cell = board.cells[row][col];
            if (cell.status == CellStatus.TO_GUESS) {
                remainingCells++;
            }
        }
    }
    statusBar.setText("Cells remaining: " + remainingCells + " | Score: " + score + " | Time: " + elapsedTime + "s");
    progressBar.setValue(SudokuConstants.GRID_SIZE * SudokuConstants.GRID_SIZE - remainingCells);

    }
    public boolean isValidInput(int row, int col, int inputNumber) {
    // Check if the input number is between 1 and 9
    if (inputNumber < 1 || inputNumber > 9) {
        return false;
    }

    // Check the row
    for (int c = 0; c < SudokuConstants.GRID_SIZE; c++) {
        if (c != col && board.cells[row][c].number == inputNumber) {
            return false; // Conflict in the same row
        }
    }

    // Check the column
    for (int r = 0; r < SudokuConstants.GRID_SIZE; r++) {
        if (r != row && board.cells[r][col].number == inputNumber) {
            return false; // Conflict in the same column
        }
    }

    // Check the 3x3 grid
    int gridRowStart = (row / 3) * 3;
    int gridColStart = (col / 3) * 3;
    for (int r = gridRowStart; r < gridRowStart + 3; r++) {
        for (int c = gridColStart; c < gridColStart + 3; c++) {
            if ((r != row || c != col) && board.cells[r][c].number == inputNumber) {
                return false; // Conflict in the 3x3 grid
            }
        }
    }

    // If the input is valid, increase the score
    score += 10; // Increment score for a valid input
    return true; // No conflicts found
    }
// Main method
    public static void main(String[] args) {
        // Simulate passing the name and difficulty from WelcomeScreen
        SwingUtilities.invokeLater(() -> new SudokuMain("PlayerName", 2)); // Example player name and difficulty
    }

}