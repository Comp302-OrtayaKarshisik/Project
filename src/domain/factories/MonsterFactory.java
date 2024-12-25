package domain.factories;

import domain.Game;
import domain.agent.monster.Archer;
import domain.agent.monster.Fighter;
import domain.agent.monster.Wizard;

import java.util.Timer;
import java.util.TimerTask;

// A class which creates random monsters
// every 8 seconds
public class MonsterFactory {

    private final Timer timer;
    private final Game game;

    public MonsterFactory(Game game){
        //Adds a reference to the monster list in the game

        timer = new Timer();
        this.game = game;
        timer.scheduleAtFixedRate(
                new TimerTask()
                {
                    public void run()
                    {
                        int monster = Game.random.nextInt(3);

                        switch (monster) {
                            case 0:
                                Archer a = new Archer();
                                game.getMonsters().add(a);
                                game.getAgents().add(a);
                                game.setLastAddedMonster(a);
                                break;
                            case 1:
                                Fighter f = new Fighter();
                                game.getMonsters().add(f);
                                game.getAgents().add(f);
                                game.setLastAddedMonster(f);
                                break;
                            case 2:
                                Wizard w = new Wizard();
                                game.getMonsters().add(w);
                                game.getAgents().add(w);
                                game.setLastAddedMonster(w);
                                break;
                        }
                    }
                },
                0,      // run first occurrence immediately
                8000);        // repeat with period of 8
    }

    public Timer getTimer() {
        return timer;
    }

}
