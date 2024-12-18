package domain.agent;

import domain.Game;
import domain.util.Coordinate;
import domain.util.Direction;

public abstract class Agent {

    // Agents need a lot of fields from the game
    // This fields may change later
    // depending on what we actually want;
    protected static Game game;
    private Direction direction;
    private Coordinate location;


    public static Game getGame() {
        return game;
    }
    public static void setGame(Game game) {
        Agent.game = game;
    }

    public Coordinate getLocation() {
        return location;
    }
    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
