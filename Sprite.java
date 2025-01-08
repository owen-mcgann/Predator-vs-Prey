import java.awt.*;

public abstract class Sprite {
    protected int row;
    protected int col;
    protected int[] position = new int[2];
    protected Color color;
    protected final char[][] gridData;
    protected boolean invince = false;
    protected boolean jumper = false;
    protected boolean slomo = false;

    /**
     * constuctor for Sprite
     * @param initialRow An integer that represents the row of the Sprite
     * @param initialCol An integer that represents the column of the Sprite
     * @param gridData A 2D Array of char that represents the what's on the board
     */
    public Sprite(int initialRow, int initialCol, char[][] gridData) {
        row = initialRow;
        col = initialCol;
        this.gridData = gridData;
        position[0] = row;
        position[1] = col;
    }
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public int[] getPos() {return position;}
    public void setPos(int row, int col){
        position[0] = row;
        position[1] = col;
    }

    /**
     * moves up
     */
    public void moveUp(){
        row--;
    }

    /**
     * moves more up if jumper is true
     * @param jumper A boolean that represents whether jumper was selected
     */
    public void moveUp(boolean jumper){
        if(jumper) {
            row--;
        }
        row--;
    }

    /**
     * moves down
     */
    public void moveDown() {
        row++;
    }

    /**
     * moves more down if jumper is true
     * @param jumper A boolean that represents whether jumper was selected
     */
    public void moveDown(boolean jumper){
        if(jumper) {
            row++;
        }
        row++;
    }

    /**
     * moves left
     */
    public void moveLeft() {
        col--;
    }
    /**
     * moves more left if jumper is true
     * @param jumper A boolean that represents whether jumper was selected
     */
    public void moveLeft(boolean jumper){
        if(jumper) {
            col--;
        }
        col--;
    }

    /**
     * moves right
     */
    public void moveRight() {
        col++;
    }

    /**
     * moves more down if jumper is true
     * @param jumper A boolean that represents whether jumper was selected
     */
    public void moveRight(boolean jumper){
        if(jumper) {
            col++;
        }
        col++;
    }
    public Color getColor(){return color;}

    public void setColor(Color c){
        color= c;
    }
    public void setInvince(Boolean bool){
        invince = bool;
    }
    public Boolean getInvince(){return invince;}
    public void setJumper(Boolean bool) {
        jumper = bool;
    }
    public Boolean getJumper(){return jumper;}
    public void setSloMo(Boolean bool){
        slomo = bool;
    }
    public Boolean getSloMo(){return slomo; }

    

}
