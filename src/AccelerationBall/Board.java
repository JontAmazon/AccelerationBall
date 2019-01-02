package AccelerationBall;

import AccelerationBall.Items.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;
import javax.swing.*;

public class Board extends JPanel {
    private static boolean inGame;
    private SmileyBall smiley;
    private GhostBall ghost;
    private DevilBall devil;
    private int frenzyCounter = 0;
    private long frenzyTimeCounter = 0;
    private long appleLife = 4000;

    private Timer timer;
    private long birthTime = System.currentTimeMillis();
    private long time = birthTime;
    private String finalTime;

    //Items:
    private ArrayList<Item> items = new ArrayList<>();
    private Apple apple = new Apple();
    private Integer appleCounter = 0;
    private Random rand = new Random();
    private int random = -1;

    //Images:
    ImageIcon ii = new ImageIcon("src/resources/background2.png");
    ImageIcon ii2 = new ImageIcon("src/resources/white_small.png");
    ImageIcon ii3 = new ImageIcon("src/resources/devil1_41x41.png");
    private Image backgroundImage = ii.getImage();
    private Image whiteImage = ii2.getImage();
    private Image menuImage = ii3.getImage();

    //Background related:
    private JLabel header;
    private JLabel appleResult;
    private JLabel timeResult;
    //private JLabel playEnterToPlay;
    //private JButton playGameButton = new JButton("Play Game");
    private JButton muteButton = new JButton("Mute music");
    private static boolean isMuted = false;


    public Board() {
        addKeyListener(new TAdapter());
        setFocusable(true);
        loadButtons();
        loadLabels();

        startGame();
    }
    private void loadButtons() {
//        playGameButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                startGame();
//            }
//        });
//        this.add(playGameButton);
        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMuted = (! isMuted);
                if (isMuted) {
                    Game.stopBackgroundMusic();
                    muteButton.setText("   Un-mute   ");
                }
                if (! isMuted) {
                    Game.playBackgroundMusic();
                    muteButton.setText("Mute music");
                }
            }
        });
        //setFont(Font font); (todoo)
        muteButton.setBackground(Color.BLUE);
        muteButton.setForeground(Color.RED);
        this.add(muteButton);
    }
    private void loadLabels() {
        header = new JLabel("ACCELERATION BALL (TM)");
        header.setFont(new Font("Verdana",1,36));
        header.setForeground(Color.BLUE);
        this.add(header);

        appleResult = new JLabel("Apples: ");
        appleResult.setFont(new Font("Verdana",1,12));
        appleResult.setForeground(Color.red);
        this.add(appleResult);

        timeResult = new JLabel("Time: ");
        timeResult.setFont(new Font("Verdana",1,12));
        timeResult.setForeground(Color.red);
        this.add(timeResult);
    }
    public void addNotify() { super.addNotify(); }
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
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_ENTER) {
            gameOver();
            startGame();            //ta bort gameOver()  ?
        }
    }  //TODO = fortsätt här för ENTER-todon. TAdaptor har keyPressed().
    private class ScheduleTask extends TimerTask {
        @Override
        public void run() {
            if (inGame) {
                time += 10;
                smiley.move();
                ghost.move(smiley);
                devil.move(smiley);
                checkCollision();
                updateBallStatuses();

                generateItems();
                checkItemCollisions();
                updateItems();
            }

            repaint();
        }
    }

    ////////////////////////////////////////////////////////
    //////////////// TO-DO LIST ////////////////////////////
    ////////////////////////////////////////////////////////
    // TODO = menu: EASY / MEDIUM / HARD
        //Colours: green / blue   / red
        //Void methods: setEasy(), setMedium(), setHard()
    // TODO = implement all audio, med if-satser för isMuted:
        // TODO = hitta ljud:   game over
        // TODO = hitta ljud:   "Strike 1!"
        // TODO = hitta ljud:   Frenzy:
        // TODO = gör så att audio kan spela 2 ggr nära inpå varann.
            //Testa att skapa ny Clip varje gång.    TODO = hur... (???)
    // TODO = Enter startar alltid om spelet.

    // Methods that run every 16 ms:
    private void checkCollision() {
        if (smiley.intersects(ghost) || smiley.intersects(devil)) {
            gameOver();
        }
        if (smiley.getRect().intersects(apple.getRect())) {
            if (! isMuted) {
                Game.playAppleAudio();
            }
            appleCounter++;
            frenzyTimeCounter = 0;
            generateNewApple();
        }
    }
    private void updateBallStatuses() {
        smiley.updateImmortality();
        if (! smiley.isImmortal()) { //EV add:   && gameMusicIsPaused()
            Game.playGameMusic();
        }
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
        if (random > 1000 && random < 1165) {
            Fire fire = new Fire();
            if (! intersectsWithSomeItem(fire)) {
                items.add(fire);
            }
        }
        if (random > 3000 && random < 3005) {
            items.add(new Immortality());
        }
    }
    private void generateNewApple() {
            apple = new Apple();
            while (intersectsWithSomeItem(apple)) {
                apple = new Apple();
            }
    }
    private boolean intersectsWithSomeItem(Item newItem) {
        for (Item item : items) {
            if (newItem.getRect().intersects(item.getRect())) {
                return true;
            }
        }
        return false;
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
                    if (! isMuted) {
                        Game.stopGameMusic();
                        Game.playSuperMario();
                    }
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
            inGame = false;
            //playGameButton.setVisible(true);                  ta bort.
            Game.stopGameMusic();
            if (! isMuted) {
                Game.playBackgroundMusic();
            }
            finalTime = Game.displayTime(time, birthTime);
            timer.cancel();
        }
    }
    private void startGame() {
        inGame = true;
        this.requestFocusInWindow();
        //playGameButton.setVisible(false);                     ta bort.
        Game.stopBackgroundMusic();
        if (! isMuted) {
            Game.playGameMusic();
        }

        smiley = new SmileyBall();
        ghost = new GhostBall();
        devil = new DevilBall();
        appleCounter = 0;
        frenzyTimeCounter = 0;
        frenzyCounter = 0;
        items = new ArrayList<>();
        timer = new Timer();
        timer.scheduleAtFixedRate(new ScheduleTask(), 1200, 16);
        birthTime = System.currentTimeMillis();
        time = birthTime;
    }
    public static void playBounceAudio() {
        if (! isMuted) {
            Game.playBounceAudio();
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
//        if (inGame) {
//            drawObjects(g2d);
//        } else {
//            drawMenu(g2d);
//        }
        drawObjects(g2d);
        Toolkit.getDefaultToolkit().sync();
    }
    private void drawObjects(Graphics2D g2d) {
        setComponentsVisible(inGame);

        //Update positions for labels and buttons.
            //(Idk why this is requiered...)
        header.setLocation(100, 25);
        muteButton.setLocation(30, Game.HEIGTH - 110);
        muteButton.setSize(110, 40);

        //Background:
        g2d.drawImage(backgroundImage, 0, 0,
                Game.WIDTH, Game.HEIGTH, this);

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

        //Score:
        appleResult.setLocation(Game.WIDTH-120, 35);
        timeResult.setLocation(Game.WIDTH-120, 20);
        appleResult.setText("Apples: " + appleCounter.toString());
        timeResult.setText("Time: " + Game.displayTime(time, birthTime));
    }
    private void setComponentsVisible(boolean inGame) {
        if (inGame) {
            muteButton.setVisible(false);
            header.setVisible(false);
        } else {
            muteButton.setVisible(true);
            header.setVisible(true);

        }
    }

}










