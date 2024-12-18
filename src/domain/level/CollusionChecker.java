package domain.level;

import domain.Game;
import domain.agent.Agent;
import domain.util.Coordinate;

import java.util.List;

public class CollusionChecker {
    // Again collusion checker may not need the game
    // Field.
    private final Game game;
    private static CollusionChecker instance;

    private CollusionChecker(Game game) {
        this.game = game;
    }

    public static CollusionChecker getInstance(Game game) {
        if (instance == null) {
            instance = new CollusionChecker(game);
        }
        return instance;
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
    public boolean checkTileCollusions (Agent agent) {
        Tile tile;
        return switch (agent.getDirection()) {
            case STOP -> {
                yield false;
            }
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
    public boolean checkAgentCollusions(Agent agent) {
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
