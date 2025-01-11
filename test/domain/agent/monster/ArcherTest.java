package domain.agent.monster;

import domain.Game;
import domain.agent.Player;
import domain.entities.Arrow;
import domain.entities.Projectile;
import domain.util.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcherTest {

    private Archer archer;
    private Projectile arrow;
    private Player player;
    private Game game;

   @BeforeEach
    void setUp() {
        game = Game.getInstance();
        player = game.getPlayer();
        archer = new Archer();
        arrow = archer.arrow;
    }

    @Test
    void testArrowShootsAtPlayerInRangeAndVisible() {

        archer.setLocation(new Coordinate(0, 0));
        player.setLocation(new Coordinate(1, 0)); // Distance = 1 less than attack range = 4
        player.setInvisible(false);
        archer.setAttackFrame(60); // Simulate reaching frame limit

        archer.shootArrow();

        assertTrue(arrow.alive);
        assertTrue(arrow.isTargetPlayer());
        assertEquals(1, arrow.dx);
        assertEquals(0, arrow.dy);
        assertEquals(1, archer.getAttackFrame());

    }

    @Test
    void testArrowShootsRandomDirectionIfPlayerOutOfRange() {
        archer.setLocation(new Coordinate(0, 0));
        player.setLocation(new Coordinate(100, 100)); // Out of range
        player.setInvisible(false);
        archer.setAttackFrame(60);

        archer.shootArrow();

        assertTrue(arrow.alive);
        assertFalse(arrow.isTargetPlayer());
        assertEquals(1, archer.getAttackFrame());
    }

    @Test
    void testArrowShootsRandomDirectionIfPlayerInvisible() {
        archer.setLocation(new Coordinate(0, 0));
        player.setLocation(new Coordinate(3, 4));
        player.setInvisible(true); // Player is invisible
        archer.setAttackFrame(60);

        archer.shootArrow();

        assertTrue(arrow.alive);
        assertFalse(arrow.isTargetPlayer());
        assertEquals(1, archer.getAttackFrame());
    }

    @Test
    void testNoActionIfAttackFrameNotReached() {
        archer.setLocation(new Coordinate(0, 0));
        archer.setAttackFrame(3); // Below limit

        archer.shootArrow();

        assertFalse(arrow.alive);
        assertEquals(4, archer.getAttackFrame());
    }

    @Test
    void testArrowUpdatesIfAlive() {
        archer.setLocation(new Coordinate(0, 0));
        arrow.set(archer.getLocation(), 1, 0, true, archer);

        archer.shootArrow();

        assertTrue(arrow.isUpdated());
    }
}