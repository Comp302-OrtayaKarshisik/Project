package UI.Swing.Panels;

import UI.Graphics.EntityGraphics;

public class CollisionChecker {
	GamePanel gp;
	public CollisionChecker(GamePanel gp) {
		this.gp=gp;
	}
	public void checkTile(EntityGraphics entity) {
		int entityLeftWorldX = entity.xCord + entity.solid.x;
		int entityRightWorldX = entity.xCord + entity.solid.x + entity.solid.width;
		int entityTopWorldY = entity.yCord + entity.solid.y;
		int entityBottomWorldY = entity.yCord + entity.solid.y + entity.solid.height;

		int entityLeftCol = entityLeftWorldX / gp.baseTileSize;
		int entityRightCol = entityRightWorldX / gp.baseTileSize;
		int entityTopRow = entityTopWorldY / gp.baseTileSize;
		int entityBottomRow = entityBottomWorldY / gp.baseTileSize;

		int tileNum1, tileNum2;

		switch (entity.direction) {
		    case 'U':
		        entityTopRow = (entityTopWorldY - entity.speed) / gp.baseTileSize;
		        tileNum1 = gp.tileMa.mapTileNum[entityLeftCol][entityTopRow];
		        tileNum2 = gp.tileMa.mapTileNum[entityRightCol][entityTopRow];
		        if (gp.tileMa.tile[tileNum1].collision == true || gp.tileMa.tile[tileNum2].collision == true) {
		            entity.collisionON = true;
		        }
		        break;
		    case 'D':
		        entityBottomRow = (entityBottomWorldY + entity.speed) / gp.baseTileSize;
		        tileNum1 = gp.tileMa.mapTileNum[entityLeftCol][entityBottomRow];
		        tileNum2 = gp.tileMa.mapTileNum[entityRightCol][entityBottomRow];
		        if (gp.tileMa.tile[tileNum1].collision == true || gp.tileMa.tile[tileNum2].collision == true) {
		            entity.collisionON = true;
		        }
		        break;
		    case 'L':
		        entityLeftCol = (entityLeftWorldX - entity.speed) / gp.baseTileSize;
		        tileNum1 = gp.tileMa.mapTileNum[entityLeftCol][entityTopRow];
		        tileNum2 = gp.tileMa.mapTileNum[entityLeftCol][entityBottomRow];
		        if (gp.tileMa.tile[tileNum1].collision == true || gp.tileMa.tile[tileNum2].collision == true) {
		            entity.collisionON = true;
		        }
		        break;
		    case 'R':
		        entityRightCol = (entityRightWorldX + entity.speed) / gp.baseTileSize;
		        tileNum1 = gp.tileMa.mapTileNum[entityRightCol][entityTopRow];
		        tileNum2 = gp.tileMa.mapTileNum[entityRightCol][entityBottomRow];
		        if (gp.tileMa.tile[tileNum1].collision == true || gp.tileMa.tile[tileNum2].collision == true) {
		            entity.collisionON = true;
		        }
		        break;
		}
	}
}
