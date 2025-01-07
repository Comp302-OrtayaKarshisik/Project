package domain.agent.monster;

import domain.Game;
import domain.level.CountDownTimer;
import domain.util.Coordinate;

public class RuneRelocationStrategy implements WizardBehaviorStrategy {
    private long lastTeleportTime = System.currentTimeMillis();

    @Override
    public void execute(Wizard wizard) {
        CountDownTimer timer = Game.getInstance().getDungeon().getCurrentHall().getTimer();
        float currentTimeRemaining = timer.getTimeRemaining();
        long currentSystemTime = System.currentTimeMillis();
        if (currentSystemTime - lastTeleportTime >= 3000) {  // 3000 ms = 3 seconds
            Game.getInstance().getDungeon().getCurrentHall().setNewRuneLocation();
            Coordinate newRuneLocation = Game.getInstance().getDungeon().getCurrentHall().getRuneLocation();
            System.out.println("Rune teleported to: (" + newRuneLocation.getX() + ", " + newRuneLocation.getY() + ")");
            lastTeleportTime = currentSystemTime;
        }
    }
}
