package sudoku.GUI;
/*Author: Abel Tabor 
 *Date: 4/20/2022
 *File name: LeaderGUI.java
 *Purpose: This class and all other GUI classes in this program utilize swing and nested classes. The GuiPanel class contains nested panel classes, each nested panel class has its own logic written out in its own class. 
 *LeaderGUI uses the database class and displays its entries ordered by time and score  
 *TODO: Add database support
 *TODO: Add functionality to leaderTable info
 * *	CHANGE LOG:
 *		Abel Tabor: 
 *			4/20/2022			
 *				-> Created file and wrote all code 
 *			4/22/2022 
 *				-> Added JTable support and created leaderBanner.png for Frame
 *			4/27/2022 
 *				-> Added database integration
 */
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import sudoku.Logic.Database;

import java.awt.event.*;
import java.util.ArrayList;
public class LeaderGUI extends JFrame {

	//field 
	private static String[] colNames = {"Name", "Time", "Score"};
	private static JTable testTable = new JTable();

	
	//methods 
	public static void updateInfo() {
		//make connection with database 
		DefaultTableModel model = new DefaultTableModel(colNames, 0);
		Database db = new Database();
		ArrayList<ArrayList<String>> entries = db.getEntries();
		for (int i = 0; i < entries.size(); i++) {
			String name = entries.get(i).get(0);
			
			//format time 
			String unFomtime = entries.get(i).get(1);
			int timeInt = Integer.parseInt(unFomtime);
			int timeMin = timeInt/60000;
			int timeSec = (timeInt/1000) % 60;
			String timeSecString = String.valueOf(timeSec);
			//allows for single digit values to have a 0 behind them
			if (timeSecString.length() == 1) {
				timeSecString = "0" + timeSecString;
			}
			
			String time = String.valueOf(timeMin) + ":" + timeSecString;		
			String score = entries.get(i).get(2);
			model.addRow(new Object[] {name, time, score});
		}
		testTable.setModel(model);
	}
	
	//inner classes 

	
	//Panel 
	static class GuiPanel extends JPanel {
		//fields 
		private GuiLeaderTable glt = new GuiLeaderTable(this);
		private GuiBannerButtons gbb = new GuiBannerButtons(this);
		
		
		//cons 
		public GuiPanel() {
			adjustLayout();
		}
		
		//methods 
		private void adjustLayout() {
			//layout manager 
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			//GuiLeaderTable
			cons.gridx = 1;
			cons.gridy = 0;
			cons.anchor = GridBagConstraints.CENTER;
			add(glt, cons);
			
			//GuiBannerButtons
			cons.gridx = 0;
			cons.gridy = 0;
			cons.anchor = GridBagConstraints.NORTHWEST;
			add(gbb, cons);
			
			
		}
	}
	
	//leaderTable 
	static class GuiLeaderTable extends JPanel {
		//fields 
		private GuiPanel gp;
		private JScrollPane so = new JScrollPane(testTable);
		
		
		public GuiLeaderTable(GuiPanel gp) {
			this.gp = gp;

			testTable.setBounds(30, 40, 100, 200);
			testTable.setEnabled(false);
			updateInfo();
			
			
			adjustLayout();
			
			
		}
		
		private void adjustLayout() {
			//layout manager
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			//so 
			cons.gridx = 0;
			cons.gridy = 0;
			cons.anchor = GridBagConstraints.CENTER;
			add(so, cons);
			
			
		}
	}
	
	//banner 
	static class GuiBannerButtons extends JPanel {
		private GuiPanel gp;
		//banner 
		private ImageIcon icon = new ImageIcon("images/leaderboard_banner.png");
		private JLabel banner = new JLabel(icon);
		//button 
		private JButton backButton = new JButton("<- Menu");
		
		//cons 
		public GuiBannerButtons(GuiPanel gp) {
			this.gp = gp;
			
			adjustLayout();
			
			//action listener 
			
			//backbutton 
			backButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					//hide current window and show menugui window
					MenuGUI.getMenuGUI().display(true);
					MenuGUI.getLeaderGUI().display(false);
				}
			});
			
		}
		
		//method 
		private void adjustLayout() {
			//layout manager
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			cons.weightx = .5;
			cons.weighty = .5;
			
			//banner 
			cons.gridx = 0;
			cons.gridy = 2;
			add(banner, cons);
			
			//backButton 
			cons.gridx = 0;
			cons.gridy = 0;
			cons.insets = (new Insets(0, 0, 100, 80));
			add(backButton, cons);
		}
		
	}
	
	//leaderGUI cons 
	public LeaderGUI() {
		super("Leaderboard");
		setSize(800, 800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new GuiPanel());
		setVisible(false);
	}
	
	//methods 
	public void display(boolean display) {
		//packs all components in window and displays at center of screen
		pack();
		setLocationRelativeTo(null);
		setVisible(display);
	}
}



