package tetris;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

import static java.awt.Color.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class TetrisGamePanel extends JPanel {
	//The actual game screen
	
	// All the colors used
	static Color[] colors = { black, green, blue, red, yellow, magenta, pink, cyan };
	//Size of a square
	static int size = 30;
	
	//The game board
	int[][] grid;
	//Width and height of the board
	int w, h; 
	//Current movable block
	Block currentBlock;
	
	//How fast the game updates
	private int timeInterval = 500;
	//Panel where score is displayed
	private ScorePanel scorePanel;
	//Score
	private int score;
	//Each time score limit is reached, game speeds up
	private int scoreLimit;
	
	//Constructor; sets up the game panel
	public TetrisGamePanel(int[][] grid, ScorePanel score) {
		this.grid = grid;
		w = grid.length;
		h = grid[0].length;
		
		scorePanel = score;
		this.score = 0;
		scoreLimit = 30;
		
		setLayout(new GridLayout(3, 1));
		setBorder(BorderFactory.createEtchedBorder(Color.CYAN, Color.CYAN));
		
		// Display the name of the game
		JLabel title = new JLabel("Tetris", SwingConstants.CENTER);
		title.setForeground(Color.CYAN);
		title.setFont(new Font("Algerian", Font.PLAIN, 50));
		add(title);

		//Display play button
		JPanel buttonPanel = new JPanel();
		JButton play = new JButton("PLAY");
		play.addActionListener(new ActionListener(){
			//If pressed, removes button and starts playing
			public void actionPerformed(ActionEvent e){
				//Key input doesn't work without setting focus
				setFocusable(true);
				removeAll();
				scorePanel.next.setVisible(true);
				updateGame();
			}
		});
		play.setBackground(CYAN);
		buttonPanel.add(play);
		buttonPanel.setBackground(BLACK);
		add(buttonPanel);
		
		addMouseListener(new MouseHandler(this));
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode() == KeyEvent.VK_SPACE){
					currentBlock.dropDown();
					repaint();
				}
			}
		});
	}

	public void paintComponent(Graphics g) {
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				g.setColor(colors[grid[i][j]]);
				g.fill3DRect(i * size, j * size, size, size, true);
			}
		}
	}
    public Dimension getPreferredSize() {
        return new Dimension(w * size, h * size);
    }
    
    //Creates a new block and (re)starts timer
    private void updateGame(){
    	currentBlock = new Block(scorePanel.next.block, grid);
    	//Incremented yOffset to make sure isValidPosition() works
    	currentBlock.yOffset++;
    	if(currentBlock.isValidPosition()){
    		currentBlock.yOffset--;
    		repaint();
    	}
    	else{
    		//End the game
    		currentBlock = null;
    		scorePanel.score.setText("Final score: "+ score);
    		return;
    	}
    	scorePanel.next.block = new Block(new int[4][4]);
    	scorePanel.repaint();
    	startTimer();
    }
    //Starts timer and updates game state; keeps the score
    private void startTimer() { 
    	Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			public void run() {
				currentBlock.yOffset++;
				if(currentBlock.isValidPosition()){
					currentBlock.updateBlockPosition();
					repaint();
				}
				else{
					timer.cancel();
					score += clearLines()*10;
					scorePanel.score.setText(""+ score);
					if(score >= scoreLimit){
						timeInterval-=100;
						scoreLimit *= 2;
					}
					updateGame();
				}
			}
		};
		timer.scheduleAtFixedRate(task, timeInterval, timeInterval);
	}
    
    //Clears lines and moves everything above lower; Returns number of lines cleared
    private int clearLines(){
    	boolean fullLine;
    	int count=0;
    	for(int row=h-1; row>=0; row--){
    		fullLine = true;
    		for(int col=0; col<w; col++){
    			if(grid[col][row] == 0){
    				fullLine=false;
    				break;
    			}
    		}
    		if(fullLine){
    			count++;
				for(int column=0; column<w; column++){
					grid[column][row] = 0;
				}
				for(int r=row; r>=0; r--){
					int temp;
					for(int c=0; c<w; c++){
						if(grid[c][r] != 0){
							temp=grid[c][r];
							grid[c][r]=grid[c][r+1];
							grid[c][r+1]=temp;
						}
					}
				}
				row=h-1;
			}
    	}
    	return count;
    }
}
