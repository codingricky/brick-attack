package org.skree.brickattack;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Entities represent the objects that appear in the game. Each entity has a
 * position expressed in x,y coordinates, velocity expressed in pixes/sec,
 * and a sprite which is will determine what the entity looks like.
 * 
 * @author michaelskree
 */
public abstract class Entity {
	// X location of entity
	protected double x;
	// Y location of entity
	protected double y;
	// Sprite that represents entity
	protected Sprite sprite;
	// Horizontal velocity in pixels/sec
	protected double dx;
	// Vertical speed in pixels/sec
	protected double dy;
	// Maximum speed in x direction in pixels/sec
	protected double maxX;
	// Maximum speed in y direction in pixels/sec
	protected double maxY;
	// Rectangle used for this entity during collision detection
	private Rectangle thisEntity = new Rectangle();
	// Rectangle used for the other entity during collision detection
	private Rectangle otherEntity = new Rectangle();
	
	/**
	 * Construct an entity with given sprite and location
	 */
	public Entity(String ref, double x, double y){
		this.sprite = SpriteStore.get().getSprite(ref);
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Request that the entity move itself based on a certain amount of time
	 * passing
	 * 
	 * @param delta The amount of time that has passed in milliseconds
	 */
	public void move(long delta){
		x += (delta*dx) / 1000;
		y += (delta*dy) / 1000;
	}
	
	/**
	 * Set the position of this entity.
	 * 
	 * @param x X position of this entity
	 * @param y Y position of this entity
	 */
	public void setX(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set the horizontal speed of this entity
	 * 
	 * @param dx Horizontal speed in pixels/sec
	 */
	public void setHorizontalSpeed(double dx){
		this.dx = dx;
	}
	
	/**
	 * Set the vertical speed of this entity
	 * 
	 * @param dy Vertical speed in pixels/sec
	 */
	public void setVerticalSpeed(double dy){
		this.dy = dy;
	}
	
	/**
	 * Get the horizontal speed of this entity
	 * 
	 * @return Horizontal speed in pixels/sec
	 */
	public double getHorizontalSpeed(){
		return dx;
	}
	
	/**
	 * Get the vertical speed of this entity
	 * 
	 * @return Vertical speed in pixels/sec
	 */
	public double getVerticalSpeed(){
		return dy;
	}
	
	/**
	 * Get the maximum horizontal speed of this entity
	 * 
	 * @return Maximum horizontal speed of this entity
	 */
	public double getMaxX(){
		return maxX;
	}
	
	/**
	 * Get the maximum vertical speed of this entity
	 * 
	 * @return Maximum vertical speed of this entity
	 */
	public double getMaxY(){
		return maxY;
	}
	
	/**
	 * Draw entity to provided graphics context
	 * 
	 * @param g The graphics context on which to draw the entity
	 */
	public void draw(Graphics g){
		sprite.draw(g, (int) x, (int) y);
	}
	
	/**
	 * Check if this entity collided with another.
	 * 
	 * @param other The other entity to check collision with
	 * @return True if the entities collided
	 */
	public boolean collidesWith(Entity other){
		thisEntity.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		otherEntity.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(), other.sprite.getHeight());
		
		return thisEntity.intersects(otherEntity);
	}
	
	/**
	 * Notification that this entity has been collided with
	 * 
	 * @param other The entity that has been collided with
	 */
	public abstract void collidedWith(Entity other);

}
