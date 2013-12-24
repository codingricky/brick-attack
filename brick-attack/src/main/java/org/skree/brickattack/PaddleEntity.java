package org.skree.brickattack;

/**
 * Entity which represents a paddle
 * 
 * @author michaelskree
 */

public class PaddleEntity extends Entity {
	
	public PaddleEntity(Game game, String ref, int x, int y){
		super(ref, x, y);
	}
	
	/**
	 * Attempt to move given a total amount of elapsed time.
	 * 
	 * @param delta Amount of elapsed time in milliseconds
	 */
	public void move(long delta){
		// If the paddle is moving left and hits the left edge of the screen
		// do nothing
		if((dx < 0) && (x < 0)){
			return;
		}
		// If the paddle is moving right and hits the right edge of the screen
		// do nothing
		if((dx > 0) && (x > 675)){
			return;
		}
		
		super.move(delta);
	}
	
	/**
	 * Notification that the paddle has been hit by the ball
	 * 
	 * @param other The entity that has been collided with
	 */
	public void collidedWith(Entity other){
		if(other.dy > 0){
			other.setVerticalSpeed(-other.dy);
			
			// Calculate the other entity's horizontal distance from the
			// middle of the paddle
			double xDiff = other.x - (x + sprite.getWidth() / 2);
			
			// Normalize this value
			double normValue = xDiff / (sprite.getWidth() / 2);
			
			double newHorizontalSpeed = other.dx + normValue*other.getMaxX();
			
			if(newHorizontalSpeed > other.getMaxX()){
				newHorizontalSpeed = other.getMaxX();
			} else if (newHorizontalSpeed < -other.getMaxX()){
				newHorizontalSpeed = -other.getMaxX();
			}
			other.setHorizontalSpeed(newHorizontalSpeed);
		}
	}
	
}
