import javax.swing.*;
import java.awt.*;

public class GameTimer extends JPanel{

    private Timer timer;
    private JLabel timeLabel = new JLabel("0:0");

    private int timeElapsed = 0;
    private int seconds = 0;
    private int minutes = 0;

    public GameTimer() {
        setSize(100,100);
        setLocation(450,25);
        setBackground(new Color(0x0D1B2A));
        this.setLayout(null);
        setVisible(true);

        setUpTimeLabel();

        add(this.timeLabel);
    }

    /**
     * sets up the timer
     */
    public void setUpTimeLabel(){
        this.timeLabel.setBounds(5,5,90,90);
        this.timeLabel.setFont(new Font("Arial",Font.PLAIN,24));
        this.timeLabel.setForeground(new Color(0xFFFFFF));//0x1B263B
        this.timeLabel.setOpaque(true);
        this.timeLabel.setBackground(new Color(0x778DA9));
        this.timeLabel.setHorizontalAlignment(JTextField.CENTER);
        this.timeLabel.setVisible(true);

        this.timer = new Timer(1000, e -> {
            this.timeElapsed += 1000;
            this.minutes = this.timeElapsed/60000;
            this.seconds = (this.timeElapsed/1000) % 60;

            this.timeLabel.setText((this.minutes)+":"+(this.seconds));
        });
    }

    public int getTime(){
        return this.timeElapsed;
    }

    /**
     * starts the timer
     */
    public void start(){
        timer.start();
    }

    /**
     * stops the timer
     */
    public void stop(){
        timer.stop();
    }

    /**
     * resets the timer
     */
    public void reset(){
        stop();
        this.timeElapsed = 0;
        this.seconds = 0;
        this.minutes = 0;
        this.timeLabel.setText("0:0");
    }
}
