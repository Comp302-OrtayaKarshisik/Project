package domain.agent;

import domain.util.Coordinate;
import domain.util.Direction;

public abstract class Agent {

    private Direction direction;
    private Coordinate location;

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
