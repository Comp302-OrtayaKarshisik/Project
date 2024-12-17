package domain.level;

import domain.util.Coordinate;

import java.awt.image.BufferedImage;

public class Tile {
	
	private BufferedImage image;
	private boolean collision;
	private boolean collectible;

	private String name;
	private Coordinate coord;

	public Tile(String name, Coordinate coord) {
		
		this.name = name;
		this.coord = coord;
		
	}

	public Tile(String name, Coordinate coord, boolean collision){
		this(name, coord);
		this.collision = collision;
	}

	public String getName() {
		return name;
	}

	public boolean isCollisable (){
		return collision;
	}

	public void setCollision(boolean collision) {
		this.collision = collision;
	}
}
