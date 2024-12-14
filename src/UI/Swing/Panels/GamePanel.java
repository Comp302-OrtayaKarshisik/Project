package UI.Swing.Panels;

import UI.EventHandler.KeyHandler;
import UI.Graphics.AgentGrapichs.ArcherGraphics;
import UI.Graphics.AgentGrapichs.FighterGraphics;
import UI.Graphics.AgentGrapichs.PlayerGraphics;
import UI.Graphics.AgentGrapichs.WizardGraphics;
import UI.Swing.Windows.pause;

import javax.swing.*;

import Tile.TileManager;

import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// In the next generations of the game KeyListener will
// be replaced by KeyBindings

// Also after receiving the horizontal and vertical squares, width heights
// and the bounds have to be final

// GameSettingsPanel will take GamePanel and will change
// FPS,ETC
public class GamePanel extends JPanel {

    // Screen settings each ca
    public final int  baseTileSize = 64; // Objects will be 64x64
    private int scalingFactor = 1; // Going to be an input for different resolutions etc

    public int horizontalSquares = 16; // how many squares in the x direction
    private int width = horizontalSquares * (scalingFactor * baseTileSize); // horizontal pixels
    private int horizontalBound = width - (scalingFactor*baseTileSize);

    public int verticalSquares = 16; // how many squares in the y direction
    private int height = verticalSquares * (scalingFactor * baseTileSize); // vertical pixels
    private int verticalBound = height - (scalingFactor*baseTileSize); //Lowest Pixel the player can go

    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;


    private int FPS = 60; // frames per second classic, can change but the default is 60
    TileManager tileMa=new TileManager(this);
    KeyHandler keyHandler = new KeyHandler(this);

    public CollisionChecker check=new CollisionChecker(this);
    private PlayerGraphics playerGraphics;
    private FighterGraphics fighterGraphics;
    private ArcherGraphics archerGraphics;
    private WizardGraphics wizardGraphics;

    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //Instead of drawing one by one, draw the imagine in the background first, then draw the entire image later
        this.addKeyListener(keyHandler); // Key listener to handle key presses
        this.setFocusable(true); // All eyes on me
        playerGraphics = new PlayerGraphics(this,baseTileSize, 16, keyHandler,horizontalBound,verticalBound);
        fighterGraphics = new FighterGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        archerGraphics = new ArcherGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        wizardGraphics = new WizardGraphics(baseTileSize);

        gameState = playState;
    }

    public GamePanel(int scalingFactor, int horizontalSquares, int verticalSquares, int FPS) {
        this(scalingFactor,horizontalSquares,verticalSquares);
        this.FPS = FPS;
    }

    public GamePanel(int scalingFactor, int horizontalSquares, int verticalSquares) {
        this.scalingFactor = scalingFactor;
        this.horizontalSquares = horizontalSquares;
        this.verticalSquares = verticalSquares;

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //Instead of drawing one by one, draw the imagine in the background first, then draw the entire image later
        this.addKeyListener(keyHandler); // Key listener to handle key presses
        this.setFocusable(true); // All eyes on me
        playerGraphics = new PlayerGraphics(this,baseTileSize, 16, keyHandler,horizontalBound,verticalBound);
        fighterGraphics = new FighterGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        archerGraphics = new ArcherGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        wizardGraphics = new WizardGraphics(baseTileSize);
    }

    public void startGame () {
        UpdateAndRender ur = new UpdateAndRender();
        //Executor runs the method instead of threads
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(ur);
    }

    private void update() {
        if (gameState == playState) {
            playerGraphics.update();
            fighterGraphics.update();
            archerGraphics.update();
        }
        // update also the other graphics in this place
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (gameState == playState) {
            tileMa.draw(g2);//for tile logic
            playerGraphics.draw(g2);
            fighterGraphics.draw(g2);
            archerGraphics.draw(g2);
            wizardGraphics.draw(g2);
        }else if(gameState == pauseState){
            drawPauseScreen(g2);
        }

        g2.dispose();
    }

    private void drawPauseScreen(Graphics2D g2) {
        // background
        g2.setColor(new Color(101, 67, 33));
        g2.fillRect(0, 0, width, height);

        // pause text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Gongster", Font.BOLD, 80));
        String pauseText = "Game Paused";
        int textWidth = g2.getFontMetrics().stringWidth(pauseText);
        g2.drawString(pauseText, (width - textWidth) / 2, height / 3);

        g2.setFont(new Font("Gongster", Font.BOLD, 50));
        String resumeText = "Press ESC to Resume";
        textWidth = g2.getFontMetrics().stringWidth(resumeText);
        g2.drawString(resumeText, (width - textWidth) / 2, height / 2);

        String helpText = "Press H for Help";
        textWidth = g2.getFontMetrics().stringWidth(helpText);
        g2.drawString(helpText, (width - textWidth) / 2, (height / 2) + 60);
    }

    private class UpdateAndRender implements Runnable {
        @Override
        public void run() {
            double currentTime;
            double frameInterval = (double) 1000000000 /FPS; // 1 billion nano second is equal to 1 secon, 1/FPS = diff between per frame
            double diff = 0; // represents the time passed between two consecutive frames
            double lastTime = System.nanoTime();

            //To break the loop, assign a boolen which becomes false
            //When ESC is pressed or when game is over
            //Handle in update method.
            //To restart the game just set boolen true and reexecuce
            while (true) {
                currentTime = System.nanoTime();
                diff += (currentTime - lastTime)/frameInterval;
                lastTime = System.nanoTime();


                if (diff >= 1) {
                    update();
                    repaint();
                    diff--;
                }
            }
        }
    }
}
