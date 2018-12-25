package AccelerationBall;

import javax.swing.*;

public class DevilBall extends Ball {
    //Direction of acceleration:
    private double xDist;
    private double yDist;
    private double distance;
    private double xQuota;
    private double yQuota;

    //Parameters:
    private final double acceleration = 0.125;
    private final double friction = 0.99;
    private final double speedLimit = 35;
    private final double orthogonalBounce = 2;
    private final double parallelBounce = 1.2;


    public DevilBall() {
        INIT_BALL_X = 100;
        INIT_BALL_Y = 350;
        resetPos();

        ImageIcon ii = new ImageIcon("src/resources/devil1_41x41.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }


    public void setX(int x) {super.x = x;}
    public void setY(int y) {this.y = y;}
    public double getXdirection() {
        return xdir;
    }
    public double getYdirection() {
        return ydir;
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
    public void move(SmileyBall smiley, double time) { //TODO = ta bort "setDirection..."
        //calculate direction of accelerat
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
        super.move();
        bounceAtWalls(time);
    }
    private void bounceAtWalls(double time) {
        if (xPos < 0) {
            xPos = 0;
            xdir *= -1;
            xdir *= orthogonalBounce;
            ydir *= parallelBounce;
//            xdir *= (1 + time/60000);
        } else if (xPos > Game.WIDTH - imageWidth - 15) {
            xPos = Game.WIDTH - imageWidth - 15;
            xdir *= -1;
            xdir *= orthogonalBounce;
            ydir *= parallelBounce;
//            xdir *= (1 + time/60000);
        }
        if (yPos < 0) {
            yPos = 0;
            ydir *= -1;
            xdir *= parallelBounce;
            ydir *= orthogonalBounce;
//            ydir *= (1 + time/60000);
        } else if (yPos > Game.HEIGTH - imageHeight - 40) {
            yPos = Game.HEIGTH - imageHeight - 40;
            ydir *= -1;
            xdir *= parallelBounce;
            ydir *= orthogonalBounce;
//            ydir *= (1 + time/60000);
        }
    }


    //Item related:
    public void enlargen() {
        enlargedBirthTime = System.currentTimeMillis();
        ImageIcon ii = new ImageIcon("src/resources/devil_80x80.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
    public void reduce() {
        ImageIcon ii = new ImageIcon("src/resources/devil1_41x41.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }

}





