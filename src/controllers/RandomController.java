package controllers;

import world.ACTIONS;

import java.util.Random;

/**
 * Add a description of the class here.
 *
 * @author Damien Anderson (Damorin)
 *         16/07/2016
 */
public class RandomController {

    private Random rng;

    public RandomController() {
        rng = new Random();
    }

    public ACTIONS makeMove() {
        int num = rng.nextInt(4);
        if (num == 0) {
            return ACTIONS.UP;
        } else if (num == 1) {
            return ACTIONS.DOWN;
        } else if (num == 2) {
            return ACTIONS.LEFT;
        } else if (num == 3) {
            return ACTIONS.RIGHT;
        } else {
            return ACTIONS.NO_MOVE;
        }

    }

}
