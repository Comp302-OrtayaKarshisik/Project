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
            Coordinate currentRuneLocation = Game.getInstance().getDungeon().getCurrentHall().getRuneLocation();
            Coordinate newRuneLocation;

            do {
                newRuneLocation=Game.getInstance().getDungeon().getCurrentHall().setNewRuneLocationstat();
            } while (newRuneLocation.equals(currentRuneLocation));  // Ensure it's different from the current location
            Game.getInstance().getDungeon().getCurrentHall().setSpecificRuneLocation(newRuneLocation);
            System.out.println("Rune teleported to: (" + newRuneLocation.getX() + ", " + newRuneLocation.getY() + ")");
            lastTeleportTime = currentSystemTime;
        }
    }
}
