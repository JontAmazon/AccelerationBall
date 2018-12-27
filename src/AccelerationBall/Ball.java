package AccelerationBall;

import javax.swing.*;
import java.awt.*;

public abstract class Ball {
    protected int INIT_BALL_X;
    protected int INIT_BALL_Y;
    protected double xPos;
    protected double yPos;
    protected int x;
    protected int y;
    protected double xdir; //step size.
    protected double ydir; //step size.

    protected Image image;
    protected int imageWidth;
    protected int imageHeight;
    protected boolean isVisible = true;

    //Item related attributes:
    protected boolean isInvisible = false;
    protected boolean isEnlarged = false;
    protected long enlargedBirthTime;
    protected long invisibilityBirthTime;
    protected long stealthTime = 1000*1;
    protected long enlargedTime = 1000*11;


    public Ball(ImageIcon imageIcon) {
        image = imageIcon.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }


    public double getXpos() { return xPos; }
    public double getYpos() { return yPos; }
    public int getX() { return x; }
    public int getY() { return y; }
    public Rectangle getRect() { return new Rectangle(x, y, image.getWidth(null), image.getHeight(null)); }
    public void resetPos() {
        xPos = INIT_BALL_X;
        yPos = INIT_BALL_Y;
        x = (int) xPos;
        y = (int) yPos;
        xdir = 0;
        ydir = 0;
    }
    protected Image getImage() { return image; }
    public int getImageWidth() { return imageWidth; }
    public int getImageHeight() { return imageHeight; }
    public boolean isVisible() { return isVisible; }
    public void setInvisible() {
        isVisible = false;
        invisibilityBirthTime = System.currentTimeMillis();
    }


    public void move() {
        xPos += xdir;
        yPos += ydir;
        x = (int) xPos;
        y = (int) yPos;
    }
    //Item related:
    public abstract void enlargen();
    public abstract void reduce();
    public void updateEnlarged() {
        if (isEnlarged && (System.currentTimeMillis() - enlargedBirthTime) > enlargedTime) {
            reduce();
        }
    }
    public void updateVisibility() {
        if ((System.currentTimeMillis() - invisibilityBirthTime) > stealthTime) {
            isVisible = true;
        }
    }


}






