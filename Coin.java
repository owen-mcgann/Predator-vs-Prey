import javax.swing.*;
import java.awt.*;

public class Coin extends Power{
    int points = 10;
    Color color = Color.black;
    private ImageIcon icon;

    /**
     * constuctor for coin
     * @param initialRow An integer that represents the row of the coin
     * @param initialCol An integer that represents the column of the coin
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public Coin(int initialRow, int initialCol, char[][] gridData) {
        super(initialRow,initialCol,gridData);
        ImageIcon imageicon = new ImageIcon("invince.png");
        Image image = imageicon.getImage();
        Image newimg = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);
    }

    @Override
    public Color getColor() {
        return color;
    }

    public int getPoints(){
        return points;
    }
    public ImageIcon getImage(){
        return icon;
    }
}
