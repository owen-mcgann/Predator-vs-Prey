import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Predator extends Sprite {

    private Color color; // Customize color if desired
    private ArrayList<Obstacle> obList; // List of obstacles
    private ImageIcon icon;

    /**
     * constuctor for Predator
     * @param initialRow An integer that represents the row of the Predator
     * @param initialCol An integer that represents the column of the Predator
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public Predator(int initialRow, int initialCol, char[][] gridData, ArrayList<Obstacle> obList) {
        super(initialRow, initialCol, gridData);
        color = Color.RED;
        this.obList = obList;
        ImageIcon imageicon = new ImageIcon("fox.png");
        Image image = imageicon.getImage();
        Image newimg = image.getScaledInstance(50,50, Image.SCALE_DEFAULT);
        icon = new ImageIcon(newimg);
    }

    //public void updatePosition(JLabel[][] gridCells) {
        // Reset the color of all cells to the original color
       // for (int i = 0; i < gridCells.length; i++) {
          //  for (int j = 0; j < gridCells[i].length; j++) {
             //   gridCells[i][j].setBackground(Color.WHITE);
           // }
      //  }

        // Set the current cell's color
       // gridCells[getRow()][getCol()].setBackground(color);
    // }

    /**
     * returns a boolean that indicates whether slot on board is empty
     * @param row An integer that represents the row of the Predator
     * @param col An integer that represents the column of the Predator
     *  @param gridData A 2D Array of char that represents the what's on the board
     * @return A boolean that represenets whether potential move is valid
     */
    public boolean isValidMove(int row, int col, char[][] gridData) {
        if (row < 0 || row >= gridData.length || col < 0 || col >= gridData[0].length) {
            return false; // Move is out of bounds
        }

        // Check if the move is obstructed by an obstacle
        for (Obstacle obs : obList) {
            if (row == obs.getRow() && col == obs.getCol()) {
                return false; // Move is obstructed by an obstacle
            }
        }

        return true; // Move is valid
    }

    // Method to make the predator move towards the player\

    /**public void moveToPlayer(Player player, char[][] gridData) {
     int playerRow = player.getRow();
     int playerCol = player.getCol();
     int predRow = getRow();
     int predCol = getCol();

     // Calculate the direction to move towards the player
     int rowDiff = playerRow - predRow;
     int colDiff = playerCol - predCol;

     if (Math.abs(rowDiff) > Math.abs(colDiff)) {
     // Move vertically towards the player
     if (rowDiff > 0 && isValidMove(predRow + 1, predCol, gridData)) {
     moveDown();
     } else if (rowDiff < 0 && isValidMove(predRow - 1, predCol, gridData)) {
     moveUp();
     }
     } else {
     // Move horizontally towards the player
     if (colDiff > 0 && isValidMove(predRow, predCol + 1, gridData)) {
     moveRight();
     } else if (colDiff < 0 && isValidMove(predRow, predCol - 1, gridData)) {
     moveLeft();
     }
     }
     }
     *
     *
     */

    /**
     * moves the Predator
     * @param newRow An int that represents the new row of the predator
     * @param newCol An int that represents the new column of the predator
     */
    public void move(int newRow, int newCol) {
        // Update the Predator's position
        row = newRow;
        col = newCol;
    }

    public Color getColor() {
        return color;
    }
    public ImageIcon getImage(){
        return icon;
    }
}
