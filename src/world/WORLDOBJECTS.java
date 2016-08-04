package world;

/**
 * Add a description of the class here.
 *
 * @author Damien Anderson (Damorin)
 *         13/07/2016
 */
public enum WORLDOBJECTS {

    EMPTY(0),
    AVATAR(1),
    GOAL(2),
    WALL(3),
    TRAP(4);

    private int id;

    WORLDOBJECTS(int id) {
        this.id = id;
    }

    public int getValue() {
        return id;
    }

}
