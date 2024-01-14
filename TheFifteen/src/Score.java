import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashMap;

/**
 * Score
 */
public class Score {

    private JLabel previousTime;
    private JLabel fastestTime;

    private HashMap<String, Integer> score = new HashMap<>();

    /**
     * sets up the labels and reads the data
     */
    public Score(){

        this.previousTime = new JLabel("<HTML>previous time:<BR>0:0</HTML>");
        this.previousTime.setBounds(25,425,200,75);
        this.previousTime.setFont(new Font("Arial",Font.PLAIN,24));
        this.previousTime.setForeground(new Color(0xFFFFFF));
        this.previousTime.setVisible(true);

        this.fastestTime = new JLabel("<HTML>fastest time:<BR/>0:0</HTML>");
        this.fastestTime.setBounds(225,425,200,75);
        this.fastestTime.setFont(new Font("Arial",Font.PLAIN,24));
        this.fastestTime.setForeground(new Color(0xFFFFFF));
        this.fastestTime.setVisible(true);

        readData();
    }

    /**
     * reads the data from "score.txt" and changes the labels
     */
    public void readData(){
        try (BufferedReader bf = new BufferedReader(new FileReader("score.txt"))){
            String line = "";
            while ((line = bf.readLine()) != null){
                String[] splited = line.split("\\=");
                this.score.put(splited[0],Integer.valueOf(splited[1]));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.previousTime.setText("<HTML>previous time:<BR>"+this.score.get("lastTime")/60000+":"+(this.score.get("lastTime")/1000) % 60+"</HTML>");
        this.fastestTime.setText("<HTML>fastest time:<BR>"+this.score.get("bestTime")/60000+":"+(this.score.get("bestTime")/1000) % 60+"</HTML>");

        if (this.score.get("bestTime") == Integer.MAX_VALUE){
            this.fastestTime.setText("<HTML>fastest time:<BR>0:0</HTML>");
        }
    }

    /**
     * writes times back to "score.txt"
     */
    public void writeData(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("score.txt"))){
           bw.write("lastTime="+this.score.get("lastTime"));
           bw.newLine();
           bw.write("bestTime="+this.score.get("bestTime"));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * just to add the labels to the frame
     * @param frame
     */
    public void addLabels(JFrame frame){
        frame.add(this.previousTime);
        frame.add(this.fastestTime);
    }

    /**
     * updates the times when user solves the puzzle
     * @param time
     */
    public void updateTime(int time){
        if(time< this.score.get("bestTime")){
            this.score.put("bestTime", time);
            this.fastestTime.setText("<HTML>fastest time:<BR>"+time/60000+":"+(time/1000) % 60+"</HTML>");
        }
        this.score.put("lastTime", time);
        this.previousTime.setText("<HTML>previous time:<BR>"+time/60000+":"+(time/1000) % 60+"</HTML>");

        writeData();
    }


}
