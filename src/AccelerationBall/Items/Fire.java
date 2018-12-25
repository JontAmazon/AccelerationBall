package AccelerationBall.Items;

import javax.swing.*;

public class Fire extends Item {
    private boolean preStatus = true;
    private long preStatusChangeTime = 2000;


    public Fire() {
        super(new ImageIcon("src/resources/preFire.png"));
        lifeTime = 7000;
    }

    public boolean isInPreStatus() {
        return preStatus;
    }

    public long getPreStatusChangeTime() {
        return preStatusChangeTime;
    }

    public void changeImage() {
        preStatus = false;
        ImageIcon imageIcon = new ImageIcon("src/resources/fire3_225x225.png");
        image = imageIcon.getImage();
    }
}