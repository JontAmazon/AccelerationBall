package AccelerationBall;

import javax.swing.*;

public class GhostBall extends Ball {
    private final double startingSpeedLimit = 0.5;
    private final double maxSpeedLimit = 0.90;
    private final double timeToReachMaxSpeed = 1000*120;
    private boolean facesRight = true;


    public GhostBall() {
        super(new ImageIcon("src/resources/ghost1_44x39-converted.png"));
        INIT_BALL_X = 100;
        INIT_BALL_Y = 550;
        resetPos();
        acceleration = 100;
        friction = 0.1;
        speedLimit = startingSpeedLimit;
    }


    public void speedUp(long age) {
            if (speedLimit < maxSpeedLimit) {
                double totalIncrease = maxSpeedLimit - startingSpeedLimit;
                double increasePerMilliSecond = totalIncrease / timeToReachMaxSpeed;
                speedLimit = startingSpeedLimit + age * increasePerMilliSecond;
            }
    }
    public void turnGhosts(SmileyBall smiley) {
            if (facesRight && ! shouldFaceRight(smiley)) {
                facesRight = false;
                //face left:
                ImageIcon imageIcon = new ImageIcon("src/resources/ghost1_44x39.png");
                image = imageIcon.getImage();
                imageWidth = image.getWidth(null);
                imageHeight = image.getHeight(null);
            } else if (!facesRight && shouldFaceRight(smiley)) {
                facesRight = true;
                //face right:
                ImageIcon imageIcon = new ImageIcon("src/resources/ghost1_44x39-converted.png");
                image = imageIcon.getImage();
                imageWidth = image.getWidth(null);
                imageHeight = image.getHeight(null);
            }
    }
    private boolean shouldFaceRight(SmileyBall smiley) { return (smiley.getX() > x); }



}
