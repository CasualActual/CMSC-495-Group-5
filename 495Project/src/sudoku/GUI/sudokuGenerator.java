/**
 * Brian Jefferson
 * 
 * 04/22/2022
 * 
 * Sudoku Generator; this class is used to randomly generate a sudoku using square root. Anywhere there is a zero 
 * that is represented as a blank space. 
 * 
 * 04/24/2022 ---->  
 * 					Modified java class to randomly generate new values every run
 * 
 *
 */

public class sudokuGenerator {
	
    int[][] mat;
    int column; // Number of columns/rows.
    int squareRoot; // Square root of column
    int missingDigits; // Number Of missing digits

    // Constructor
    sudokuGenerator(int column, int missingDigits) {
    	
        this.column = column;
        this.missingDigits = missingDigits;

        Double sqrt = Math.sqrt(column);
        squareRoot = sqrt.intValue();

        mat = new int[column][column];
    }

    public static void main(String[] args) {
    	
        int column = 9, missingDigits = 20;
        sudokuGenerator sudoku = new sudokuGenerator(column, missingDigits);
        sudoku.fillValues();
        sudoku.getBoard();
    }

    // Sudoku Generator
    public void fillValues() {
    	
        // Fill the diagonal
        fillDiagonal();

        // Fill remaining spaces
        fillRemaining(0, squareRoot);

        // Remove missing digits 
        removeDigits();
    }

    // Fill the diagonal squareRoot number of squareRoot x squareRoot matrices
    void fillDiagonal() {

        for (int k = 0; k < column; k = k + squareRoot)

            // for diagonal box, start coordinates->k==j
            fillBox(k, k);
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedBox(int rowStart, int colStart, int num) {
        for (int k = 0; k < squareRoot; k++)
            for (int j = 0; j < squareRoot; j++)
                if (mat[rowStart + k][colStart + j] == num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix.
    void fillBox(int row, int col) {
        int num;
        for (int k = 0; k < squareRoot; k++) {
            for (int j = 0; j < squareRoot; j++) {
                do {
                    num = randomGen(column);
                }
                while (!unUsedBox(row, col, num));

                mat[row + k][col + j] = num;
            }
        }
    }

    // Random generator
    int randomGen(int num) {
        return (int) Math.floor((Math.random() * num + 1));
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int k, int j, int num) {
        return (unUsedRow(k, num) &&
                unUsedCol(j, num) &&
                unUsedBox(k - k % squareRoot, j - j % squareRoot, num));
    }

    // check in the row for existence
    boolean unUsedRow(int k, int num) {
        for (int j = 0; j < column; j++)
            if (mat[k][j] == num)
                return false;
        return true;
    }

    // check in the row for existence
    boolean unUsedCol(int j, int num) {
        for (int k = 0; k < column; k++)
            if (mat[k][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int k, int j) {
        //  System.out.println(k+" "+j);
        if (j >= column && k < column - 1) {
            k = k + 1;
            j = 0;
        }
        if (k >= column && j >= column)
            return true;

        if (k < squareRoot) {
            if (j < squareRoot)
                j = squareRoot;
        } else if (k < column - squareRoot) {
            if (j == (k / squareRoot) * squareRoot)
                j = j + squareRoot;
        } else {
            if (j == column - squareRoot) {
                k = k + 1;
                j = 0;
                if (k >= column)
                    return true;
            }
        }

        for (int num = 1; num <= column; num++) {
            if (CheckIfSafe(k, j, num)) {
                mat[k][j] = num;
                if (fillRemaining(k, j + 1))
                    return true;

                mat[k][j] = 0;
            }
        }
        return false;
    }

    // Remove missing digits
    public void removeDigits() {
        int count = missingDigits;
        while (count != 0) {
            int cell = randomGen(column * column) - 1;

            int k = (cell / column);
            int j = cell % 9;
            if (j != 0)
                j = j - 1;

            // System.out.println(k+" "+j);
            if (mat[k][j] != 0) {
                count--;
                mat[k][j] = 0;
            }
        }
    }

    // Print sudoku
    public int[][] getBoard() {
        for (int k = 0; k < column; k++) {
            for (int j = 0; j < column; j++)
                System.out.print(mat[k][j] + " ");
            System.out.println();
        }
        System.out.println();
		return mat;
    }


}
