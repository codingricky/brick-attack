package org.skree.brickattack;

import com.leapmotion.leap.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

/**
 * This is our Game object. It contains our main game loop and other state
 * information.
 *
 * @author michaelskree
 */
public class Game extends Canvas {

    private static final long serialVersionUID = 1L;

    private PaddleEntity paddle;
    private BallEntity ball;
    // Number of remaining bricks
    private int brickCount;
    // Current game message to display
    private String message = "";
    // Player's current score
    private int score;
    // Current level
    private int level = 1;
    // Number of remaining lives
    private int remainingLives = 4;
    // Y coordinate for the top wall
    private int top = 50;

    // Buffer strategy that allows us to use accelerated surface
    private BufferStrategy strategy;
    // True if game is running
    private boolean gameRunning = true;
    // List of all entities in the game
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    // List of entities to be removed
    private ArrayList<Entity> removeList = new ArrayList<Entity>();
    // Keyboard event handler
    private KeyInputHandler keyInputHandler;
    // True if a collision has been detected during the current loop
    private boolean collisionDetected = false;
    // True if a life has just been lost
    private boolean lifeLost = false;
    // True if a new game should be started
    private boolean newGame = true;

    /**
     * Creates Game object and starts the game running.
     */
    public Game() {
        // Create a JFrame for our game
        JFrame container = new JFrame("Brick Attack");

        // Get content panel associated with frame and set up the resolution
        JPanel panel = (JPanel) container.getContentPane();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(null);

        // Set up canvas size and add it to content of frame
        setBounds(0, 0, 800, 600);
        panel.add(this);

        // Tell AWT not to repaint canvas because we'll do it ourselves
        // in accelerated mode
        setIgnoreRepaint(true);

        // Make window visible
        container.pack();
        container.setResizable(false);
        container.setVisible(true);

        // Add listener to respond to user closing window
        container.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        // Add keyboard event listener
        keyInputHandler = new KeyInputHandler(this);
        addKeyListener(keyInputHandler);

        // Request focus so we can read key events
        requestFocus();

        // Create buffer strategy that will allow us to use accelerated surface
        createBufferStrategy(2);
        strategy = getBufferStrategy();

        initEntities();
    }

    /**
     * Clear out old data and start a new game
     */
    public void startGame() {
        initEntities();

        // Clear our keyboard data
        keyInputHandler.clearData();

        // Clear current game message
        message = "";

        // Set score to 0
        score = 0;

        // Reset lives
        remainingLives = 4;
        lifeLost = false;

        // Reset to level 1
        level = 1;

        // We are no longer requesting a new game
        newGame = false;
    }

    /**
     * Initialize all entities. Set up grid of bricks and paddle.
     */
    public void initEntities() {
        entities.clear();
        brickCount = 0;

        // Add brick grid
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 11; col++) {
                BrickEntity brick = new BrickEntity(this,
                        "sprites/brick.gif",
                        50 + col * 60,
                        top + 50 + row * 30);
                entities.add(brick);
                brickCount++;
            }
        }

        // Add paddle
        paddle = new PaddleEntity(this,
                "sprites/paddle.gif",
                337,
                550);
        entities.add(paddle);

        // Add ball
        ball = new BallEntity(this,
                "sprites/ball.gif",
                400,
                350);
        entities.add(ball);

    }

    /**
     * Resume game after a life has been lost.
     */
    public void resumeAftrLostLife() {
        ball.setX(400, 350);
        keyInputHandler.clearData();
        ball.setHorizontalSpeed(0);
        ball.setVerticalSpeed(250);
        paddle.setX(337, 550);
        lifeLost = false;
        message = "";
    }

    /**
     * Remove an entity from the game.
     *
     * @param entity The entity to be removed
     */
    public void removeEntity(Entity entity) {
        removeList.add(entity);
    }

    /**
     * Notify game that a brick has been hit.
     */
    public void notifyBrickHit() {
        brickCount--;
        score += 50;
        if (brickCount == 0) {
            notifyLevelComplete();
        }
    }

    /**
     * Notify player that they have completed the current level.
     */
    public void notifyLevelComplete() {
        message = "Congratulations! Level Complete!";
        level++;
        keyInputHandler.clearData();
        keyInputHandler.waitForKeyPress();
    }

    /**
     * Notify game that the player missed the ball
     */
    public void notifyPaddleMiss() {
        if (remainingLives == 0) {
            notifyGameOver();
        } else {
            remainingLives--;
            lifeLost = true;
            message = "You lost a life!";
        }
        keyInputHandler.waitForKeyPress();
    }

    /**
     * Notify game that the player has lost all their lives
     */
    public void notifyGameOver() {
        message = "Game Over";

        // Request that a new game be started
        newGame = true;
    }

    /**
     * Check if new game is requested.
     *
     * @return True if a new game should be started
     */
    public boolean checkNewGame() {
        return newGame;
    }

    /**
     * Check if player has just lost a life.
     *
     * @return True if a life has just been lost
     */
    public boolean checkLifeLost() {
        return lifeLost;
    }

    /**
     * Our main game loop
     */
    public void gameLoop() {
        long lastLoopTime = System.currentTimeMillis();

        while (gameRunning) {
            // Calculate how long it's been since last loop
            long delta = System.currentTimeMillis() - lastLoopTime;
            lastLoopTime = System.currentTimeMillis();

            collisionDetected = false;

            // Get graphics context and fill it with black
            Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 800, 600);

            // Create white bar for top wall
            g.setColor(Color.white);
            g.drawLine(0, top, 800, top);

            // Iterate through list of entities and move each one
            if (!keyInputHandler.waitingForAnyKeyPress()) {
                for (int i = 0; i < entities.size(); i++) {
                    Entity entity = (Entity) entities.get(i);
                    entity.move(delta);
                }
            }

            // Iterate through list of entities and draw each one
            for (int i = 0; i < entities.size(); i++) {
                Entity entity = (Entity) entities.get(i);
                entity.draw(g);
            }

            // Check collisions
            int i = 0;
            while ((!collisionDetected) && (i < entities.size())) {
                Entity entity = (Entity) entities.get(i);
                if (!(entity instanceof BallEntity)) {
                    if (entity.collidesWith(ball)) {
                        entity.collidedWith(ball);
                        collisionDetected = true;
                    }
                }
                i++;
            }

            // Display current score, level, and remaining lives
            g.setColor(Color.white);
            g.drawString("Score: " + score,
                    10,
                    25);
            g.drawString("Level: " + level,
                    400,
                    25);
            g.drawString("Remaining Lives: " + remainingLives,
                    650,
                    25);

            // Remove entities that have been hit
            entities.removeAll(removeList);
            removeList.clear();

            // If we're waiting for an "any key" press, print the correct
            // message
            if (keyInputHandler.waitingForAnyKeyPress()) {
                g.setColor(Color.white);
                g.drawString(message,
                        (800 - g.getFontMetrics().stringWidth(message)) / 2,
                        300);
                g.drawString("Press Any Key",
                        (800 - g.getFontMetrics().stringWidth("Press Any Key")) / 2,
                        350);
            }

            // Drawing is now complete, so clear graphics and switch buffers
            // over
            g.dispose();
            strategy.show();
            movePaddle(paddle);

            // Pause for a little bit
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
    }

    protected void movePaddle(PaddleEntity paddle) {
        // Figure out movement of paddle.
        paddle.setHorizontalSpeed(0);


        if ((keyInputHandler.isLeftPressed()) && (!keyInputHandler.isRightPressed())) {
            paddle.setHorizontalSpeed(-300);
        } else if (!keyInputHandler.isLeftPressed() && (keyInputHandler.isRightPressed())) {
            paddle.setHorizontalSpeed(300);
        }
    }
}
