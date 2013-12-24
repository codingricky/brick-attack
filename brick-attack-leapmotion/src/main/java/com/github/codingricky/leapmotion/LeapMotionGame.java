package com.github.codingricky.leapmotion;

import com.leapmotion.leap.Controller;
import org.skree.brickattack.Game;
import org.skree.brickattack.PaddleEntity;

public class LeapMotionGame extends Game {

    private static final int MOVEMENT_MULTIPLIER = 4;
    private final Controller controller;
    private final LeapMotionListener listener;

    public LeapMotionGame() {
        // need to keep references to these objects otherwise
        // the leap motion libraries crash
        listener = new LeapMotionListener();
        controller = new Controller(listener);
    }

    @Override
    protected void movePaddle(PaddleEntity paddle) {
        paddle.setHorizontalSpeed(listener.getX() * MOVEMENT_MULTIPLIER);
    }

    public static void main(String[] args) {
        final LeapMotionGame leapMotionGame = new LeapMotionGame();
        leapMotionGame.gameLoop();
    }
}
