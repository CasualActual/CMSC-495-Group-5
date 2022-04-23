import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class sudokuGenerator {

    private Table sudokuTable = new Table();

    private JFrame frame = new JFrame("Sudoku Game");
    private JTextField textField[][] = new JTextField[9][9];
    private GridPanel gridPanel = new GridPanel(new GridLayout(9, 9, 1, 1));

    // constructor //
    void GameGUI() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y] = new JTextField();
                textField[x][y].setForeground(Color.YELLOW);
                gridPanel.add(textField[x][y]);
            }
        }
    }

    boolean checkText() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (!textField[x][y].getText().equals("")) {
                    try {
                        int digit = Integer.parseInt(textField[x][y].getText());
                        if (digit <= 0 || digit >= 10)
                            return false;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void GUIToSudoku() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (!textField[x][y].getText().equals("")) {
                    sudokuTable.getDigit(x, y).setAnswer(Integer.parseInt(textField[x][y].getText()));
                    sudokuTable.getDigit(x, y).setSafe(true); // set starting digits to true
                    textField[x][y].setForeground(Color.YELLOW); // color starting digits yellow
                } else {
                    sudokuTable.getDigit(x, y).setAnswer(0); // clear any blank table digits by setting them to 0
                    textField[x][y].setForeground(Color.BLACK); // color blanks to black
                }
            }
        }
    }

    public void sudokuTable() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (sudokuTable.getDigit(x, y).isSolved())
                    textField[x][y].setText(String.valueOf(sudokuTable.getDigit(x, y).getAnswer()));
            }
        }
    }

    public void clearGrid() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                textField[x][y].setText("");
                textField[x][y].setForeground(Color.YELLOW);
            }
        }
    }

    public void createGUI() {
        JPanel PANEL = new JPanel(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        gridBagConstraints.weighty = 1;
        gridBagConstraints.weightx = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;

        //add label to top of main panel
        JLabel topLabel = new JLabel("Sudoku Game", SwingConstants.CENTER);
        topLabel.setOpaque(true);
        topLabel.setBackground(Color.BLACK);
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weighty = 0.05;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        PANEL.add(topLabel, gridBagConstraints);


        //add grid panel
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weighty = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        PANEL.add(gridPanel, gridBagConstraints);

        //set these gridBagConstraints for all bottom buttons
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 0.1;

        JButton generateButton = new JButton("Generate");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTH;
        gridBagConstraints.ipadx = 40;
        PANEL.add(generateButton, gridBagConstraints);

        JButton clearButton = new JButton("Clear Table");
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.ipadx = 0;
        PANEL.add(clearButton, gridBagConstraints);

        //create frame and add main panel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.getContentPane().add(PANEL); //add main panel to frame
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }

    public class GridPanel extends JPanel{
		private static final long serialVersionUID = 1L;

		GridPanel(GridLayout layout){
			super(layout);
		}
    }
    public class SudokuStart {
        public static void main(String args[]) {
            GameGUI gui = new GameGUI();
            gui.createGUI();
        }
    }
