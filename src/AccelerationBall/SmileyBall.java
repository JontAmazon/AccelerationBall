package AccelerationBall;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class SmileyBall extends Ball {
    private double speed = 3;
    private boolean isImmortal = false;
    private long immortalityBirthTime;
    private final long immortalTime = 1000*10;

    public SmileyBall() {
        super(new ImageIcon("src/resources/smiley2_46x41.png"));
        INIT_BALL_X = 650;
        INIT_BALL_Y = 350;
        resetPos();
    }


    public void keyPressed (KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            xdir = -speed;
        }
        if (key == KeyEvent.VK_RIGHT) {
            xdir = speed;
        }
        if (key == KeyEvent.VK_UP) {
            ydir = -speed;
        }
        if (key == KeyEvent.VK_DOWN) {
            ydir = speed;
        }
    }
    public void keyReleased (KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT) {
            xdir = 0;
        }
        if (key == KeyEvent.VK_RIGHT) {
            xdir = 0;
        }
        if (key == KeyEvent.VK_UP) {
            ydir = 0;
        }
        if (key == KeyEvent.VK_DOWN) {
            ydir = 0;
        }
    }
    public void move() {
        super.move();
        restrictAtWalls();
    }
    private void restrictAtWalls() {
        if (xPos < 0) {
            xPos = 0;
        } else if (xPos > Game.WIDTH - imageWidth - 15) {
            xPos = Game.WIDTH - imageWidth - 15;
        }
        if (yPos < 0) {
            yPos = 0;
        } else if (yPos > Game.HEIGTH - imageHeight - 40) {
            yPos = Game.HEIGTH - imageHeight - 40;
        }
    }

    //Item related:
    public boolean isImmortal() {
        return isImmortal;
    }
    public void setImmortal() {
        isImmortal = true;
        immortalityBirthTime = System.currentTimeMillis();
    }
    public void updateImmortality() {
//        if ((System.currentTimeMillis() - immortalityBirthTime) < Board.blinkingConstant) { blink... //TODO
        if ((System.currentTimeMillis() - immortalityBirthTime) > immortalTime) {
            isImmortal = false;
        }
    }

    public void enlargen() {
        isEnlarged = true;
        enlargedBirthTime = System.currentTimeMillis();
        ImageIcon ii = new ImageIcon("src/resources/smiley2_big.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
    public void reduce() {
        isEnlarged = false;
        ImageIcon ii = new ImageIcon("src/resources/smiley2_46x41.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }

}
