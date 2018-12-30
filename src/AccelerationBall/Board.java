package AccelerationBall;

import AccelerationBall.Items.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class Board extends JPanel {
    private boolean inGame = true;         //TODO = NOTE: false
    private SmileyBall smiley;
    private GhostBall ghost;
    private DevilBall devil;
    private int frenzyCounter = 0;
    private long frenzyTimeCounter = 0;
    private long appleLife = 4000;

    private Timer timer;
    private long birthTime = System.currentTimeMillis();
    private long time = birthTime;

    //Images:
    ImageIcon ii = new ImageIcon("src/resources/background2.png");
    ImageIcon ii2 = new ImageIcon("src/resources/white_small.png");
    ImageIcon ii3 = new ImageIcon("src/resources/devil1_41x41.png");
    private Image backgroundImage = ii.getImage();
    private Image whiteImage = ii2.getImage();
    private Image menuImage = ii3.getImage();

    //Items:
    private ArrayList<Item> items = new ArrayList<>();
    private Apple apple = new Apple();
    private Integer appleCounter = 0;
    private Random rand = new Random();
    private int random = -1;

    //Audio:



    public Board() {
        smiley = new SmileyBall();
        ghost = new GhostBall();
        devil = new DevilBall();
        // inGame = true;                           Ta bort, nog.
        addKeyListener(new TAdapter());
        setFocusable(true); //(hmm)
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 4000, 16);
    }
    ////////////////////////////////////////////////////////////////////
    // // // // // // // // // // // // // // // // // // // // // ///
    @Override                                        ///////////////
    public void addNotify() { super.addNotify(); }    ////////////
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
    // TODO = Menu --> ta bort timer.cancel() i gameOver().
    // TODO = SmileyBalls  keyPressed och keyReleased.  if-sats som kollar vilken riktning den åker mot.
        // Om redan åker åt motsatt håll ska hastigheten inte sättas till 0...
    // TODO = mute-knapp.
    // TODO = apple !intersects(Fire)
    // TODO = implement all audio:
        // TODO = gameMusic vs. backgroundMusic i gameOver();
        // TODO = hitta ljud:   game over
        // TODO = hitta ljud:   "Strike 1!"
        // TODO = hitta ljud:   Frenzy:
            // EV snabba upp musik vid frenzy
            // ^Note: jag kan "snabba upp den" om jag har 2 filer och kan välja var i filen jag börjar spela upp.
            // EV hitta 1 kort ljud för det, helt enkelt.
        // TODO = gör så att audio kan spela 2 ggr.


    private void checkCollision() {
        if (smiley.intersects(ghost) || smiley.intersects(devil)) {
            gameOver();
        }
        if (smiley.getRect().intersects(apple.getRect())) {
            Game.playAppleAudio();
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
        if (frenzyTimeCounter > appleLife) {
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
        if (random > 1000 && random < 1120) {
            Fire fire = new Fire();
            addIfNotIntersects(fire);
        }
        if (random > 3000 && random < 3010) {
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
                    if (smiley.intersects(item)) {
                        gameOver();
                    }
                }
            } else if (item instanceof Immortality) {
                if (smiley.getRect().intersects(item.getRect())) {
                    Game.playSuperMario();
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

            timer.cancel();
            timer = new Timer();
            timer.scheduleAtFixedRate(new ScheduleTask(), 2000, 16);
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













