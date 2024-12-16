package domain.agent.monster;

import domain.Game;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// A class which creates random monsters
// every 8 seconds
public class MonsterFactory {

    private List<Monster> monsters;
    private final Timer timer;

    public MonsterFactory(){
        monsters = new LinkedList<>();
        timer = new Timer();
        timer.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        int monster = Game.random.nextInt(3);

                        switch (monster) {
                            case 0:
                                monsters.add(new Archer(Game.getInstance()));
                                break;
                            case 1:
                                monsters.add(new Fighter());
                                break;
                            case 2:
                                monsters.add(new Wizard());
                                break;
                        }
                    }
                },
                0,      // run first occurrence immediately
                8000);        // repeat with period of 8
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

}
