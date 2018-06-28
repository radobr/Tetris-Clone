package tetris;

import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class TetrisMain extends JFrame {
	//Starts the game
	
	//Constructor; sets up the frame
	public TetrisMain(){
		super("Tetris");
		int width = 10;
		int height = 20;
		int[][] grid = new int[width][height];
		ScorePanel score = new ScorePanel();
		add(BorderLayout.CENTER, new TetrisGamePanel(grid, score));
		add(BorderLayout.EAST, score);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setVisible(true);
	}
	//Create game
	public static void main(String[] args){
		new TetrisMain();
	}
}
