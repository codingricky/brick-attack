Brick-Attack Leap Motion
========================

[![Build Status](https://travis-ci.org/codingricky/brick-attack-leapmotion.png?branch=leap-motion)](https://travis-ci.org/codingricky/brick-attack-leapmotion)

As part of the [DiUS](http://www.dius.com.au) Hack Day, I took an existing [Breakout clone](https://github.com/michaelskree/brick-attack) and attached very basic
[Leap Motion](https://www.leapmotion.com/) controls to it. The reasons I did this were firstly to justify my purchase of the Leap Motion toy :), and also secondly
to experiment with the Leap Motion API.


Build Instructions
------------------
1. git clone https://github.com/codingricky/brick-attack-leapmotion
2. cd brick-attack-leapmotion
3. mvn clean install
4. cd brick-attack-leapmotion/target/brick-attack-leapmotion-1.0-SNAPSHOT-release/brick-attack-leapmotion-1.0-SNAPSHOT/
5. ./game.sh

You should be able to use your hands to move the paddle left and right.