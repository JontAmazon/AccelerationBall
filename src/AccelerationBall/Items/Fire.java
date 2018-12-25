package AccelerationBall.Items;

import AccelerationBall.Board;
import AccelerationBall.DevilBall;
import AccelerationBall.SmileyBall;

import javax.swing.*;

public class Fire extends Item {
    private boolean preStatus = true;

    public Fire() {
        super(new ImageIcon("src/resources/preFire_small.png"));
    }

    public boolean isInPreStatus() {
        return preStatus;
    }

    @Override
    public boolean shouldBeRemoved() {
        double age = getAge();
        changeImage(age);
        return (age > 7000);
    }
    private void changeImage(double age) {
        if (age > 2000 || preStatus) {
            preStatus = false;
            ImageIcon imageIcon = new ImageIcon("src/resources/fire1_small.png");
            image = imageIcon.getImage();
        }
    }

//    @Override
//    public void checkCollision(SmileyBall smiley, DevilBall devil) {
//        //TODO = nog to bort. Nog göra i Board.
//    }
}
