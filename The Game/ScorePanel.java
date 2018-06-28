package tetris;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
	//Contains panel with next piece, the score and my student details
	
	//Non-editable text field that keeps the score
	JTextField score;
	//Where the next block is shown
	NextBlock next;
	
	private static final JLabel NAME = new JLabel("Radu Adobroaie - 1502093");
	
	public ScorePanel(){
		super(new GridLayout(4,1));
		setBackground(new Color(25, 0, 51));
		setBorder(BorderFactory.createEtchedBorder(Color.CYAN, Color.CYAN));
		
		next = new NextBlock(new Block(new int[4][4]));
		next.setVisible(false);
		add(next);
		next.setAlignmentX(CENTER_ALIGNMENT);
		
		score = new JTextField("0", 12);
		score.setBackground(Color.CYAN);
		score.setEditable(false);
		JPanel scoreSubPanel = new JPanel();
		scoreSubPanel.setBackground(new Color(25, 0, 51));
		scoreSubPanel.add(score);
		add(scoreSubPanel);
		
		NAME.setForeground(Color.CYAN);
		add(NAME);
	}
}
