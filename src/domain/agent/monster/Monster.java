package domain.agent.monster;

import domain.Game;
import domain.agent.Agent;
import domain.util.Coordinate;

public abstract class Monster extends Agent {

    public Monster() {
        super();
        super.setLocation(new Coordinate(Game.random.nextInt(16), Game.random.nextInt(16)));
    }

    public abstract void move();
}
