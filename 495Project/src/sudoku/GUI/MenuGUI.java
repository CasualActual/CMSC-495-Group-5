package sudoku.GUI;
/*Author: Abel Tabor 
 *Date: 4/20/2022
 *File name: MenuGUI.java
 *Purpose: This class and all other GUI classes in this program utilize swing and nested classes. The GuiPanel class contains nested panel classes, each nested panel class has its own logic written out in its own class. 
 *MenuGUI serves as the main class.
 *When users make a choice of new game or leaderboard, the MenuGUI window is hidden and a new Frame is opened. 
 *	CHANGE LOG:
 *		Abel Tabor: 
 *			4/20/2022			
 *				-> Created file and wrote all code, created banner.png for GUI 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuGUI extends JFrame {
	//MenuGUI fields
	
	//These allow for other classes to manipulate windows 
	private static MenuGUI mGUI = new MenuGUI();
	private static GameGUI gGUI = new GameGUI();
	private static LeaderGUI lGUI = new LeaderGUI();
	
	//DEBUG - DELETE
	public static  MenuGUI getMenuGUI() {
		return mGUI;
	}
	public static GameGUI getGameGUI() {
		return gGUI;
	}
	public static LeaderGUI getLeaderGUI() {
		return lGUI;
	}
	
	
	// inner classes 
	
	//PANEL
	static class GuiPanel extends JPanel {
		//fields 
		private GuiButtons gbu = new GuiButtons(this);
		private GuiBanner gba = new GuiBanner(this);
		
		public GuiPanel() {
			adjustLayout();
		}
		
		//methods 
		private void adjustLayout() {
			//layout manager 
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			//Buttons
			cons.gridx = 0;
			cons.gridy = 50;
			cons.anchor = GridBagConstraints.SOUTH;
			add(gbu, cons);
			
			//banner
			cons.gridx = 0;
			cons.gridy = 0;
			cons.anchor = GridBagConstraints.NORTH;
			add(gba, cons);
		}
	}
	
	//BUTTONS 
	static class GuiButtons extends JPanel {
		//fields 
		private GuiPanel gp; 
		
		//buttons 
		private JButton leaderBoard = new JButton("Leaderboards");
		private JButton newGame = new JButton("Start a new game");
		
		public GuiButtons(GuiPanel gp) {
			
			this.gp = gp;
			
			adjustLayout();
			
			//action listeners
			
			//newGame 
			newGame.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//creates new GameGUI and hides menuGUI
					gGUI.display(true);
					mGUI.display(false);
					
					
				}
			});
			//leaderBoard
			leaderBoard.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//hides current window and displays leaderGUI window 
					MenuGUI.lGUI.display(true);
					MenuGUI.mGUI.display(false);
				}
			});
			
		}
		
		//methods 
		private void adjustLayout() {
			//layout manager
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			//leaderBoard
			cons.gridx = 5;
			cons.gridy = 50;
			cons.insets = (new Insets(30, 0, 0, 0));
			add(leaderBoard, cons);
			
			//newGame 
			cons.gridx = 0;
			cons.gridy = 50;
			cons.insets = (new Insets(30, 0, 0, 0));
			add(newGame, cons);
			
		}
		
	}
	
	//GuiBanner 
	static class GuiBanner extends JPanel {
		//fields 
		private GuiPanel gp;
		private ImageIcon icon = new ImageIcon("images/banner.png");
		private JLabel banner = new JLabel(icon);
		
		public GuiBanner(GuiPanel gp) {
			this.gp = gp;
			
			adjustLayout();
		}
		
		private void adjustLayout() {
			//layout manager
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			//banner
			cons.gridx = 0;
			cons.gridy = 200;
			add(banner, cons);
		}
		
		
	}
	
	//MenuGUI Cons
	public MenuGUI() {
		super("Sudoku");
		setSize(750, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new GuiPanel());
	}
	
	public void display(boolean isDisplayed) {
		setVisible(isDisplayed);
	}
	
	public static void main(String[] args) {
		
		mGUI.display(true);
	}
	
	
	
}

