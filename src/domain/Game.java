package domain;





public class Game {
	
	private static Game instance;
	
	private Hall[] halls;

	private RegularObject[] objects; 
  	
	public Game() {
		
		//CREATE HALLS!!!
		
	}
	
	
	
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	/*
	
	public void placeObject(Coordinate c, ObjectType t) {
		
	}
	
	public void removeObject(Coordinate mouseCoordinates, ObjectType type) {
		
	}	
	
	public Tile[][] getGrid({
		
	} 
	
	public List<GameObject> getObjects() {
		
	}
	
	*/
	
}
