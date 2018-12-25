package AccelerationBall.Items;

import AccelerationBall.DevilBall;
import AccelerationBall.Game;
import AccelerationBall.SmileyBall;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public abstract class Item {
    protected long birthTime;
    protected long lifeTime;
    protected boolean consumed = false;

    protected Image image;
    protected int imageWidth;
    protected int imageHeight;
    private Random rand = new Random();
    protected int x;
    protected int y;


    public Item(ImageIcon imageIcon) {
        birthTime = System.currentTimeMillis();
        consumed = false;
        x = rand.nextInt(Game.WIDTH-90);
        y = rand.nextInt(Game.HEIGTH-90);

        image = imageIcon.getImage();
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }


    public Image getImage() {
        return image;
    }
    public int getImageWidth() {
        return imageWidth;
    }
    public int getImageHeight() {
        return imageHeight;
    }
    public boolean isConsumed() {
        return consumed;
    }
    public void consume() {
        consumed = true;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public long getAge() {
        return (System.currentTimeMillis() - birthTime);
    }
    public long getLife() { return lifeTime; }

    public Rectangle getRect() {
        return new Rectangle(x, y,
                image.getWidth(null), image.getHeight(null));
    }

}
