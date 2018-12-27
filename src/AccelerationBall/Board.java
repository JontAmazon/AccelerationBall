package AccelerationBall;

import AccelerationBall.Items.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class Board extends JPanel {
    private boolean inGame;
    private SmileyBall smiley;
    private GhostBall ghost;
    private DevilBall devil;
    private int frenzyCounter = 0;
    private long frenzyTimeCounter = 0;
    private long frenzyStrike = 4500;

    private Timer timer;
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
    private Random rand = new Random();
    private int random = -1;


    public Board() {
        smiley = new SmileyBall();
        ghost = new GhostBall();
        devil = new DevilBall();
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
            ghost.move(smiley);
            devil.move(smiley);
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
    // TODO = gameOver():  wait(2000).
    // TODO = Bakgrundsmusik + Super Mario-musik + EV frenzy-musik
    // TODO = nytt item:   halvera djävulens fart (EV ikon: grön -)
    // TODO = implementera ny intersect().
    // TODO = create a Ghost class.
    // TODO = pusha


    private void checkCollision() {
        if (smiley.getRect().intersects(ghost.getRect())) {
            gameOver();
        }
        if (smiley.getRect().intersects(devil.getRect())) {
            gameOver();
        }

        if (smiley.getRect().intersects(apple.getRect())) {
            appleCounter++;
            frenzyTimeCounter = 0;
            apple = new Apple();
        }
    }
    private void updateBallStatuses() {
        smiley.updateImmortality();

        ghost.turnGhosts(smiley);
        ghost.speedUp(time-birthTime);

        //devil:
        if (! devil.isInFrenzy()) {
            frenzyTimeCounter += 10;
        }
        if (frenzyTimeCounter > frenzyStrike) {
            frenzyTimeCounter = 0;
            frenzyCounter++;
            apple = new Apple();
        }
        if (frenzyCounter == 3) {
            frenzyTimeCounter = 0;
            frenzyCounter = 0;
            devil.frenzy();
        }
        if (devil.isInFrenzy() && (time - devil.getFrenzyBirthTime()) > devil.getFrenzyTime()) {
            devil.stopFrenzy();
        }
    }
    private void generateItems() {
        random = rand.nextInt(1000000/16);
        //Intervall=100 ==> 1 item var tioende sekund.
        if (random > 1000 && random < 1090) {
            Fire fire = new Fire();
            addIfNotIntersects(fire);
        }
        if (random > 3000 && random < 3005) {
            Immortality flask = new Immortality();
            addIfNotIntersects(flask);
        }
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
            } else if (item instanceof Immortality) {
                if (smiley.getRect().intersects(item.getRect())) {
                    ((Immortality) item).consume();
                    smiley.setImmortal();
                }
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
        if (! smiley.isImmortal()) {
            //inGame = false;
            //timer.cancel();
            //SHOW MENU =)

            smiley = new SmileyBall();
            ghost = new GhostBall();
            devil = new DevilBall();
            frenzyTimeCounter = 0;
            frenzyCounter = 0;

            items = new ArrayList<>();
            appleCounter = 0;
            birthTime = System.currentTimeMillis();
            time = birthTime;
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
        g2d.drawImage(smiley.getImage(), smiley.getX(), smiley.getY(),
                    smiley.getImageWidth(), smiley.getImageHeight(), this);
        g2d.drawImage(ghost.getImage(), ghost.getX(), ghost.getY(),
                ghost.getImageWidth(), ghost.getImageHeight(), this);
        g2d.drawImage(devil.getImage(), devil.getX(), devil.getY(),
                        devil.getImageWidth(), devil.getImageHeight(), this);
    }

}













