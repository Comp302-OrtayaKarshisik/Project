package domain.Agent;

import domain.Collectables.Enchantment;

public class Player {

    private int health = 3;
    private Bag bag = new Bag();
    private boolean hasRune = false;

    // x y feels better
    private int xCord = 0;
    private int yCord = 0;
    private int[][] location = new int[2][1];

    public Player(int speed) {
    }

    public void move () {}
    public void useEnch(Enchantment ench) {}
    public void collectEnch(Enchantment ench) {}
    public void increaseHealth() {}
    public void reduceHealth() {}
    public void collectRune() {}
    public void gainInvisibility() {}

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Bag getBag() {
        return bag;
    }

    public void setBag(Bag bag) {
        this.bag = bag;
    }

    public boolean isHasRune() {
        return hasRune;
    }

    public void setHasRune(boolean hasRune) {
        this.hasRune = hasRune;
    }

    public int[][] getLocation() {
        return location;
    }

    public void setLocation(int[][] location) {
        this.location = location;
    }

    public int getxCord() {
        return xCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }
}
