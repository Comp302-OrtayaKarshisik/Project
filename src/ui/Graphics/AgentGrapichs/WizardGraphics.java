package ui.Graphics.AgentGrapichs;

import domain.agent.monster.Wizard;
import ui.Graphics.EntityGraphics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class WizardGraphics extends EntityGraphics {

    protected int size;
    protected BufferedImage img;
    private LinkedList<Wizard> wizards;

    public WizardGraphics(int size) {
        this.size = size;
        wizards = new LinkedList<>();
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
        for (Wizard w: wizards) {
            g.drawImage(img, w.getLocation().getX()*64, (15 - w.getLocation().getY())*64, size, size,null);
        }
    }

    public LinkedList<Wizard> getWizards() {
        return wizards;
    }
    public void setWizards(LinkedList<Wizard> wizards) {
        this.wizards = wizards;
    }
}
