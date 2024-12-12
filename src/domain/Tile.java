package domain;

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
	
	
	
	
	

}
