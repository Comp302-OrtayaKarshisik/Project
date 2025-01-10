package domain.agent.monster;

import domain.Game;
import domain.util.Coordinate;
import domain.level.CountDownTimer;
import ui.Graphics.AgentGrapichs.WizardGraphics;

public class Wizard extends Monster{
    private WizardBehaviorStrategy currentBehavior;

    public Wizard() {
        System.out.println("Wizard created.");
    }

    /**
     * Requires: The game must have a valid dungeon with an active timer and a valid current hall.
     * Modifies: This method modifies the current behavior of the wizard and may teleport the player or move the rune.
     * Effects:
     * - Chooses the behavior based on the remaining game time:
     *   - If time remaining is less than 30%, it teleports the player and makes the wizard disappear.
     *   - If time remaining is more than 70%, it moves the rune every 3 seconds.
     *   - If time remaining is between 30% and 70%, it makes the wizard stay idle and disappear after 2 seconds.
     */
    @Override
    public void move() {
        CountDownTimer timer = Game.getInstance().getDungeon().getCurrentHall().getTimer();

        double timeRemaining = timer.getTimeRemaining();
        double totalTime = timer.getInitialTimeRemaining();
        double timeRemainingPercentage = (timeRemaining / totalTime) * 100;

        if (timeRemainingPercentage < 30) {
            setBehavior(new PlayerRelocationStrategy());
        } else if (timeRemainingPercentage > 70) {
            if (currentBehavior instanceof RuneRelocationStrategy) {
            }else {
                setBehavior(new RuneRelocationStrategy());
            }
        } else {
            setBehavior(new IdleStrategy());
        }
        currentBehavior.execute(this);
    }

    public void setBehavior(WizardBehaviorStrategy behavior) {
        this.currentBehavior = behavior;
    }

    public void  disappear() {
        System.out.println("Wizard disappeared!");
        Game.getInstance().removeMonster(this);
        WizardGraphics.getInstance(48).onDeletionEventOne(this); //!!!!!!!!!!!!!I did a remove method to game class pls check !!!!!!!!!!11
    }



}
