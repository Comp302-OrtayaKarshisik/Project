package domain.agent.monster;

import domain.Game;
import domain.util.Coordinate;
import domain.util.Direction;

public class Archer extends Monster {

    private static final int ATTACK_RANGE = 4;
    private static final int MOVE_FRAME_LIMIT = 20;
    private static final int ATTACK_FRAME_LIMIT = 60;

    private int moveFrame;
    private int attackFrame;

    public Archer() {
        super();
        moveFrame = 0;
        attackFrame = ATTACK_FRAME_LIMIT;
    }

    // move method of the archer
    public void move() {

        shootArrow();

        //Move after each 0.33 seconds
        if (++moveFrame <= MOVE_FRAME_LIMIT) return;

        moveFrame = 0;

        Direction prev = getDirection();
        setDirection(DIRECTIONS[Game.random.nextInt(4)]);

        if (Game.getInstance().getDungeon().getCollisionChecker().validMove(this)) {
            switch (getDirection()) {
                case UP -> getLocation().setY(getLocation().getY() + 1);
                case DOWN -> getLocation().setY(getLocation().getY() - 1);
                case RIGHT -> getLocation().setX(getLocation().getX() + 1);
                case LEFT -> getLocation().setX(getLocation().getX() - 1);
            }
        } else {
            setDirection(prev);
        }
    }

    private void shootArrow() {
        if (Coordinate.euclideanDistance(this.getLocation(), Game.getInstance().getPlayer().getLocation()) <= ATTACK_RANGE &&
                !Game.getInstance().getPlayer().isInvisible() && attackFrame >= ATTACK_FRAME_LIMIT) {
            Game.getInstance().getPlayer().reduceHealth();
            attackFrame = 0;
        }
        attackFrame++;
    }

}
