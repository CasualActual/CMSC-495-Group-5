package sudoku.GUI;
/*Author: Abel Tabor 
 *Date: 4/20/2022
 *File name: LeaderGUI.java
 *Purpose: This class and all other GUI classes in this program utilize swing and nested classes. The GuiPanel class contains nested panel classes, each nested panel class has its own logic written out in its own class. 
 *LeaderGUI uses the database class and displays its entries ordered by time and score  
 *TODO: Add database support
 *TODO: Add functionality to leaderTable info
 */
import java.awt.*;
import javax.swing.*;


import java.awt.event.*;
public class LeaderGUI extends JFrame {

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
		//TODO: Add real info 
		private String[][] info = {
				{"Abel", "3000", "1:56"},
				{"Aaaa", "7800", "6:45"}
		};
		private String[] colNames = {"Name", "Score", "Time"};
		private JTable leaderTable = new JTable(info, colNames);
		private JScrollPane sp = new JScrollPane(leaderTable);
		
		
		public GuiLeaderTable(GuiPanel gp) {
			this.gp = gp;
			
			leaderTable.setBounds(30, 40, 100, 200);
			leaderTable.setEnabled(false);
			adjustLayout();
		}
		
		private void adjustLayout() {
			//layout manager
			setLayout(new GridBagLayout());
			
			//cons manager 
			GridBagConstraints cons = new GridBagConstraints();
			
			//sp 
			cons.gridx = 0;
			cons.gridy = 0;
			cons.anchor = GridBagConstraints.CENTER;
			add(sp, cons);
			
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
					MenuGUI.mGUI.display(true);
					MenuGUI.lGUI.display(false);
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



