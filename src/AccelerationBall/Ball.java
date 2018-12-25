package AccelerationBall;

import java.awt.*;

public class Ball {
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

    //Item related attributes:
    protected boolean isInvisible = false;
    protected boolean isEnlarged = false;
    protected double enlargedBirthTime;
    protected double immortalityBirthTime;
    protected double invisibilityBirthTime;


    public Ball() { }


    public double getXpos() {
        return xPos;
    }
    public double getYpos() {
        return yPos;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getImageWidth() { return imageWidth; }
    public int getImageHeight() { return imageHeight; }
    public boolean isInvisible() {
        return isInvisible;
    }
    public void setInvisibility(boolean bool) {
        isInvisible = bool;
    }
    public boolean isEnlarged() {
        return isEnlarged;
    }
    public void setEnlarged(boolean bool) {
        isEnlarged = bool;
    }
    public double getEnlargedAge() {
        return (System.currentTimeMillis() - enlargedBirthTime);
    }
    public double getImmortalityAge() {
        return (System.currentTimeMillis() - immortalityBirthTime);
    }
    public double getInvisibilityAge() {
        return (System.currentTimeMillis() - invisibilityBirthTime);
    }


    public void move() {
        xPos += xdir;
        yPos += ydir;
        x = (int) xPos;
        y = (int) yPos;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

    protected Image getImage() {
        return image;
    }

    public void resetPos() {
        xPos = INIT_BALL_X;
        yPos = INIT_BALL_Y;
        x = INIT_BALL_X;
        y = INIT_BALL_Y;
        xdir = 0;
        ydir = 0;
    }


}
