import java.awt.*;

public class Obstacle extends Sprite {
    private Color color; // Customize color if desired

    /**
     * constuctor for Obstacle
     * @param initialRow An integer that represents the row of the Obstacle
     * @param initialCol An integer that represents the column of the Obstacle
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public Obstacle(int initialRow, int initialCol, char[][] gridData) {
        super(initialRow, initialCol, gridData);
        color = Color.GRAY;
    }

    public Color getColor() {
        return color;
    }
}
