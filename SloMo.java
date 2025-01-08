import javax.swing.*;
import java.awt.*;

public class SloMo extends Power{

    private int row;
    private int col;
    private int[] position = new int[]{row,col};
    private static Color color; // Customize color if desired
    private char[][] gridData;
    private static int duration;
    private ImageIcon icon;

    /**
     * constuctor for SlowMO
     * @param initialRow An integer that represents the row of the SlowMo
     * @param initialCol An integer that represents the column of the SlowMo
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public SloMo(int initialRow, int initialCol, char[][] gridData) {
        super(initialRow,initialCol,gridData);
        color = Color.blue;
        duration = 5000;
        ImageIcon imageicon = new ImageIcon("sloMo.png");
        Image image = imageicon.getImage();
        Image newimg = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
    }

    /**
     * basic constructor
     */
    public SloMo(){
        color = Color.blue;
        duration = 5000;
    }
    public void setColor(Color c){
        color = c;
    }
    public Color getColor(){
        return color;
    }
    public static int getDuration(){return duration;}

    public String toString()
    {
        return "SLOMO!";
    }

    public ImageIcon getImage(){
        return icon;
    }
}
