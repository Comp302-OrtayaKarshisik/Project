package ui.Graphics.AgentGrapichs;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FighterGraphics extends MobilMonsterGraphics {

    public FighterGraphics(int size, int speed, int horizontalBound, int verticalBound) {
        super(size,speed,horizontalBound,verticalBound);
        getDefaultImages();
    }

    protected void getDefaultImages()   {
        try {
            leftPic = ImageIO.read(new File("src/assets/fighter4xLeft.png"));
            rightPic = ImageIO.read(new File("src/assets/fighter4xRight.png"));
            currentImg = rightPic;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
