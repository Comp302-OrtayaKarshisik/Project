package domain.agent.monster;

import domain.Game;
import java.util.Timer;
import java.util.TimerTask;
public class Wizard extends Monster{
    private WizardBehaviorStrategy currentBehavior;
    private Timer behaviorTimer;

    public Wizard() {
        behaviorTimer = new Timer(true);  // Run timer as a daemon thread
        startBehaviorMonitoring();
    }

    // This method monitors the game state and switches the strategy dynamically.
    private void startBehaviorMonitoring() {
        behaviorTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateBehavior();
            }
        }, 0, 1000);  // Check every second
    }

    // Continuously updates the behavior based on time remaining
    //I made public methods for getTimeRemaining and getTotal in game class because I dont have the time implementation yet
    public void updateBehavior() {
        double timeRemainingPercentage = (double) Game.getInstance().getTimeRemaining() / Game.getInstance().getTotalTime() * 100;

        if (timeRemainingPercentage < 30) {
            setBehavior(new PlayerRelocationStrategy());
        } else if (timeRemainingPercentage > 70) {
            setBehavior(new RuneRelocationStrategy());
        } else {
            setBehavior(new IdleStrategy());
        }
        currentBehavior.execute(this);
    }

    public void setBehavior(WizardBehaviorStrategy behavior) {
        if (currentBehavior instanceof RuneRelocationStrategy) {
            ((RuneRelocationStrategy) currentBehavior).stop();  // Stop the previous timer if RuneRelocationStrategy
        }
        this.currentBehavior = behavior;
    }

    public void disappear() {
        System.out.println("Wizard disappeared!");
        Game.getInstance().removeMonster(this);//!!!!!!!!!!!!!I did a remove method to game class pls check !!!!!!!!!!11
        behaviorTimer.cancel();  // Stop the behavior monitoring when the wizard disappears
    }
    @Override
    public void move() {}

}
