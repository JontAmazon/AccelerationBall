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
    public static final long blinkingConstant = 2000;
    private Random rand = new Random();
    private int random = -1;

    public Board() {
        smiley = new SmileyBall();
        devil = new DevilBall();
        inGame = true;
        addKeyListener(new TAdapter());
        setFocusable(true); //(hmm)
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 7000, 16);

        items.add(new Fire());
    }

    // // // // // // // // // // // // // // // // // //
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            smiley.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            smiley.keyPressed(e);
        }
    }    //
    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            time += 10;
            smiley.move();
            devil.move(smiley, time);
            checkCollision();
            updateBallStatuses();
            updateItems();
            generateItems();
            repaint();
        }
    } //
    // // // // // // // // // // // // // // // // // //

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
    private void checkItemCollisions() {
        for (Item item : items) {
            if (item instanceof Fire) {
                if (! ((Fire) item).isInPreStatus()) {
                    if (smiley.getRect().intersects(item.getRect())) {
                        gameOver();
                    }
                }
            } else if (item instanceof Enlarger) {
                if (smiley.getRect().intersects(item.getRect())) {
                    ((Enlarger) item).consume(); //FIXME: kan tas bort?
                    smiley.enlargen();
                }
                if (devil.getRect().intersects(item.getRect())) {
                    ((Enlarger) item).consume();
                    devil.enlargen();
                }
            } else if (item instanceof Immortality) {
                if (smiley.getRect().intersects(item.getRect())) {
                    ((Immortality) item).consume();
                    smiley.setImmortal();
                }
            } else if (item instanceof InvisibilityCloak) {
                if (smiley.getRect().intersects(item.getRect())) {
                    ((InvisibilityCloak) item).consume();
                    smiley.setInvisible();
                }
                if (devil.getRect().intersects(item.getRect())) {
                    ((InvisibilityCloak) item).consume();
                    devil.setInvisible();
                }
            }
        }
    }
    private void updateItems() {
        for (Item item : items) {
            if (item.getAge() > item.getLife()) {
                item.consume();
            }
            if (item instanceof Fire && ((Fire)item).isInPreStatus() &&
                    ((Fire)item).getAge() > ((Fire)item).getPreStatusChangeTime()) {
                ((Fire)item).changeImage();
            }
        }
        Iterator<Item> it = items.iterator();
        while (it.hasNext()) {
            if (it.next().isConsumed()) {
                it.remove();
            }
        }
    } //TODO = nog HÄR = blinkning.
    private void updateBallStatuses() {
        smiley.updateEnlarged();
        smiley.updateImmortality();
        smiley.updateVisibility();
        devil.updateEnlarged();
        devil.updateVisibility();
    }
    private void generateItems() {
        random = rand.nextInt(1000000/16);
        //Intervall=100 ==> 0.1 items per sekund.
        if (random > 1000 && random < 1050) {
            items.add(new Fire());
        }
        if (random > 2000 && random < 2040) {
            items.add(new Enlarger());
        }
        if (random > 3000 && random < 3025) {
            items.add(new Immortality());
        }
        if (random > 4000 && random < 4025) {
            items.add(new InvisibilityCloak());
        }
    }

    @Override
    public void addNotify() { //TODOO = testa ta bort.
        super.addNotify();
    }
    private void gameOver() {
//        inGame = false;
//        timer.cancel();
//        SHOW MENU =)
        if (! smiley.isImmortal()) {
            smiley.resetPos();
            devil.resetPos();
            appleCounter = 0;
            items = new ArrayList<>();
        }
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
        g2d.drawImage(apple.getImage(), apple.getX(), apple.getY(),
                apple.getImageWidth(), apple.getImageHeight(), this);

        if (smiley.isVisible()) {
            g2d.drawImage(smiley.getImage(), smiley.getX(), smiley.getY(),
                    smiley.getImageWidth(), smiley.getImageHeight(), this);
        }
        if (devil.isVisible()) {
            g2d.drawImage(devil.getImage(), devil.getX(), devil.getY(),
                    devil.getImageWidth(), smiley.getImageHeight(), this);
        }
    }

}




