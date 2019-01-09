package AccelerationBall;

import javax.swing.*;

public class GhostBall extends Ball {
    private double startingSpeedLimit = 2;
    private double maxSpeedLimit = 2;
    private double timeToReachMaxSpeed = 60*1000;
    private boolean facesRight = true;


    public GhostBall() {
        super(new ImageIcon("src/resources/ghost1_44x39-converted_trans.png"));
        INIT_BALL_X = 100;
        INIT_BALL_Y = 550;
        resetPos();
        friction = 0.05;
        acceleration = 1000;
    }


    public void speedUp(long age) {
            if (speedLimit < maxSpeedLimit) {
                double totalIncrease = maxSpeedLimit - startingSpeedLimit;
                double increasePerMilliSecond = totalIncrease / timeToReachMaxSpeed;
                speedLimit = startingSpeedLimit + age * increasePerMilliSecond;
            }
    }
    public void setStartingSpeedLimit(double startingSpeedLimit) { this.startingSpeedLimit = startingSpeedLimit; }
    public void setMaxSpeedLimit(double maxSpeedLimit) { this.maxSpeedLimit = maxSpeedLimit; }
    public void setSpeedLimit(double limit) { speedLimit = limit; }
    public void setTimeToReachMaxSpeed(long time) { timeToReachMaxSpeed = time; }
    public void turnGhosts(SmileyBall smiley) {
            if (facesRight && ! shouldFaceRight(smiley)) {
                facesRight = false;
                //face left:
                ImageIcon imageIcon = new ImageIcon("src/resources/ghost1_44x39_trans.png");
                image = imageIcon.getImage();
                imageWidth = image.getWidth(null);
                imageHeight = image.getHeight(null);
            } else if (!facesRight && shouldFaceRight(smiley)) {
                facesRight = true;
                //face right:
                ImageIcon imageIcon = new ImageIcon("src/resources/ghost1_44x39-converted_trans.png");
                image = imageIcon.getImage();
                imageWidth = image.getWidth(null);
                imageHeight = image.getHeight(null);
            }
    }
    private boolean shouldFaceRight(SmileyBall smiley) { return (smiley.getX() > x); }



}
