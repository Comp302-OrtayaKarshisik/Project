package UI.Graphics.AgentGrapichs;

import UI.EventHandler.KeyHandler;
import UI.Graphics.EntityGraphics;
import UI.Swing.Panels.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PlayerGraphics extends EntityGraphics {

    private final KeyHandler keyHandler;
    GamePanel gp;
    private BufferedImage rightPic, leftPic;
    private char direction = ' ';
    private int speed = 16;
    private final int size;
    private final int horizontalBound;
    private final int verticalBound;
    private BufferedImage currentImg;

    public PlayerGraphics(GamePanel gp,int size, int speed, KeyHandler keyHandler, int horizontalBound, int verticalBound) {
    	this.gp=gp;
    	this.size = size;
       
        this.speed = speed;
        this.keyHandler = keyHandler;
        this.horizontalBound = horizontalBound;
        this.verticalBound = verticalBound;
        
        solid=new Rectangle(8,16,32,32);
        getDefaultImages();
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
        if (keyHandler.goDown)  {
        	direction='D';
           // yCord += speed;
           
        }
        else if (keyHandler.goUp)  {
        	direction='U';
            //yCord -= speed;
           
        }
        else if (keyHandler.goLeft)  {
           // xCord -= speed;
            direction = 'L';
         
        }
        else if (keyHandler.goRight)  {
           // xCord += speed;
            direction = 'R';
          
        }else {
            // No key is pressed, stop movement
            direction = ' ';
        }
        
        collisionON=false;
        gp.check.checkTile(this);
        
        if(collisionON==false) {

    		switch (direction) {
    		    case 'U':
    		    	yCord -= speed;
    		    	 if (yCord <= 0)
    		                yCord = 0;
    		        break;
    		    case 'D':
    		    	yCord += speed;
    		    	 if (yCord  >= verticalBound)
    		                yCord =  verticalBound;
    		        break;
    		    case 'L':
    		    	 xCord -= speed;
    		    	   if (xCord <= 0)
    		                xCord = 0;
    		        break;
    		    case 'R':
    		    	xCord += speed;
    		    	  if (xCord >= horizontalBound)
    		                xCord = horizontalBound;
    		        break;
    		}
        }
    }

    public void draw(Graphics g) {
        if (direction == 'L') {
            currentImg = leftPic;
        } else if (direction == 'R') {
            currentImg = rightPic;
        }
        g.drawImage(currentImg, xCord, yCord, size, size,null);

    }
}
