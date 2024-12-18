package ui.Graphics.AgentGrapichs;

import domain.agent.Player;
import domain.util.Direction;
import ui.Graphics.EntityGraphics;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerGraphics extends EntityGraphics {

    private static PlayerGraphics instance;
    private BufferedImage rightPic, leftPic;
    private final int size;
    private BufferedImage currentImg;
    private final Player player;

    private PlayerGraphics(int size, Player player) {
        this.size = size;
        this.player = player;
        getDefaultImages();
    }

    public static PlayerGraphics getInstance(int size, Player player) {
        if (instance == null) {
            instance = new PlayerGraphics(size,player);
        }
        return instance;
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
        player.move();
    }

    public void draw(Graphics g) {
        if (player.getDirection() == Direction.LEFT) {
            currentImg = leftPic;
        } else if (player.getDirection() == Direction.RIGHT) {
            currentImg = rightPic;
        }
        g.drawImage(currentImg, player.getLocation().getX() * size, (15 - player.getLocation().getY()) * size, size, size,null);
    }
}
