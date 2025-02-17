package AccelerationBall.Items;

import javax.swing.*;

public class Fire extends Item {
    private boolean preStatus = true;
    private final long preStatusChangeTime = 1000*3;
    private boolean isInPreStatus1 = true;
    private boolean isInPreStatus2 = false;
    private boolean isInPreStatus3 = false;


    public Fire(long life) {
        super(new ImageIcon("src/resources/fire3_1rings_starz_trans.png"));
        lifeTime = life;
    }

    public boolean isInPreStatus() {
        if (isInPreStatus1 && getAge() > 1000) {
            ImageIcon imageIcon = new ImageIcon("src/resources/fire3_2rings_starz_trans.png");
            image = imageIcon.getImage();
            isInPreStatus1 = false;
            isInPreStatus2 = true;
        } else if (isInPreStatus2 && getAge() > 2000) {
            ImageIcon imageIcon = new ImageIcon("src/resources/fire3_3rings_starz_trans.png");
            image = imageIcon.getImage();
            isInPreStatus2 = false;
            isInPreStatus3 = true;
        }

        return preStatus;
    }

    public long getPreStatusChangeTime() {
        return preStatusChangeTime;
    }

    public void changeImage() {
        preStatus = false;
        ImageIcon imageIcon = new ImageIcon("src/resources/fire3_225x225_starz_trans.png");
        image = imageIcon.getImage();
    }
}