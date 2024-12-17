package controllers;
import domain.level.GameHall;
import domain.level.Hall;
import domain.objects.ObjectType;
import domain.util.Coordinate;
import ui.BuildModePage;
import domain.*;

public class BuildingModeHandler {
	
	private Game game;
	private final int gameHallCount = 4;
	private GameHall[] gameHalls = new GameHall[gameHallCount + 1];


	private int currentGameHall = 0;

	private ObjectType selectedObject;

	public BuildingModeHandler() {
		
		this.game = Game.getInstance();
		Textures.createSprites();
		for(int i = 0; i < gameHalls.length; i++){
			gameHalls[i] = new GameHall(16,16, 10);
		}

	}

	public GameHall getCurrentHall(){
		return gameHalls[currentGameHall];
	}

	public void setSelectedObject(ObjectType object) {
		this.selectedObject = object;
	}
	public ObjectType getSelectedObject(){
		return this.selectedObject;
	}

	public  boolean placeObjectAt(int row, int col) {
		if(selectedObject == null) return false;
		GameHall currentHall = gameHalls[currentGameHall];
        return currentHall.placeObject(row,col,selectedObject);
	}

	// Goes to next hall and returns if that hall is last or not.
	public boolean goNextHall() {
		GameHall currentHall = gameHalls[currentGameHall];
		if(!currentHall.isPlacementComplete())
		{
			System.out.println("You need to place at least " + (currentHall.getMinObjectLimit() - currentHall.getPlacedObjectCount()) + " more objects.");
			return false;
		}
		if(currentGameHall == gameHallCount - 1){
			currentGameHall += 1;
			System.out.println("Last hall.");
			return true;
		}
		currentGameHall += 1;
		return false;
	}

	public int getCurrentGameHall() {
		return currentGameHall;
	}


	public boolean areAllHallsComplete() {
		for(GameHall hall : gameHalls){
			if(!hall.isPlacementComplete()){
				return false;
			}
		}
		return true;
	}



	/*
	
	public Coordinate placeObject(Coordinate mouseCoordinates, ObjectType type) {
		
		
	}
	
	public void removeObject(Coordinate mouseCoordinates, ObjectType type) {
		
	}
	
	public boolean validatePlacement(Coordinate coord) {
		
	}
	
	public boolean isReady() {
		
	}
	
	public Hall getNextHall() {
		
	}
	
	public void initializePlayMode() {
		
	}*/
	
	
	
	
}
