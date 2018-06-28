package tetris;

import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class NextBlock extends JPanel {
	//Displays the next block
	
	//Block to be displayed in this panel
	Block block;
	//This panel's display board
	int grid[][];
	
	//How big squares are
	private int squareSize;
	
	//Creates the panel with the next block displayed
	public NextBlock(Block nextBlock){
		super();
		this.block = nextBlock;
		squareSize = TetrisGamePanel.size;
	}
	public void paintComponent(Graphics g){
		grid = block.currentBlock;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				g.setColor(TetrisGamePanel.colors[grid[j][i]]);
				//Used i+1 here to make sure panel doesn't seem to be part of the game
				g.fill3DRect((i+1) * squareSize, j * squareSize, squareSize, squareSize, true);
			}
		}
	}
}
