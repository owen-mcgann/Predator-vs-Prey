import java.awt.*;
import javax.swing.*;

public class Player extends Sprite {
    private int row;
    private int col;
    private int[] position = new int[2];
    private Color color; // Customize color if desired
    private char[][] gridData; // Add gridData as an instance variable
    private ImageIcon icon;


    /**
     * constuctor for Player
     * @param initialRow An integer that represents the row of the Player
     * @param initialCol An integer that represents the column of the Player
     * @param gridData A 2D Array of char that represents the what's on the board
     */

    public Player(int initialRow, int initialCol, char[][] gridData) {
        super(initialRow,initialCol,gridData);
        color = Color.GREEN;
        ImageIcon imageicon = new ImageIcon("hamster.png");
        Image image = imageicon.getImage();
        Image newimg = image.getScaledInstance(50,50, Image.SCALE_DEFAULT);
        icon = new ImageIcon(newimg);
    }


 //   public boolean isValidMove(char[][] gridData) {
      //  return row >= 0 && row < gridData.length && col >= 0 && col < gridData[0].length;
    //}

    //public void updatePosition(JLabel[][] gridCells) {
        // Reset the color of all cells to the original color
       // for (int i = 0; i < gridCells.length; i++) {
          //  for (int j = 0; j < gridCells[i].length; j++) {
              //  gridCells[i][j].setBackground(Color.WHITE);
          //  }
       // }
    
        // Set the current cell's color
       // if (isValidMove(gridData)) {
         //   gridCells[row][col].setBackground(color); // Update player's current cell
            //position[0] = row;
          //  position[1] = col;
       // }
   // }
    public Color getColor(){return color;}

    public void setColor(Color c){
        color= c;
    }

    public ImageIcon getImage(){
        return icon;
    }

    /**
     * sets the Image of the Hamster and scales the image of the Hamster
     * @param filepath A String that represents the file name of the hamster
     */
    public void setImage(String filepath){
        ImageIcon imageicon = new ImageIcon(filepath);
        Image image = imageicon.getImage();
        Image newimg = image.getScaledInstance(50,50, Image.SCALE_DEFAULT);
        icon = new ImageIcon(newimg);
    }
}
