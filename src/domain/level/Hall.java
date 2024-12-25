package domain.level;

import domain.Game;
import domain.Textures;
import domain.agent.Player;
import domain.collectables.EnchantmentType;
import domain.collectables.Enchantment;
import domain.objects.ObjectType;
import domain.util.Coordinate;

import javax.xml.stream.Location;
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

    public EnchantmentType type(int [][] xy) {return null;}
    public void increaseTime() {}
    public String typeOfCollectable() {return null;}
    public void higlightRune() {}
    public void removeHiglight() {}
    public void throwLure(Key key) {}

    public int checkTimer() {return 0;}
    // An ench factory will do this
    public void createEnchantment() {}


    public void fillHall(Object obj, int[][] loc) {}
    private boolean isRuneLocation(Coordinate c1) {
        return c1.equals(this.runeLocation);
    }

    public void handleChosenBox(Player player, Coordinate c1) {
        Tile obj = grid[c1.getX()][c1.getY()];
        if (obj.getName() == "aa" || obj.getName() == "COLUMN") {
            return;
        }
        grid[c1.getX()][c1.getY()] = new Tile("aa",new Coordinate(c1.getX(), c1.getY()));
        if (isRuneLocation(c1)) {
            System.out.println("found rune");
            player.setHasRune(true);
        }
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
                            new Tile(newTile.toString(), objCoordinate, true);
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
