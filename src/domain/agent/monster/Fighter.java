package domain.agent.monster;

import domain.Game;
import domain.agent.Player;
import domain.util.Coordinate;
import domain.util.Direction;

public class Fighter extends Monster  {

    private boolean lured;
    private Coordinate lureLoc;
    private Game game;


    public Fighter() {
        super();
        this.game = game;
        this.lured = false;
    }

    public void move() {
        // Fighter already reached the lure location
        if (this.getLocation().equals(lureLoc))
            lured = false;

        if (!lured) {
            //not lured, in this section for now
            // monsters moves completly random
            // without any collusion check
            int dir = Game.random.nextInt(4);

            if (dir == 0)
                setDirection(Direction.UP);
            else if (dir == 1)
                setDirection(Direction.DOWN);
            else if (dir == 2)
                setDirection(Direction.RIGHT);
            else
                setDirection(Direction.LEFT);


            if (!game.getCollusionChecker().checkTile(this) &&
                    !game.getCollusionChecker().checkTile(this) &&
                    !game.getCollusionChecker().checkAgents(this, game.getAgents())) {

                switch (getDirection()) {
                    case UP -> getLocation().setY(getLocation().getY() + 1);
                    case DOWN -> getLocation().setY(getLocation().getY() - 1);
                    case RIGHT -> getLocation().setX(getLocation().getX() + 1);
                    case LEFT -> getLocation().setX(getLocation().getX() - 1);
                }
            }
        } else {
            // fighter goes to the lure location
            // first goes from the x directen
            // then goes from the y direction
            if (this.getLocation().getX() != lureLoc.getX())
                if (lureLoc.getX() - this.getLocation().getX() > 0)
                    this.getLocation().setX(this.getLocation().getX() + 1);
                else
                    this.getLocation().setX(this.getLocation().getX() - 1);
            else
                if (lureLoc.getY() - this.getLocation().getY() > 0)
                    this.getLocation().setY(this.getLocation().getY() + 1);
                else
                    this.getLocation().setY(this.getLocation().getY() - 1);
        }
    }

    public void hitPlayer(Player player) {
        // If it is adjacent to player hit it
        // Ordering of these methods and other will matter.
        if (checkPlayerAdj(player))
            player.reduceHealth();
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
