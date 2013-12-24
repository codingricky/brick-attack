package com.github.codingricky.leapmotion;


import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;

public class LeapMotionListener extends Listener {

    private float x;

    public LeapMotionListener() {
    }

    public void onFrame(Controller controller) {
        final Hand hand = controller.frame().hands().get(0);
        if (hand.palmPosition().isValid()) {
            x = hand.palmPosition().getX();
        } else {
            x = 0;
        }
    }

    public float getX() {

        return x;
    }
}