package AccelerationBall;

import AccelerationBall.Items.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class Board extends JPanel {
    private Timer timer;
    private boolean inGame;
    private String message = "Game Over";
    private SmileyBall smiley;
    private DevilBall devil;
    private double time = 0;
    ImageIcon ii = new ImageIcon("src/resources/background2.png");
    ImageIcon ii2 = new ImageIcon("src/resources/white_small.png");
    private Image backgroundImage = ii.getImage();
    private Image whiteImage = ii2.getImage();

    //Items:
    private ArrayList<Item> items = new ArrayList<>();
    private Apple apple = new Apple();
    private Integer appleCounter = 0;


    public Board() {
        smiley = new SmileyBall();
        devil = new DevilBall();
        inGame = true;
        addKeyListener(new TAdapter());
        setFocusable(true); //(hmm)
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 2000, 16);

        items.add(new Fire());
    }


    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            smiley.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            smiley.keyPressed(e);
        }
    }

    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            time += 10;
            smiley.move();
            devil.move(smiley, time);
            checkCollision();
            removeFires();
            repaint();
//            generateItems(); //TODO = implement
        }
    }

    private void checkCollision() {
        if (smiley.getRect().intersects(devil.getRect())) {
            gameOver();
        }
        if (smiley.getRect().intersects(apple.getRect())) {
            appleCounter++;
            apple = new Apple();
        }
        checkItemCollisions();
    }

    private void gameOver() {
//        inGame = false;
//        timer.cancel();
//        SHOW MENU =)
        smiley.resetPos();
        devil.resetPos();
        appleCounter = 0;
        items = new ArrayList<>();
    }

    @Override
    public void addNotify() { //TODOO = testa ta bort.
        super.addNotify();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        if (inGame) {
            drawObjects(g2d);
        } else {
            //gameFinished(g2d);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(backgroundImage, 0, 0,
                Game.WIDTH, Game.HEIGTH, this);
        g2d.drawImage(whiteImage, Game.WIDTH - 55, 0,
                whiteImage.getWidth(null), 27, this);
        g2d.drawString(appleCounter.toString(), Game.WIDTH - 45, 20);

        for (Item item : items) {
            g2d.drawImage(item.getImage(), item.getX(), item.getY(),
                    item.getImageWidth(), item.getImageHeight(), this);
        }

        g2d.drawImage(smiley.getImage(), smiley.getX(), smiley.getY(),
                smiley.getImageWidth(), smiley.getImageHeight(), this);
        g2d.drawImage(devil.getImage(), devil.getX(), devil.getY(),
                devil.getImageWidth(), smiley.getImageHeight(), this);
        g2d.drawImage(apple.getImage(), apple.getX(), apple.getY(),
                apple.getImageWidth(), apple.getImageHeight(), this);
    }

    private void removeFires() {
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().shouldBeRemoved()) {
                it.remove();
            }
        }
    }

    private void checkItemCollisions() {
        for (Item item : items) {
            if (item instanceof Fire) {
                if (! ((Fire) item).isInPreStatus()) {
                    if (smiley.getRect().intersects(item.getRect())) {
                        gameOver();
                    }
                }
            } else if (item instanceof Enlarger) {
                //TODO
            } else if (item instanceof Immortality) {
                //TODO
            } else if (item instanceof InvisibilityCloak) {
                //TODO
            }
        }


    }

}









