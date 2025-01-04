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

    public HallPanelHolder(JPanel externalPanel) {
        this.setPreferredSize(new Dimension(850, 850));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setLayout(null);

        externalPanel.setLocation(39, 41);
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
        drawHallStructure(g);
    }

    private void drawHallStructure(Graphics g) {
        
        BufferedImage horizontalWall = TileSetImageGetter.getInstance().getImage(0, 0);
        BufferedImage verticalWall = Textures.getSprite("verticalWall");

        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,850, 850, null);


        drawHorizontalWalls(g, horizontalWall);
        drawVerticalWalls(g, verticalWall);

        if(isDoorOpen) {
            BufferedImage doorOpen = Textures.getSprite("dooropen");
            this.drawDoorOpen(g, doorOpen);
        } else {
            BufferedImage door = Textures.getSprite("door");
            this.drawDoor(g, door);
        }

    }

    public void drawHorizontalWalls(Graphics g, BufferedImage horizontalWall) {
        int start = 30;
        for (int row = 0; row < tileNumber; row++){
            g.drawImage(horizontalWall,row*tileSize + start,-5,tileSize,tileSize,null);
            if(row != 9){
                g.drawImage(horizontalWall,row*tileSize + start,789,tileSize,tileSize,null);
            }
        }
        int offset = start + 15;
        g.drawImage(horizontalWall,(tileNumber -1)*tileSize + offset,-5,tileSize,tileSize,null);
        g.drawImage(horizontalWall,(tileNumber-1)*tileSize + offset,789,tileSize,tileSize,null);
    }

    public void drawVerticalWalls(Graphics g, BufferedImage verticalWall) {
        int start = 15;
        for (int col = 0; col < (764/88); col++){
            g.drawImage(verticalWall,31,col*88 + start, 6,88,null);
            g.drawImage(verticalWall,807,col*88 + start, 6,88,null);
        }
        int offset = start + 20;
        g.drawImage(verticalWall,31,7*88+70 + offset, 6,88,null);
        g.drawImage(verticalWall,807,7*88+70 + offset, 6,88,null);
    }

    private void drawDoor(Graphics g, BufferedImage door){
        g.drawImage(door, 462,789, tileSize+50, tileSize, null);
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
