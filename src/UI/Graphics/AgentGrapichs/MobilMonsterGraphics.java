package UI.Graphics.AgentGrapichs;

import UI.Graphics.EntityGraphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

public class MobilMonsterGraphics extends EntityGraphics {

    protected  BufferedImage rightPic, leftPic;
    protected char direction = ' ';
    protected int speed = 16;
    protected int size;
    protected int horizontalBound;
    protected int verticalBound;
    protected BufferedImage currentImg;
    public static SecureRandom random = new SecureRandom();
    private int delta = 0; // Holds # frames since last move

    public MobilMonsterGraphics(int size, int speed, int horizontalBound, int verticalBound) {
        this.size = size;
        this.speed = 32;
        this.horizontalBound = horizontalBound;
        this.verticalBound = verticalBound;
        xCord = random.nextInt(50,700);
        yCord = random.nextInt(50,700);
    }

    public void update() {
        //This update only lets one key movement to be recorded at the same time
        //For diagonal movements switch else ifs to just ifs
        int dir = random.nextInt(4);

        if (delta == 4) {
            if (dir == 0) {
                yCord += speed;
                if (yCord >= verticalBound)
                    yCord = verticalBound;
            } else if (dir == 1) {
                yCord -= speed;
                if (yCord <= 0)
                    yCord = 0;
            } else if (dir == 2) {
                xCord -= speed;
                direction = 'L';
                if (xCord <= 0)
                    xCord = 0;
            } else {
                xCord += speed;
                direction = 'R';
                if (xCord >= horizontalBound)
                    xCord = horizontalBound;
            }
            delta = 0;
        }
        delta++;
    }

    public void draw(Graphics g) {
        if (direction == 'L') {
            currentImg = leftPic;
        } else if (direction == 'R') {
            currentImg = rightPic;
        }
        g.drawImage(currentImg, xCord, yCord, size, size,null);
    }

}
