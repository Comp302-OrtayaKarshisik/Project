package domain;


import controllers.KeyHandler;
import domain.agent.Agent;
import domain.agent.Player;
import domain.agent.monster.Monster;
import domain.collectables.Enchantment;
import domain.factories.EnchantmentFactory;
import domain.factories.MonsterFactory;
import domain.level.Dungeon;
import domain.level.GridDesign;
import domain.util.Coordinate;
import listeners.GameListener;
import ui.Graphics.AgentGrapichs.PlayerGraphics;
import ui.Graphics.ArrowGraphics;

import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game {

    private ExecutorService executor;
    private List<GameListener> listeners;
    public final static SecureRandom random = new SecureRandom();
    private static Game instance;
    private Player player;

    private volatile boolean paused;
    private KeyHandler keyHandler; // this field is for now

    private List<Agent> agents; // Holds set of agent monsters + players, removing and creating this may take some time
    private List<Enchantment> enchantments;
    private Dungeon dungeon;


    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    private Game() {
        Agent.setGame(this);

        executor =  Executors.newSingleThreadExecutor();
        player = new Player();

        dungeon = new Dungeon();
        listeners = new LinkedList<>();
        enchantments = new LinkedList<>();

        agents = new LinkedList<>();
        agents.add(player);
        paused = false;
    }

    public void startGame () {
        MonsterFactory.getInstance().newGame();
        EnchantmentFactory.getInstance().newGame();

        ArrowGraphics.getInstance(48).onNewGameEvent();
        //Executor runs the method instead of threads
        executor.execute(new Update());
    }

    // a method which will terminate everything in the game
    public void endGame() {
        //really important

        //NORMALLY GAME LOOP HAS TO TERMINATTE ITSELF
        dungeon.getCurrentHall().getTimer().pause();
        executor.shutdown();

        MonsterFactory.getInstance().gameOver();
        EnchantmentFactory.getInstance().gameOver();
        ArrowGraphics.getInstance(48).onGameOverEvent();

        instance = null;
    }

    // A method which will be used for the time passage of the game.
    public void update(){
        // really important fact is that player has to be the
        // first element of the given list
        for (Agent m : agents) {
            m.move();
        }

        for (Enchantment e : enchantments) {
            e.decreaseRemainingFrame();
        }
    }

    public void publishGameEvent() {
        for (GameListener gl : listeners)
            gl.onGameEvent(this);
    }

    public void initPlayMode(GridDesign[] gridDesigns) {
        dungeon.loadDesigns(gridDesigns);
        dungeon.getCurrentHall().getTimer().start();
    }

    public void nextHall() {
        dungeon.getCurrentHall().getTimer().pause(); //stop the timer of the previous hall.
        // should just end at this point
        if(dungeon.getCurrentHallIndex() == 3) {
            return;
        }

        dungeon.nextHall();
        dungeon.getCurrentHall().getTimer().start();
        //clears the agent problem
        agents.clear();
        agents.add(player);

        enchantments = new LinkedList<>();

        MonsterFactory.getInstance().publishNextHallEvent();
        EnchantmentFactory.getInstance().publishNextHallEvent();
    }

    public synchronized void togglePause() {
        paused = !paused;

        if (paused)
            pauseGame();
        else
            resumeGame();

        publishGameEvent(); // Notify listeners
    }

    public void pauseGame() {
        dungeon.getCurrentHall().getTimer().pause();
        MonsterFactory.getInstance().pauseCreation();
        EnchantmentFactory.getInstance().pauseCreation();
    }

    public void resumeGame() {
        dungeon.getCurrentHall().getTimer().start(); //start = resume
        MonsterFactory.getInstance().resumeCreation();
        EnchantmentFactory.getInstance().resumeCreation();
    }

    public void handleChosenBox(Player player, Coordinate c1) {
        dungeon.getCurrentHall().handleChosenBox(player,c1);
    }


    public void openHall() {}
    public void createVictoryScreen() {}
    public boolean isTimeExpired () {return false;}
    public boolean isPlayerDead() {return true;}
    public Object getObject() {return null;}

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
                        publishGameEvent();
                        diff = 0;
                    }
                }
            }
        }
    }

    public synchronized void removeMonster(Monster monster) {
        if (agents.contains(monster)) {
            agents.remove(monster);
            System.out.println("Monster removed: " + monster.getClass().getSimpleName());
        } else {
            System.out.println("Monster not found!");
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void addListener(GameListener gl) {
        listeners.add(gl);
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }

    public Player getPlayer() {
        return player;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public void setKeyHandler(KeyHandler keyHandler) {
        this.keyHandler = keyHandler;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public Dungeon getDungeon(){
        return dungeon;
    }

}
