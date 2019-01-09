package AccelerationBall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Game extends JFrame {
    public final static int WIDTH = 800;
    public final static int HEIGTH = 700;

    private static Clip backgroundMusic;
    private static Clip gameMusic;
    private static Clip superMario;
    private static Clip frenzy;
    private static Clip gameOver;

    public Game() {
        loadAudio();
        initUI();
    }

    private void loadAudio() {
        try {
            URL url = this.getClass().getClassLoader().getResource("resources/_menu_5min(wav).wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioIn);

            url = this.getClass().getClassLoader().getResource("resources/_game_bra2_5min(wav).wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            gameMusic = AudioSystem.getClip();
            gameMusic.open(audioIn);

            url = this.getClass().getClassLoader().getResource("resources/_Super Mario_delayed start(wav).wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            superMario = AudioSystem.getClip();
            superMario.open(audioIn);

            url = this.getClass().getClassLoader().getResource("resources/_frenzy_cat.wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            frenzy = AudioSystem.getClip();
            frenzy.open(audioIn);

            url = this.getClass().getClassLoader().getResource("resources/_game over.wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            gameOver = AudioSystem.getClip();
            gameOver.open(audioIn);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void initUI() {
        add(new Board());

        setDefaultCloseOperation(EXIT_ON_CLOSE); //(hmm)
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); //(hmm)
        setResizable(false);
        setTitle("Acceleration Ball (TM)");
        setIconImage((new ImageIcon("src/resources/smiley2_46x41_trans.png").getImage()));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }

    public static void playBackgroundMusic() {
        backgroundMusic.setFramePosition(0);
        backgroundMusic.start();
    }
    public static void stopBackgroundMusic() { backgroundMusic.stop(); }
    public static void playGameMusic() {
        gameMusic.setFramePosition(0);
        gameMusic.start();
    }
    public static void stopGameMusic() { gameMusic.stop(); }
    public static void playSuperMario() {
        superMario.setFramePosition(0);
        superMario.start();
    }
    public static void playBounceAudio() {
        try {
            URL url = new URL("file:/C:/Users/jonat/IdeaProjects/AccelerationBall/out/production/AccelerationBall/resources/_1bounce_bra_loud.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip bounce = AudioSystem.getClip();
            bounce.open(audioIn);
            bounce.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static void playAppleAudio() {
        try {
            URL url = new URL("file:/C:/Users/jonat/IdeaProjects/AccelerationBall/out/production/AccelerationBall/resources/_apple.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip apple = AudioSystem.getClip();
            apple.open(audioIn);
            apple.start();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public static void playFrenzyAudio() {
        frenzy.setFramePosition(0);
        frenzy.start();
    }
    public static void playGameOverAudio() {
        gameOver.setFramePosition(0);
        gameOver.start();
    }

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




