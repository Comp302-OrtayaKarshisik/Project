package domain.agent.monster;

import domain.Game;
import domain.agent.Agent;
import domain.util.Coordinate;

import java.io.Serializable;

public abstract class Monster extends Agent implements Serializable {

    public Monster() {
        super();
        super.setLocation(new Coordinate(Game.random.nextInt(16), Game.random.nextInt(16)));
    }

}


