import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GamePanel extends JPanel {

    private int sizeX;
    private int sizeY;

    private Tile[] tiles;
    private int emptyTileIndex = 15;
    private boolean hasWon = false;
    private boolean timerCounting = false;

    private GameTimer gameTimer;
    private Score score;

    Random random = new Random();

    public GamePanel(GameTimer t, Score s) {
        this.sizeX = 400;
        this.sizeY = 400;
        this.gameTimer = t;
        this.score = s;

        initializePanel();

        this.tiles = creteTiles();

        start();
    }

    /**
     * starts the game by shuffling the tiles
     */
    public void start(){
        do {
            randomiseTiles();
        } while (!solvable());

        //finds index of empty tile in the array
        for (int i = 0; i < this.tiles.length; i++) {
            if (this.tiles[i].getTileNumber() == 16) {
                this.emptyTileIndex = i;
                break;
            }
        }
    }

    /**
     * creates the look of the panel
     */
    public void initializePanel() {
        setBounds(25,25,this.sizeX,this.sizeY);
        setBackground(new Color(0x0D1B2A));
        setLayout(null);
    }

    /**
     * creates an array of tiles, and adds them to the panel
     * @return array of tiles
     */
    public Tile[] creteTiles() {
        Tile[] tiles = new Tile[]{
                new Tile(1, 0, 0, new ImageIcon("res/tiles/tile1.png"), this),
                new Tile(2, 100, 0, new ImageIcon("res/tiles/tile2.png"), this),
                new Tile(3, 200, 0, new ImageIcon("res/tiles/tile3.png"), this),
                new Tile(4, 300, 0, new ImageIcon("res/tiles/tile4.png"), this),
                new Tile(5, 0, 100, new ImageIcon("res/tiles/tile5.png"), this),
                new Tile(6, 100, 100, new ImageIcon("res/tiles/tile6.png"), this),
                new Tile(7, 200, 100, new ImageIcon("res/tiles/tile7.png"), this),
                new Tile(8, 300, 100, new ImageIcon("res/tiles/tile8.png"), this),
                new Tile(9, 0, 200, new ImageIcon("res/tiles/tile9.png"), this),
                new Tile(10, 100, 200, new ImageIcon("res/tiles/tile10.png"), this),
                new Tile(11, 200, 200, new ImageIcon("res/tiles/tile11.png"), this),
                new Tile(12, 300, 200, new ImageIcon("res/tiles/tile12.png"), this),
                new Tile(13, 0, 300, new ImageIcon("res/tiles/tile13.png"), this),
                new Tile(14, 100, 300, new ImageIcon("res/tiles/tile14.png"), this),
                new Tile(15, 200, 300, new ImageIcon("res/tiles/tile15.png"), this),
                new Tile(16, 300, 300, null, this),
        };

        for (Tile tile : tiles) {
            this.add(tile);
        }

        return tiles;
    }

    /**
     * randomises the tiles in array and visually
     */
    public void randomiseTiles() {
        for (int i = 0; i < this.tiles.length; i++) {
            //swaps in array
            int randomIndex = random.nextInt(this.tiles.length);
            Tile temp = this.tiles[randomIndex];
            this.tiles[randomIndex] = this.tiles[i];
            this.tiles[i] = temp;
            //swaps visually
            int tempX = this.tiles[i].getX();
            int tempY = this.tiles[i].getY();
            this.tiles[i].setPositions(this.tiles[randomIndex].getX(), this.tiles[randomIndex].getY());
            this.tiles[randomIndex].setPositions(tempX, tempY);
        }
    }

    Tile tempTile; //temporary tile

    /**
     * moves the tile
     * @param tileNumber
     */
    public void move(int tileNumber) {
        if (!this.hasWon) { //stops from moving the tiles after a win

            if (!this.timerCounting){
                this.gameTimer.start();
            }

            //finds index of the selected tile
            int tileIndex = 0;
            for (int i = 0; i < this.tiles.length; i++) {
                if (this.tiles[i].getTileNumber() == tileNumber) {
                    tileIndex = i;
                }
            }

            //checks if the selected tile is next to the empty one
            if (tileIndex - 1 == this.emptyTileIndex || tileIndex + 1 == this.emptyTileIndex || tileIndex - 4 == this.emptyTileIndex || tileIndex + 4 == this.emptyTileIndex) {
                //swaps in array
                this.tempTile = this.tiles[tileIndex];
                this.tiles[tileIndex] = this.tiles[this.emptyTileIndex];
                this.tiles[this.emptyTileIndex] = tempTile;
                //visually swaps
                int tempX = this.tiles[this.emptyTileIndex].getX();
                int tempY = this.tiles[this.emptyTileIndex].getY();
                this.tiles[this.emptyTileIndex].setPositions(this.tiles[tileIndex].getX(), this.tiles[tileIndex].getY());
                this.tiles[tileIndex].setPositions(tempX, tempY);

                this.emptyTileIndex = tileIndex;

                if (checkWin()) {
                    for (Tile tile : this.tiles) {
                        tile.setWinColor();
                    }
                    this.hasWon = true;
                    this.gameTimer.stop();
                }
            }
        }
    }

    /**
     * checks if every tile is in a right position
     * @return true if puzzle is solved
     */
    public boolean checkWin() {
        for (int i = 0; i < this.tiles.length; i++) {
            if (this.tiles[i].getTileNumber() != i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * checks the number of inversions in an array, used to calculate solvability
     * @return number of inversions
     */
    private int inversionCount() {
        int count = 0;
        for (int i = 0; i < this.tiles.length - 1; i++) {
            for (int j = i + 1; j < this.tiles.length; j++) {
                if (this.tiles[i].getTileNumber() != 16 && this.tiles[j].getTileNumber() != 16 && this.tiles[i].getTileNumber() > this.tiles[j].getTileNumber()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * finds in which row the empty tile is, used to calculate solvability
     * @return the row number
     */
    private int findEmptyTilePosition() {
        int index = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            if (this.tiles[i].getTileNumber() == 16) {
                index = i;
                break;
            }
        }
        return (int) Math.ceil((double) (16 - index) / 4);
    }

    /**
     * checks if the game is solvable
     * @return true if solvable
     */
    private boolean solvable() {
        int emptyPos = findEmptyTilePosition();
        int inversionCount = inversionCount();
        if (emptyPos % 2 == 1) { //is odd
            return inversionCount % 2 == 0; // true if even
        } else { //is even
            return inversionCount % 2 == 1; // true if odd
        }
    }

    /**
     * resets the game
     */
    public void resetGame(){
        for (Tile tile : this.tiles) {
            tile.resetColor();
        }
        if (this.hasWon){
            this.score.updateTime(this.gameTimer.getTime());
        }
        this.hasWon = false;
        this.timerCounting = false;
        this.gameTimer.reset();

        start();
    }

}
