package domain;


import domain.Agent.Monster.Monster;
import domain.Agent.Player;
import domain.Level.Hall;

import java.util.List;
import java.util.Timer;

public class Game {

	private static Game instance;
	private RegularObject[] objects; 
    private Player player;
    private Timer timer;
    private List<Monster> monsters;
    private Hall[] halls;
    
    
    public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

    public void spawnMonster() {}
    public void removeEnch() {}
    public void spawnEnch() {}
    public void endGame() {}
    public void pauseGame() {}
    public void resumeGame() {}
    public void handleCollectable() {}
    public void openHall() {}
    public void createVictoryScreen() {}
    public boolean isTimeExpired () {return false;}
    public boolean isPlayerDead() {return true;}
    public void handlePause() {}
    public void handleResume() {}
    public Object getObject() {return null;}

    public Hall[] getHalls() {
        return halls;
    }
    public void setHalls(Hall[] halls) {
        this.halls = halls;
    }
    public List<Monster> getMonsters() {
        return monsters;
    }
    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
    }
    public Timer getTimer() {
        return timer;
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }
    public Player getPlayer() {
        return player;
    }
    public void setPlayer(Player player) {
        this.player = player;
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
