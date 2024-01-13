import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reset extends JPanel implements ActionListener {

    private GamePanel gamePanel;
    private JButton resetButton;


    public Reset(GamePanel gp){
        this.gamePanel = gp;

        setSize(100,100);
        setLocation(450,150);
        setBackground(new Color(0x0D1B2A));
        this.setLayout(null);
        setVisible(true);

        setUpResetButton();

        add(this.resetButton);
    }

    /**
     * sets up the reset button
     */
    public void setUpResetButton(){
        this.resetButton = new JButton("reset");
        this.resetButton.setBounds(5,5,90,90);
        this.resetButton.setIcon(null);
        this.resetButton.setBackground(new Color(0x778DA9));
        this.resetButton.setBorderPainted(false);
        this.resetButton.setFont(new Font("Arial",Font.PLAIN,24));
        this.resetButton.setForeground(new Color(0xFFFFFF));
        this.resetButton.setFocusable(false);
        this.resetButton.addActionListener(this);

        this.resetButton.setVisible(true);
    }

    /**
     * resets the whole game
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.gamePanel.resetGame();
    }
}
