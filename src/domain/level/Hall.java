package domain.level;

import domain.Game;
import domain.Textures;
import domain.agent.Player;
import domain.collectables.EnchantmentType;
import domain.collectables.Enchantment;
import domain.objects.ObjectType;
import domain.util.Coordinate;

import java.awt.image.BufferedImage;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

public class Hall {

    private Timer timer; // this timer is responsible for creating enchantments
    private final String type;
    private Coordinate runeLocation;
    private List<Enchantment> enchantments;
    private final int objectCapacity;
    private ArrayList<Coordinate> runeLocations;

    private Tile[][] grid;

    public Hall(String type, int objectCapacity) {
        timer = new Timer();
        this.type = type;
        this.runeLocations = new ArrayList<Coordinate>();
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

    // Transfer grid  design from build mode to Hall
    public void transferGridDesign(GridDesign gridDesign) {
        if(gridDesign==null) return;
        ObjectType[][] gridCreated = gridDesign.getGrid();
        for(int row = 0; row < gridCreated.length;row++) {
            int verticalSize = gridCreated[row].length;
            for(int col = 0; col < verticalSize; col++) {
                ObjectType newTile = gridCreated[row][col];
                if(newTile != null) {
                    Coordinate objCoordinate = new Coordinate(row, verticalSize-col-1);
                    this.grid[row][verticalSize-col-1] =
                            new Tile(newTile.toString(), objCoordinate, newTile == ObjectType.COLUMN);
                    if(newTile != ObjectType.COLUMN) {
                        runeLocations.add(objCoordinate);
                    }
                }
            }
        }

        this.setNewRuneLocation();
    }

    public void setNewRuneLocation() {
        int randomRuneLoc = Game.random.nextInt(runeLocations.size());
        this.runeLocation = runeLocations.get(randomRuneLoc);
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public void setGrid(Tile[][] grid) {
        this.grid = grid;
    }


}
