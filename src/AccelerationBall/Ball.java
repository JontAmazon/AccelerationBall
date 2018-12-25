package AccelerationBall;

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
    protected long stealthTime = 2000;
    protected long enlargedTime = 5000;


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
    public double getInvisibilityAge() {
        return (System.currentTimeMillis() - invisibilityBirthTime);
    }
    public boolean isVisible() {
        return isVisible;
    }
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


    //Item related:
    public abstract void enlargen();
    public abstract void reduce();
    public void updateEnlarged() {
        if ((System.currentTimeMillis() - enlargedBirthTime) > enlargedTime) {
            reduce();
        }
    }
    public void updateVisibility() {
        if ((System.currentTimeMillis() - invisibilityBirthTime) > stealthTime) {
            isVisible = true;
        }
    }



}






