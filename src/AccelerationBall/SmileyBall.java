package AccelerationBall;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class SmileyBall extends Ball {
    private double speed;
    private boolean isImmortal = false;
    private long immortalityBirthTime;
    private final long immortalTime = 1000*16;

    public SmileyBall() {
        super(new ImageIcon("src/resources/smiley2_46x41_trans.png"));
        INIT_BALL_X = 650;
        INIT_BALL_Y = 350;
        resetPos();
    }


    public void keyPressed (KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_NUMPAD1) {
            xdir = -speed;
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_NUMPAD3) {
            xdir = speed;
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_NUMPAD5) {
            ydir = -speed;
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S || key == KeyEvent.VK_NUMPAD2) {
            ydir = speed;
        }
    }
    public void keyReleased (KeyEvent e){
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A || key == KeyEvent.VK_NUMPAD1) {
            if (xdir != speed) {
                xdir = 0;
            }
        }
        if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D || key == KeyEvent.VK_NUMPAD3) {
            if (xdir != -speed) {
                xdir = 0;
            }
        }
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W || key == KeyEvent.VK_NUMPAD5) {
            if (ydir != speed) {
                ydir = 0;
            }
        }
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S || key == KeyEvent.VK_NUMPAD2) {
            if (ydir != -speed) {
                ydir = 0;
            }
        }
    }
    public void move() {
        super.step();
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
    public void setSpeed(double speed) { this.speed = speed; }

    //Item related:
    public boolean isImmortal() { return isImmortal; }
    public void setImmortal() {
        isImmortal = true;
        immortalityBirthTime = System.currentTimeMillis();

        ImageIcon imageIcon = new ImageIcon("src/resources/smiley2_big_trans.png");
        image = imageIcon.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
    public void updateImmortality() {
//        if ((System.currentTimeMillis() - immortalityBirthTime) < Board.blinkingConstant) { blink... //TODO
        if ((System.currentTimeMillis() - immortalityBirthTime) > immortalTime) {
            isImmortal = false;

            ImageIcon imageIcon = new ImageIcon("src/resources/smiley2_46x41_trans.png");
            image = imageIcon.getImage();
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
        }
    }


}
