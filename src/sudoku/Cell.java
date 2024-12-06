package sudoku;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * The Cell class models the cells of the Sudoku puzzle, by customizing
 * (subclassing) the javax.swing.JTextField to include row/column, puzzle number
 * and status.
 */
public class Cell extends JTextField {
    private static final long serialVersionUID = 1L; // to prevent serial warning

    // Define named constants for JTextField's colors and fonts
    public static final Color BG_GIVEN = new Color(240, 240, 240); // RGB
    public static final Color FG_GIVEN = Color.BLACK;
    public static final Color FG_NOT_GIVEN = Color.GRAY;
    public static final Color BG_TO_GUESS = Color.YELLOW;
    public static final Color BG_CORRECT_GUESS = new Color(0, 216, 0);
    public static final Color BG_WRONG_GUESS = new Color(216, 0, 0);
    public static final Color BG_CONFLICT = Color.RED; // Highlight for conflicts
    public static final Font FONT_NUMBERS = new Font("OCR A Extended", Font.PLAIN, 28);

    // Define properties (package-visible)
    int row, col; // The row and column number [0-8] of this cell
    int number; // The puzzle number [1-9] for this cell
    CellStatus status; // The status of this cell defined in enum CellStatus
    private boolean isInConflict = false; // Whether this cell is in conflict

    /** Constructor */
    public Cell(int row, int col) {
        super(); // JTextField
        this.row = row;
        this.col = col;
        super.setHorizontalAlignment(JTextField.CENTER);
        super.setFont(FONT_NUMBERS);
        addActionListener(e -> handleInput());
    }

    /**
     * Reset this cell for a new game, given the puzzle number and isGiven
     */
    public void newGame(int number, boolean isGiven) {
        this.number = number;
        this.status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        this.isInConflict = false; // Reset conflict status for a new game
        paint(); // Paint the cell based on its initial status
    }

    /**
     * This Cell (JTextField) paints itself based on its status and conflict
     */
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
        try {
            int inputNumber = Integer.parseInt(getText());
            SudokuMain mainFrame = (SudokuMain) SwingUtilities.getWindowAncestor(this);
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
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
        }
    }


    /**
     * Set whether this cell is in conflict
     * 
     * @param conflict True to mark this cell as in conflict, false otherwise
     */
    public void setConflict(boolean conflict) {
        this.isInConflict = conflict;
        paint(); // Repaint the cell to reflect the conflict status

        if (conflict) {
            // Timer untuk mengatur ulang warna setelah 5 detik
            new javax.swing.Timer(2000, e -> {
                this.isInConflict = false; // Reset status konflik
                paint(); // Kembalikan warna ke normal
            }).start();
        }
    }

    /**
     * Get whether this cell is in conflict
     * 
     * @return True if the cell is in conflict, false otherwise
     */
    public boolean isConflict() {
        return isInConflict;
    }
}
