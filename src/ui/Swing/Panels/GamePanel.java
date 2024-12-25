package ui.Swing.Panels;

import controllers.KeyHandler;

import javax.swing.*;

import domain.Game;
import domain.Textures;

import domain.agent.monster.Fighter;
import domain.agent.monster.Wizard;
import domain.level.GridDesign;
import domain.level.Hall;
import domain.level.Tile;
import ui.Graphics.AgentGrapichs.ArcherGraphics;
import ui.Graphics.AgentGrapichs.FighterGraphics;
import ui.Graphics.AgentGrapichs.PlayerGraphics;
import ui.Graphics.AgentGrapichs.WizardGraphics;
import ui.Graphics.TileSetImageGetter;
import ui.PlayModePage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// In the next generations of the game KeyListener will
// be replaced by KeyBindings

// Also after receiving the horizontal and vertical squares, width heights
// and the bounds have to be final

// GameSettingsPanel will take GamePanel and will change
// FPS,ETC
public class GamePanel extends JPanel {

    // to incorporate grid designs from build mode
    private int currentHall = 0;
    private GridDesign[] gridDesigns;
    public int gameState;
    public final int playState = 1;
    public final int pauseState = 2;

    // to display hero's lives
    private PlayModePage playModePage;

    // Screen settings each ca
    private final int  baseTileSize = 48; // Objects will be 64x64
    private int scalingFactor = 1; // Going to be an input for different resolutions etc

    private int horizontalSquares = 16; // how many squares in the x direction
    private int width = horizontalSquares * (scalingFactor * baseTileSize); // horizontal pixels

    private int verticalSquares = 16; // how many squares in the y direction
    private int height = verticalSquares * (scalingFactor * baseTileSize); // vertical pixels

    private int FPS = 60; // frames per second classic, can change but the default is 60

    KeyHandler keyHandler = new KeyHandler(this);

    private final PlayerGraphics playerGraphics;
    private final FighterGraphics fighterGraphics;
    private final ArcherGraphics archerGraphics;
    private final WizardGraphics wizardGraphics;
    private Game game;



    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //Instead of drawing one by one, draw the imagine in the background first, then draw the entire image later
        this.addKeyListener(keyHandler); // Key listener to handle key presses
        this.setFocusable(true); // All eyes on me
        game = Game.getInstance();
        playerGraphics = PlayerGraphics.getInstance(48, game.getPlayer());
        fighterGraphics =  FighterGraphics.getInstance(48);
        archerGraphics = ArcherGraphics.getInstance(48);
        wizardGraphics = WizardGraphics.getInstance(48);
        gameState = playState;
        game.setCurrentHall(new Hall("s",0));
        game.setKeyHandler(keyHandler);
    }

    public GamePanel(GridDesign[] gridDesigns, PlayModePage playModePage) {
        this();
        this.gridDesigns = gridDesigns;
        this.playModePage = playModePage;

        if(gridDesigns != null) {
            this.game.getCurrentHall().transferGridDesign(gridDesigns[0]);
        }

    }

    /***
    public GamePanel(int scalingFactor, int horizontalSquares, int verticalSquares, int FPS) {
        this(scalingFactor,horizontalSquares,verticalSquares);
        this.FPS = FPS;
    } ***/

    /***
    public GamePanel(int scalingFactor, int horizontalSquares, int verticalSquares) {
        this.scalingFactor = scalingFactor;
        this.horizontalSquares = horizontalSquares;
        this.verticalSquares = verticalSquares;

        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //Instead of drawing one by one, draw the imagine in the background first, then draw the entire image later
        this.addKeyListener(keyHandler); // Key listener to handle key presses
        this.setFocusable(true); // All eyes on me
        playerGraphics = new PlayerGraphics(baseTileSize, game.getPlayer());
        //ighterGraphics = new FighterGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        //archerGraphics = new ArcherGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        //wizardGraphics = new WizardGraphics(baseTileSize);
    } ***/


    public void startGame () {
        UpdateAndRender ur = new UpdateAndRender();
        //Executor runs the method instead of threads
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(ur);
    }


    // In this method we will update for the new graphics etc
    private void update() {
    if(gameState == playState) {
        if (game.getLastAddedMonster() != null) {
            System.out.print(game.getLastAddedMonster().getClass().getSimpleName());
            if (game.getLastAddedMonster() instanceof Wizard)
                wizardGraphics.getWizards().add((Wizard) game.getLastAddedMonster());
            else if (game.getLastAddedMonster() instanceof Fighter)
                fighterGraphics.getMonsters().add(game.getLastAddedMonster());
            else
                archerGraphics.getMonsters().add(game.getLastAddedMonster());

            game.setLastAddedMonster(null);
        }

        game.update(); // Time passed through the game
        // update also the other graphics in this place
    }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == playState) {
            // draw empty hall
            this.initEmptyHall(g);

            // to display hero's lives
            playModePage.displayLives(game.getPlayer().getHealth());

            //draw objects from build mode
            drawObjects(g);

            playerGraphics.draw(g2);
            fighterGraphics.draw(g2);
            archerGraphics.draw(g2);
            wizardGraphics.draw(g2);
        }else if(gameState == pauseState){
            drawPauseScreen(g2);
        }
        g2.dispose();
    }

    // for drawing empty hall
    public void initEmptyHall(Graphics g) {
        this.setOpaque(false);
    }
    //Pause screen
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
    // for drawing hall object from build mode
    private void drawObjects(Graphics g) {
        Tile[][] grid = game.getCurrentHall().getGrid();
        for(int row = 0; row < grid.length;row++) {
            // grid size needs to change
            int verticalSize = 16;
            for(int col = 0; col < verticalSize; col++) {
                Tile gridObject = grid[row][col];
                if(gridObject != null && (gridObject.getName() == "COLUMN" || gridObject.getName() == "CHEST_FULL" || gridObject.getName() == "CHEST_FULL_GOLD" || gridObject.getName() == "CHEST_CLOSED")) {
                    BufferedImage objectSprite = Textures.getSprite(grid[row][col].getName().toLowerCase());
                    int w = objectSprite.getWidth();
                    int h = objectSprite.getHeight();
                    int offsetX = (baseTileSize - w) / 2;
                    int offsetY = (baseTileSize - h) / 2;
                    g.drawImage(objectSprite, row*baseTileSize+offsetX,(verticalSize-col-1)*baseTileSize+offsetY, w, h,null);
                }
            }
        }
    }

    // for drawing the rune
    private void drawRune(Graphics g) {
        BufferedImage runeSprite = Textures.getSprite("rune");
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
