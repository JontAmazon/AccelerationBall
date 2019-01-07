package AccelerationBall;

import javax.swing.*;

public class DevilBall extends Ball {
    private double normalAcceleration = 0.12;
    private double frenzyAcceleration = 0.12 * 1.3;

    private double normalBounce = 2;
    private double frenzyBounce = 2;
    private double orthogonalBounce = 2;

    private double parallelBounce = 1.18;

    private final long frenzyTime = 1000*5;
    private long frenzyBirthTime;
    private boolean isInFrenzy = false;


    public DevilBall() {
        super(new ImageIcon("src/resources/devil1_41x41.png"));
        INIT_BALL_X = 100;
        INIT_BALL_Y = 150;
        resetPos();
        acceleration = normalAcceleration;
        friction = 0.99;
        speedLimit = 10;
    }


    //Setters and getters:
    public long getFrenzyBirthTime() { return frenzyBirthTime; }
    public long getFrenzyTime() { return frenzyTime; }
    public void setNormalAcceleration(double acc) { normalAcceleration = acc; }
    public void setFrenzyAcceleration(double acc) { frenzyAcceleration = acc; }
    public void setNormalOrthogonalBounce(double bounce) { normalBounce = bounce; }
    public void setFrenzyBounce(double bounce) { frenzyBounce = bounce; }
    public void setParallelBounce(double bounce) { parallelBounce = bounce; }
    public void setFriction(double friction) { super.friction = friction; }

    public boolean isInFrenzy() { return isInFrenzy; }
    public void frenzy(boolean isMuted) {
        isInFrenzy = true;
        frenzyBirthTime = System.currentTimeMillis();
        orthogonalBounce = frenzyBounce;
        acceleration = frenzyAcceleration;

        ImageIcon ii = new ImageIcon("src/resources/devil_80x80.png");
        image = ii.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);

        if (! isMuted) {
            Game.playFrenzyAudio();
        }
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


    @Override
    public void move(SmileyBall smiley) {
        super.move(smiley);
        bounceIfAtWalls();
    }
    private void bounceIfAtWalls() {
        if (xPos < 0) {
            Board.playBounceAudio();
            xPos = 0;
            xdir *= -1;
            xdir *= orthogonalBounce;
            ydir *= parallelBounce;
        } else if (xPos > Game.WIDTH - imageWidth - 15) {
            Board.playBounceAudio();
            xPos = Game.WIDTH - imageWidth - 15;
            xdir *= -1;
            xdir *= orthogonalBounce;
            ydir *= parallelBounce;
        }
        if (yPos < 0) {
            Board.playBounceAudio();yPos = 0;
            ydir *= -1;
            xdir *= parallelBounce;
            ydir *= orthogonalBounce;
        } else if (yPos > Game.HEIGTH - imageHeight - 40) {
            Board.playBounceAudio();
            yPos = Game.HEIGTH - imageHeight - 40;
            ydir *= -1;
            xdir *= parallelBounce;
            ydir *= orthogonalBounce;
        }

    }


}





