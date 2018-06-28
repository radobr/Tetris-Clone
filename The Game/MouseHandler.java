package tetris;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseHandler extends MouseAdapter {
	//Handles mouse input 
	
	//The application
	TetrisGamePanel game;
	
	//Constructor
	public MouseHandler(TetrisGamePanel tetris){
		game = tetris;
	}
	//Specifies behavior for mouse input
	public void mouseClicked(MouseEvent e){
		try{
			switch(e.getButton()){
				case MouseEvent.BUTTON1:{
					game.currentBlock.moveLeft();
					game.repaint();
					break;
				}
				case MouseEvent.BUTTON2:{
					game.currentBlock.rotateBlock();
					game.repaint();
					break;
				}
				case MouseEvent.BUTTON3:{
					game.currentBlock.moveRight();
					game.repaint();
					break;
				}
			}
		}
		catch(NullPointerException ex){
			return;
		}
	}
	//TODO: doesn't seem to work for some reason; might try to fix it later
	public void mouseWheelMoved(MouseWheelEvent e){
		if(e.getWheelRotation() > 0){
			game.currentBlock.dropDown();
			game.repaint();
		}
	}
}
