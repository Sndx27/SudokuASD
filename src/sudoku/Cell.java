package sudoku;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
    public static final Color BG_WRONG_GUESS = new Color(255, 182, 193); // Latar belakang merah muda untuk angka salah
    public static final Color BG_CONFLICT = new Color(255, 99, 71); // Highlight merah untuk konflik
    public static final Font FONT_NUMBERS = new Font("SansSerif", Font.BOLD, 24); // Font angka
    public static final Color GRID_LINE_COLOR = new Color(181, 85, 96); // Warna pink kemerahan untuk garis grid
    public static final Color GRID_LINE_COLOR_NON3X3 = new Color(239,239,239,255); // Warna pink kemerahan untuk garis grid
    public static final Color NUMBER_COLOR = Color.BLACK; // Warna angka (Hitam)

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
    
        // Atur Border Kustom
        boolean topThick = (row % 3 == 0) && (row != 0); // Garis tebal di awal subgrid (selain baris pertama)
        boolean leftThick = (col % 3 == 0) && (col != 0); // Garis tebal di awal subgrid (selain kolom pertama)
        boolean bottomThick = (row == SudokuConstants.GRID_SIZE - 1); // Tebal di bagian bawah grid
        boolean rightThick = (col == SudokuConstants.GRID_SIZE - 1); // Tebal di bagian kanan grid
    
        Border cellBorder = BorderFactory.createMatteBorder(
            topThick ? 3 : 1, // Ketebalan atas
            leftThick ? 3 : 1, // Ketebalan kiri
            bottomThick ? 3 : 1, // Ketebalan bawah (pada grid terakhir)
            rightThick ? 3 : 1, // Ketebalan kanan (pada grid terakhir)
            GRID_LINE_COLOR // Warna border
        );
    
        super.setBorder(cellBorder);
    }

    public void newGame(int number, boolean isGiven) {
        this.number = number;
        this.status = isGiven ? CellStatus.GIVEN : CellStatus.TO_GUESS;
        this.isInConflict = false; // Reset conflict status for a new game
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
        g2.setColor(minorGridColor); // Warna abu-abu terang untuk grid minor
        g2.setStroke(new java.awt.BasicStroke(1)); // Tipis untuk grid minor
        if (row > 0) { // Hindari menggambar garis atas jika berada di baris pertama
            g2.drawLine(0, 0, getWidth(), 0); // Garis atas minor
        }
        if (col > 0) { // Hindari menggambar garis kiri jika berada di kolom pertama
            g2.drawLine(0, 0, 0, getHeight()); // Garis kiri minor
        }
        if (row < 8) { // Hindari menggambar garis bawah jika berada di baris terakhir
            g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // Garis bawah minor
        }
        if (col < 8) { // Hindari menggambar garis kanan jika berada di kolom terakhir
            g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight()); // Garis kanan minor
        }
    
        // Tentukan apakah ini bagian dari subgrid 3x3
        boolean isTopBorder = (row % 3 == 0); // Garis atas subgrid
        boolean isLeftBorder = (col % 3 == 0); // Garis kiri subgrid
        boolean isBottomBorder = (row == 8); // Garis bawah grid keseluruhan
        boolean isRightBorder = (col == 8); // Garis kanan grid keseluruhan
    
        // Gambar garis pink untuk subgrid 3x3 di atas grid minor
        g2.setColor(GRID_LINE_COLOR); // Warna pink untuk garis subgrid
        g2.setStroke(new java.awt.BasicStroke(4)); // Tebal untuk subgrid
    
        if (isTopBorder && row > 0) { // Hindari menggambar garis atas jika berada di baris pertama
            g2.drawLine(0, 0, getWidth(), 0); // Garis atas
        }
        if (isLeftBorder && col > 0) { // Hindari menggambar garis kiri jika berada di kolom pertama
            g2.drawLine(0, 0, 0, getHeight()); // Garis kiri
        }
        if (isBottomBorder && row < 8) { // Hindari menggambar garis bawah jika berada di baris terakhir
            g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1); // Garis bawah
        }
        if (isRightBorder && col < 8) { // Hindari menggambar garis kanan jika berada di kolom terakhir
            g2.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight()); // Garis kanan
        }
    }
    
    
    
       
    public boolean isConflict() {
        return isInConflict;
    }
}
