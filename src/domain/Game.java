package domain;


import controllers.KeyHandler;
import domain.agent.Agent;
import domain.agent.monster.Monster;
import domain.agent.Player;
import domain.factories.MonsterFactory;
import domain.entities.RegularObject;
import domain.level.CollusionChecker;
import domain.level.Hall;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

public class Game {

    public final static SecureRandom random = new SecureRandom();
	private static Game instance;
	private RegularObject[] objects;
    private final Player player;
    private Timer timer;
    private List<Monster> monsters;
    private Hall[] halls;

    private KeyHandler keyHandler; // this field is for now
    private CollusionChecker collusionChecker; // collusion checker of the game
    private List<Agent> agents; // Holds set of agent monsters + players, removing and creating this may take some time
    private Monster lastAddedMonster; // Holds the last added monster, to add it easily to the graphics
    private Hall currentHall;


    private Game() {
        player = new Player();
        timer = new Timer(); // no idea what will this do;
        //this.halls = halls;
        monsters = new LinkedList<>();
        //keyHandler = new KeyHandler();
        agents = new LinkedList<>();
        agents.add(player);
        lastAddedMonster = null;
        halls = new Hall[4];
        currentHall = halls[0];
        collusionChecker = CollusionChecker.getInstance(this);
        MonsterFactory mf = new MonsterFactory(this);
    }
    
    public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

    // No need for this method, game delegates its responsibility to
    // Monster factory.
    private void spawnMonster() {}

    // A method which will be used for the time passage of the game.
    public void update(){
        player.move();
        for (Monster m : monsters) {
            m.move();
        }
    }

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
    public Hall getCurrentHall() {
        return currentHall;
    }
    public void setCurrentHall(Hall currentHall) {
        this.currentHall = currentHall;
    }
    public KeyHandler getKeyHandler() {
        return keyHandler;
    }
    public void setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }
    public CollusionChecker getCollusionChecker() {
        return collusionChecker;
    }
    public void setCollusionChecker(CollusionChecker collusionChecker) {
        this.collusionChecker = collusionChecker;
    }
    public List<Agent> getAgents() {
        return agents;
    }
    public void setAgents(List<Agent> agents) {
        this.agents = agents;
    }
    public Monster getLastAddedMonster() {
        return lastAddedMonster;
    }
    public void setLastAddedMonster(Monster lastAddedMonster) {
        this.lastAddedMonster = lastAddedMonster;
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
