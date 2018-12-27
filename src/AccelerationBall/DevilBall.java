package AccelerationBall;

import javax.swing.*;

public class DevilBall extends Ball {
    //Ghost attributes:
    private boolean facesRight = true;

    //Direction of acceleration:
    private double xDist;
    private double yDist;
    private double distance;
    private double xQuota;
    private double yQuota;

    //Devil parameters:
    private double acceleration = 0.115;
    private final double normalAcceleration = 0.115;
    private final double frenzyAcceleration = 0.115 * 1.3;
    private double orthogonalBounce = 2;
    private final double normalBounce = 2;
    private final double frenzyBounce = 2;
    private long frenzyBirthTime;
    private final long frenzyTime = 1000*8;
    private boolean isInFrenzy = false;

    private double parallelBounce = 1.18;
    private double friction = 0.991;
    private double speedLimit = 35;
    //Ghost parameters:
    private static final double startingSpeedLimit = 0.5;
    public static double getStartingSpeedLimit() { return startingSpeedLimit; }
    private final double linearSpeedTimeFactor = 0.000008;
    private final double maxSpeedLimit = 0.90;
    private final double timeToReachMaxSpeed = 1000*120;
//    private final double logarithmicSpeedTimeFactor = 0.000030;
//    private final double logarithm = 2;


    public DevilBall(ImageIcon imageIcon, double acceleration, double friction,
                     double speedLimit, double orthogonalBounce, double parallelBounce,
                     int INIT_X, int INIT_Y) {
        this(imageIcon);
        this.acceleration = acceleration;
        this.friction = friction;
        this.speedLimit = speedLimit;
        this.orthogonalBounce = orthogonalBounce;
        this.parallelBounce = parallelBounce;
        INIT_BALL_X = INIT_X;
        INIT_BALL_Y = INIT_Y;
        resetPos();
    }
    public DevilBall(ImageIcon imageIcon) {
        super(imageIcon);
        INIT_BALL_X = 100;
        INIT_BALL_Y = 350;
        resetPos();
    }


    public void move(SmileyBall smiley, double time) {
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
        super.move();
        bounceIfAtWalls(time);
    }
    private void bounceIfAtWalls(double time) {
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


    //Devil methods:
    public long getFrenzyBirthTime() { return frenzyBirthTime; }
    public long getFrenzyTime() { return frenzyTime; }
    public boolean isInFrenzy() { return isInFrenzy; }
    public void frenzy() {
        isInFrenzy = true;
        frenzyBirthTime = System.currentTimeMillis();
        orthogonalBounce = frenzyBounce;
        acceleration = frenzyAcceleration;

        ImageIcon ii = new ImageIcon("src/resources/devil_80x80.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
    public void stopFrenzy() {
        isInFrenzy = false;
        orthogonalBounce = normalBounce;
        acceleration = normalAcceleration;

        ImageIcon ii = new ImageIcon("src/resources/devil1_41x41.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }


    //Ghost methods:
    private void restrictSpeed() { //EV TODO = nu rör sig spöket som mig:
        // lika snabbt i X och Y-led. EV Ändra.
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
    public void speedUp(long age) {
        if (isGhost()) {
            if (speedLimit < maxSpeedLimit) {
                double totalIncrease = maxSpeedLimit - startingSpeedLimit;
                double increasePerMilliSecond = totalIncrease / timeToReachMaxSpeed;
                speedLimit = startingSpeedLimit + age * increasePerMilliSecond;

                //Logarithmic:
                //speedLimit += (seconds * logarithmicSpeedTimeFactor * Math.log(age) / Math.log(logarithm));
            }
        }
    }
    public void turnGhosts(SmileyBall smiley) {
        if (isGhost()) {
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
    }
    private boolean shouldFaceRight(SmileyBall smiley) { return (smiley.getX() > x); }


    //Item related:
    public void enlargen() {
        if (isDevil()) {
            isEnlarged = true;
            enlargedBirthTime = System.currentTimeMillis();
            ImageIcon ii = new ImageIcon("src/resources/devil_80x80.png");
            image = ii.getImage();
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
        } else if (isGhost()) {
            //nothing
        }
    }
    public void reduce() {
        isEnlarged = false;
        if (isDevil()) {
            ImageIcon ii = new ImageIcon("src/resources/devil1_41x41.png");
            image = ii.getImage();
            imageWidth = image.getWidth(null);
            imageHeight = image.getHeight(null);
        } else if (isGhost()) {
            //nothing.
        }
    }
    public boolean isDevil() { return (acceleration < 0.49); }
    public boolean isGhost() { return (acceleration > 0.49); }


}





