package Tile;
import Tile.tile;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import UI.Swing.Panels.GamePanel;

public class TileManager {
	public int mapTileNum[][];
	GamePanel gp;
	public tile[] tile;
	public TileManager(GamePanel gp) {
		this.gp=gp;
		tile=new tile[2];	
		mapTileNum=new int[gp.horizontalSquares][gp.verticalSquares];
		getTileImage();	
		mapTileNum[2][2] = 1;
	}
	public void getTileImage() {
		try {
			tile[0]=new tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("tile2.png"));
			tile[1]=new tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("tile3.png"));
			tile[1].collision=true;
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	public void draw(Graphics2D g2) {

		int col=0;
		int row=0;
		int x=0;
		int y=0;
		while(col< gp.horizontalSquares && row< gp.verticalSquares) {
			//for testing with a collision tile
			if(col==2 && row==2) {
				g2.drawImage(tile[1].image,x,y,gp.baseTileSize,gp.baseTileSize,null);

			}else {
				g2.drawImage(tile[0].image,x,y,gp.baseTileSize,gp.baseTileSize,null);

			}
			col++;
			x+= gp.baseTileSize;
			if(col==gp.horizontalSquares) {
				col=0;
				x=0;
				row++;
				y+=gp.baseTileSize;
			}
		
			
			
		}
	}
}
