package org.skree.brickattack;

/**
 * Entity which represents a ball.
 * 
 * @author michaelskree
 */

public class BallEntity extends Entity {
	
	private Game game;
	private double initialdx = 0;
	private double initialdy = 250;
	// Time of most recent collision
	private long lastCollision;
	
	public BallEntity(Game game, String ref, int x, int y){
		super(ref, x, y);
		
		this.game = game;
		lastCollision = 0;
		dx = initialdx;
		dy = initialdy;
		maxX = 350;
		maxY = 350;
	}
	
	/**
	 * Attempt to move given a total amount of elapsed time.
	 * 
	 * @param delta Total amount of elapsed time in milliseconds
	 */
	public void move(long delta){
		// If we hit a wall flip the correct velocity
		if (((dx < 0) && (x < 0)) || ((dx > 0) && (x > 785))){
			dx = -dx;
		}
		if ((dy < 0) && (y < 50)){
			dy = -dy;
		}
		
		// If the ball hit's the bottom, notify game the game that the player
		// missed the ball
		if ((dy > 0) && (y > 580)){
			game.notifyPaddleMiss();
		}
		
		super.move(delta);
	}
	
	/**
	 * Get the initial x velocity of ball.
	 * 
	 * @return Initial x velocity of ball
	 */
	public double getInitialdx(){
		return initialdx;
	}
	
	/**
	 * Get the initial y velocity of ball.
	 * 
	 * @return Initial y velocity of ball
	 */
	public double getInitialdy(){
		return initialdy;
	}
	
	/**
	 * Get the time of the most recent collision the ball was involved in
	 * 
	 * @return Time of most recent collision in milliseconds
	 */
	public long lastCollisionTime(){
		return lastCollision;
	}
	
	/**
	 * Notification that this entity has been collided with. 
	 * 
	 * @param other The other entity that has been collided with
	 */
	public void collidedWith(Entity other){
		lastCollision = System.currentTimeMillis();
	}

}
