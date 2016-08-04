package world;

/**
 * Add a description of the class here.
 *
 * @author Damien Anderson (Damorin)
 *         11/07/2016
 */
public class GridSquare {

    private WORLDOBJECTS content;
    private int x;
    private int y;

    public GridSquare(int x, int y) {
        this.setContent(WORLDOBJECTS.EMPTY);
        this.x = x;
        this.y = y;
    }

    public GridSquare(int x, int y, WORLDOBJECTS content) {
        this.setContent(content);
        this.x = x;
        this.y = y;
    }

    public void setContent(WORLDOBJECTS newContent) {
        this.content = newContent;
    }

    public WORLDOBJECTS getContent() {
        return content;
    }
}
