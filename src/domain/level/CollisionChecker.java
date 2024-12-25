package domain.level;

import domain.Game;
import domain.agent.Agent;
import domain.util.Coordinate;

public class CollisionChecker {
    // Again collision checker may not need the game
    // Field.
    private final Game game;
    private static CollisionChecker instance;

    private CollisionChecker(Game game) {
        this.game = game;
    }

    public static CollisionChecker getInstance(Game game) {
        if (instance == null) {
            instance = new CollisionChecker(game);
        }
        return instance;
    }


    public boolean validMove(Agent agent) {
        return isInBoundary(agent) &&
                !checkTileCollusions(agent) &&
                !checkAgentCollusions(agent);
    }

    // Checks whether movement is in the boundary
    // returns true if the movement is in the boundary
    public boolean isInBoundary (Agent agent) {
        return switch (agent.getDirection()) {
            case UP -> agent.getLocation().getY() + 1 <= 15;
            case DOWN -> agent.getLocation().getY() - 1 >= 0;
            case LEFT -> agent.getLocation().getX() - 1 >= 0;
            case RIGHT -> agent.getLocation().getX() + 1 <= 15;
            case STOP -> true;
        };
    }

    // A really primitive checker for the tiles
    // with the located object
    public boolean checkTileCollisions (Agent agent) {
        Tile tile;
        return switch (agent.getDirection()) {
            case STOP -> false;
            case UP -> {
                tile = game.getCurrentHall().getGrid()[agent.getLocation().getX()][agent.getLocation().getY() + 1];
                yield tile.isCollisable();
            }
            case DOWN -> {
                tile = game.getCurrentHall().getGrid()[agent.getLocation().getX()][agent.getLocation().getY() - 1];
                yield tile.isCollisable();
            }
            case LEFT -> {
                tile = game.getCurrentHall().getGrid()[agent.getLocation().getX() - 1][agent.getLocation().getY()];
                yield tile.isCollisable();
            }
            case RIGHT -> {
                tile = game.getCurrentHall().getGrid()[agent.getLocation().getX() + 1][agent.getLocation().getY()];
                yield tile.isCollisable();
            }
        };
    }

    // The target list contains all the agents
    public boolean checkAgentCollisions(Agent agent) {
        Tile tile;
        Coordinate c;

        switch (agent.getDirection()) {
            case STOP -> c = new Coordinate(agent.getLocation().getX(),agent.getLocation().getY());
            case UP -> c = new Coordinate(agent.getLocation().getX(),agent.getLocation().getY() + 1);
            case DOWN -> c = new Coordinate(agent.getLocation().getX(),agent.getLocation().getY() - 1);
            case RIGHT -> c = new Coordinate(agent.getLocation().getX() + 1, agent.getLocation().getY());
            case LEFT -> c = new Coordinate(agent.getLocation().getX() - 1, agent.getLocation().getY());
            default -> c = agent.getLocation();
        }

        for (Agent target : game.getAgents()) {
            if (!target.equals(agent) && target.getLocation().equals(c)) {
                return true;
            }
        }
        return false;
    }

    public Game getGame() {
        return game;
    }
}
