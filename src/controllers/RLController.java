package controllers;

import com.sun.javafx.geom.Vec2d;
import world.ACTIONS;
import world.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Add a description of the class here.
 *
 * @author Damien Anderson (Damorin)
 *         16/07/2016
 */
public class RLController {

    private int[][] values;
    private List<ACTIONS> path;
    private Grid grid;
    private Random rng;

    private Vec2d position;

    private int size;

    public RLController(int size, Grid grid) {
        path = new ArrayList<>();
        rng = new Random();
        this.size = size;
        values = new int[size][size];
        this.grid = grid;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                values[i][j] = 0;
            }
        }
    }

    public ACTIONS makeMove() {
        this.position = grid.getAvatarPosition();
        ACTIONS toReturn;

        List<Integer> actionValues = checkValues();
        int action = -1;
        int value = 0;
        for (int i = 0; i < actionValues.size(); i++) {
            if (actionValues.get(i) > value)
                action = i;
        }

        if (action >= 0) {
            System.out.println("Good Move");
            if (action == 0) {
                toReturn = ACTIONS.UP;
            } else if (action == 1) {
                toReturn = ACTIONS.DOWN;
            } else if (action == 2) {
                toReturn = ACTIONS.LEFT;
            } else if (action == 3) {
                toReturn = ACTIONS.RIGHT;
            } else {
                toReturn = ACTIONS.NO_MOVE;
            }
            return toReturn;
        } else {

            System.out.println("Random Move");
            int num = rng.nextInt(4);
            if (num == 0) {
                toReturn = ACTIONS.UP;
            } else if (num == 1) {
                toReturn = ACTIONS.DOWN;
            } else if (num == 2) {
                toReturn = ACTIONS.LEFT;
            } else if (num == 3) {
                toReturn = ACTIONS.RIGHT;
            } else {
                toReturn = ACTIONS.NO_MOVE;
            }
            path.add(toReturn);
            return toReturn;
        }
    }

    private List<Integer> checkValues() {

        List<Integer> actionValues = new ArrayList<>();

        int x = (int) position.x;
        int y = (int) position.y;

        if (x - 1 >= 0 && x - 1 < size)
            actionValues.add(values[x - 1][y]);
        else
            actionValues.add(-1000);
        if (x + 1 >= 0 && x + 1 < size)
            actionValues.add(values[x + 1][y]);
        else
            actionValues.add(-1000);
        if (y - 1 >= 0 && y - 1 < size)
            actionValues.add(values[x][y - 1]);
        else
            actionValues.add(-1000);
        if (y + 1 >= 0 && y + 1 < size)
            actionValues.add(values[x][y + 1]);
        else
            actionValues.add(-1000);

        return actionValues;
    }

    public void updateValues() {
        int avatarX = (int) grid.getAvatarPosition().x;
        int avatarY = (int) grid.getAvatarPosition().y;
        values[(int) grid.getAvatarPosition().x][(int) grid.getAvatarPosition().y] = 1 / 5;
        for (int i = path.size() - 1; i >= 0; i--) {
            ACTIONS action = path.get(i);
            if (action == ACTIONS.UP) {
                if (avatarX - 1 >= 0 && avatarX - 1 < size)
                    values[avatarX - 1][avatarY] = ((grid.getScore() / 10));
            } else if (action == ACTIONS.DOWN) {
                if (avatarX + 1 >= 0 && avatarX + 1 < size)
                    values[avatarX + 1][avatarY] = ((grid.getScore() / 10));
            } else if (action == ACTIONS.LEFT) {
                if (avatarY - 1 >= 0 && avatarY - 1 < size)
                    values[avatarX][avatarY - 1] = ((grid.getScore() / 10));
            } else if (action == ACTIONS.RIGHT) {
                if (avatarY + 1 >= 0 && avatarY + 1 < size)
                    values[avatarX][avatarY + 1] = ((grid.getScore() / 10));
            }
        }
    }

    public void displayValues() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(values[j][i] + " ");
            }
            System.out.println();
        }
    }
}
