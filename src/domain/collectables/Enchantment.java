package domain.collectables;

import domain.Game;
import domain.util.Coordinate;

public class Enchantment {

    private final EnchantmentType type;
    private int remainingTime;
    private final Coordinate location;


    public Enchantment(EnchantmentType type) {
        this.type = type;
        this.remainingTime = 12;
        this.location = new Coordinate(Game.random.nextInt(64),Game.random.nextInt(64));
    }

    public void decreaseRemainingTime() {
        remainingTime--;
    }

    public Coordinate getLocation() {return location;}
    public EnchantmentType getType() {
        return type;
    }

    public float getRemainingTime() {
        return remainingTime;
    }
    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}
