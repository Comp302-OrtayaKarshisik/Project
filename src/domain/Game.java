package domain;


import controllers.KeyHandler;
import domain.agent.Agent;
import domain.agent.monster.Monster;
import domain.agent.Player;
import domain.entities.RegularObject;
import domain.factories.MonsterFactory;
import domain.level.CollisionChecker;
import domain.level.Hall;
import listeners.GameListener;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private ExecutorService executor;
    private final List<GameListener> listeners;
    public final static SecureRandom random = new SecureRandom();
	private static Game instance;
	private RegularObject[] objects;
    private final Player player;
    private Timer timer;
    private List<Monster> monsters;
    private Hall[] halls;
    private volatile boolean paused;
    private KeyHandler keyHandler; // this field is for now
    private CollisionChecker collisionChecker; // collusion checker of the game
    private final List<Agent> agents; // Holds set of agent monsters + players, removing and creating this may take some time
    private Hall currentHall;


    private Game() {
        executor =  Executors.newSingleThreadExecutor();
        player = new Player();
        timer = new Timer(); // no idea what will this do;
        //this.halls = halls;
        listeners = new LinkedList<>();
        monsters = new LinkedList<>();
        //keyHandler = new KeyHandler();
        agents = new LinkedList<>();
        agents.add(player);
        paused = false;
        halls = new Hall[4];
        currentHall = halls[0];
        collisionChecker = CollisionChecker.getInstance(this);
    }
    
    public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

    // A method which will be used for the time passage of the game.
    public void update(){
        player.move();
        for (Monster m : monsters) {
            m.move();
        }
    }

    public void pubishGameEvent() {
        for (GameListener gl : listeners)
            gl.onGameEvent(this);
    }

    public void startGame () {
        Update up = new Update();
        //Executor runs the method instead of threads
        executor.execute(up);
    }

    public synchronized void togglePause() {
        paused = !paused;

        if (paused)
            pauseGame();
        else
            resumeGame();

        pubishGameEvent(); // Notify listeners
    }

    public boolean isPaused() {
        return paused;
    }

    // maybe exac service is better
    public void pauseGame() {
       MonsterFactory.getInstance().pauseCreation();
    }

    public void resumeGame() {
        MonsterFactory.getInstance().resumeCreation();
    }


    public void removeEnch() {}
    public void spawnEnch() {}
    public void endGame() {}
    public void handleCollectable() {}
    public void openHall() {}
    public void createVictoryScreen() {}
    public boolean isTimeExpired () {return false;}
    public boolean isPlayerDead() {return true;}
    public Object getObject() {return null;}

    public void addListener(GameListener gl) {
        listeners.add(gl);
    }

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

    public CollisionChecker getCollisionChecker() {
        return collisionChecker;
    }
    
    public List<Agent> getAgents() {
        return agents;
    }

    private class Update implements Runnable {
        @Override
        public void run() {
            double currentTime;
            double frameInterval = (double) 1000000000 / 60; // 1 billion nano second is equal to 1 secon, 1/FPS = diff between per frame
            double diff = 0; // represents the time passed between two consecutive frames
            double lastTime = System.nanoTime();

            //To break the loop, assign a boolen which becomes false
            //When ESC is pressed or when game is over
            //Handle in update method.
            //To restart the game just set boolen true and reexecuce
            while (true) {
                if (!paused) {
                    currentTime = System.nanoTime();
                    diff += (currentTime - lastTime)/frameInterval;
                    lastTime = System.nanoTime();

                    if (diff >= 1) {
                        update();
                        pubishGameEvent();
                        diff = 0;
                    }
                }
            }
        }     
    }
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


