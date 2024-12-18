package domain.agent.monster;

import domain.Game;
import domain.agent.Player;
import domain.level.CollusionChecker;
import domain.util.Coordinate;
import domain.util.Direction;

public class Archer extends Monster {

    private int frame = 0;

    public Archer() {
        super();
    }

    public void move() {

        //Move after each 0.33 seconds
        if (frame != 20) {
            frame++;
            return;
        }
        frame = 0;

        int dir = Game.random.nextInt(4);

        if (dir == 0)
            setDirection(Direction.UP);
        else if (dir == 1)
            setDirection(Direction.DOWN);
        else if (dir == 2)
            setDirection(Direction.RIGHT);
        else
            setDirection(Direction.LEFT);


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

    public void shootArrow() {
        if (Coordinate.euclideanDistance(this.getLocation(),game.getPlayer().getLocation()) <= 4)
            game.getPlayer().reduceHealth();
    }
}
