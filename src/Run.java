import world.ACTIONS;
import world.Grid;

/**
 * Add a description of the class here.
 *
 * @author Damien Anderson (Damorin)
 *         11/07/2016
 */
public class Run {

    public static void main(String[] args) {
        Grid grid = new Grid(10);
        grid.displayWorld();
        grid.move(ACTIONS.RIGHT);
        grid.move(ACTIONS.RIGHT);
        grid.move(ACTIONS.RIGHT);
        grid.move(ACTIONS.RIGHT);
        grid.move(ACTIONS.LEFT);
        grid.move(ACTIONS.UP);
        grid.move(ACTIONS.RIGHT);
        grid.move(ACTIONS.DOWN);
        grid.move(ACTIONS.DOWN);
        System.out.println();
        grid.displayWorld();
    }

}
