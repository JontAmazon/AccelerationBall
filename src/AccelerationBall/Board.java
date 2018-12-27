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
    private DevilBall ghost;
    private DevilBall devil;
    private ArrayList<DevilBall> enemies = new ArrayList<>();
    private long birthTime = System.currentTimeMillis();
    private long time = birthTime;
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
        devil = new DevilBall(new ImageIcon("src/resources/devil1_41x41.png"));
        ghost = new DevilBall(new ImageIcon("src/resources/ghost1_44x39-converted.png"), 100 / 100000,
                0.1, DevilBall.getStartingSpeedLimit(), 1, 1, 130, 500);
        enemies.add(ghost);
        enemies.add(devil);
        inGame = true;
        addKeyListener(new TAdapter());
        setFocusable(true); //(hmm)
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 4000, 16);
    }
    ////////////////////////////////////////////////////////////////////
    // // // // // // // // // // // // // // // // // // // // // ///
    @Override                                        ///////////////
    public void addNotify() { //TODOO = testa ta bort.
        super.addNotify();
    }    ////////////
    private class TAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            smiley.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            smiley.keyPressed(e);
        }
    }    /////////
    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            time += 10;
            smiley.move();
            for (DevilBall devil : enemies) {
                devil.move(smiley, time-birthTime);
            }
            checkCollision();
            updateBallStatuses();

            generateItems();
            checkItemCollisions();
            updateItems();

            repaint();
            }
    } ///////
    ////////////////////////////////////////////////////////
    // TODO = Menu
    // TODO = Super Mario-musik
    // TODO = nytt item:   halvera djävulens fart
    // TODO = - Varje äpple måste tas inom 5s,
    // efter 3 missar (POP-UP = "STRIKE 1"!) kommer djävulen in i en Frenzy (LJUD: "FRENZY!"): studsfaktor 2.5

    private void checkCollision() {
        for (DevilBall devil : enemies) {
            if (smiley.getRect().intersects(devil.getRect())) {
                gameOver();
            }
        }
        if (smiley.getRect().intersects(apple.getRect())) {
            appleCounter++;
            apple = new Apple();
        }
    }
    private void updateBallStatuses() {
        smiley.updateEnlarged();
        smiley.updateImmortality();
        smiley.updateVisibility();
        for (DevilBall devil : enemies) {
            devil.updateEnlarged();
            devil.updateVisibility();
            if (devil.isGhost()) {
                devil.turnGhosts(smiley);
                devil.speedUp(time-birthTime);
            }
        }
    }
    private void generateItems() {
        random = rand.nextInt(1000000/16);
        //Intervall=100 ==> 1 item var tioende sekund.
        if (random > 1000 && random < 1090) {
            Fire fire = new Fire();
            addIfNotIntersects(fire);
        }
        if (random > 2000 && random < 2010) {
            Enlarger enlarger = new Enlarger();
            addIfNotIntersects(enlarger);
        }
        if (random > 3000 && random < 3005) {
            Immortality flask = new Immortality();
            addIfNotIntersects(flask);
        }
//        if (random > 4000 && random < 4005) {
//                InvisibilityCloak cloak = new InvisibilityCloak();
//                addIfNotIntersects(cloak);
//        }
    }
    private void addIfNotIntersects(Item newItem) {
        for (Item item : items) {
            if (newItem.getRect().intersects(item.getRect())) {
                return;
            }
        }
        items.add(newItem);
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
                for (DevilBall devil : enemies) {
                    if (devil.getRect().intersects(item.getRect())) {
                        ((Enlarger) item).consume();
                        devil.enlargen();
                    }
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
//                for (DevilBall devil : enemies) {
//                    if (devil.getRect().intersects(item.getRect())) {
//                        ((InvisibilityCloak) item).consume();
//                        devil.setInvisible();
//                    }
//                }
            }
        }
    }
    private void updateItems() {
        //NOTE = includes removing consumed items.

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
    }

    private void gameOver() {
        //EV TODO = bättre: "smiley = new Smiley". Således resetta items.
        //                   devil = new Devil. ghost = new Ghost.
        if (! smiley.isImmortal()) {
            //inGame = false;
            //timer.cancel();
            //SHOW MENU =)

            smiley = new SmileyBall();
            devil = new DevilBall(new ImageIcon("src/resources/devil1_41x41.png"));
            ghost = new DevilBall(new ImageIcon("src/resources/ghost1_44x39-converted.png"), 100,
                    0.1, DevilBall.getStartingSpeedLimit(), 1, 1, 130, 500);
            enemies = new ArrayList<>();
            enemies.add(ghost);
            enemies.add(devil);
            items = new ArrayList<>();

            appleCounter = 0;
            birthTime = System.currentTimeMillis();
            time = birthTime;
        }

        //EV todo = timer.wait(2000).
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
            //drawMenu.
        }
        Toolkit.getDefaultToolkit().sync();
    }
    private void drawObjects(Graphics2D g2d) {
        //Background:
        g2d.drawImage(backgroundImage, 0, 0,
                Game.WIDTH, Game.HEIGTH, this);
        g2d.drawImage(whiteImage, Game.WIDTH - 85, 0,
                whiteImage.getWidth(null), 27, this);
        g2d.drawString(appleCounter.toString() + "    " + Game.displayTime(time, birthTime),
                Game.WIDTH - 80, 20);
        //Items:
        for (Item item : items) {
            g2d.drawImage(item.getImage(), item.getX(), item.getY(),
                    item.getImageWidth(), item.getImageHeight(), this);
        }
        g2d.drawImage(apple.getImage(), apple.getX(), apple.getY(),
                apple.getImageWidth(), apple.getImageHeight(), this);
        //Balls:
        if (smiley.isVisible()) {
            g2d.drawImage(smiley.getImage(), smiley.getX(), smiley.getY(),
                    smiley.getImageWidth(), smiley.getImageHeight(), this);
        }
        for (DevilBall devil : enemies) {
            if (devil.isVisible()) {
                g2d.drawImage(devil.getImage(), devil.getX(), devil.getY(),
                        devil.getImageWidth(), devil.getImageHeight(), this);
            }
        }
    }

}













