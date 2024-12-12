package ui.Graphics.AgentGrapichs;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ArcherGraphics extends MobilMonsterGraphics {

    public ArcherGraphics(int size, int speed, int horizontalBound, int verticalBound) {
        super(size,speed,horizontalBound,verticalBound);
        getDefaultImages();
    }

    protected void getDefaultImages()   {
        try {
            leftPic = ImageIO.read(new File("src/assets/archer4xLeft.png"));
            rightPic = ImageIO.read(new File("src/assets/archer4xRight.png"));
            currentImg = rightPic;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
