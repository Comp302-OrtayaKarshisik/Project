package ui.Swing.Panels;

import controllers.KeyHandler;

import javax.swing.*;

import domain.Game;
import domain.agent.Agent;
import domain.agent.Player;
import domain.agent.monster.Archer;
import domain.agent.monster.Wizard;
import domain.level.CollusionChecker;
import domain.level.Hall;
import ui.Graphics.AgentGrapichs.ArcherGraphics;
import ui.Graphics.AgentGrapichs.FighterGraphics;
import ui.Graphics.AgentGrapichs.PlayerGraphics;
import ui.Graphics.AgentGrapichs.WizardGraphics;
import ui.Graphics.TileSetImageGetter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
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
    private final int  baseTileSize = 48; // Objects will be 64x64
    private int scalingFactor = 1; // Going to be an input for different resolutions etc

    private int horizontalSquares = 16; // how many squares in the x direction
    private int width = horizontalSquares * (scalingFactor * baseTileSize); // horizontal pixels

    private int verticalSquares = 16; // how many squares in the y direction
    private int height = verticalSquares * (scalingFactor * baseTileSize); // vertical pixels

    private int FPS = 60; // frames per second classic, can change but the default is 60

    KeyHandler keyHandler = new KeyHandler();

    private PlayerGraphics playerGraphics;
    //private FighterGraphics fighterGraphics;
    //private ArcherGraphics archerGraphics;
    //private WizardGraphics wizardGraphics;
    //private WizardGraphics wizardGraphics1;
    private Game game;



    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //Instead of drawing one by one, draw the imagine in the background first, then draw the entire image later
        this.addKeyListener(keyHandler); // Key listener to handle key presses
        this.setFocusable(true); // All eyes on me
        game = Game.getInstance();
        game.setPlayer(new Player(game));
        this.playerGraphics = new PlayerGraphics(48, game.getPlayer());
        game.setKeyHandler(keyHandler);


        LinkedList<Agent> a = new LinkedList<>();
        //a.add(w);
        //a.add(w1);
        a.add(game.getPlayer());

        game.setAgents(a);
        game.setCollusionChecker(new CollusionChecker(game));
        game.setCurrentHall(new Hall("s",0));

        //playerGraphics = new PlayerGraphics(baseTileSize, game.getPlayer());
        //wizardGraphics = new WizardGraphics(baseTileSize);
        //wizardGraphics.wizards.add(w);
        //wizardGraphics.wizards.add(w1);
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
        playerGraphics = new PlayerGraphics(baseTileSize, game.getPlayer());
        //ighterGraphics = new FighterGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        //archerGraphics = new ArcherGraphics(baseTileSize, 16,horizontalBound,verticalBound);
        //wizardGraphics = new WizardGraphics(baseTileSize);
    }

    public void startGame () {
        UpdateAndRender ur = new UpdateAndRender();
        //Executor runs the method instead of threads
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(ur);
    }

    private void update() {
        playerGraphics.update();

        // update also the other graphics in this place
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw empty hall
        this.initEmptyHall(g);

        Graphics2D g2 = (Graphics2D) g;
        playerGraphics.draw(g2);
        //wizardGraphics.draw(g2);
        g2.dispose();
    }

    // for drawing empty hall
    public void initEmptyHall(Graphics g) {
        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,48*16, 48*16, null);

        this.setBorder(BorderFactory.createLineBorder(new Color(40, 20, 30), 3));
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
