package org.skree.brickattack;

import java.awt.Graphics;
import java.awt.Image;

/**
 * Sprite to be displayed on screen. A sprite is simply an image and contains
 * no other state information.
 * 
 * @author michaelskree
 */
public class Sprite {
	
	// Image to be drawn for this sprite
	private Image image;
	
	/**
	 * Construct a new sprite based on a given image.
	 * 
	 * @param image The image to be used for the sprite
	 */
	public Sprite(Image image){
		this.image = image;
	}
	
	/**
	 * Get the width of sprite
	 * 
	 * @return The width of the sprite
	 */
	public int getWidth(){
		return image.getWidth(null);
	}
	
	/**
	 * Get the height of the sprite
	 * 
	 * @return The height of the sprite
	 */
	public int getHeight(){
		return image.getHeight(null);
	}
	
	/**
	 * Draw sprite onto graphics context provided
	 * 
	 * @param g The graphics context onto which the sprite should be drawn
	 * @param x The x location at which to draw the sprite
	 * @param y The y location at which to draw the sprite
	 */
	public void draw(Graphics g, int x, int y){
		g.drawImage(image, x, y, null);
	}
}
