package sudoku;
import java.util.*;
import java.util.*;
public class Puzzle {
   // All variables have package access
   int[][] numbers = new int[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];
   boolean[][] isGiven = new boolean[SudokuConstants.GRID_SIZE][SudokuConstants.GRID_SIZE];

   Random random = new Random();

   // Hardcoded puzzles
   int[][][] puzzles = {
      {{5, 3, 4, 6, 7, 8, 9, 1, 2}, {6, 7, 2, 1, 9, 5, 3, 4, 8}, {1, 9, 8, 3, 4, 2, 5, 6, 7},
       {8, 5, 9, 7, 6, 1, 4, 2, 3}, {4, 2, 6, 8, 5, 3, 7, 9, 1}, {7, 1, 3, 9, 2, 4, 8, 5, 6},
       {9, 6, 1, 5, 3, 7, 2, 8, 4}, {2, 8, 7, 4, 1, 9, 6, 3, 5}, {3, 4, 5, 2, 8, 6, 1, 7, 9}},
      
      {{1, 6, 9, 4, 3, 5, 8, 2, 7}, {7, 5, 3, 1, 2, 8, 9, 6, 4}, {8, 4, 2, 7, 6, 9, 5, 3, 1},
       {9, 3, 8, 2, 4, 7, 6, 1, 5}, {5, 7, 1, 3, 8, 6, 2, 4, 9}, {6, 2, 4, 5, 9, 1, 7, 8, 3},
       {2, 1, 7, 6, 5, 4, 3, 9, 8}, {3, 8, 5, 9, 1, 2, 4, 7, 6}, {4, 9, 6, 8, 7, 3, 1, 5, 2}},
      
      // Tambahkan Num3 hingga Num6
      {{8, 2, 7, 1, 5, 4, 3, 9, 6}, {9, 6, 5, 3, 2, 7, 1, 4, 8}, {3, 4, 1, 6, 8, 9, 7, 5, 2},
       {5, 9, 3, 4, 6, 8, 2, 7, 1}, {4, 7, 2, 5, 1, 3, 6, 8, 9}, {6, 1, 8, 9, 7, 2, 4, 3, 5},
       {7, 8, 6, 2, 3, 5, 9, 1, 4}, {1, 5, 4, 7, 9, 6, 8, 2, 3}, {2, 3, 9, 8, 4, 1, 5, 6, 7}}
   };

   Random random = new Random();

   // Hardcoded puzzles
   int[][][] puzzles = {
      {{5, 3, 4, 6, 7, 8, 9, 1, 2}, {6, 7, 2, 1, 9, 5, 3, 4, 8}, {1, 9, 8, 3, 4, 2, 5, 6, 7},
       {8, 5, 9, 7, 6, 1, 4, 2, 3}, {4, 2, 6, 8, 5, 3, 7, 9, 1}, {7, 1, 3, 9, 2, 4, 8, 5, 6},
       {9, 6, 1, 5, 3, 7, 2, 8, 4}, {2, 8, 7, 4, 1, 9, 6, 3, 5}, {3, 4, 5, 2, 8, 6, 1, 7, 9}},
      
      {{1, 6, 9, 4, 3, 5, 8, 2, 7}, {7, 5, 3, 1, 2, 8, 9, 6, 4}, {8, 4, 2, 7, 6, 9, 5, 3, 1},
       {9, 3, 8, 2, 4, 7, 6, 1, 5}, {5, 7, 1, 3, 8, 6, 2, 4, 9}, {6, 2, 4, 5, 9, 1, 7, 8, 3},
       {2, 1, 7, 6, 5, 4, 3, 9, 8}, {3, 8, 5, 9, 1, 2, 4, 7, 6}, {4, 9, 6, 8, 7, 3, 1, 5, 2}},
      
      // Tambahkan Num3 hingga Num6
      {{8, 2, 7, 1, 5, 4, 3, 9, 6}, {9, 6, 5, 3, 2, 7, 1, 4, 8}, {3, 4, 1, 6, 8, 9, 7, 5, 2},
       {5, 9, 3, 4, 6, 8, 2, 7, 1}, {4, 7, 2, 5, 1, 3, 6, 8, 9}, {6, 1, 8, 9, 7, 2, 4, 3, 5},
       {7, 8, 6, 2, 3, 5, 9, 1, 4}, {1, 5, 4, 7, 9, 6, 8, 2, 3}, {2, 3, 9, 8, 4, 1, 5, 6, 7}}
   };

   // Constructor
   public Puzzle() {
      super();
   }

   /**
    * Generate a new puzzle given the difficulty level (cellsToGuess).
    */
   /**
    * Generate a new puzzle given the difficulty level (cellsToGuess).
    */
   public void newPuzzle(int cellsToGuess) {
      // Pilih puzzle secara acak
      int randomIndex = random.nextInt(puzzles.length);
      int[][] selectedPuzzle = puzzles[randomIndex];
      // Pilih puzzle secara acak
      int randomIndex = random.nextInt(puzzles.length);
      int[][] selectedPuzzle = puzzles[randomIndex];

      // Salin puzzle terpilih ke array "numbers"
      // Salin puzzle terpilih ke array "numbers"
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            numbers[row][col] = selectedPuzzle[row][col];
            numbers[row][col] = selectedPuzzle[row][col];
         }
      }

      // Tentukan sel mana yang perlu ditebak
      boolean[][] grid = new boolean[9][9];
      for (int i = 0; i < 9; i++) {
         Arrays.fill(grid[i], true); // Semua sel diatur sebagai "given"
      }

      // Hapus sejumlah sel sesuai dengan tingkat kesulitan
      int falseCount = 0;
      while (falseCount < cellsToGuess) {
         int randomRow = random.nextInt(9);
         int randomCol = random.nextInt(9);

         if (!grid[randomRow][randomCol]) continue; // Jika sudah false, lewati

         grid[randomRow][randomCol] = false;
         falseCount++;
      }

      // Salin grid ke array "isGiven"
      // Tentukan sel mana yang perlu ditebak
      boolean[][] grid = new boolean[9][9];
      for (int i = 0; i < 9; i++) {
         Arrays.fill(grid[i], true); // Semua sel diatur sebagai "given"
      }

      // Hapus sejumlah sel sesuai dengan tingkat kesulitan
      int falseCount = 0;
      while (falseCount < cellsToGuess) {
         int randomRow = random.nextInt(9);
         int randomCol = random.nextInt(9);

         if (!grid[randomRow][randomCol]) continue; // Jika sudah false, lewati

         grid[randomRow][randomCol] = false;
         falseCount++;
      }

      // Salin grid ke array "isGiven"
      for (int row = 0; row < SudokuConstants.GRID_SIZE; ++row) {
         for (int col = 0; col < SudokuConstants.GRID_SIZE; ++col) {
            isGiven[row][col] = grid[row][col];
            isGiven[row][col] = grid[row][col];
         }
      }
   }
}