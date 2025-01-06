package domain.agent;

import domain.Game;
import domain.agent.monster.Fighter;
import domain.agent.monster.Monster;
import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;
import domain.level.Hall;
import domain.util.Coordinate;
import domain.util.Direction;
import listeners.PlayerListener;

import java.util.*;

public class Player extends Agent {

    private final int MAX_HEALTH = 3;
    private final int INVISIBILITY_DURATION = 5;

    private final List<PlayerListener> listeners;
    private int health;
    private final Bag bag;
    private boolean hasRune;
    private boolean invisible;
    private final Timer timer; // This methods is for now;
    private Coordinate doorCoordinate;

    public Player() {
        listeners = new LinkedList<>();
        health = 3;
        bag = new Bag();
        hasRune = false;
        invisible = false;
        setLocation(new Coordinate(0,0));
        doorCoordinate = new Coordinate(9, 0);
        timer = new Timer();
        setDirection(Direction.STOP);
    }

    // else statement is empty for now
    // also luring gem and highlight mostly changes
    // other objects thus other methods will help it
    // for now
    public void useEnchantment(Enchantment enchantment) {

        if(enchantment.getType() == EnchantmentType.Life || enchantment.getType() == EnchantmentType.Time) {
            if (enchantment.getType() == EnchantmentType.Life) {increaseHealth();}
            else {Game.getInstance().getDungeon().getCurrentHall().getTimer().increaseTimeRemaining(5);}
        }
        else {
            if (bag.containsEnchantment(enchantment)) {
                bag.removeEnchantment(enchantment);
                if (enchantment.getType() == EnchantmentType.Cloak) {gainInvisibility();}
                else if (enchantment.getType() == EnchantmentType.Reveal) {Game.getInstance().getDungeon().getCurrentHall().higlightRune();}
                else { // sikintili
                    Coordinate c;
                    for (Agent m : Game.getInstance().getAgents()) {
                        if (m instanceof Fighter) {
                            ((Fighter) m).lureUsed(new Coordinate(5, 5));
                        }
                    }
                }
            }
        }

    }

    public void collectEnchantment(Enchantment Enchantment) {
        bag.addEnchantment(Enchantment);
        if (Enchantment.getType() == EnchantmentType.Life && Enchantment.getType() == EnchantmentType.Time)
            useEnchantment(Enchantment);
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

        Direction currDirection = getDirection();

        if (Game.getInstance().getDungeon().getCollisionChecker().validMove(this)) {

            switch (currDirection) {
                case UP -> getLocation().setY(getLocation().getY() + 1);
                case DOWN -> getLocation().setY(getLocation().getY() - 1);
                case RIGHT -> getLocation().setX(getLocation().getX() + 1);
                case LEFT -> getLocation().setX(getLocation().getX() - 1);
            }
        }

        // for getting to the next Hall
        if (currDirection == Direction.DOWN && hasRune && location.equals(doorCoordinate)) {
            this.location.setLocation(0, 0);
            this.setHasRune(false);
            Game.getInstance().nextHall();
        }
    }

    public void addListener(PlayerListener pl) {
        listeners.add(pl);
    }

    public void publishEvent(int num) {
        for (PlayerListener pl : listeners)
            pl.onHealthEvent(num);
    }

    public void gainInvisibility() {
        invisible = true;
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
               invisible = false;
            }
        }, INVISIBILITY_DURATION * 1000);
    }

    public void collectRune() {
        hasRune = true;
    }

    public void increaseHealth() {
        if (health < MAX_HEALTH) {
            health++;
            publishEvent(health);
        }
    }

    public void reduceHealth() {
        health--;
        publishEvent(health);
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
        for (PlayerListener pl : listeners)
            pl.onRuneEvent(hasRune);
    }

    public boolean isInvisible() {
        return invisible;
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
    }

}
