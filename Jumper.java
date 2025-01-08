import javax.swing.*;
import java.awt.*;

public class Jumper extends Power{

    private int row;
    private int col;
    private int[] position = new int[]{row,col};
    private static Color color; // Customize color if desired
    private char[][] gridData;
    private static int duration;
    private ImageIcon icon;

    /**
     * constuctor for Jumper
     * @param initialRow An integer that represents the row of the Jumper
     * @param initialCol An integer that represents the column of the Jumper
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public Jumper(int initialRow, int initialCol, char[][] gridData) {
        super(initialRow,initialCol,gridData);
        duration = 7000;
        color = Color.ORANGE;
        ImageIcon imageicon = new ImageIcon("jumperShoes.png");
        Image image = imageicon.getImage();
        Image newimg = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

    }

    /**
     * basic constructor
     */
    public Jumper(){
        color = Color.ORANGE;
        duration = 7000;
    }
    public void setColor(Color c) {
        color = c;
    }
    public Color getColor(){
        return color;
    }
    public static int getDuration(){return duration;}

    public String toString()
    {
        return "JUMPER!";
    }

    public ImageIcon getImage(){
        return icon;
    }
}
