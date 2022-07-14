package sudoku;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class SudokuGenerator {
	private static int counter = 0;
	
	public static int[][] generate() {
		int[][] grid = new int[9][9];

		generateSudoku(grid, false);

		removeNumbers(grid);
		
		return grid;
	}
	
	public static void removeNumbers(int[][] grid) {
		Random rand = new Random();
		
		int attempts = 2;
		
		while (attempts > 0) {
			int row;
			int col;
			
			do {
				row = rand.nextInt(9);
				col = rand.nextInt(9);
			} while(grid[row][col] == 0);
			
			int backup = grid[row][col];
			grid[row][col] = 0;
			
			int[][] copyGrid = grid.clone();
			
			counter = 0;

			generateSudoku(copyGrid, true);

			if (counter != 1) {
				grid[row][col] = backup;
				attempts -= 1;
			}
		}
		
	}
	
	public static boolean generateSudoku(int[][] grid, boolean solve) {
		int[] numbers = new int[] {1,2,3,4,5,6,7,8,9};
		
		Map<Integer, Integer> rowcolmapper = new HashMap<Integer, Integer>() {private static final long serialVersionUID = 1L;
		{
		    put(0, 3); put(1, 3); put(2, 3);
		    put(3, 6); put(4, 6); put(5, 6);
		    put(6, 9); put(7, 9); put(8, 9);
		}};
		
		int row = 0;
		int col = 0;
		
		//fill grid
		for (int i = 0; i < 81; i++) {
			row = i/9;
			col = i%9;
			
			if (grid[row][col] == 0) {
				if (!solve) {
					shuffleArray(numbers);
				}
				for (int value : numbers) {
					if(!contains(grid[row], value)) { //checks row
						if(!containcolumn(grid, col, value)) { //checks col
							int[] square = getSquare(grid, rowcolmapper.get(col), rowcolmapper.get(row)); //gets square
							if (!contains(square, value)) { //checks square
								grid[row][col] = value;
								
								if (checkGrid(grid)) {
									if (solve) {
										counter += 1;
										break;
									} else {
										return true;
									}	
								} else {
									if (generateSudoku(grid, solve)) {
										return true;
									}
								}
							}
						}
					}
				}
				break;
			}
		}
		
		grid[row][col] = 0;
		return false;
	}
	
	static boolean checkGrid(int[][] ar) {
		for (int i= 0; i < 9; i++) {
			for (int x = 0; x < 9; x++) {
				if (ar[i][x] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	// Implementing Fisher–Yates shuffle
	static void shuffleArray(int[] ar)
	  {
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = ar.length - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      int a = ar[index];
	      ar[index] = ar[i];
	      ar[i] = a;
	    }
	  }
	
	static boolean contains(int[] ar, int num) {
		for (int i = 0; i < 9; i++) {
			if(num == ar[i]) {
				return true;
			}
		}
		
		return false;
	}
	
	static boolean containcolumn(int[][] ar, int col, int num) {
		for (int i = 0; i < 9; i++) {
			if (ar[i][col] == num) {
				return true;
			}
		}
		
		return false;
	}
	
	static int[] getSquare(int[][] ar, int maxcol, int maxrow) {
		int mincol = maxcol-3;
		int minrow = maxrow-3;
		int[] out = new int[9];
		int x = 0;
		
		for (int i = minrow; i < maxrow; i++) {
			for (int j = mincol; j < maxcol; j++) {
				out[x] = ar[i][j];
				x++;
			}
		}
		
		return out;
	}

	public static void printGrid(int[][] grid) {
		String underline = "------|-------|------";
		
		for(int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(grid[i][j]);
				System.out.print(" ");
				
				if((j+1) % 3 == 0 && j != 8) {
					System.out.print("| ");
				}
			}
			
			System.out.println();
			
			if ((i+1) % 3 == 0 && i != 8) {
				System.out.println(underline);
			}
		}
	}
}
