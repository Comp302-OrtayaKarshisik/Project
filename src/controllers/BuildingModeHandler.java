package controllers;
import domain.level.GridDesign;
import domain.objects.ObjectType;
import domain.util.Coordinate;
import ui.BuildModePage;
import domain.*;
import ui.PageManager;

public class BuildingModeHandler {
	
	private Game game;

	private int gameHallCount = 5;

	private GridDesign[] gridDesigns = new GridDesign[gameHallCount];


	private int currentGameHall = 0;

	private ObjectType selectedObject;

	public BuildingModeHandler() {
		
		this.game = Game.getInstance();
		Textures.createSprites();
		for(int i = 0; i < gridDesigns.length; i++){
			gridDesigns[i] = new GridDesign(16,16,10);
		}

	}

	public GridDesign getCurrentHall(){
		return gridDesigns[currentGameHall];
	}

	public void setSelectedObject(ObjectType object) {
		this.selectedObject = object;
	}
	public ObjectType getSelectedObject(){
		return this.selectedObject;
	}

	public  boolean placeObjectAt(int row, int col) {
		if(selectedObject == null) return false;
		GridDesign currentHall = gridDesigns[currentGameHall];
        return currentHall.placeObject(row,col,selectedObject);
	}

	// Goes to next hall and returns if that hall is last or not.
	public boolean goNextHall() {
		GridDesign currentHall = gridDesigns[currentGameHall];
		if(!currentHall.isPlacementComplete())
		{
			System.out.println("You need to place at least " + (currentHall.getMinObjectLimit() - currentHall.getPlacedObjectCount()) + " more objects.");
			return false;
		}
		if(currentGameHall == gameHallCount - 2){
			currentGameHall += 1;
			System.out.println("Last hall.");
			return true;
		}
		if(currentGameHall == gameHallCount -1) {
			PageManager.getInstance().showPlayModePage(gridDesigns);
		}
		currentGameHall += 1;
		return false;
	}

	public int getCurrentGameHall() {
		return currentGameHall;
	}

	public boolean removeObjectAt(int row, int col){
		GridDesign currentHall = gridDesigns[currentGameHall];
		return currentHall.removeObject(row, col);
	}

	public boolean areAllHallsComplete() {
		for(GridDesign hall : gridDesigns){
			if(!hall.isPlacementComplete()){
				return false;
			}
		}
		return true;
	}



	public boolean isObjectPresent (int row, int col) {
		GridDesign currentHall = getCurrentHall();
		return currentHall.isObjectPresent(row,col);
	}

	/*
	
	public Coordinate placeObject(Coordinate mouseCoordinates, ObjectType type) {
		
		
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
