package sudoku.Logic;
import java.util.Random;
public class SudokuGenerator {
	private int[][] board = new int[9][9];
	
	public SudokuGenerator() {
		genBoard();
	}
	
	public int[][] getBoard() {
		return board;
	}
	
	private void genBoard() {
		Random random = new Random();
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				this.board[x][y] = random.nextInt(10);
			}
		}
	}
}