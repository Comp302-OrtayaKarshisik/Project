package ui.windows;

import domain.TileManager;

import javax.swing.*;
import java.awt.*;

public class BuildPanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    TileManager tileM = new TileManager(this);
    Thread buildThread;


    public BuildPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);

    }

    public void startBuildThread() {
        buildThread = new Thread(this);
        buildThread.start();
    }
    @Override
    public void run() {
        while (buildThread != null) {

            //UPDATE:
            update();

            //DRAW:
            repaint();
        }
    }
    public void update() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.white);
        g2.fillRect(100, 100, tileSize, tileSize);

        g2.dispose(); //to free memory
    }
}
