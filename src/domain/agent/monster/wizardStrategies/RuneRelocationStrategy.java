package domain.agent.monster.wizardStrategies;

import domain.Game;
import domain.agent.monster.Wizard;

import java.util.Timer;
import java.util.TimerTask;
public class RuneRelocationStrategy implements WizardBehaviorStrategy{
    private Timer timer;

    @Override
    public void execute(Wizard wizard) {
        System.out.println("Wizard: Making the game more challenging by moving the rune every 3 seconds.");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Game.getInstance().getDungeon().getCurrentHall().setNewRuneLocation();
                System.out.println("Rune moved to a new location!");
            }
        }, 0, 3000);  // Move every 3 seconds
    }

    public void stop() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
