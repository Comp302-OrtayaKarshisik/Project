package UI.Swing.Panels;

import UI.EventHandler.KeyHandler;

import javax.swing.*;
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
    private final int  baseTileSize = 64; // Objects will be 64x64
    private int scalingFactor = 1; // Going to be an input for different resolutions etc

    private int horizontalSquares = 16; // how many squares in the x direction
    private int width = horizontalSquares * (scalingFactor * baseTileSize); // horizontal pixels
    private int horizontalBound = width - (scalingFactor*baseTileSize);

    private int verticalSquares = 16; // how many squares in the y direction
    private int height = verticalSquares * (scalingFactor * baseTileSize); // vertical pixels
    private int verticalBound = height - (scalingFactor*baseTileSize); //Lowest Pixel the player can go

    private int FPS = 60; // frames per second classic, can change but the default is 60

    KeyHandler keyHandler = new KeyHandler();

    private int x = 0;
    private int y = 0;
    private int speed = 16;


    public GamePanel() {
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); //Instead of drawing one by one, draw the imagine in the background first, then draw the entire image later
        this.addKeyListener(keyHandler); // Key listener to handle key presses
        this.setFocusable(true); // All eyes on me
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
    }

    public void startGame () {
        UpdateAndRender ur = new UpdateAndRender();
        //Executor runs the method instead of threads
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(ur);
    }

    private void update() {
        //This update only lets one key movement to be recorded at the same time
        //For diagonal movements switch else ifs to just ifs
        if (keyHandler.goDown)  {
            y += speed;
            if (y  >= verticalBound)
                y =  verticalBound;
        }
        else if (keyHandler.goUp)  {
            y -= speed;
            if (y <= 0)
                y = 0;
        }
        else if (keyHandler.goLeft)  {
            x -= speed;
            if (x <= 0)
                x = 0;
        }
        else if (keyHandler.goRight)  {
            x += speed;
            if (x >= horizontalBound)
                x = horizontalBound;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(x,y,64,64);
        g2.dispose();
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
