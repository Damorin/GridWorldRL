package world;

import com.sun.javafx.geom.Vec2d;

import java.util.Random;

/**
 * Add a description of the class here.
 *
 * @author Damien Anderson (Damorin)
 *         11/07/2016
 */
public class Grid {

    private GridSquare[][] originalWorld;
    private GridSquare[][] currentWorld;

    private Random rng;

    private Vec2d avatarPosition;

    private int size;

    private boolean gameOver = false;
    private boolean playerWon = false;

    private int score;

    public Grid(int size) {
        this.originalWorld = new GridSquare[size][size];
        this.currentWorld = new GridSquare[size][size];

        this.score = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.originalWorld[i][j] = new GridSquare(i, j);
                this.currentWorld[i][j] = new GridSquare(i, j);
            }
        }

        this.avatarPosition = new Vec2d(0, 0);
        this.rng = new Random();
        this.size = size;
        this.createWorld();
    }

    private void createWorld() {
        // Avatar Start Position
        int row = this.rng.nextInt(size);
        int column = 0;

        this.avatarPosition.set(row, column);
        this.currentWorld[row][column].setContent(WORLDOBJECTS.AVATAR);
        this.originalWorld[row][column].setContent(WORLDOBJECTS.AVATAR);

        // Goal position
        while (row == this.avatarPosition.x && column == this.avatarPosition.y) {
            row = this.rng.nextInt(size);
            column = this.rng.nextInt(size);
        }

        this.currentWorld[row][column].setContent(WORLDOBJECTS.GOAL);
        this.originalWorld[row][column].setContent(WORLDOBJECTS.GOAL);

        for (int i = 0; i < size * 1.5; i++) {
            while (this.currentWorld[row][column].getContent() != WORLDOBJECTS.EMPTY) {
                row = this.rng.nextInt(size);
                column = this.rng.nextInt(size);
            }
            this.currentWorld[row][column].setContent(WORLDOBJECTS.TRAP);
            this.originalWorld[row][column].setContent(WORLDOBJECTS.TRAP);
        }
    }

    public void displayWorld() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(this.currentWorld[i][j].getContent() + " ");
            }
            System.out.println();
        }
    }

    public boolean move(ACTIONS action) {
        int avatarX = (int) getAvatarPosition().x;
        int avatarY = (int) getAvatarPosition().y;


        if (action == ACTIONS.UP) {
            if (isWithinBounds(avatarX - 1)) {
                currentWorld[avatarX][avatarY].setContent(WORLDOBJECTS.EMPTY);
                checkWinner(avatarY, avatarX - 1);
                checkTrap(avatarY, avatarX - 1);
                currentWorld[avatarX - 1][avatarY].setContent(WORLDOBJECTS.AVATAR);
                avatarPosition.x = avatarX - 1;
            }
        } else if (action == ACTIONS.DOWN) {
            if (isWithinBounds(avatarX + 1)) {
                currentWorld[avatarX][avatarY].setContent(WORLDOBJECTS.EMPTY);
                checkWinner(avatarY, avatarX + 1);
                checkTrap(avatarY, avatarX + 1);
                currentWorld[avatarX + 1][avatarY].setContent(WORLDOBJECTS.AVATAR);
                avatarPosition.x = avatarX + 1;
            }
        } else if (action == ACTIONS.LEFT) {
            if (isWithinBounds(avatarY - 1)) {
                currentWorld[avatarX][avatarY].setContent(WORLDOBJECTS.EMPTY);
                checkWinner(avatarY - 1, avatarX);
                checkTrap(avatarY - 1, avatarX);
                currentWorld[avatarX][avatarY - 1].setContent(WORLDOBJECTS.AVATAR);
                avatarPosition.y = avatarY - 1;
            }
        } else if (action == ACTIONS.RIGHT) {
            if (isWithinBounds(avatarY + 1)) {
                currentWorld[avatarX][avatarY].setContent(WORLDOBJECTS.EMPTY);
                checkWinner(avatarY + 1, avatarX);
                checkTrap(avatarY + 1, avatarX);
                currentWorld[avatarX][avatarY + 1].setContent(WORLDOBJECTS.AVATAR);
                avatarPosition.y = avatarY + 1;
            }
        }
        return true;
    }

    private boolean isWithinBounds(int avatarY) {
        return avatarY >= 0 && avatarY + 1 < size;
    }

    private void checkWinner(int avatarY, int i) {
        if (getStatusOf(i, avatarY) == WORLDOBJECTS.GOAL) {
            System.out.println("You Win!");
            score = 100;
            gameOver = true;
            playerWon = true;
        }
    }


    private void checkTrap(int avatarY, int i) {
        if (getStatusOf(i, avatarY) == WORLDOBJECTS.TRAP) {
            System.out.println("You Lose!");
            score = 10;
            gameOver = true;
            playerWon = false;
        }
    }

    public Vec2d getAvatarPosition() {
        return avatarPosition;
    }


    public WORLDOBJECTS getStatusOf(int i, int j) {
        if (i < 0 || j < 0 || i >= size || j >= size) {
            return null;
        }
        return currentWorld[i][j].getContent();
    }

    public int getScore() {
        return this.score;
    }

    public boolean hasPlayerWon() {
        return playerWon;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void reset() {

        this.currentWorld = new GridSquare[size][size];
        this.score = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.currentWorld[i][j] = new GridSquare(i, j, originalWorld[i][j].getContent());
            }
        }

        this.gameOver = false;
        this.playerWon = false;
    }
}
