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
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game("Acceleration Ball(TM)");
            game.setVisible(true);
        });
    }









}




