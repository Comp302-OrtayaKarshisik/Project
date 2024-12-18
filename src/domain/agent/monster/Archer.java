package domain.agent.monster;

import domain.Game;
import domain.util.Coordinate;
import domain.util.Direction;

public class Archer extends Monster {

    private int frame = 0;

    public Archer() {
        super();
    }

    // move method of the archer
    public void move() {

        shootArrow();

        //Move after each 0.33 seconds
        if (frame != 20) {
            frame++;
            return;
        }

        frame = 0;

        setDirection(Direction.values()[Game.random.nextInt(4)]);

        if (game.getCollusionChecker().isInBoundary(this) &&
                !game.getCollusionChecker().checkTileCollusions(this) &&
                !game.getCollusionChecker().checkAgentCollusions(this)) {

            switch (getDirection()) {
                case UP -> getLocation().setY(getLocation().getY() + 1);
                case DOWN -> getLocation().setY(getLocation().getY() - 1);
                case RIGHT -> getLocation().setX(getLocation().getX() + 1);
                case LEFT -> getLocation().setX(getLocation().getX() - 1);
            }
        }
    }

    private void shootArrow() {
        if (Coordinate.euclideanDistance(this.getLocation(),game.getPlayer().getLocation()) <= 4)
            game.getPlayer().reduceHealth();
    }

}
