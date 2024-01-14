import javax.swing.*;
import java.awt.*;

/**
 * Main frame
 */
public class MainFrame extends JFrame {

    public MainFrame(){

        setTitle("15 Puzzle");
        ImageIcon s = new ImageIcon("res/tiles/tile15.png");
        setIconImage(s.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,600);
        setResizable(false);

        this.getContentPane().setBackground(new Color(0x5292D0));
        setLayout(null);

        GameTimer gameTimer = new GameTimer();
        Score score = new Score();

        GamePanel gamePanel = new GamePanel(gameTimer, score);

        Reset reset = new Reset(gamePanel);

        add(gamePanel);
        add(gameTimer);
        add(reset);

        score.addLabels(this);

        setVisible(true);

    }

}
