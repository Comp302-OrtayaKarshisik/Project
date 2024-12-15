package domain.level;

import domain.agent.Player;
import domain.collectables.EnchantmentType;
import domain.collectables.Enchantment;

import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

public class Hall {

    private Timer timer = new Timer();
    private String type;
    private int[][] rune;
    //Still belive that x and y is better then loc rn
    private int runeX;
    private int runeY;

    private int width;
    private int height;
    private List<Enchantment> enchs;
    private int objectCapacity;

    public Hall() {}
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
    public boolean adjacentToRune(Player player) {return true;}

    public int getObjectCapacity() {
        return objectCapacity;
    }

    public void setObjectCapacity(int objectCapacity) {
        this.objectCapacity = objectCapacity;
    }

    public List<Enchantment> getEnchs() {
        return enchs;
    }

    public void setEnchs(List<Enchantment> enchs) {
        this.enchs = enchs;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getRuneY() {
        return runeY;
    }

    public void setRuneY(int runeY) {
        this.runeY = runeY;
    }

    public int getRuneX() {
        return runeX;
    }

    public void setRuneX(int runeX) {
        this.runeX = runeX;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int[][] getRune() {
        return rune;
    }

    public void setRune(int[][] rune) {
        this.rune = rune;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

}
