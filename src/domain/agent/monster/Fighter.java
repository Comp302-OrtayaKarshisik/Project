package domain.agent.monster;

import domain.Game;
import domain.agent.Player;
import domain.level.CollusionChecker;
import domain.util.Coordinate;
import domain.util.Direction;

public class Fighter extends Monster  {

    private boolean lured;
    private Coordinate lureLoc;
    private int frame = 0;

    public Fighter() {
        super();
        this.lured = false;
    }

    public void move() {

        hitPlayer();

        if (frame != 20) {
            frame++;
            return;
        }
        frame = 0;

        // Fighter already reached the lure location
        if (this.getLocation().equals(lureLoc))
            lured = false;

        if (!lured) {
            //not lured, in this section for now
            // monsters moves completly random
            // without any collusion check
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
        } else {
            // really primitive way to find the action,
            // wont work if the monster stuck between
            // two crates, one is left of the monster,
            // other one is blow
            for (Direction direction : Direction.values()) {
                setDirection(direction);

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
                break;
            }
        }
    }

    public void hitPlayer() {
        // If it is adjacent to player hit it
        // Ordering of these methods and other will matter.
        if (checkPlayerAdj(game.getPlayer()))
            game.getPlayer().reduceHealth();
    }

    public void lureUsed(Coordinate lureLoc) {
        this.lureLoc = lureLoc;
        lured = true;
    }

    private boolean checkPlayerAdj(Player player) {
        return (player.getLocation().getX() == this.getLocation().getX() && player.getLocation().getY() == this.getLocation().getY()) ||
                ((player.getLocation().getX() + 1) == this.getLocation().getX() && player.getLocation().getY() == this.getLocation().getY()) ||
                ((player.getLocation().getX() - 1) == this.getLocation().getX() && player.getLocation().getY() == this.getLocation().getY()) ||
                (player.getLocation().getX()  == this.getLocation().getX() && (player.getLocation().getY() + 1 == this.getLocation().getY())) ||
                (player.getLocation().getX()  == this.getLocation().getX() && (player.getLocation().getY() - 1  == this.getLocation().getY()));
    }

    public boolean isLured() {
        return lured;
    }
    public void setLured(boolean lured) {
        this.lured = lured;
    }

    public Coordinate getLureLoc() {
        return lureLoc;
    }
    public void setLureLoc(Coordinate lureLoc) {
        this.lureLoc = lureLoc;
    }
}
