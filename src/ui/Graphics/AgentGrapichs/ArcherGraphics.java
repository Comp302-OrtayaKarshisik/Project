package ui.Graphics.AgentGrapichs;

import controllers.KeyHandler;
import domain.agent.monster.Archer;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class ArcherGraphics extends MobilMonsterGraphics {

    public ArcherGraphics(int size) {
        super(size);
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
