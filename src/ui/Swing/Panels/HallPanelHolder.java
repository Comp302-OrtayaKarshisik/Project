package ui.Swing.Panels;


import domain.Textures;
import ui.Graphics.TileSetImageGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Wrapper for hall panel
// Offsets will be replaced with constants for better readabilty

public class HallPanelHolder extends JPanel {
    public final int tileSize = 48;
    public final int tileNumber = 16;
    private JPanel externalPanel;
    private boolean isDoorOpen = false;
    private int currentHall = 0;
    private boolean isBuildMode = true;

    public HallPanelHolder(JPanel externalPanel) {
        this.setPreferredSize(new Dimension(850, 850));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);

        externalPanel.setLocation(140, 164);
        externalPanel.setSize(externalPanel.getPreferredSize());

        externalPanel.setFocusable(true);
        externalPanel.requestFocusInWindow();
        externalPanel.setOpaque(false);

        this.add(externalPanel);

        this.externalPanel = externalPanel;
        // draw walls
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(isBuildMode) {
            drawBuildMode(g);
        }
        else {
            drawHallStructure(g);
        }
    }

    private void drawBuildMode(Graphics g) {
        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,850, 850, null);
        BufferedImage buildM = Textures.getSprite("buildmode");
        g.drawImage(buildM,0, 0,850, 850, null);
    }

    private void drawHallStructure(Graphics g) {

        BufferedImage horizontalWall = TileSetImageGetter.getInstance().getImage(0, 0);
        BufferedImage verticalWall = Textures.getSprite("verticalWall");

        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,850, 850, null);

        BufferedImage hall = Textures.getSprite("hearth");
        g.drawImage(hall, 119, 110, 618, 685, null);
        if(isDoorOpen) {
            BufferedImage doorOpen = Textures.getSprite("dooropen");
            this.drawDoorOpen(g, doorOpen);
        } else {
            BufferedImage door = Textures.getSprite("door");
            this.drawDoor(g, door);
        }

    }


    private void drawDoor(Graphics g, BufferedImage door){
        switch (currentHall) {
            case 1:
                g.drawImage(door, 180,727, 130, 51, null);
                break;
            case 2:
                g.drawImage(door, 180,727, 130, 51, null);
                break;
            case 3:
                g.drawImage(door, 180,727, 130, 51, null);
                break;
            default:
                g.drawImage(door, 180,724, 130, 52, null);
                break;
        }
    }

    private void drawDoorOpen(Graphics g, BufferedImage doorOpen) {
        g.drawImage(doorOpen, 462,789, tileSize, tileSize, null);
    }

    public void setDoorOpen(boolean isDoorOpen) {
        this.isDoorOpen = isDoorOpen;
        this.repaint();
    }

    public JPanel getExternalPanel() {
        return externalPanel;
    }

    public GamePanel getGamePanel() {
        return (GamePanel) this.externalPanel;
    }

}
