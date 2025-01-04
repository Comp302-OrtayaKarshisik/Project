package domain;


import controllers.KeyHandler;
import domain.agent.Agent;
import domain.agent.monster.Monster;
import domain.agent.Player;
import domain.collectables.Enchantment;
import domain.entities.RegularObject;
import domain.factories.EnchantmentFactory;
import domain.factories.MonsterFactory;
import domain.level.CollisionChecker;
import domain.level.GridDesign;
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
    private int currentHall;
    private List<Enchantment> enchantments;
    private GridDesign[] gridDesigns;


    private Game() {
        executor =  Executors.newSingleThreadExecutor();
        player = new Player();
        timer = new Timer(); // no idea what will this do;
        //this.halls = halls;
        listeners = new LinkedList<>();
        monsters = new LinkedList<>();
        enchantments = new LinkedList<>();
        //keyHandler = new KeyHandler();
        agents = new LinkedList<>();
        agents.add(player);
        paused = false;
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
        for (Enchantment e : enchantments) {
            e.decreaseRemainingFrame();
        }
    }

    public void pubishGameEvent() {
        for (GameListener gl : listeners)
            gl.onGameEvent(this);
    }

    public void initPlayMode(GridDesign[] gridDesigns) {
        this.gridDesigns = gridDesigns;
        int placedObjectCount = gridDesigns[0].getPlacedObjectCount();

        halls = new Hall[4];
        halls[0] = new Hall(null, placedObjectCount);
        halls[0].transferGridDesign(gridDesigns[0]);

        currentHall = 0;
    }

    public void nextHall() {
        if(currentHall == 3) {
            return;
        }
        currentHall++;
        halls[currentHall] = new Hall("a", gridDesigns[currentHall].getPlacedObjectCount());
        halls[currentHall].transferGridDesign(gridDesigns[currentHall]);

        monsters = new LinkedList<>();
        enchantments = new LinkedList<>();

        MonsterFactory.getInstance().publishNextHallEvent();
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
        EnchantmentFactory.getInstance().pauseCreation();
    }

    public void resumeGame() {
        MonsterFactory.getInstance().resumeCreation();
        EnchantmentFactory.getInstance().resumeCreation();
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

    public List<Enchantment> getEnchantments() {
        return enchantments;
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
        return halls[currentHall];
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
    public double getTimeRemaining(){return 0;}
    public double getTotalTime(){return 0;}
    public synchronized void removeMonster(Monster monster) {
        if (monsters.contains(monster)) {
            monsters.remove(monster);
            agents.remove(monster);
            System.out.println("Monster removed: " + monster.getClass().getSimpleName());
        } else {
            System.out.println("Monster not found!");
        }
    }
    private class Update implements Runnable {
        @Override
        public void run() {
            double currentTime;
            double frameInterval = (double) 1000000000 / 24; // 1 billion nano second is equal to 1 secon, 1/FPS = diff between per frame
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