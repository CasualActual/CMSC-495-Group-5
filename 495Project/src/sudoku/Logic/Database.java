package sudoku.Logic;

/*Author: Abel Tabor 
 *Date: 4/29/2022
 *File name: Database.java
 *Purpose: Connects to a local database and allows other classes to retrieve or store information 
 *	CHANGE LOG:
 *		Abel Tabor: 
 *			4/29/2022			
 *				-> Created file and code
 */		

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Database {
	//fields 
	private Connection conn = null;
	private final String classString = "org.sqlite.JDBC";
	private final String connString = "jdbc:sqlite:database.db";
	
	//cons 
	public Database() {
		
	}
	
	//methods 
	//connects to the database using field conn
	private void connect() {
		try {
			Class.forName(classString);
			this.conn = DriverManager.getConnection(connString);
			
			//create database 
			Statement statem = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS PLAYERS " + "(ID INTEGER PRIMARY KEY NOT NULL," + "NAME TEXT NOT NULL, " + "TIME INT NOT NULL, " + "SCORE INT NOT NULL)";
			statem.executeUpdate(sql);
			statem.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
		}
	}
	//enters an entry into the database 
	//returns true of entry was entered, false if not
	public boolean enterEntry(String name, int time, int score) {
		try {
			connect();
			PreparedStatement stm = this.conn.prepareStatement("INSERT INTO PLAYERS (name, time, score) VALUES (?, ?, ?)");
			stm.setString(1, name);
			stm.setInt(2, time);
			stm.setInt(3, score);
			stm.executeUpdate();
			//close resources
			stm.close();
			this.conn.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			return false;
		}
		return true;
	}
	//returns an arraylist of all the entires in the database
	public ArrayList<ArrayList<String>> getEntries() {
		ArrayList<ArrayList<String>> entries = new ArrayList<>();
		try {
			connect();
			Statement stm = conn.createStatement();
			String sql = "SELECT * FROM PLAYERS";
			ResultSet res = stm.executeQuery(sql);
			while (res.next()) {
				ArrayList <String> entry = new ArrayList<>();
				entry.add(res.getString("name"));
				
				int time = res.getInt("time");
				int score = res.getInt("score");

				entry.add( String.valueOf(time));
				entry.add( String.valueOf(score));
				//adding value for collections 
				int finalScore = score + (100000/time);
				entry.add(String.valueOf(finalScore));
				entries.add(entry);
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + " : " + e.getMessage());
			return null;
		}
		
		//sort 
		Collections.sort(entries, new Comparator<ArrayList<String>>() {
			@Override 
			public int compare(ArrayList<String> o1, ArrayList<String> o2) {
				Integer one = Integer.valueOf(o1.get(3));
				Integer two = Integer.valueOf(o2.get(3));
				return two.compareTo(one);
			}
		});
		
		return entries;
	}
	
	
}
