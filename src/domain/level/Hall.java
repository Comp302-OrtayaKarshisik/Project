package domain.level;

import domain.Game;
import domain.Textures;
import domain.agent.Player;
import domain.collectables.EnchantmentType;
import domain.collectables.Enchantment;
import domain.factories.EnchantmentFactory;
import domain.objects.ObjectType;
import domain.util.Coordinate;
import listeners.GameListener;
import ui.Graphics.EnchantmentGraphics;

import javax.xml.stream.Location;
import java.awt.image.BufferedImage;
import java.security.Key;
import java.security.SecureRandom;
import java.util.*;

public class Hall {

    private CountDownTimer timer;
    private final String type;
    private Coordinate runeLocation;
    private List<Enchantment> enchantments;
    private final int placedObjectCount; //initial timer value is 5 times this.
    private ArrayList<Coordinate> runeLocations;
    private Game game;

    private Tile[][] grid;

    public Hall(String type, int placedObjectCount) {
        timer = new CountDownTimer(placedObjectCount * 5);
        this.type = type;
        game = Game.getInstance();
        this.runeLocations = new ArrayList<Coordinate>();
        this.enchantments = new LinkedList<>();
        this.placedObjectCount = placedObjectCount;
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

    public void higlightRune() {
        int minX = Math.max(runeLocation.getX()-3, 0);
        int maxX = Math.min(runeLocation.getX(), 13);
        int minY = Math.max((15-runeLocation.getY())-3, 0);
        int maxY = Math.min((15-runeLocation.getY()), 13);
        int x = Game.random.nextInt(minX, maxX);
        int y = Game.random.nextInt(minY, maxY);
        for(GameListener gl: Game.getInstance().getListeners()) {
            gl.onHighlightEvent(new Coordinate(x, y));
        }
    }
    public void removeHiglight() {}
    public void throwLure(Key key) {}

    public int checkTimer() {return 0;}
    // An ench factory will do this
    public void createEnchantment() {}


    public void fillHall(Object obj, int[][] loc) {}
    private boolean isRuneLocation(Coordinate c1) {
        return c1.equals(this.runeLocation);
    }

    /**
     * Handles the next action when the user clicks inside the hall in play mode.
     *
     * Requires:
     * - Game, Game->enchantments, Enchantment, Player, Coordinate, grid.
     * - Coordinate object to be a valid coordinate inside the grid.
     * -
     * Modifies:
     * - Modifies the grid by changing the tiles.
     * - Modifies the Game by changing Game->enchantments.
     * - Modifies Player->hasRune if the rune is found.
     * -
     * Effects:
     * - If the player has chosen an enchantment, collectEnchantment function is called
     * - If the player chooses an object, the object disappears and checked whether the rune is found.
     */

    public void handleChosenBox(Player player, Coordinate c1) {

        // for enchantment logic
        for (Enchantment enchantment : game.getEnchantments()) {
            //System.out.println("scanning found enchantment at x: " + enchantment.getLocation().getX() + " y: " + enchantment.getLocation().getY());
            if (enchantment.getLocation().equals(c1)) {
                //handle enchantment logic.
                Game.getInstance().getPlayer().collectEnchantment(enchantment);//probably shouldn't call it here.
                game.getEnchantments().remove(enchantment);
                EnchantmentFactory.getInstance().notifyRemoval(enchantment);
                return;
            }
        }

        // for rune logic

        Tile obj = grid[c1.getX()][c1.getY()];
        if (obj.getName() == "aa" || obj.getName() == "COLUMN") {
            return;
        }

        if (Coordinate.manhattanDistance(game.getPlayer().getLocation(), c1) <= 1) {
            grid[c1.getX()][c1.getY()] = new Tile("aa",new Coordinate(c1.getX(), c1.getY()));
            if(isRuneLocation(c1)) {
                System.out.println("found rune");
                player.setHasRune(true);
            }
        }
    }

    public CountDownTimer getTimer() {
        return timer;
    }

    public List<Enchantment> getEnchantments() {
        return enchantments;
    }
    public void setEnchantments(List<Enchantment> enchantments) {
        this.enchantments = enchantments;
    }

    public int getObjectCapacity() {
        return placedObjectCount;
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
