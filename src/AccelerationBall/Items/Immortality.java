package AccelerationBall.Items;

import javax.swing.*;

public class Immortality extends Item {

    public Immortality() {
        super(new ImageIcon("src/resources/flask_small.png"));
        lifeTime = 1000*1000;
    }
}

