package UI.Graphics.AgentGrapichs;

import UI.Graphics.EntityGraphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;

public class WizardGraphics extends EntityGraphics {

    protected int size;
    protected BufferedImage img;
    public static SecureRandom random = new SecureRandom();

    public WizardGraphics(int size) {
        this.size = size;
        xCord = 64;
        yCord = 64;
        getDefaultImages();
    }

    protected void getDefaultImages()   {
        try {
            img = ImageIO.read(new File("src/assets/wizard4x.png"));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(img, xCord, yCord, size, size,null);
    }
}
