import javax.swing.*;
import java.awt.*;

public class Invince extends Power{

    private int row;
    private int col;
    private int[] position = new int[]{row,col};
    private static Color color; // Customize color if desired
    private char[][] gridData;
    private static int duration;
    private ImageIcon icon;

    /**
     * constuctor for Invince
     * @param initialRow An integer that represents the row of the Invince
     * @param initialCol An integer that represents the column of the Invince
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public Invince(int initialRow, int initialCol, char[][] gridData) {
        super(initialRow,initialCol,gridData);
        duration = 4000;
        color = Color.magenta;
        ImageIcon imageicon = new ImageIcon("invince.png");
        Image image = imageicon.getImage();
        Image newimg = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

    }
    //public Invince(){
       // color = Color.magenta;
        //duration = 4000;
   // }

    public void setColor(Color c){
        color = c;
    }
    public Color getColor(){
        return color;
    }
    public static int getDuration(){return duration;}

    public String toString()
    {
        return "INVINCABLE!";
    }
    public ImageIcon getImage(){
        return icon;
    }
}
