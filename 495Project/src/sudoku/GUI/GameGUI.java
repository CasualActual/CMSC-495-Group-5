package sudoku.GUI;

/*Author: Abel Tabor 
 *Date: 4/20/2022
 *File name: GameGUI.java
 *Purpose: This class and all other GUI classes in this program utilize swing and nested classes. The GuiPanel class contains nested panel classes, each nested panel class has its own logic written out in its own class. 
 * GameGUI contains the logic needed to run the sudoku puzzle for the end user. 
 * TODO: Add functionality to time and score JLabel
 *	CHANGE LOG:
 *		Abel Tabor: 
 *			4/20/2022			
 *				-> Created file and wrote all code 
 *			4/22/2022 
 *				-> Added JOptionPane's to backButton and and leaderButton
 *			4/25/2022
 *				-> Added support for SudokuGenerator
 *				-> Started logic for submit button 
 */
import java.awt.*;
import javax.swing.*;

import sudoku.Logic.SudokuGenerator;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
public class GameGUI extends JFrame {
	private static int[] submitCheck;
	//inner classes 
	
	//Panel 
	static class GuiPanel extends JPanel {
		//fields
		private GuiMenuButtons gmb = new GuiMenuButtons(this);
		private GuiGameButtons ggb = new GuiGameButtons(this);
		private GuiGameInfo ggi = new GuiGameInfo(this);
		
		public GuiPanel() {
			adjustLayout();
			
		}
		
		
		private void adjustLayout() {
			//layout manager 
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			//GuiMenuButtons
			cons.gridx = 1;
			cons.gridy = 1;
			cons.weightx = .5;
			cons.weighty = .5;
			cons.anchor = GridBagConstraints.NORTHWEST;
			add(gmb, cons);
			
			//GuiGameButtons 
			cons.gridx = 1;
			cons.gridy = 1;
			cons.anchor = GridBagConstraints.CENTER;
			add(ggb, cons);
			
			//GuiGameInfo 
			cons.gridx = 1;
			cons.gridy = 1;
			cons.anchor = GridBagConstraints.SOUTHWEST;
			add(ggi, cons);
		}
		
		public GuiGameButtons getGuiGameButtons() {
			return this.ggb;
		}
	}
	
	static class GuiMenuButtons extends JPanel {
		//fields 
		private GuiPanel gp;
		private JButton backButton = new JButton("<- Back");
		private JButton leaderButton = new JButton("Leaderboards");
		
		//cons
		public GuiMenuButtons(GuiPanel gp) {
			this.gp = gp;
			adjustLayout();
			
			//action listeners 
			
			//backButton 
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//hides current window and displays MenuGUI's window 
					
					int result = JOptionPane.showConfirmDialog(null, "Current puzzle progress will not be saved if you leave. Are you sure you want to exit?", "Are you sure?" ,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {						
						MenuGUI.getMenuGUI().display(true);
						MenuGUI.getGameGUI().display(false);

						submitCheck = gp.ggb.fillBoard();
					}
					
				}
			});
			
			//leaderButton 
			leaderButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//hides current window and displays leaderGUI window 
					int result = JOptionPane.showConfirmDialog(null, "Current puzzle progress will not be saved if you leave. Are you sure you want to exit?", "Are you sure?" ,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						MenuGUI.getLeaderGUI().display(true);
						MenuGUI.getGameGUI().display(false);

						submitCheck = gp.ggb.fillBoard();
					}

					
				}
			});
		}
		
		//methods
		private void adjustLayout() {
			//layout manager 
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			cons.weightx = .5;
			cons.weighty = .5;
			
			
			//backButton
			cons.gridx = 0;
			cons.gridy = 0;
			add(backButton, cons);
			
			//leaderButton
			cons.gridx = 10;
			cons.gridy = 0;
			cons.insets = (new Insets(0, 294, 0, 0));
			add(leaderButton, cons);
			
		}
	}

	static class GuiGameButtons extends JPanel {
		//fields
		private GuiPanel gp;
		private ArrayList<JButton> buttons = new ArrayList<>();
		private int currentRegion = 0; //keeps track of regions for buttons
		private int[] matchingIndex = {0, 3, 6, 27, 30 , 33, 54, 57, 60}; //for help with currentRegion
		
		//cons 
		public GuiGameButtons(GuiPanel gp) {
			this.gp = gp;
			
			createButtons();

			submitCheck = fillBoard();
			adjustLayout();
		}
		
		//methods 
		private void adjustLayout() {
			//region class 
			class region extends JPanel {
				public region() {
					super(new GridLayout(3,3, 3 ,3));
					//creates a int[] that has the indexes of the buttons 
					//this allows for the 9 3x3 regions to exist 
					//while also allowing for the buttons arraylist 
					//to still be ordered to allow for easy gets of the board
					int start = matchingIndex[currentRegion]; 
					int[] totalIndex = new int[9];
					int lastnum = 0;
					for (int i = 0; i < 9; i++) {
						int addby = 1; 
						if (i == 0) {
							totalIndex[i] = start;
							lastnum = start;
							continue;
						}
						if ((i % 3) == 0) {
							addby = 7;
						}
						totalIndex[i] = lastnum + addby;
						lastnum = totalIndex[i];
					}
					currentRegion++;
					
					
					for (int i = 0; i < 9; i++) {
						
						add(buttons.get(totalIndex[i]));
					}
				}
			}
			//full board 
			class fullBoard extends JPanel {
				public fullBoard() {
					super(new GridLayout(3,3, 15, 15));
					for (int i = 0; i < 9; i++) {
						add(new region());
					}
				}
			}
			add(new fullBoard(), BorderLayout.CENTER);
			
			
		}
		
		//creates the 81 buttons for the sudoku game
		private void createButtons() {
			for (int i = 0; i < 81; i++) {
				buttons.add(new JButton(" "));
				addUseButton(buttons.get(i));
			}
		}
		
		//creates board 
		private int[] fillBoard() {
			SudokuGenerator sud = new SudokuGenerator(5);
			int [][] board = sud.getBoard();
			int [] fullBoard = sud.getFullBoard();
			int count = 0; 
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					
					buttons.get(count).setText(String.valueOf(board[x][y]));
					//checks if the current cell is missing or not
					//0 represents a cell that the user can use 
					if (board[x][y] == 0) {
						buttons.get(count).setEnabled(true);
						buttons.get(count).setText("  ");
					}
					else {
						buttons.get(count).setText(String.valueOf(board[x][y]));
						buttons.get(count).setEnabled(false);
					}
					count++;
				}
			}
			return fullBoard;
		}
		
		private void addUseButton(JButton button) {
			//adds action listener to passed button
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//numpad frame
					JFrame numpadFrame = new JFrame("Numpad");
					numpadFrame.setSize(300, 300);
					//numpad panel 
					JPanel inputPanel = new JPanel();
					inputPanel.setLayout(new GridLayout(3, 3));
					
					//buttons and action listeners 
					ArrayList<JButton> numpad =  new ArrayList<>();
					for (int i = 1; i < 10; i++) {
						//add numbers to numpad
						numpad.add(new JButton(String.valueOf(i)));
						//adds action listener to each number 
						int z = i - 1;
						numpad.get(i-1).addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent a) {
								button.setText(numpad.get(z).getText());
								numpadFrame.dispose();
							}
						});
					}
					//adds buttons to panel 
					for (int i = 0; i < 9; i++) {
						inputPanel.add(numpad.get(i));
					}
					
					//adds panel to frame 
					numpadFrame.add(inputPanel);
					
					//sets window visible
					numpadFrame.setLocationRelativeTo(null);
					numpadFrame.setVisible(true);
				}
			});
		}
		
	}
	
	static class GuiGameInfo extends JPanel {
		//fields
		private GuiPanel gp;
		private JButton submit = new JButton("Submit");
		private JLabel score = new JLabel("Score: ");
		private JLabel time = new JLabel("Time: ");
		
		//cons 
		public GuiGameInfo(GuiPanel gp) {
			this.gp = gp;
			
			adjustLayout();
			
			//action listeners 
			
			//submit 
			submit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//TODO: Submit logic
					int length = gp.ggb.buttons.size();
					int[] userSubmission = new int[length];
					for (int i = 0; i < length; i++) {
						try {
							userSubmission[i] = Integer.parseInt(gp.ggb.buttons.get(i).getText());
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Please enter values for every cell");
							return;
						}
					}
					//TODO: Remove Debug
					/*
					for (int i = 0; i < length; i++) {
						System.out.print(userSubmission[i]);
					}
					System.out.println("Real entry:");
					for (int i = 0; i < length; i++) {
						System.out.print(submitCheck[i]);
					}
					*/
				
					//TODO: remove debug
					//Arrays.equals(userSubmission, submitCheck)
					if (Arrays.equals(userSubmission, submitCheck)) {
						JOptionPane.showMessageDialog(null, "Correct");
						//TODO: add GameLogic here
						String name =JOptionPane.showInputDialog("What is your name?");
						System.out.println(name);
					}
					else {
						JOptionPane.showMessageDialog(null, "Incorrect");
						
					}
				}
			});
			
		}
		
		//methods 
		private void adjustLayout() {
			//layout manager 
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			cons.weightx = .5;
			cons.weighty = .5;
			
			
			//submit
			cons.gridx = 0;
			cons.gridy = 0;
			cons.insets = (new Insets(0, 100, 0, 0));
			add(submit, cons);
			
			//score
			cons.gridx = 1;
			cons.gridy = 0;
			cons.insets = (new Insets(0, 150, 25, 50));
			add(score, cons);
			
			//time
			cons.gridx = 1;
			cons.gridy = 1;
			add(time, cons);
			
			
		}
		
		
		
		
	}
	
	public GameGUI() {
		super("Sudoku");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new GuiPanel());
		setVisible(false);
	}
	
	public void display(boolean display) {
		setVisible(display);
	}
	
	
}
