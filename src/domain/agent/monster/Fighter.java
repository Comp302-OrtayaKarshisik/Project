package domain.agent.monster;

import domain.Game;
import domain.agent.Player;
import domain.util.Coordinate;
import domain.util.Direction;

public class Fighter extends Monster  {

    private static final int MOVE_FRAME_LIMIT = 20;
    private static final int ATTACK_FRAME_LIMIT = 60;

    private int moveFrame;
    private int attackFrame;
    private boolean lured;
    private Coordinate lureLoc;

    public Fighter() {
        super();
        setType("fighter");
        this.lured = false;
        moveFrame = 0;
        attackFrame = ATTACK_FRAME_LIMIT;
    }

    public void move() {

        hitPlayer();

        if (moveFrame != MOVE_FRAME_LIMIT) {
            moveFrame++;
            return;
        }

        moveFrame = 0;

        // Fighter already reached the lure location
        if (this.getLocation().equals(lureLoc))
            lured = false;

        if (!lured) {
            //not lured, in this section for now
            // monsters moves completly random
            // without any collusion check
            Direction prev = getDirection();
            setDirection(Direction.values()[Game.random.nextInt(4)]);

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

        } else {
            // really primitive way to find the action,
            // wont work if the monster stuck between
            // two crates, one is left of the monster,
            // other one is blow
            for (Direction direction : Direction.values()) {
                setDirection(direction);

                if (Game.getInstance().getDungeon().getCollisionChecker().validMove(this)) {
                    switch (getDirection()) {
                        case UP -> getLocation().setY(getLocation().getY() + 1);
                        case DOWN -> getLocation().setY(getLocation().getY() - 1);
                        case RIGHT -> getLocation().setX(getLocation().getX() + 1);
                        case LEFT -> getLocation().setX(getLocation().getX() - 1);
                    }
                }
                break;
            }
        }
    }

    public void hitPlayer() {
        // If it is adjacent to player hit it
        // Ordering of these methods and other will matter.
        if (checkPlayerAdj(Game.getInstance().getPlayer()) &&
                !Game.getInstance().getPlayer().isInvisible() && attackFrame >= ATTACK_FRAME_LIMIT) {
            Game.getInstance().getPlayer().reduceHealth();
            attackFrame = 0;
        }
        attackFrame++;
    }

    public void lureUsed(Coordinate lureLoc) {
        this.lureLoc = lureLoc;
        lured = true;
    }

    private boolean checkPlayerAdj(Player player) {
        int dx = Math.abs(player.getLocation().getX() - this.getLocation().getX());
        int dy = Math.abs(player.getLocation().getY() - this.getLocation().getY());

        return (dy == 0  && dx == 1) || (dy == 1 && dx == 0);
    }

}
