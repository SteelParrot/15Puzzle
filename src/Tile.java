import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile extends JButton implements ActionListener {

    private int posX, posY;

    private int tileNumber;
    private final GamePanel gamePanel;

    private final ImageIcon iconImage;

    public Tile(int tileNumber, int posX, int posY,ImageIcon iconImage, GamePanel gamePanel){
        this.tileNumber = tileNumber;
        this.gamePanel = gamePanel;

        this.iconImage = iconImage;

        this.posX = posX;
        this.posY = posY;

        initializeTile();
    }

    /**
     * creates the look of the tile and hides tile number 16
     */
    public void initializeTile(){
        setBounds(this.posX,this.posY,100,100);
        setIcon(this.iconImage);
        setBackground(new Color(0x778DA9));
        setBorderPainted(false);
        setFocusable(false);
        setVisible(false);

        if (this.tileNumber != 16){
            addActionListener(this);
            setVisible(true);
        }
    }

    public int getTileNumber() {
        return tileNumber;
    }

    /**
     * sets the tile to a specific position x,y
     * @param x
     * @param y
     */
    public void setPositions(int x, int y){
        this.posX = x;
        this.posY = y;
        setBounds(x,y,100,100);
    }

    /**
     * sets the color to the default one
     */
    public void resetColor(){
        setBackground(new Color(0x778DA9));
    }

    /**
     * sets the color to a "win" color, when the user solves the puzzle
     */
    public void setWinColor(){
        setBackground(new Color(0xA2FD19));
    }

    /**
     * calls move method when the tile is clicked
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.gamePanel.move(this.tileNumber);
    }

}
