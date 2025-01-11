package domain.entities;

import domain.Game;
import domain.agent.Agent;
import domain.agent.monster.Archer;
import domain.util.Coordinate;
import domain.util.Direction;


//user Archer olarak seçildi
// Entity -> Projectile -> Arrow
// Entity -> Agent -> Monster -> Archer
// şeklinde hiyerarşiler oluşturabiliriz o zaman değişebilir.

public class Projectile extends Entity {

    public Archer user;
    public Coordinate pos;
    public int dx;
    public int dy;
    public boolean alive;
    public final int speed = 1;
    private final int maxLife = 60;
    public int life;

    public boolean targetPlayer;

    public boolean updated;


    public Projectile() {}

    public void set(Coordinate pos, int dx, int dy, boolean alive, Archer user) {
        this.pos = pos;
        this.dx = dx;
        this.dy = dy;
        this.alive = alive;
        this.user = user;
        this.life = maxLife;
    }

    public void update() {

        pos.setX(pos.getX() + dx * speed);
        pos.setY(pos.getY() + dy * speed);


        Coordinate playerLoc = Game.getInstance().getPlayer().getLocation();
        if (pos.equals(playerLoc) && Coordinate.euclideanDistance(user.getLocation(), playerLoc) <= 4 && !Game.getInstance().getPlayer().isInvisible()) {
            Game.getInstance().getPlayer().reduceHealth();
            alive = false;
            return;
        }
        life--;

        if (life <= 0) {
            alive = false;
        }
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public boolean isTargetPlayer() {
        return targetPlayer;
    }

    public void setTargetPlayer(boolean targetPlayer) {
        this.targetPlayer = targetPlayer;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

}
