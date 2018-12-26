package AccelerationBall;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGTH = 700;

    public Game(String title) {
        initUI();
    }

    private void initUI() {
        add(new Board());

        setDefaultCloseOperation(EXIT_ON_CLOSE); //(hmm)
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); //(hmm)
        setResizable(false);
        setTitle("Acceleration Ball (TM)");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game("Acceleration Ball(TM)");
            game.setVisible(true);
        });
    }


    //For debugging:
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
    //For debugging: Insert:
    //System.out.println(" - " + Game.getLineNumber() + " - " + getClass());

    public static String displayTime(long time, long birthTime) {
        double seconds = (double) (time - birthTime) / 1000;
        String display = Double.toString(seconds);
        if (seconds < 10) {
            return "" + display.charAt(0) + display.charAt(1) + display.charAt(2);
        } else if (seconds < 60) {
            return "" + display.charAt(0) + display.charAt(1) + display.charAt(2) + display.charAt(3);
        } else if (seconds < 70) {
            seconds -= 60;
            display = Double.toString(seconds);
            return "1:0" + display.charAt(0) + display.charAt(1) + display.charAt(2);
        } else if (seconds < 120) {
            seconds -= 60;
            display = Double.toString(seconds);
            return "1:" + display.charAt(0) + display.charAt(1) + display.charAt(2) + display.charAt(3);
        } else if (seconds < 130) {
            seconds -= 120;
            display = Double.toString(seconds);
            return "2:0" + display.charAt(0) + display.charAt(1) + display.charAt(2);
        } else if (seconds < 180) {
            seconds -= 120;
            display = Double.toString(seconds);
            return "2:" + display.charAt(0) + display.charAt(1) + display.charAt(2) + display.charAt(3);
        } else if (seconds < 190) {
            seconds -= 180;
            display = Double.toString(seconds);
            return "3:0" + display.charAt(0) + display.charAt(1) + display.charAt(2);
        } else if (seconds < 240) {
            seconds -= 180;
            display = Double.toString(seconds);
            return "3:" + display.charAt(0) + display.charAt(1) + display.charAt(2) + display.charAt(3);
        } else {
            return "Gandalf time!";
        }
    }

}




