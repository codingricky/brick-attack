package org.skree.brickattack;

/**
 * Entry point for the game. Initialize a game object and start game loop.
 * 
 * @author michaelskree
 */
public class Main {
	
	public static void main(String[] args){
		
		Game game = new Game();
		game.gameLoop();
		
	}
}
