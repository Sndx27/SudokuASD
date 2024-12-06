package sudoku;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;


/**
 * The Cell class models the cells of the Sudoku puzzle, by customizing
 * (subclassing) the javax.swing.JTextField to include row/column, puzzle number
 * and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L; // to prevent serial warning

    // Define named constants for JTextField's colors and fonts
    public static final Color FG_GIVEN = new Color(0, 0, 0); // Warna angka yang sudah diberikan (Hitam)
    public static final Color FG_NOT_GIVEN = new Color(0, 0, 0); // Warna angka yang belum diberikan (Hitam)
    public static final Color BG_GIVEN = Color.WHITE; // Latar belakang angka yang diberikan (Abu-abu terang)
    public static final Color BG_TO_GUESS = Color.WHITE; // Latar belakang untuk angka yang bisa diubah (Putih)
    public static final Color BG_CORRECT_GUESS = new Color(144, 238, 144); // Latar belakang hijau untuk angka benar
    public static final Color BG_WRONG_GUESS = new Color(144, 238, 144); // Latar belakang merah muda untuk angka salah
    public static final Color BG_CONFLICT = new Color(255, 99, 71); // Highlight merah untuk konflik
    public static final Font FONT_NUMBERS = new Font("SansSerif", Font.BOLD, 24); // Font angka
    public static final Color GRID_LINE_COLOR = new Color(181, 85, 96); // Warna pink kemerahan untuk garis grid
    public static final Color GRID_LINE_COLOR_NON3X3 = new Color(239,239,239,255); // Warna pink kemerahan untuk garis grid
    public static final Color NUMBER_COLOR = Color.BLACK; // Warna angka (Hitam)

    // Define properties (package-visible)
    int row, col; // The row and column number [0-8] of this cell
    int number; // The puzzle number [1-9] for this cell
    CellStatus status; // The status of this cell defined in enum CellStatus
    public boolean isInConflict = false; // Whether this cell is in conflict
    public boolean isGiven;

    /** Constructor */
    public Cell(int row, int col) {
        super(); // JTextField
        this.row = row;
        this.col = col;
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
        addActionListener(e -> handleInput());
        

        setKeyBindings();
    }

    private void setKeyBindings() {
        // Bind number keys to input numbers
        for (int i = 1; i <= 9; i++) {
            final int number = i;
            getInputMap().put(KeyStroke.getKeyStroke(String.valueOf(i)), "input" + i);
            getActionMap().put("input" + i, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    handleInput();
                }
            });
        }

        // Bind arrow keys for navigation
        getInputMap().put(KeyStroke.getKeyStroke("UP"), "moveUp");
        getActionMap().put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveToCell(row - 1, col);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moveDown");
        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveToCell(row + 1, col);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveToCell(row, col - 1);
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveToCell(row, col + 1);
            }
        });
    }

    private void moveToCell(int newRow, int newCol) {
        if (newRow >= 0 && newRow < SudokuConstants.GRID_SIZE && newCol >= 0 && newCol < SudokuConstants.GRID_SIZE) {
            SudokuMain mainFrame = (SudokuMain) SwingUtilities.getWindowAncestor(this);
            mainFrame.board.cells[newRow][newCol].requestFocus(); // Move focus to the new cell
        }
    }

    
    /**
     * Reset this cell for a new game, given the puzzle number and isGiven
     */
    public void newGame(int number, boolean isGiven) {
        this.number = number; // Set the number for the cell
        this.isGiven = isGiven; // Set whether the cell is given or not
        this.isInConflict = false; // Reset conflict status for a new game
    
        if (isGiven) {
            setText(String.valueOf(number)); // Display the number if it's given
            setEditable(false); // Make the cell non-editable if it's a given number
            this.status = CellStatus.GIVEN; // Set status to GIVEN
        } else {
            setText(""); // Clear the text if it's not a given number
            setEditable(true); // Make the cell editable if it's not a given number
            this.status = CellStatus.TO_GUESS; // Set status to TO_GUESS
        }
    
        paint(); // Paint the cell based on its initial status
    }

    public void paint() {
        if (isInConflict) {
            super.setBackground(BG_CONFLICT); // Highlight cell in red
        } else if (status == CellStatus.GIVEN) {
            super.setText(number + "");
            super.setEditable(false);
            super.setBackground(BG_GIVEN);
            super.setForeground(FG_GIVEN);
        } else if (status == CellStatus.TO_GUESS) {
            super.setText("");
            super.setEditable(true);
            super.setBackground(BG_TO_GUESS);
            super.setForeground(FG_NOT_GIVEN);
        } else if (status == CellStatus.CORRECT_GUESS) {
            super.setBackground(BG_CORRECT_GUESS);
        } else if (status == CellStatus.WRONG_GUESS) {
            super.setBackground(BG_WRONG_GUESS);
        }
    }
    private void handleInput() {
        handleInput(getText());
    }
    
    private void handleInput(String input) {
        SudokuMain mainFrame = (SudokuMain) SwingUtilities.getWindowAncestor(this);
        int inputNumber;
    
        // Try to parse the input if it's not empty
        if (input.isEmpty()) {
            return; // Do nothing if the input is empty
        }
    
        try {
            inputNumber = Integer.parseInt(input);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            setText(""); // Clear the cell's text
            return; // Exit if the input is not a valid number
        }
    
        // Validate the input number
        if (mainFrame.isValidInput(row, col, inputNumber)) {
            if (inputNumber == number) {
                status = CellStatus.CORRECT_GUESS;
            } else {
                status = CellStatus.WRONG_GUESS;
            }
            paint(); // Update the cell's appearance
            mainFrame.updateStatusBar(); // Update the status bar
        } else {
            JOptionPane.showMessageDialog(this, "Invalid input!");
            setText(""); // Clear the cell's text
        }
    }


    /**
     * Set whether this cell is in conflict
     * 
     * @param conflict True to mark this cell as in conflict, false otherwise
     */
    public void setConflict(boolean conflict) {
        this.isInConflict = conflict;
        paint(); 

        if (conflict) {
       
            new javax.swing.Timer(2000, e -> {
                this.isInConflict = false; 
                paint(); 
            }).start();
        }
    }

    @Override
    protected void paintBorder(java.awt.Graphics g) {
        java.awt.Graphics2D g2 = (java.awt.Graphics2D) g;
    
        // Warna untuk grid minor (bukan subgrid 3x3)
        Color minorGridColor = new Color(239, 239, 239, 255);
    
        // Gambar grid minor terlebih dahulu
        g2.setColor(minorGridColor); 
        g2.setStroke(new java.awt.BasicStroke(1)); 
        if (row > 0) { 
            g2.drawLine(0, 0, getWidth(), 0); 
        }
        if (col > 0) { 
            g2.drawLine(0, 0, 0, getHeight()); 
        }
        if (row < 8) { 
            g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); 
        }
        if (col < 8) { 
            g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        }
    
        // Tentukan apakah ini bagian dari subgrid 3x3
        boolean isTopBorder = (row % 3 == 0);
        boolean isLeftBorder = (col % 3 == 0);
        boolean isBottomBorder = (row == 8);
        boolean isRightBorder = (col == 8); 
    
        // Gambar garis pink untuk subgrid 3x3 di atas grid minor
        g2.setColor(GRID_LINE_COLOR); 
        g2.setStroke(new java.awt.BasicStroke(4)); 
    
        if (isTopBorder && row > 0) { 
            g2.drawLine(0, 0, getWidth(), 0); 
        }
        if (isLeftBorder && col > 0) { 
            g2.drawLine(0, 0, 0, getHeight());
        }
        if (isBottomBorder && row < 8) {
            g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
        }
        if (isRightBorder && col < 8) { 
            g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
        }
    }
    
    
    
       
    public boolean isConflict() {
        return isInConflict;
    }
}
