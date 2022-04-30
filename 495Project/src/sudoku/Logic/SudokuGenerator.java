package sudoku.Logic;
/*Author: Brian Jefferson 
 *Date: 4/20/2022
 *File name: SudokuGenerator.java
 *Purpose: Sudoku Generator; this class is used to randomly generate a sudoku using square root. Anywhere there is a zero 
 * that is represented as a blank space
 * CHANGELOG:  
 * 		Brian Jefferson:
 * 
 * 			04/22/2022 ----> 
 * 					Created file, mockup of ui with random board
 * 
 * 			04/24/2022 ---->  
 * 					Modified java class to randomly generate new values every run
 * 
 * 		Abel Tabor: 
 * 
 *			 4/25/2022 ---->
 * 				   Modified variables and methods to private
 * 				   Changed constructor to only need 1 variable, changed column to final value of 9
 * 				   Added fullMat and fillFullMat to better work with GameGUI 
 * 				   		
 * 
 * 
 *
 */

public class SudokuGenerator {
	
	//FIELDS
    private int[][] mat; //board 
    private int[] fullMat; //board without missing values 
    private static final int COLUMN = 9; // Number of COLUMNs/rows.
    private int squareRoot; // Square root of COLUMN
    private int missingDigits; // Number Of missing digits

    // Constructor
    public SudokuGenerator(int missingDigits) {
    	
        this.missingDigits = missingDigits;

        Double sqrt = Math.sqrt(COLUMN);
        squareRoot = sqrt.intValue();

        mat = new int[COLUMN][COLUMN];
        
        fillValues();
    }
    
    
    //METHODS
    
    // Sudoku Generator
    private void fillValues() {
    	
        // Fill the diagonal
        fillDiagonal();

        // Fill remaining spaces
        fillRemaining(0, squareRoot);
        
        //fills 1d array 
        fillFullMat();
        

        // Remove missing digits 
        removeDigits();
    }

    // Fill the diagonal squareRoot number of squareRoot x squareRoot matrices
    private void fillDiagonal() {

        for (int k = 0; k < COLUMN; k = k + squareRoot)

            // for diagonal box, start coordinates->k==j
            fillBox(k, k);
    }

    // Returns false if given 3 x 3 block contains num.
    private boolean unUsedBox(int rowStart, int colStart, int num) {
        for (int k = 0; k < squareRoot; k++)
            for (int j = 0; j < squareRoot; j++)
                if (mat[rowStart + k][colStart + j] == num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix.
    private void fillBox(int row, int col) {
        int num;
        for (int k = 0; k < squareRoot; k++) {
            for (int j = 0; j < squareRoot; j++) {
                do {
                    num = randomGen(COLUMN);
                }
                while (!unUsedBox(row, col, num));

                mat[row + k][col + j] = num;
            }
        }
    }

    // Random generator
    private int randomGen(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    // Check if safe to put in cell
    private boolean CheckIfSafe(int k, int j, int num) {
        return (unUsedRow(k, num) &&
                unUsedCol(j, num) &&
                unUsedBox(k - k % squareRoot, j - j % squareRoot, num));
    }

    // check in the row for existence
    private boolean unUsedRow(int k, int num) {
        for (int j = 0; j < COLUMN; j++)
            if (mat[k][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    private boolean unUsedCol(int j, int num) {
        for (int k = 0; k < COLUMN; k++)
            if (mat[k][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    private boolean fillRemaining(int k, int j) {
        if (j >= COLUMN && k < COLUMN - 1) {
            k = k + 1;
            j = 0;
        }
        if (k >= COLUMN && j >= COLUMN)
            return true;

        if (k < squareRoot) {
            if (j < squareRoot)
                j = squareRoot;
        } else if (k < COLUMN - squareRoot) {
            if (j == (k / squareRoot) * squareRoot)
                j = j + squareRoot;
        } else {
            if (j == COLUMN - squareRoot) {
                k = k + 1;
                j = 0;
                if (k >= COLUMN)
                    return true;
            }
        }

        for (int num = 1; num <= COLUMN; num++) {
            if (CheckIfSafe(k, j, num)) {
                mat[k][j] = num;
                if (fillRemaining(k, j + 1))
                    return true;

                mat[k][j] = 0;
            }
        }
        return false;
    }

    //fills fullMat[] using mat[][] values
    private void fillFullMat() {
    	//get total amount of cells
    	int totalValues = COLUMN * COLUMN;
    	this.fullMat = new int[totalValues];
    	int count = 0;
    	
    	for (int y = 0; y < COLUMN; y++) {
    		for (int x = 0; x < COLUMN; x++) {
    			this.fullMat[count] = mat[y][x];
    			count++;
    		}
    	}
    }
    
    
    // Remove missing digits
    private void removeDigits() {
        int count = missingDigits;
        while (count != 0) {
            int cell = randomGen(COLUMN * COLUMN) - 1;

            int k = (cell / COLUMN);
            int j = cell % 9;
            if (j != 0)
                j = j - 1;

            if (mat[k][j] != 0) {
                count--;
                mat[k][j] = 0;
            }
        }
    }

    
    //GETTERS
    
    //mat
    public int[][] getBoard() {
		return mat;
    }
    
    //fullMat 
    public int[] getFullBoard() {
    	return fullMat;
    }


}
