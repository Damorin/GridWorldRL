package ui;

import controllers.RLController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import world.ACTIONS;
import world.Grid;
import world.WORLDOBJECTS;

import java.util.ArrayList;

/**
 * Add a description of the class here.
 *
 * @author Damien Anderson (Damorin)
 *         16/07/2016
 */
public class GameView extends Application {

    private Grid world;
    private Image avatar;
    private Image goal;
    private Image empty;
    private Image trap;

    private int worldSize = 5;

    @Override
    public void start(Stage primaryStage) throws Exception {
        world = new Grid(worldSize);
        world.displayWorld();

        RLController player = new RLController(worldSize, world);

        primaryStage.setTitle("GridWorld");

        Group root = new Group();
        Scene theScene = new Scene(root);
        primaryStage.setScene(theScene);

        Canvas canvas = new Canvas(500, 500);
        root.getChildren().add(canvas);

        ArrayList<String> input = new ArrayList<String>();

//        theScene.setOnKeyPressed(
//                e -> {
//                    String code = e.getCode().toString();
//
//                    // only add once... prevent duplicates
//                    if (!input.contains(code))
//                        input.add(code);
//                });
//
//        theScene.setOnKeyReleased(
//                e -> {
//                    String code = e.getCode().toString();
//                    input.remove(code);
//                });

        GraphicsContext gc = canvas.getGraphicsContext2D();
        GridPane grid = new GridPane();

        avatar = new Image("avatar.gif");
        goal = new Image("goal.png");
        empty = new Image("empty.gif");
        //Image wall = new Image("wall");
        trap = new Image("trap.png");

        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                if (!world.isGameOver()) {
                    input.add(player.makeMove().name());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    player.updateValues();
                    player.displayValues();
                    world.reset();
                    input.clear();
                }

                if (input.contains("LEFT")) {
                    if (world.move(ACTIONS.LEFT)) {
                        updateWorld(gc);
                    }
                } else if (input.contains("RIGHT")) {
                    if (world.move(ACTIONS.RIGHT)) {
                        updateWorld(gc);
                    }
                } else if (input.contains("UP")) {
                    if (world.move(ACTIONS.UP)) {
                        updateWorld(gc);
                    }
                } else if (input.contains("DOWN")) {
                    if (world.move(ACTIONS.DOWN)) {
                        updateWorld(gc);
                    }
                }
                input.clear();

            }
        }.start();


        root.getChildren().add(grid);

        primaryStage.show();
    }


    private void updateWorld(GraphicsContext gc) {
        for (int i = 0; i < worldSize; i++) {
            for (int j = 0; j < worldSize; j++) {
                if (world.getStatusOf(i, j) == WORLDOBJECTS.AVATAR) {
                    gc.drawImage(avatar, (100 * j), (100 * i));
                } else if (world.getStatusOf(i, j) == WORLDOBJECTS.GOAL) {
                    gc.drawImage(goal, (100 * j), (100 * i));
                } else if (world.getStatusOf(i, j) == WORLDOBJECTS.WALL) {
                } else if (world.getStatusOf(i, j) == WORLDOBJECTS.TRAP) {
                    gc.drawImage(trap, (100 * j), (100 * i));
                } else {
                    gc.drawImage(empty, (100 * j), (100 * i));
                }
            }
        }
    }
}
