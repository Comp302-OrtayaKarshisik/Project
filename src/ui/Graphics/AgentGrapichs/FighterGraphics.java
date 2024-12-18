package ui.Graphics.AgentGrapichs;

import controllers.KeyHandler;
import domain.Game;
import domain.agent.monster.Archer;
import domain.agent.monster.Fighter;
import domain.agent.monster.Monster;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FighterGraphics extends MobilMonsterGraphics {

    private static FighterGraphics instance;

    private FighterGraphics(int size) {
        super(size);
        getDefaultImages();
    }

    public static FighterGraphics getInstance(int size) {
        if (instance == null) {
            instance = new FighterGraphics(size);
        }
        return instance;
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
