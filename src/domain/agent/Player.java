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
    private final Timer timer;
    private Game game; // This methods is for now;


    public Player(Game game) {
        this.health = 3;
        this.bag = new Bag();
        this.hasRune = false;
        this.invisible = false;
        super.setLocation(new Coordinate(0,0));
        this.timer = new Timer();
        this.game = game;
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
        this.bag.addEnchantment(Enchantment);
    }

    public void move () {

        if (game.getKeyHandler().goUp)
            setDirection(Direction.UP);
        else if (game.getKeyHandler().goDown)
            setDirection(Direction.DOWN);
        else if (game.getKeyHandler().goRight)
            setDirection(Direction.RIGHT);
        else if (game.getKeyHandler().goLeft)
            setDirection(Direction.LEFT);
        else
            setDirection(Direction.STOP);



        if (!game.getCollusionChecker().checkBoundary(this) &&
                !game.getCollusionChecker().checkTile(this) &&
                !game.getCollusionChecker().checkAgents(this, game.getAgents())) {
            System.out.print("Direction inside thep layer");
            System.out.println(getDirection());
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
