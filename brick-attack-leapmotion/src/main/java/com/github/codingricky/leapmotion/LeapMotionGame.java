package com.github.codingricky.leapmotion;

import com.leapmotion.leap.Controller;
import org.skree.brickattack.Game;
import org.skree.brickattack.PaddleEntity;

public class LeapMotionGame extends Game {

    private final Controller controller;
    private final LeapMotionListener listener;

    public LeapMotionGame() {
        listener = new LeapMotionListener();
        controller = new Controller(listener);
    }

    @Override
    protected void movePaddle(PaddleEntity paddle) {
        paddle.setHorizontalSpeed(listener.getX() * 4);
    }

    public static void main(String[] args) {
        final LeapMotionGame leapMotionGame = new LeapMotionGame();
        leapMotionGame.gameLoop();
    }
}
