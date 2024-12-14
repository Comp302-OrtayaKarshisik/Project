package ui.Graphics.AgentGrapichs;

import controllers.KeyHandler;
import ui.Graphics.EntityGraphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerGraphics extends EntityGraphics {

    private final KeyHandler keyHandler;
    private BufferedImage rightPic, leftPic;
    private char direction = ' ';
    private int speed = 16;
    private final int size;
    private final int horizontalBound;
    private final int verticalBound;
    private BufferedImage currentImg;

    public PlayerGraphics(int size, int speed, KeyHandler keyHandler, int horizontalBound, int verticalBound) {
        this.size = size;
        this.speed = speed;
        this.keyHandler = keyHandler;
        this.horizontalBound = horizontalBound;
        this.verticalBound = verticalBound;
        getDefaultImages();
    }

    private void getDefaultImages()   {
        try {
            leftPic = ImageIO.read(new File("src/assets/player4xLeft.png"));
            rightPic = ImageIO.read(new File("src/assets/player4xRight.png"));
            currentImg = rightPic;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void update() {
        //This update only lets one key movement to be recorded at the same time
        //For diagonal movements switch else ifs to just ifs
        if (keyHandler.goDown)  {
            yCord += speed;
            if (yCord  >= verticalBound)
                yCord =  verticalBound;
        }
        else if (keyHandler.goUp)  {
            yCord -= speed;
            if (yCord <= 0)
                yCord = 0;
        }
        else if (keyHandler.goLeft)  {
            xCord -= speed;
            direction = 'L';
            if (xCord <= 0)
                xCord = 0;
        }
        else if (keyHandler.goRight)  {
            xCord += speed;
            direction = 'R';
            if (xCord >= horizontalBound)
                xCord = horizontalBound;
        }
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
