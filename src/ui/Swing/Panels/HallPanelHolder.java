package ui.Swing.Panels;

import controllers.BuildingModeHandler;
import domain.Textures;
import domain.level.GameHall;
import domain.objects.ObjectType;
import ui.Graphics.TileSetImageGetter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class HallPanelHolder extends JPanel {


    public final int tileSize = 48;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;

    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;




    public void drawEmptyHall(Graphics g) {
        BufferedImage floor = TileSetImageGetter.getInstance().getFloorImage();
        g.drawImage(floor,0, 0,maxScreenRow*tileSize, maxScreenCol*tileSize, null);
        // Draw walls
        BufferedImage horizontalWall = TileSetImageGetter.getInstance().getImage(0, 0);
        BufferedImage verticalWall = Textures.getSprite("verticalWall");
        drawHorizontalWalls(g, horizontalWall);
        drawVerticalWalls(g, verticalWall);
    }

    public void drawHorizontalWalls(Graphics g, BufferedImage horizontalWall) {
        int start = 20;
        for (int row = 0; row < maxScreenRow-1; row++){
            g.drawImage(horizontalWall,row*tileSize + start,10,tileSize,tileSize,null);
            g.drawImage(horizontalWall,row*tileSize + start,700,tileSize,tileSize,null);
        }
    }

    public void drawVerticalWalls(Graphics g, BufferedImage verticalWall) {
        int start = 29;
        for (int row = 0; row < (764/88)-1; row++){
            g.drawImage(verticalWall,21,row*88 + start, 7,88,null);
            g.drawImage(verticalWall,734,row*88 + start, 7,88,null);
        }
        g.drawImage(verticalWall,21,6*88+74 + start, 7,88,null);
        g.drawImage(verticalWall,734,6*88+74 + start, 7,88,null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Ensures the panel is properly rendered
        drawEmptyHall(g);
        GameHall currentHall = buildingModeHandler.getCurrentHall();
        if(currentHall != null) {
            drawPlacedObjects(g,currentHall);
        }
    }

    private void drawPlacedObjects(Graphics g, GameHall hall) {
        System.out.println("Drawing objects");
        ObjectType[][] grid = hall.getGrid();
        for(int row = 0; row < grid.length;row++) {
            for(int col = 0; col < grid[row].length; col++) {
                if(grid[row][col] != null) {
                    BufferedImage objectSprite = Textures.getSprite(grid[row][col].toString().toLowerCase());
                    g.drawImage(objectSprite, col*tileSize,row*tileSize,tileSize,tileSize,null);
                }
            }
        }
    }



}
