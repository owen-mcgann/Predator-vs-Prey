import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Queue;
import java.io.FileWriter;
import java.io.IOException;

public class Grid extends JFrame implements KeyListener {

    private final int cellSize = 50; // Adjust as needed
    private final int margin = 5;
    private char[][] gridData;
    private PredRunner runner = new PredRunner();

    private PredRunner2 runner2 = new PredRunner2();
    private JPanel q = new JPanel();
    private JLabel timer = new JLabel();

    // Declare gridCells and player as instance variables
    private JLabel[][] gridCells;
    private Player player;
    private Predator pred;
    private Predator pred2;
    private ArrayList<Obstacle> obList = new ArrayList<>();
    public boolean over;
    private Queue<Power> powQueue = new LinkedList<>();
    private ArrayList<Power> powList = new ArrayList<>();
    private JLabel score;
    private JLabel nextPow;
    private ArrayList<Coin> coinList = new ArrayList<>();
    private int points;
    private ImageIcon coinIcon;
    private boolean menuDone;
    private String name;

    //TODO visualize powerups

    /**
     * basic constructor
     */
    public Grid() {

        super("Predator vs. Prey Game");
    }

    /** starts the game
     * sets up Player and Predator object variables
     * sets everything displayed in the grid such as timer, score
     * sets JFrame size
     * sets coin icon
     * sets GridLayout
     */
    public void startGame() {
    // setting up info bar at top
    setLayout(new BorderLayout());
    JPanel top = new JPanel();
    top.setLayout(new GridLayout(1,3));
    add(top,BorderLayout.NORTH);
    timer = new JLabel("", SwingConstants.CENTER);
    timer.setText("0:00");
    top.add(timer);
    q = new JPanel(); // panel to show next powerup in queue
    nextPow = new JLabel("No Powers!");
    q.add(nextPow);
    q.setBackground(Color.white);
    points=0;
    score = new JLabel("000000000",SwingConstants.CENTER);
    top.add(q);
    top.add(score);
    ////player
    ImageIcon imageicon = new ImageIcon("coin.png");
    Image image = imageicon.getImage();
    Image newimg = image.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
    coinIcon = new ImageIcon(newimg);
    ////
    setPreferredSize(new Dimension(1440,1000));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    /////////////
    addComponents();
    setVisible(true);
    pack(); // Calculate appropriate frame size
    setMinimumSize(getMinimumSize()); // Ensure minimum size
    }

    /** Displays all interactions and components to grid
     * Handels placing predator and player on board
     * Re-sizes the window and cells dynamicaly
     */
    private void addComponents() {
        over = false;
        // Initialize grid data (ensure it's not null before passing to Player)
        gridData = new char[20][20]; // Adjust dimensions as needed

        // Create a panel to hold the grid
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(gridData.length, gridData[0].length, margin, margin)); // Dynamic grid layout
    
        // Initialize gridCells array
        gridCells = new JLabel[gridData.length][gridData[0].length];

        // Add labels (or custom components) to represent grid cells
        for (int row = 0; row < gridData.length; row++) {
            for (int col = 0; col < gridData[row].length; col++) {
                //JLabel cellLabel = new JLabel("(" + row + ", " + col + ")"); // Display coordinates in each cell
                JLabel cellLabel = new JLabel();
                cellLabel.setBackground(Color.WHITE); // Set initial background color
                cellLabel.setOpaque(true); // Ensure background color is applied
                gridPanel.add(cellLabel);
    
                // Store labels in the gridCells array
                gridCells[row][col] = cellLabel;
            }
        }

        // Start player at the middle of the board, passing gridData
        player = new Player(gridData.length / 2, gridData[0].length / 2, gridData);
        pred = new Predator(1,1,gridData, obList);
        pred2 = new Predator(15,15,gridData, obList);

        // generate obstacles
        generateObstacles(20);

        // Add key listener for player movement
        addKeyListener(this);
    
        // Add the grid panel to the frame
        add(gridPanel);
        generateBoard();
        Update update = new Update();
        update.start();
        runner.start();
        runner2.start();
    
        // Handle window resizing to adjust cell sizes dynamically
        addComponentListener(new ComponentAdapter() {
            @Override
            /**Runs update cell sizes
             * revalidates layout
             */
            public void componentResized(ComponentEvent e) {
                updateCellSizes();
                gridPanel.revalidate(); // Revalidate layout after size change
            }
        });
    }

    // Method to update cell sizes dynamically when window is resized
    /**Calculates new cell sizes based on space
     * Sets cell size and updates bounds
     */
    private void updateCellSizes() {
        // Calculate new cell size based on available space
        int newCellSize = Math.min(getWidth() / gridData[0].length - margin, getHeight() / gridData.length - margin);

        // Set new cell size and update label bounds
        for (Component component : getComponents()) {
            if (component instanceof JLabel) {
                component.setPreferredSize(new Dimension(newCellSize, newCellSize));
                component.setBounds(component.getX(), component.getY(), newCellSize, newCellSize);
            }
        }
    }

    /**Helper method for keyEvent
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Ignore for now
    }

    /**
     * all of the possible key events
     * @param e the event to be processed
     */
    @Override
public void keyPressed(KeyEvent e) {
    int keyCode = e.getKeyCode();

    switch (keyCode) {
        //moves the player up
        case KeyEvent.VK_W:
            if (player.getJumper()) {
                if (isValidMove(player.getRow() - 2, player.getCol(), player)) {
                    player.moveUp(true);
                    break;
                }
            }
            if (isValidMove(player.getRow() - 1, player.getCol(), player)) {
                player.moveUp();
            }
            break;
            //moves the player down
        case KeyEvent.VK_S:
            if (player.getJumper()) {
                if (isValidMove(player.getRow() + 2, player.getCol(), player)) {
                    player.moveDown(true);
                    break;
                }
            }
            if (isValidMove(player.getRow() + 1, player.getCol(), player)) {
                player.moveDown();
            }
            break;
        //moves the player left
        case KeyEvent.VK_A:
            if (player.getJumper()) {
                if (isValidMove(player.getRow(), player.getCol() - 2, player)) {
                    player.moveLeft(true);
                    break;
                }
            }
            if (isValidMove(player.getRow(), player.getCol() - 1, player)) {
                player.moveLeft();
            }
            break;
            //moves the player right
        case KeyEvent.VK_D:
            if (player.getJumper()) {
                if (isValidMove(player.getRow(), player.getCol() + 2, player)) {
                    player.moveRight(true);
                    break;
                }
            }
            if (isValidMove(player.getRow(), player.getCol() + 1, player)) {
                player.moveRight();
            }
            break;
            //dequeues first powerup in the queue
        case KeyEvent.VK_F:
            //Activate the power at the head of the queue.
            Power currentPower = powQueue.poll();
            // Apply power-up effect only to the player
            if (currentPower instanceof Invince) {
                InvinceRunner runner = new InvinceRunner();
                runner.start();
            } else if (currentPower instanceof Jumper) {
                JumperRunner runner = new JumperRunner();
                runner.start();
            } else if (currentPower instanceof SloMo) {
                SloMoRunner runner = new SloMoRunner();
                runner.start();
            }
            if(powQueue.isEmpty()){
                q.setBackground(Color.WHITE);
                nextPow.setText("No Powers!");
            }else{
                q.setBackground(powQueue.peek().getColor());
                nextPow.setText("Next Power: " + powQueue.peek().toString());

            }
            
            break;
            //generates powerups
        case (KeyEvent.VK_P):
            generatePower();
            break;
            //resets the game
        case (KeyEvent.VK_R): // Check for 'R' key press
            Grid g = new Grid();
            g.startGame();
            Grid grid = this;
            grid.over = true;
            grid.dispose();
            saveHighScores();
    }
}

    /**Generates the board
     * Handles updating the cells colors based on specific interactions
     */
    public void generateBoard(){
    for (int i = 0; i < gridCells.length; i++) {
        for (int j = 0; j < gridCells[i].length; j++) {
            gridCells[i][j].setIcon(null);
            gridCells[i][j].setBackground(Color.WHITE);
        }
    }
    player.setPos(player.getRow(),player.getCol());
    pred.setPos(pred.getRow(),pred.getCol());
    //Game over condition
    if(Arrays.equals(player.getPos(),pred.getPos())&&!player.getInvince()){
        gameOver();
    }
    // Set each cell's color
    for(Obstacle obs: obList){
        gridCells[obs.getRow()][obs.getCol()].setBackground(obs.getColor());
    }
    gridCells[pred.getRow()][pred.getCol()].setBackground(pred.getColor());
    gridCells[player.getRow()][player.getCol()].setBackground(player.getColor());
}

    /**
     * Updates position of player predator and obList
     * @param gridCells 2D Array of the cells in the grid.
     * sets the power/oblist cells colors
     */

    public void updatePosition(JLabel [][] gridCells) {
    // Reset the color of all cells to the original color
    for (int i = 0; i < gridCells.length; i++) {
        for (int j = 0; j < gridCells[i].length; j++) {
            gridCells [i][j].setIcon(null);
            int obsNum =0;
            for(Obstacle obs: obList) {
                if (!(i == obs.getRow()&&j==obs.getCol())) {
                    obsNum++;
                }
            }
            if(obsNum == obList.size()){
                gridCells[i][j].setBackground(Color.WHITE);
            }
        }
    }
    player.setPos(player.getRow(),player.getCol());
    pred.setPos(pred.getRow(),pred.getCol());
    pred2.setPos(pred2.getRow(),pred2.getCol());
    if(Arrays.equals(player.getPos(),pred.getPos())&&!player.getInvince()){
        gameOver();
    }
    if(Arrays.equals(player.getPos(),pred2.getPos())&&!player.getInvince()){
        gameOver();
    }
    // Set each cell's color
    for(Obstacle obs:obList) {
        gridCells[obs.getRow()][obs.getCol()].setBackground(obs.getColor());
    }
    for(Coin coin: coinList){
        gridCells[coin.getRow()][coin.getCol()].setIcon(coinIcon);
    }
    for(Power pow:powList){
        gridCells[pow.getRow()][pow.getCol()].setIcon(pow.getImage());
    }
    gridCells[pred.getRow()][pred.getCol()].setIcon(pred.getImage());
    gridCells[pred2.getRow()][pred2.getCol()].setIcon(pred2.getImage());
    gridCells[player.getRow()][player.getCol()].setIcon(player.getImage());// Update player's current cell with image

}

    /**Helper method for keyEvent
     *
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        // Ignore for now
    }

    /**
     * uses isValid() funciton to generate all possible moves for
     * predator and player objects
     */
    public void getMove() {
        int playerRow = player.getRow();
        int playerCol = player.getCol();
        int playerRow2 = player.getRow();
        int playerCol2 = player.getCol();
        int predRow = pred.getRow();
        int predCol = pred.getCol();
        int predRow2 = pred2.getRow();
        int predCol2 = pred2.getCol();


        ArrayList<int[]> possibleMoves = new ArrayList<>();
        ArrayList<int[]> possibleMoves2 = new ArrayList<>();

        // Calculate all possible moves, including diagonal movements
        if ((pred.isValidMove(predRow - 1, predCol, gridData)) && noMerge()) { // Up
            possibleMoves.add(new int[]{predRow - 1, predCol});
        }
        if ((pred.isValidMove(predRow + 1, predCol, gridData))&& noMerge()){ // Down
            possibleMoves.add(new int[]{predRow + 1, predCol});
        }
        if ((pred.isValidMove(predRow, predCol - 1, gridData))&& noMerge()) { // Left
            possibleMoves.add(new int[]{predRow, predCol - 1});
        }
        if ((pred.isValidMove(predRow, predCol + 1, gridData))&& noMerge()) { // Right
            possibleMoves.add(new int[]{predRow, predCol + 1});
        }
        if ((pred.isValidMove(predRow - 1, predCol - 1, gridData))&& noMerge()) { // Up-Left
            possibleMoves.add(new int[]{predRow - 1, predCol - 1});
        }
        if ((pred.isValidMove(predRow - 1, predCol + 1, gridData)) && noMerge()) { // Up-Right
            possibleMoves.add(new int[]{predRow - 1, predCol + 1});
        }
        if ((pred.isValidMove(predRow + 1, predCol - 1, gridData))&& noMerge()) { // Down-Left
            possibleMoves.add(new int[]{predRow + 1, predCol - 1});
        }
        if ((pred.isValidMove(predRow + 1, predCol + 1, gridData))&& noMerge()) { // Down-Right
            possibleMoves.add(new int[]{predRow + 1, predCol + 1});
        }

        if ((pred2.isValidMove(predRow2 - 1, predCol2, gridData))&& noMerge()) { // Up
            possibleMoves2.add(new int[]{predRow2 - 1, predCol2});
        }
        if ((pred2.isValidMove(predRow2 + 1, predCol2, gridData))&& noMerge()) { // Down
            possibleMoves2.add(new int[]{predRow2 + 1, predCol2});
        }
        if ((pred2.isValidMove(predRow2, predCol2 - 1, gridData))&& noMerge()) { // Left
            possibleMoves2.add(new int[]{predRow2, predCol2 - 1});
        }
        if ((pred2.isValidMove(predRow2, predCol2 + 1, gridData))&& noMerge()) { // Right
            possibleMoves2.add(new int[]{predRow2, predCol2 + 1});
        }
        if ((pred2.isValidMove(predRow2 - 1, predCol2 - 1, gridData))&& noMerge()) { // Up-Left
            possibleMoves2.add(new int[]{predRow2 - 1, predCol2 - 1});
        }
        if ((pred2.isValidMove(predRow2 - 1, predCol2 + 1, gridData))&& noMerge()) { // Up-Right
            possibleMoves2.add(new int[]{predRow2 - 1, predCol2 + 1});
        }
        if ((pred2.isValidMove(predRow2 + 1, predCol2 - 1, gridData))&& noMerge()) { // Down-Left
            possibleMoves2.add(new int[]{predRow2 + 1, predCol2 - 1});
        }
        if ((pred2.isValidMove(predRow2 + 1, predCol2 + 1, gridData))&& noMerge()) { // Down-Right
            possibleMoves2.add(new int[]{predRow2 + 1, predCol2 + 1});
        }

        if(!(noMerge())) {
            int a = predRow+1;
            int b = predCol+1;
            int c= predRow2-1;
            int d = predCol2-1;
            if(a > 19) {
                a = 18;
            } else if (a < 0) {
                a = 4;
            }
            if(b > 19) {
                b = 18;
            } else if (b < 0) {
                b = 4;
            }
            if(c > 19) {
                c = 14;
            } else if (c < 0) {
                c = 7;
            }
            if(d > 19) {
                d = 14;
            } else if (d < 0) {
                d= 7;
            }
            possibleMoves.add(new int[]{a, b});
            possibleMoves2.add(new int[]{c, d});

        }

        // Filter out moves obstructed by obstacles
        ArrayList<int[]> validMoves = new ArrayList<>();
        ArrayList<int[]> validMoves2 = new ArrayList<>();
        for (int[] move : possibleMoves) {
            if (!isObstructed(move[0], move[1])) {
                validMoves.add(move);
            }
        }
        for (int[] move : possibleMoves2) {
            if (!isObstructed(move[0], move[1])) {
                validMoves2.add(move);
            }
        }

        // Prioritize moves based on proximity to the player
        int closestDistance = Integer.MAX_VALUE;
        int[] closestMove = null;
        int closestDistance2 = Integer.MAX_VALUE;
        int[] closestMove2 = null;
        for (int[] move : validMoves) {
            int moveRow = move[0];
            int moveCol = move[1];
            //(Manhattan distance - total sum of absolute diff in x and y coordinates - fastest path)
            int distance = Math.abs(playerRow - moveRow) + Math.abs(playerCol- moveCol);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestMove = move;
            }
        }
        for (int[] move2 : validMoves2) {
            int moveRow2 = move2[0];
            int moveCol2 = move2[1];
            int distance2 = Math.abs(playerRow2 - moveRow2) + Math.abs(playerCol2 - moveCol2);
            if (distance2 < closestDistance2) {
                closestDistance2 = distance2;
                closestMove2 = move2;
            }
        }

        // Execute the chosen move
        if (closestMove != null) {
            int targetRow = closestMove[0];
            int targetCol = closestMove[1];

            // Move the predator towards the target position
            pred.move(targetRow, targetCol);
        }
        if (closestMove2 != null) {
            int targetRow2 = closestMove2[0];
            int targetCol2 = closestMove2[1];

            // Move the predator towards the target position
            pred2.move(targetRow2, targetCol2);
        }
    }

    /**
     * ensures that predator objects do not merge into one object
     * @return A boolean that represents whether predator objects are about to merge
     */

    public boolean noMerge() {
        if((pred.getRow() == pred2.getRow()+1)&&(pred.getCol() == pred2.getCol())) {
            return false;
        } else if ((pred.getRow()+1 == pred2.getRow())&&(pred.getCol() == pred2.getCol())) {
            return false;
        }  else if((pred.getRow() == pred2.getRow())&&(pred.getCol() == pred2.getCol()+1)) {
            return false;
        }
        else if((pred.getRow() == pred2.getRow())&&(pred.getCol()+1 == pred2.getCol())) {
            return false;
        } else if((pred.getRow()+1 == pred2.getRow())&&(pred.getCol()+1 == pred2.getCol())) {
            return false;
        } else if((pred.getRow()+1 == pred2.getRow())&&(pred.getCol() == pred2.getCol()+1)) {
            return false;
        } else if((pred.getRow() == pred2.getRow()+1)&&(pred.getCol()+1 == pred2.getCol())) {
            return false;
        } else if((pred.getRow() == pred2.getRow()+1)&&(pred.getCol() == pred2.getCol()+1)) {
            return false;
        } else if((pred.getRow()+1 == pred2.getRow()+1)&&(pred.getCol()+1 == pred2.getCol())) {
            return false;
        } else if((pred.getRow() == pred2.getRow()+1)&&(pred.getCol()+1 == pred2.getCol()+1)) {
            return false;
        } else if((pred.getRow()+1 == pred2.getRow()+1)&&(pred.getCol()+1 == pred2.getCol()+1)) {
                return false;
            } else if ((pred.getRow() == pred2.getRow()-1)&&(pred.getCol() == pred2.getCol()+1)) {
                return false;
            }  else if((pred.getRow() == pred2.getRow()-1)&&(pred.getCol() == pred2.getCol()-1)) {
                return false;
            } else if((pred.getRow() == pred2.getRow()+1)&&(pred.getCol() == pred2.getCol()-1)) {
                return false;
            }
        return true;
    }


    /**
     * Helper method isValid
     * ensures that player and predator objects do not collide into obstacle
     * @param row An int that represents the row
     * @param col An colmun that represents the column
     * @return a boolean that represents whether tile is obstructed
     */
    // Check if a move is obstructed by an obstacle
    private boolean isObstructed(int row, int col) {
        for (Obstacle obs : obList) {
            if (row == obs.getRow() && col == obs.getCol()) {
                return true; // Move is obstructed by an obstacle
            }
        }
        return false; // Move is not obstructed
    }


    /**
     * checks if move is valid
     * @param row An int that represents the row
     * @param col An colmun that represents the column
     * @return a boolean that represents whether tile is possible move
     */
    public boolean isValidMove(int row, int col, Sprite ani) {
        // Check for obstacles
        for (Obstacle obs : obList) {
            if (row == obs.getRow() && col == obs.getCol()) {
                return false; // Obstacle found, movement is invalid
            }
        }
        // If the move is onto a cell containing a power-up, collect it
        if(ani instanceof Player)
        {
            Power[] powArray = new Power[powList.size()];
            int idx = 0;
            for (Power pow : powList)
            {
                powArray[idx] = pow;
                idx++;
            }
            for (Power pow : powArray)
            {
                if (row == pow.getRow() && col == pow.getCol())
                {
                    powQueue.add(pow);
                    powList.remove(pow);
                    q.setBackground(powQueue.peek().getColor());
                    nextPow.setText("Next Power: " + powQueue.peek().toString());
                }
            }
            Coin[] coinArray = new Coin[coinList.size()];
            idx = 0;
            for (Coin coin: coinList)
            {
                coinArray[idx] = coin;
                idx++;
            }
            for (Coin coin: coinArray)
            {
                if (row == coin.getRow() && col == coin.getCol())
                {
                    coinList.remove(coin);
                    points += coin.getPoints();
                }
            }
        }

        // Check if the move is outside the grid
        return row >= 0 && row < gridData.length && col >= 0 && col < gridData[0].length;
    }

    /**
     * generates random obstacles based on num
     * @param num An int that represents the number of obstacles to be genreated
     */
    public void generateObstacles(int num){
        for(int i=0;i<num;i++){
            boolean done = false;
            while(!done) {
                int noObs = 0;
                Random random = new Random();
                int randRow = random.nextInt(gridData.length);
                int randCol = random.nextInt(gridData.length);
                for(Obstacle obs:obList){
                    if(!(randRow == obs.getRow() && randCol == obs.getCol())) {
                        noObs++; //if not all the obstacles add to this number, then we know it is an invalid placement
                    }
                }
                // if there is nothing in the way, place an obstacle
                if(!(randRow == player.getRow()&& randCol == player.getCol())&&
                    !(randRow == pred.getRow()&& randCol == player.getCol())&& noObs == obList.size()&&!(randRow == pred2.getRow())){
                        Obstacle obstacle = new Obstacle(randRow, randCol, gridData);
                        obList.add(obstacle);
                        done = true;
                }
            }
        }
    }

    /**
     * generates powers overtime
     * or when user presses P
     */
    public void generatePower(){
        Random random = new Random();
        int rand = random.nextInt(3);
        switch (rand){
            case(0):
                int row=0;
                int col=0;
                boolean done = false;
                while(!done) {
                    row = random.nextInt(gridData.length);
                    col = random.nextInt(gridData.length);
                    int obsNum = 0;
                    for(Obstacle obs: obList) {
                        if (!(row == obs.getRow() && col == obs.getCol())){
                            obsNum ++;
                        }
                    }
                    if(obsNum== obList.size()){break;}
                }
                Invince invin = new Invince(row, col, gridData);
                powList.add(invin);
                break;
            case(1):
                row=0;
                col=0;
                done = false;
                while(!done) {
                    row = random.nextInt(gridData.length);
                    col = random.nextInt(gridData.length);
                    int obsNum = 0;
                    for(Obstacle obs: obList) {
                        if (!(row == obs.getRow() && col == obs.getCol())){
                            obsNum ++;
                        }
                    }
                    if(obsNum== obList.size()){break;}
                }
                Jumper jump = new Jumper(row, col, gridData);
                powList.add(jump);
                break;
            case(2):
                row=0;
                col=0;
                done = false;
                while(!done) {
                    row = random.nextInt(gridData.length);
                    col = random.nextInt(gridData.length);
                    int obsNum = 0;
                    for(Obstacle obs: obList) {
                        if (!(row == obs.getRow() && col == obs.getCol())){
                            obsNum ++;
                        }
                    }
                    if(obsNum== obList.size()){break;}
                }
                SloMo slo = new SloMo(row, col, gridData);
                powList.add(slo);
                break;
        }
    }

    /**
     * generates coins over time
     */
    public void generateCoin(){
        Random random = new Random();
        int row=0;
        int col=0;
        boolean done = false;
        while(!done) {
            row = random.nextInt(gridData.length);
            col = random.nextInt(gridData.length);
            int obsNum=0;
            for(Obstacle obs: obList) {
                if (!(row == obs.getRow() && col == obs.getCol())){
                    obsNum ++;
                }
            }
            if(obsNum== obList.size()){break;}
        }
        Coin coin = new Coin(row, col, gridData);
        coinList.add(coin);
    }

    /**
     * saves high score and time of the user and saves in file
     */
    // Method to save player's high scores to a text file
    private void saveHighScores() {
        try {
            FileWriter writer = new FileWriter("PlayerHighScores.txt", true); // Append mode
            writer.write("Timer: " + timer.getText() + ", Score: " + score.getText() + "\n");
            writer.close();
            System.out.println("High scores saved successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while saving high scores: " + e.getMessage());
        }
    }

    /**
     * displays message when player dies
     */
    public void gameOver(){
        over = true;
        JOptionPane dead = new JOptionPane();
        dead.showMessageDialog(null,"Game Over!\nPress 'Okay' Then Press 'R' To Restart");
        saveHighScores();
    }
    /*
    * method PredRunner handles the movements of the preadator
    * predator speed increases with each move the predator makes
    *
     */
    private class PredRunner extends Thread {
        private int idx = 0;
    
        public void run() {
            while (!over) {
                try {
                    double timeDouble = idx * 5;
                    long time = 1000 - Math.round(timeDouble);
                    if (pred.getSloMo()) {
                        time = 1500;
                    }
                    Thread.sleep(time);
                    getMove();
                    idx++;
                    if (idx % 5 == 0) {
                        generateCoin();
                    }
                    if (idx % 10 == 0) {
                        generatePower();
                    }
                } catch (InterruptedException e) {
                    System.exit(0);
                }
            }
        }
    }
/*
*This thread exeutes the same function as PredRunner, but for the second predator
 */
    private class PredRunner2 extends Thread {
        private int idx = 0;

        public void run() {
            while (!over) {
                try {
                    double timeDouble = idx * 5;
                    long time = 1000 - Math.round(timeDouble);
                    if (pred2.getSloMo()) {
                        time = 1500;
                    }
                    Thread.sleep(time);
                    getMove();
                    idx++;
                } catch (InterruptedException e) {
                    System.exit(0);
                }
            }
        }
    }

    /**
     * InvinceRunner handles running the invincible player status
     * It changes the color of the player's icon and makes them unable to be killed by predators
     */
    private class InvinceRunner extends Thread {
        public void run() {
            try {
                player.setInvince(true);
                long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < Invince.getDuration()) {
                    player.setImage("hamsterRed.png");
                    Thread.sleep(100);
                    player.setImage("hamsterOrange.png");
                    Thread.sleep(100);
                    player.setImage("hamsterYellow.png");
                    Thread.sleep(100);
                    player.setImage("hamsterGreen.png");
                    Thread.sleep(100);
                    player.setImage("hamsterCyan.png");
                    Thread.sleep(100);
                }
                player.setInvince(false);
                player.setImage("hamster.png");
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    /**
     * JumperRunner handles running the jumper player status
     * It changes the color of the player's icon and makes the player jump 2 places with each move
     */
    private class JumperRunner extends Thread {
        public void run() {
            Jumper jumper = new Jumper();
            try {
                player.setJumper(true);
                player.setImage("hamsterOrange.png");
                Thread.sleep(Jumper.getDuration());
                player.setJumper(false);
                player.setImage("hamster.png");
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }
    /**
     * SloMoRunner handles running the slomo predator status
     * It changes the color of the player's icon and slows down predators
     */
    private class SloMoRunner extends Thread {
        public void run() {
            SloMo slomo = new SloMo();
            try {
                pred.setSloMo(true);
                pred2.setSloMo(true);
                player.setImage("hamsterCyan.png");
                Thread.sleep(SloMo.getDuration());
                pred.setSloMo(false);
                pred2.setSloMo(false);
                player.setImage("hamster.png");
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }
    /**
     * Update Thread updates the gameboard with players, predators, powers, and coins every 10 milliseconds
     */
    private class Update extends Thread{
        public void run(){
            try {
                int min = 0;
                int sec = 0;
                int idx = 0;
                while (!over) {
                    updatePosition(gridCells);
                    score.setText("" + points);
                    Thread.sleep(10);
                    if(idx % 100 == 0){
                        sec++;
                        if(sec <10){
                            timer.setText(min + ":0"+sec);
                        }else {
                            timer.setText(min + ":" + sec);
                        }
                    }
                    if(sec == 60){
                        min++;
                        sec =0;
                        timer.setText(min + ":"+sec);
                    }
                    idx++;
                }
            }catch(InterruptedException e){
                System.exit(0);
            }
        }
    }


    public static void main(String[] args) {
        Grid g = new Grid();
        g.startGame();
    }
}
