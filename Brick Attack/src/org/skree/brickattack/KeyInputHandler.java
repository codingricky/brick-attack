package org.skree.brickattack;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * A class that handles keyboard input from the user.
 * 
 * @author michaelskree
 */
public class KeyInputHandler extends KeyAdapter {
	
	// True if we are waiting for an "any key" press
	private boolean waitingForKeyPress = true;
	// True if left arrow key is currently pressed
	private boolean leftPressed = false;
	// True if right arrow key is currently pressed
	private boolean rightPressed = false;
	// Our current game
	private Game game;
	
	/**
	 * Construct a Key Input Handler.
	 * 
	 * @param game Our current game
	 */
	public KeyInputHandler(Game game){
		this.game = game;
	}
	
	/**
	 * Notification from AWT that a key has been pressed, i.e. pushed down
	 * 
	 * @param e The details of the key that was pressed.
	 */
	public void keyPressed(KeyEvent e){
		// If we're waiting for "any key" don't do anything
		if (waitingForKeyPress){
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			leftPressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = true;
		}
	}
	
	/**
	 * Notification from AWT that a key has been released
	 * 
	 * @param e The details of the key that was released
	 */
	public void keyReleased(KeyEvent e){
		// If we're waiting for "any key" don't do anything
		if (waitingForKeyPress){
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			leftPressed = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightPressed = false;
		}
	}
	
	/**
	 * Notification from AWT that a key has been typed. This means it has been
	 * pressed and released
	 * 
	 * @param e The details of the key that was typed
	 */
	public void keyTyped(KeyEvent e){
		// If we're waiting for a key press start or resume the game
		if (waitingForKeyPress){
			waitingForKeyPress = false;
			if (game.checkNewGame()){
				game.startGame();
			} else if (game.checkLifeLost()) {
				game.resumeAftrLostLife();
			} else {
				game.initEntities();
			}
		}
		
		// If we hit escape, exit the game
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}
	
	/**
	 * Clear our keyboard data, i.e. set all key pressed values to false.
	 */
	public void clearData(){
		leftPressed = false;
		rightPressed = false;
	}
	
	/**
	 * Request that game play be halted until "any key" is pressed.
	 */
	public void waitForKeyPress(){
		waitingForKeyPress = true;
	}
	
	/**
	 * Are we waiting for an "any key" press?
	 * 
	 * @return True if we are waiting for an "any key" press.
	 */
	public boolean waitingForAnyKeyPress(){
		return waitingForKeyPress;
	}
	
	/**
	 * Check if left arrow is pressed.
	 * 
	 * @return True if left arrow key is pressed
	 */
	public boolean isLeftPressed(){
		return leftPressed;
	}
	
	/**
	 * Check if right arrow is pressed.
	 * 
	 * @return True if right arrow key is pressed
	 */
	public boolean isRightPressed(){
		return rightPressed;
	}
}
