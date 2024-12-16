package domain.level;

import domain.Game;
import domain.agent.Player;
import domain.collectables.EnchantmentType;
import domain.collectables.Enchantment;
import domain.util.Coordinate;

import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

public class Hall {

    private Timer timer; // this timer is responsible for creating enchantments
    private final String type;
    private final Coordinate runeLocation;
    private List<Enchantment> enchantments;
    private final int objectCapacity;



    private Tile[][] grid;

    public Hall(String type, int objectCapacity) {
        timer = new Timer();
        this.type = type;
        this.runeLocation = new Coordinate(Game.random.nextInt(64),Game.random.nextInt(64));
        this.enchantments = new LinkedList<>();
        this.objectCapacity = objectCapacity;
        this.grid = new Tile[64][64];
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j <16; j++) {
                grid[i][j] = new Tile("aa",new Coordinate(i,j));
            }
        }
    }

    public void update (Player player, HashMap<String,String > p) {}
    public EnchantmentType type(int [][] xy) {return null;}
    public void increaseTime() {}
    public String typeOfCollectable() {return null;}
    public void higlightRune() {}
    public void removeHiglight() {}
    public void throwLure(Key key) {}
    public boolean isValidMove(int[][] loc) {return true;}
    public int checkTimer() {return 0;}
    public void createEnchantment() {}
    public void fillHall(Object obj, int[][] loc) {}

    public boolean isPlayerAdjacentToRune(Player player) {
        return (player.getLocation().getX() == runeLocation.getX() && player.getLocation().getY() == runeLocation.getY()) ||
                ((player.getLocation().getX() + 1) == runeLocation.getX() && player.getLocation().getY() == runeLocation.getY()) ||
                ((player.getLocation().getX() - 1) == runeLocation.getX() && player.getLocation().getY() == runeLocation.getY()) ||
                (player.getLocation().getX()  == runeLocation.getX() && (player.getLocation().getY() + 1 == runeLocation.getY())) ||
                (player.getLocation().getX()  == runeLocation.getX() && (player.getLocation().getY() - 1  == runeLocation.getY()));
    }

    public Timer getTimer() {
        return timer;
    }
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }
    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public int getObjectCapacity() {
        return objectCapacity;
    }

    public String getType() {
        return type;
    }

    public Coordinate getRuneLocation() {
        return runeLocation;
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public void setGrid(Tile[][] grid) {
        this.grid = grid;
    }
}
