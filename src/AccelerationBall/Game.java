package AccelerationBall;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;

public class Game extends JFrame {
    public final static int WIDTH = 800;
    public final static int HEIGTH = 700;

    //            game.rootPane.getJMenuBar();
//            game.rootPane.getLayeredPane();
//            game.rootPane.getLayout();
//            game.getJMenuBar();



    //Audio:
    public static Clip backgroundMusic; // SIMON, om jag assignar denna till null funkar det ej. Varför?
    public static Clip gameMusic;
    public static Clip superMario;
    public static Clip bounce;
    public static Clip apple;

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

            url = this.getClass().getClassLoader().getResource("resources/_1bounce_bra_loud.wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            bounce = AudioSystem.getClip();
            bounce.open(audioIn);

            url = this.getClass().getClassLoader().getResource("resources/_apple.wav");
            audioIn = AudioSystem.getAudioInputStream(url);
            apple = AudioSystem.getClip();
            apple.open(audioIn);
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
        setIconImage((new ImageIcon("src/resources/smiley2_46x41.png").getImage()));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }

    public static void playBackgroundMusic() { backgroundMusic.start(); }
    public static void stopBackgroundMusic() { backgroundMusic.stop(); }
    public static void playGameMusic() { gameMusic.start(); }
    public static void stopGameMusic() { gameMusic.stop(); }
    public static void playSuperMario() {
        bounce.setFramePosition(0);
        superMario.start();
    }
    public static void stopSuperMario() { superMario.stop(); }
    public static void playBounceAudio() {
        bounce.setFramePosition(0);
        bounce.start();
    }
    public static void stopBounceAudio() { bounce.stop(); }
    public static void playAppleAudio() {
        apple.setFramePosition(0);
        apple.start();
    }
    public static void stopAppleAudio() { apple.stop(); }
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




