package domain.level;

import domain.Game;
import domain.agent.Agent;
import domain.util.Coordinate;

import java.util.List;

public class CollusionChecker {
    private Game game;

    public CollusionChecker(Game game) {
        this.game = game;
    }

    // Checks whether movement is in the boundary
    // returns yes if the movement passes the boundary
    public boolean checkBoundary (Agent agent) {
        return switch (agent.getDirection()) {
            case UP -> agent.getLocation().getY() + 1 > 15;
            case DOWN -> agent.getLocation().getY() - 1 < 0;
            case LEFT -> agent.getLocation().getX() - 1 < 0;
            case RIGHT -> agent.getLocation().getX() + 1 > 15;
            case STOP -> false;
        };
    }
    // A really primitive checker for the tiles
    // with the located object
    public boolean checkTile (Agent agent) {
        Tile tile;
        return switch (agent.getDirection()) {
            case STOP -> {
                tile = game.getCurrentHall().getGrid()[agent.getLocation().getX()][agent.getLocation().getY()];
                yield tile.isCollisable();
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
    public boolean checkAgents(Agent agent, List<Agent> targets) {
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

        for (Agent target : targets) {
            if (!target.equals(agent) && target.getLocation().equals(c)) {
                System.out.println(target.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }
}
