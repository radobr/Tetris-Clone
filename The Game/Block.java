package tetris;

import java.util.Arrays;
import java.util.Random;
import java.util.TreeMap;
import java.util.Vector;
 
public class Block{
	//Contains all information about the shapes and how to manipulate them
	
	//Used for (re)positioning blocks and in determining square coordinates
	int xOffset, yOffset;
	//Stores current block in matrix form
	int[][] currentBlock;
	
	//The shapes 
	private static final int [][] SQUARE_BLOCK = {{0, 1, 1, 0},
							 			  		  {0, 1, 1, 0},
												  {0, 0, 0, 0},
												  {0, 0, 0, 0}};
	
	private static final int [][] T_BLOCK = {{0, 0, 1, 0},
			 				   		 		 {0, 1, 1, 0},
											 {0, 0, 1, 0},
											 {0, 0, 0, 0}};
	
	private static final int [][] Z_BLOCK = {{0, 0, 1, 1},
			 				   		 		 {0, 1, 1, 0},
											 {0, 0, 0, 0},
											 {0, 0, 0, 0}};
	
	private static final int [][] L_BLOCK = {{0, 1, 0, 0},
							   		 		 {0, 1, 0, 0},
							   		 		 {0, 1, 1, 0},
							   		 		 {0, 0, 0, 0}};
		
	private static final int [][] BAR_BLOCK = {{0, 1, 0, 0},
			 			  		 	   		   {0, 1, 0, 0},
			 			  		 	   		   {0, 1, 0, 0},
			 			  		 	   		   {0, 1, 0, 0}};

	//List of all shapes
	private static final int [][][] blockList = {SQUARE_BLOCK,
								   		  T_BLOCK,
								   		  Z_BLOCK,
								   		  L_BLOCK,
								   		  BAR_BLOCK};
	//Used to select a random shape and a random color
	private static Random r = new Random();
	//Needed to display blocks on the game screen 
	private int[][] grid; 
	//Stores coordinates of all squares in the block
	private Vector<Integer> blockCoords;
	
	//Constructor; creates a random shape and displays it on the game screen
	public Block(int[][] gamestate){
		grid = gamestate;
		currentBlock = blockList[r.nextInt(blockList.length)].clone();
		int color = 0;
		while(color == 0){
			color = r.nextInt(TetrisGamePanel.colors.length);
		}
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(currentBlock[i][j] != 0)
					currentBlock[i][j] = color; 
			}
		}
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				//Game grid was inverted so needed to swap j and i for current block to display blocks correctly
				if(currentBlock[j][i] != 0){
					grid[i+xOffset][j+yOffset] = currentBlock[j][i];
				}
			}
		}

	}
	//Constructor; creates block based on another one and the current state of the game
	public Block(Block nextBlock, int gamestate[][]){
		
		grid = gamestate;
		currentBlock = nextBlock.currentBlock;
		xOffset = (int)(grid.length/2) - 2;
		yOffset = 0;
		blockCoords = new Vector<>(8);
		
		for(int i=0; i<4; i++){
			for(int j=0; j<4; j++){
				if(currentBlock[j][i] != 0){
					blockCoords.add(i);//+xOffset
					blockCoords.add(j);//+yOffset
					grid[i+xOffset][j+yOffset] = currentBlock[j][i];
				}
			}
		}
	}
	
	//Lowers block by one line
	void updateBlockPosition(){
		int temp;
		for(int i=6; i>=0; i-=2){
			temp = grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset-1];
			grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset-1] = grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset];
			grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset] = temp;
		}
	}

	//Checks if this position is valid; normally called right after offsets are changed
	boolean isValidPosition() {
		//Stores the maximum y value for every x in the grid
		TreeMap<Integer, Integer> yMaxValues = new TreeMap<>();
		int x, y;
		
		for(int i=0; i<8; i+=2){
			x=blockCoords.get(i)+xOffset;
			y=blockCoords.get(i+1)+yOffset;
			if(y>=grid[0].length){
				return false;
			}
			yMaxValues.put(x, y);
		}
		for(int key:yMaxValues.keySet()){
			if(grid[key][yMaxValues.get(key)] != 0){
				return false;
			}
		}
		return true;
	}
	//Moves currently active block to the right
	void moveRight(){
		TreeMap<Integer, Integer> xMaxValues = new TreeMap<>();
		int x, y;
		
		xOffset++;
		for(int i=0; i<8; i+=2){
			x=blockCoords.get(i)+xOffset;
			y=blockCoords.get(i+1)+yOffset;
			if(x>=grid.length){
				xOffset--;
				return;
			}
			xMaxValues.put(y, x);
		}
		for(int key:xMaxValues.keySet()){
			if(grid[xMaxValues.get(key)][key] != 0){
				xOffset--;
				return;
			}
		}
		int temp;
		for(int i=6; i>=0; i-=2){
			temp = grid[blockCoords.get(i)+xOffset-1][blockCoords.get(i+1)+yOffset];
			grid[blockCoords.get(i)+xOffset-1][blockCoords.get(i+1)+yOffset] = grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset];
			grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset] = temp;
		}
	}
	//Moves currently active block to the left
	void moveLeft(){
		TreeMap<Integer, Integer> xMinValues = new TreeMap<>();
		int x, y;
		
		xOffset--;
		for(int i=6; i>=0; i-=2){
			x=blockCoords.get(i)+xOffset;
			y=blockCoords.get(i+1)+yOffset;
			if(x<0){
				xOffset++;
				return;
			}
			xMinValues.put(y, x);
		}
		for(int key:xMinValues.keySet()){
			if(grid[xMinValues.get(key)][key] != 0){
				xOffset++;
				return;
			}
		}
		int temp;
		for(int i=0; i<8; i+=2){
			temp = grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset];
			grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset] = grid[blockCoords.get(i)+xOffset+1][blockCoords.get(i+1)+yOffset];
			grid[blockCoords.get(i)+xOffset+1][blockCoords.get(i+1)+yOffset] = temp;
		}
	}
	//Rotates current block by 90 degrees clockwise; does nothing if position would be invalid
	void rotateBlock(){
		if(Arrays.deepEquals(currentBlock, SQUARE_BLOCK)){
			return;
		}
		// Create copy of current block's matrix
		int blockCopy[][] = new int[4][4];
		for (int i = 0; i<4; i++) {
			for (int j = 0; j<4; j++) {
				blockCopy[i][j] = currentBlock[i][j];
			}
		}
		// Rotate copy of current block's matrix
		for (int i = 0; i<=2 - i; i++) {
			for (int j = i; j<3 - i; j++) {
				int temp = blockCopy[i][j];
				blockCopy[i][j] = blockCopy[3-j][i];
				blockCopy[3-j][i] = blockCopy[3-i][3-j];
				blockCopy[3-i][3-j] = blockCopy[j][3-i];
				blockCopy[j][3-i] = temp;
			}
		}
		// Temporarily remove current block from grid
		for (int i = 6; i>=0; i-=2) {
			grid[blockCoords.get(i) + xOffset][blockCoords.get(i+1) + yOffset] = 0;
		}
		//Check if rotated block position is valid
		boolean ok = true;
		for (int i = 0; i<4 && ok ; i++) {
			for (int j = 0; j<4; j++) {
				if (blockCopy[j][i] != 0) {
					if (i + xOffset >= grid.length ||
						i + xOffset <0 ||
						j + yOffset >= grid[0].length) {
						ok = false;
						break;
					} else {
						if (grid[i + xOffset][j + yOffset] != 0) {
							ok = false;
							break;
						}
					}
				}
			}
		}
		//Sets current block matrix and coordinates 
		if(ok){
			currentBlock = blockCopy;
			blockCoords = new Vector<>(8);
			for(int i=0; i<4; i++){
				for(int j=0; j<4; j++){
					if(currentBlock[j][i] != 0){
						blockCoords.add(i);
						blockCoords.add(j);
					}
				}
			}
		}
		//Puts current block back in the grid, whether it was changed or not
		for(int i=6; i>=0; i-=2){
			grid[blockCoords.get(i)+xOffset][blockCoords.get(i+1)+yOffset]=currentBlock[blockCoords.get(i+1)][blockCoords.get(i)];
		}
	}
	//Drops current block all the way down
	void dropDown(){
		while(true){
			yOffset++;
			if(isValidPosition()){
				updateBlockPosition();
			}
			else{
				yOffset--;
				break;
			}
		}
	}
}
