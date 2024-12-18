package domain.agent.monster;

import domain.Game;
import domain.agent.Player;
import domain.util.Direction;

public class Archer extends Monster {

    private Game game;

    private int frame = 0;

    public Archer(Game game) {
        super();
        this.game =game;
    }

    public void move() {

        if (frame != 10) {
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


        if (!game.getCollusionChecker().checkBoundary(this) &&
                !game.getCollusionChecker().checkTile(this) &&
                !game.getCollusionChecker().checkAgents(this, game.getAgents())) {

            switch (getDirection()) {
                case UP -> getLocation().setY(getLocation().getY() + 1);
                case DOWN -> getLocation().setY(getLocation().getY() - 1);
                case RIGHT -> getLocation().setX(getLocation().getX() + 1);
                case LEFT -> getLocation().setX(getLocation().getX() - 1);
            }
        }
    }

    public void shootArrow(Player player) {

    }



    private boolean checkPlayerAdj(Player player) {
        return (player.getLocation().getX() == this.getLocation().getX() && player.getLocation().getY() == this.getLocation().getY()) ||
                ((player.getLocation().getX() + 4) == this.getLocation().getX() && player.getLocation().getY() == this.getLocation().getY()) ||
                ((player.getLocation().getX() - 4) == this.getLocation().getX() && player.getLocation().getY() == this.getLocation().getY()) ||
                (player.getLocation().getX()  == this.getLocation().getX() && (player.getLocation().getY() + 1 == this.getLocation().getY())) ||
                (player.getLocation().getX()  == this.getLocation().getX() && (player.getLocation().getY() - 1  == this.getLocation().getY()));
    }
}
