package sudoku.Logic;

/*Author: Abel Tabor 
 *Date: 4/27/2022
 *File name: GameLogic.java
 *Purpose: This class stores the information for a game session and submits a completed game to the database 
 *	CHANGE LOG:
 *		Thomas Edwards: 
 *			4/25/2022
 *				-> Wrote GameTimer.java
 *		Abel Tabor: 
 *			4/27/2022			
 *				-> Created file
 *				-> Integrated sections of Thomas' GameTimer class to create timer functionality 
 *TODO: Add database support
 */		

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class GameLogic {
	//final fields 
	private final int MAXSCORE = 10000;
	//fields 
	private int gameTime, sec, min; //user number information 
	private int score = MAXSCORE;
	private String name; //user name 
	
	//cons 
	public GameLogic() {
		newGame();

	}
	//getters 
	public int getScore() {
		return score;
	}
	public String getStringTime( ) {
		String sec_string = String.format("%02d", sec);
		String min_string = String.format("%02d", min);
		return min_string + ":" + sec_string;
	}
	
	//methods 
	
	//checks if name is valid 
	//returns true if valid name
	public boolean properName(String name) {
		Pattern pat = Pattern.compile("^[a-zA-Z0-9]+$");
		Matcher mat = pat.matcher(name);
		if (mat.matches()) {
			this.name = name; 
			return true;
		}
		return false;
	}
	
	//indicates that a new game has started
	public void newGame() {
		this.gameTime = 0;
		this.sec = 0;
		this.min = 0;
	}
	
	//increments time by 1 sec or 1000 msec
	public void incremTime() {
		this.gameTime += 1000;
		this.min = gameTime/60000;
		this.sec = (gameTime/1000) % 60;
	}
	
	//updates score based on failed submit 
	public void failedSubmit() {
		if (score < 100) {
			return;
		} else {
			this.score = score/2;
		}
		
	}
	
	//game won submission 
	public boolean submitGame() {
		//TODO: add database support 
		//checks if name is valid again 
		boolean validName = properName(this.name);
		if (validName) {
			//TODO: submit database stuff here
			return true;
		} 
		return false;
	}
	
}
