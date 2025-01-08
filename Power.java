import javax.swing.*;
import java.awt.*;

public class Power {
    private int row;
    private int col;
    private int[] position = new int[]{row,col};
    private Color color; // Customize color if desired
    private char[][] gridData;
    private int duration;

    /**
     * constuctor for Power
     * @param initialRow An integer that represents the row of the Power
     * @param initialCol An integer that represents the column of the Power
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public Power(int initialRow, int initialCol, char[][] gridData) {
        row = initialRow;
        col = initialCol;
        this.gridData = gridData;
        position[0] = row;
        position[1] = col;
    }

    /**
     * basic constructor
     */
    public Power(){

    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
   // public int[] getPos() {return position;}
   // public void setPos(int row, int col){
       // position[0] = row;
       // position[1] = col;
  //  }
    public Color getColor(){
        return color;
    }

    public ImageIcon getImage(){
        return null;
    }

}
