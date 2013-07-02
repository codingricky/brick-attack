package org.skree.brickattack;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * A sprite resource manager. Sprites are cached in a hash map which allows
 * us to use the same sprite for multiple entities without having to reload
 * the image. Implemented as a singleton.
 * 
 * @author michaelskree
 */
public class SpriteStore {
	
	// Single instance of SpriteStore class
	private static SpriteStore store = new SpriteStore();
	
	/**
	 * Get the single instance of SpriteStore class
	 * 
	 * @return The single instance of SpriteStore class
	 */
	public static SpriteStore get(){
		return store;
	}
	
	// Hash map into which sprites are stored.
	// Contains mappings from reference strings to sprite instances
	private HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	/**
	 * Get a sprite from the store
	 * 
	 * @param ref The reference string for this sprite
	 * @return The desired sprite instance
	 */
	public Sprite getSprite(String ref){
		// If the sprite is already in the hash map, then return it
		if(sprites.get(ref) != null){
			return (Sprite) sprites.get(ref);
		}
		
		// If the sprite is not already in hash map, then retrieve it from
		// resource loader, add it to the map, and return it
		BufferedImage spriteImage = null;
		
		try{
			URL url = this.getClass().getClassLoader().getResource(ref);
			
			if(url == null){
				// If we can't load the image then exit
				System.out.println("Can't load image: " + ref);
				System.exit(0);
			}
			
			spriteImage = ImageIO.read(url);
		} catch(IOException e){
			System.out.println("Failed to load: " + ref);
			System.exit(0);
		}
			
		// Create accelerated image of correct size
		GraphicsConfiguration gc = 
				GraphicsEnvironment.getLocalGraphicsEnvironment().
				getDefaultScreenDevice().
				getDefaultConfiguration();
		Image image = gc.createCompatibleImage(spriteImage.getWidth(),
				                               spriteImage.getHeight(),
				                               Transparency.BITMASK);
			
		// Draw sprite image into accelerated image
		image.getGraphics().drawImage(spriteImage, 0, 0, null);
			
		// Create sprite, add it to hash map, and return it
		Sprite sprite = new Sprite(image);
		sprites.put(ref, sprite);
			
		return sprite;
	}
}
