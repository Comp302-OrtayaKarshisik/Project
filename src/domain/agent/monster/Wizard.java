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
