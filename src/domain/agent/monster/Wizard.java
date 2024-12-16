package domain.agent.monster;

import domain.Game;
import domain.util.Coordinate;

public class Wizard extends Monster{

    // A method which generates a random coordinate for the
    public void teleportRune(Coordinate runeLocation) {
        // This method just randomly changes the location of the rune without any constraints
        runeLocation.setLocation(Game.random.nextInt(16),Game.random.nextInt(16));
    }
}
