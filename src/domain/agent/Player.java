package domain.agent;

import domain.Game;
import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;
import domain.util.Coordinate;
import domain.util.Direction;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class Player extends Agent {

    private int health;
    private final Bag bag;
    private boolean hasRune;
    private boolean invisible;
    private final Timer timer; // This methods is for now;

    public Player() {
        health = 3;
        bag = new Bag();
        hasRune = false;
        invisible = false;
        setLocation(new Coordinate(0,0));
        timer = new Timer();
        setDirection(Direction.STOP);
    }

    // else statement is empty for now
    // also luring gem and highlight mostly changes
    // other objects thus other methods will help it
    // for now
    public void useEnchantment(Enchantment enchantment) {
        if (bag.containsEnchantment(enchantment)) {
            bag.removeEnchantment(enchantment);
            if (enchantment.getType() == EnchantmentType.Cloak)
                gainInvisibility();
        }
    }

    public void collectEnchantment(Enchantment Enchantment) {
        bag.addEnchantment(Enchantment);
    }

    public void move () {

        if (Game.getInstance().getKeyHandler().goUp)
            setDirection(Direction.UP);
        else if (Game.getInstance().getKeyHandler().goDown)
            setDirection(Direction.DOWN);
        else if (Game.getInstance().getKeyHandler().goRight)
            setDirection(Direction.RIGHT);
        else if (Game.getInstance().getKeyHandler().goLeft)
            setDirection(Direction.LEFT);
        else
            setDirection(Direction.STOP);

        if (Game.getInstance().getCollisionChecker().validMove(this)) {

            switch (getDirection()) {
                case UP -> getLocation().setY(getLocation().getY() + 1);
                case DOWN -> getLocation().setY(getLocation().getY() - 1);
                case RIGHT -> getLocation().setX(getLocation().getX() + 1);
                case LEFT -> getLocation().setX(getLocation().getX() - 1);
            }
        }

    }

    public void gainInvisibility() {
        invisible = true;
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
               invisible = false;
            }
        }, 5000);
    }

    public void collectRune() {
        hasRune = true;
    }

    public void increaseHealth() {
        if (health < 3)
            health++;
    }

    public void reduceHealth() {
        health--;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Bag getBag() {
        return bag;
    }

    public boolean isHasRune() {
        return hasRune;
    }

    public void setHasRune(boolean hasRune) {
        this.hasRune = hasRune;
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }
}
