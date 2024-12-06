package sudoku;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoardPanel extends JPanel {
   private static final long serialVersionUID = 1L;  // to prevent serial warning

   // Define named constants for UI sizes
   public static final int CELL_SIZE = 60;   // Cell width/height in pixels
   public static final int BOARD_WIDTH  = CELL_SIZE * SudokuConstants.GRID_SIZE;
   public static final int BOARD_HEIGHT = CELL_SIZE * SudokuConstants.GRID_SIZE;
                                             // Board width/height in pixels

   // Define properties
   /** The game board composes of 9x9 Cells (customized JTextFields) */
   private Cell[][] cells = new Cell[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   /** It also contains a Puzzle with array numbers and isGiven */
   private Puzzle puzzle = new Puzzle();

   /** Constructor */
   public GameBoardPanel() {
      super.setLayout(new GridLayout(SudokuConstants.GRID_SIZE, SudokuConstants.GRID_SIZE));  // JPanel

      // Allocate the 2D array of Cell, and added into JPanel.
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            cells[row][col] = new Cell(row, col);
            super.add(cells[row][col]);   // JPanel
         }
      }

      // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
      //  Cells (JTextFields)
      CellInputListener listener = new CellInputListener();

      // [TODO 4] Adds this common listener to all editable cells
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row ) {
    	   for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col ) {
    	      if (cells[row][col].isEditable()) {
    	      // For all editable rows and cols
             cells[row][col].addActionListener(listener);
    	      }
    	   }
    	}

      super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
   }

   /**
    * Generate a new puzzle; and reset the game board of cells based on the puzzle.
    * You can call this method to start a new game.
    */
   public void newGame() {
      // Generate a new puzzle
      puzzle.newPuzzle(2);

      // Initialize all the 9x9 cells, based on the puzzle.
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
         }
      }
   }

   /**
    * Return true if the puzzle is solved
    * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
    */
   public boolean isSolved() {
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            if (cells[row][col].status == CellStatus.TO_GUESS || cells[row][col].status == CellStatus.WRONG_GUESS) {
               return false;
            }
         }
      }
      return true;
   }

// [TODO 2] Define a Listener Inner Class for all the editable Cells
   private class CellInputListener implements ActionListener {
      @Override
      public void actionPerformed(ActionEvent e) {
         // Get a reference of the JTextField that triggers this action event
         Cell sourceCell = (Cell)e.getSource();
		 
         // Retrieve the int entered
         int numberIn = Integer.parseInt(sourceCell.getText());
         // For debugging
         System.out.println("You entered " + numberIn);

         
//          * [TODO 5] (later - after TODO 3 and 4)
//          * Check the numberIn against sourceCell.number.
//          * Update the cell status sourceCell.status,
//          * and re-paint the cell via sourceCell.paint().
          
          if (numberIn == sourceCell.number) {
             sourceCell.status = CellStatus.CORRECT_GUESS;
          } else {
        	  sourceCell.status = CellStatus.WRONG_GUESS;
          }
          sourceCell.paint();   // re-paint this cell based on its status

         
//          * [TODO 6] (later)
//          * Check if the player has solved the puzzle after this move,
//          *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
          if (isSolved()) {
        	  JOptionPane.showMessageDialog(null, "Congratulation!");
          }

          try {
    
            if (isValidInput(sourceCell.row, sourceCell.col, numberIn)) {
                // Input valid
                if (numberIn == sourceCell.number) {
                    sourceCell.status = CellStatus.CORRECT_GUESS;
                } else {
                    sourceCell.status = CellStatus.WRONG_GUESS;
                }
            } else {
                // Input tidak valid, sorot konflik
                highlightConflicts(sourceCell.row, sourceCell.col, numberIn);
                JOptionPane.showMessageDialog(null, "Konflik terdeteksi! Angka sudah ada di baris, kolom, atau sub-grid.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harap masukkan angka valid.");
        }
    
        sourceCell.paint(); // Perbarui tampilan sel
      }
   }   
  
   private boolean isValidInput(int row, int col, int userInput) {
      // Periksa baris dan kolom
      for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
         if (puzzle.numbers[row][i] == userInput && puzzle.isGiven[row][i]) {
            return false; // Konflik di baris
         }
         if (puzzle.numbers[i][col] == userInput && puzzle.isGiven[i][col]) {
            return false; // Konflik di kolom
         }
      }

      // Periksa sub-grid
      int subGridRowStart = (row / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
      int subGridColStart = (col / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
      for (int r = subGridRowStart; r < subGridRowStart + SudokuConstants.SUBGRID_SIZE; r++) {
         for (int c = subGridColStart; c < subGridColStart + SudokuConstants.SUBGRID_SIZE; c++) {
            if (puzzle.numbers[r][c] == userInput && puzzle.isGiven[r][c]) {
                  return false; // Konflik di sub-grid
            }
         }
      }

      return true; // Input valid
   }

   private void highlightConflicts(int row, int col, int userInput) {
      // Reset semua sel ke warna default
      for (int r = 0; r < SudokuConstants.GRID_SIZE; r++) {
         for (int c = 0; c < SudokuConstants.GRID_SIZE; c++) {
            cells[r][c].setConflict(false);
         }
      }

      // Sorot konflik di baris dan kolom
      for (int i = 0; i < SudokuConstants.GRID_SIZE; i++) {
         if (puzzle.numbers[row][i] == userInput) {
            cells[row][i].setConflict(true);
         }
         if (puzzle.numbers[i][col] == userInput) {
            cells[i][col].setConflict(true);
         }
      }

      // Sorot konflik di sub-grid
      int subGridRowStart = (row / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
      int subGridColStart = (col / SudokuConstants.SUBGRID_SIZE) * SudokuConstants.SUBGRID_SIZE;
      for (int r = subGridRowStart; r < subGridRowStart + SudokuConstants.SUBGRID_SIZE; r++) {
         for (int c = subGridColStart; c < subGridColStart + SudokuConstants.SUBGRID_SIZE; c++) {
            if (puzzle.numbers[r][c] == userInput) {
                  cells[r][c].setConflict(true);
            }
         }
      }
   }

}

