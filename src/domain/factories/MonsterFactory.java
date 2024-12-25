package domain.factories;

import domain.Game;
import domain.agent.monster.Archer;
import domain.agent.monster.Fighter;
import domain.agent.monster.Monster;
import domain.agent.monster.Wizard;
import listeners.FactoryListener;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// A class which creates random monsters
// every 8 seconds
public class MonsterFactory {

    private final Timer timer;
    private final List<FactoryListener> listeners;
    private static MonsterFactory instance;
    private long stopTime;

    public static MonsterFactory getInstance() {
        if (instance == null) {
            instance = new MonsterFactory();
        }
        return instance;
    }

    private MonsterFactory(){
        //Adds a reference to the monster list in the game
        listeners = new LinkedList<>();
        timer = new Timer();
        timer.scheduleAtFixedRate(new MonsterCreationTask(),50, 8000);        // repeat with period of 8
    }

    public void pauseCreation() {
        timer.cancel();
        stopTime = System.nanoTime();
    }

    public void resumeCreation () {
        long dt = (System.nanoTime() - stopTime)/1000;
        timer.scheduleAtFixedRate(new MonsterCreationTask(), dt, 8000);
    }

    public void addListener(FactoryListener fl) {
        listeners.add(fl);
    }

    public void publishCreationEvent(Monster monster) {
        for (FactoryListener fl: listeners)
            fl.onCreationEvent(monster);
    }

    public Timer getTimer() {
        return timer;
    }

    private class MonsterCreationTask extends TimerTask {
        @Override
        public void run() {
            int monster = Game.random.nextInt(3);
            Monster w = switch (monster) {
                case 0 -> new Archer();
                case 1 -> new Fighter();
                case 2 -> new Wizard();
                default -> null;
            };

            Game.getInstance().getMonsters().add(w);
            Game.getInstance().getAgents().add(w);
            publishCreationEvent(w);
        }
    }

}
