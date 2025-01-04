package domain.agent.monster;

import domain.Game;
import domain.util.Coordinate;

public class Wizard extends Monster{

    // A method which generates a random coordinate for the
    public void teleportRune() {
        // This method just randomly changes the location of the rune without any constraints
        Game.getInstance().getDungeon().getCurrentHall().setNewRuneLocation();
    }

    @Override
    public void move() {}

}
