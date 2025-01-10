package domain.agent;

import domain.entities.Entity;
import domain.util.Coordinate;
import domain.util.Direction;

public abstract class Agent extends Entity {

    // Agents need a lot of fields from the game
    // This fields may change later
    // depending on what we actually want;
    protected Direction direction;
    protected Coordinate location;
    protected final Direction[] DIRECTIONS = Direction.values();

    public abstract void move();

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = new Coordinate(location.getX(), location.getY());  // Create new reference
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}