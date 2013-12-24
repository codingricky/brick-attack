package org.skree.brickattack;

import java.awt.Rectangle;
import java.awt.geom.Line2D;

/**
 * Entity which represents a brick
 * 
 * @author michaelskree
 */
public class BrickEntity extends Entity {
	// Line segments representing the four edges of brick.
	// Used for collision detection
	private Line2D top;
	private Line2D left;
	private Line2D bottom;
	private Line2D right;
	
	private Game game;
	private double moveSpeed = 0;
	
	public BrickEntity(Game game, String ref, int x, int y){
		super(ref, x, y);
		
		this.game = game;
		dx = moveSpeed;
		
		top = new Line2D.Double(x, y, x + sprite.getWidth(), y);
		left = new Line2D.Double(x, y, x,  y + sprite.getHeight());
		bottom = new Line2D.Double(x, y + sprite.getHeight(), x + sprite.getWidth(),y + sprite.getHeight());
		right = new Line2D.Double(x + sprite.getWidth(), y, x + sprite.getWidth(), y + sprite.getHeight());
		
		
	}
	
	/**
	 * Notification that this brick has been hit by the ball.
	 * 
	 * @param other Entity that has collided with the brick
	 */
	public void collidedWith(Entity other){
		Rectangle ballRectangle = new Rectangle((int) other.x,
				                                (int) other.y,
				                                (int) other.sprite.getWidth(),
				                                (int) other.sprite.getHeight());
		
		if ((top.intersects(ballRectangle)) && other.dy > 0){
				other.setVerticalSpeed(-other.dy);
		} else if ((left.intersects(ballRectangle)) && other.dx > 0){
				other.setHorizontalSpeed(-other.dx);
		} else if ((bottom.intersects(ballRectangle)) && other.dy < 0){
				other.setVerticalSpeed(-other.dy);
		} else if ((right.intersects(ballRectangle)) && other.dx < 0){
				other.setHorizontalSpeed(-other.dx);
		}
		
		game.removeEntity(this);
		game.notifyBrickHit();
		
	}
}
