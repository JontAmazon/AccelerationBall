package AccelerationBall;

import AccelerationBall.Items.Item;

import javax.swing.*;
import java.awt.*;

public abstract class Ball {
    protected int INIT_BALL_X; //TODO, när allt allt OK, testa göra om till package-private.
    protected int INIT_BALL_Y;
    protected double xPos;
    protected double yPos;
    protected int x;
    protected int y;
    protected double xdir; //step size.
    protected double ydir; //step size.
    //Direction of acceleration:
    protected double xDist;
    protected double yDist;
    protected double distance;
    protected double xQuota;
    protected double yQuota;

    protected double acceleration;
    protected double friction;
    protected double speedLimit;

    protected Image image;
    protected int imageWidth;
    protected int imageHeight;


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


    public void move(SmileyBall smiley) {
        //calculate direction of acceleration.
        xDist = smiley.getXpos() - xPos;
        yDist = smiley.getYpos() - yPos;
        distance = Math.sqrt(xDist * xDist + yDist * yDist);
        xQuota = xDist / distance;
        yQuota = yDist / distance;
        //move:
        xdir += xQuota * acceleration;
        ydir += yQuota * acceleration;
        xdir *= friction;
        ydir *= friction;
        restrictSpeed();
        step();
    }
    private void restrictSpeed() {
        if (xdir > speedLimit) {
            xdir = speedLimit;
        } else if (xdir < -speedLimit) {
            xdir = -speedLimit;
        }
        if (ydir > speedLimit) {
            ydir = speedLimit;
        } else if (ydir < -speedLimit) {
            ydir = -speedLimit;
        }
    }
    protected void step() {
        double tempXdir = xdir;
        double tempYdir = ydir;
        if (this instanceof SmileyBall) {
            if (Math.abs(xdir) != 0 && Math.abs(ydir) != 0) {
                tempXdir *= 1.32/(Math.sqrt(2));
                tempYdir *= 1.32/(Math.sqrt(2));
            }
        }

        xPos += tempXdir;
        yPos += tempYdir;
        x = (int) xPos;
        y = (int) yPos;
    }

    public boolean intersects(Ball ball) {
        double thisXcenter = this.xPos + this.image.getWidth(null)/2;
        double ballXcenter = ball.getXpos() + ball.getImage().getWidth(null)/2;
        double thisYcenter = this.yPos + this.image.getHeight(null)/2;
        double ballYcenter = ball.getYpos() + ball.getImage().getHeight(null)/2;
        double distance = Math.sqrt((thisXcenter - ballXcenter)*(thisXcenter - ballXcenter)
                + (thisYcenter - ballYcenter)*(thisYcenter - ballYcenter));
        //Approximate an object "radius" from its square image.
        double thisRadius = (image.getWidth(null) + image.getHeight(null)) / 4 * 0.90;
        double ballRadius = (ball.getImage().getWidth(null) + ball.getImage().getHeight(null)) / 4 * 0.90;
        return distance < (thisRadius + ballRadius);
    }
    public boolean intersects(Item item) {
        double thisXcenter = this.xPos + this.image.getWidth(null)/2;
        double ballXcenter = item.getX() + item.getImage().getWidth(null)/2;
        double thisYcenter = this.yPos + this.image.getHeight(null)/2;
        double ballYcenter = item.getY() + item.getImage().getHeight(null)/2;
        double distance = Math.sqrt((thisXcenter - ballXcenter)*(thisXcenter - ballXcenter)
                + (thisYcenter - ballYcenter)*(thisYcenter - ballYcenter));
        //Approximate an object "radius" from its square image.
        double thisRadius = (image.getWidth(null) + image.getHeight(null)) / 4 * 0.90;
        double itemRadius = (item.getImage().getWidth(null) + item.getImage().getHeight(null)) / 4 * 0.90;
        return distance < (thisRadius + itemRadius);
    }
}






