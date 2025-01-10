package domain.agent.monster;

import domain.Game;
import domain.agent.Player;
import domain.entities.Arrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcherTest {

    private Archer archer;
    private Arrow arrow;
    private Player player;
    private Game game;

   /* @BeforeEach
    void setUp() {
        archer = new Archer();
        arrow = new Arrow();
        player = new Player();
        game = Game.getInstance();

        game.setPlayer(player);
        archer.setArrow(arrow);
    }*/

    @Test
    void testArrowShootsAtPlayerInRangeAndVisible() {

    }

    @Test
    void testArrowShootsRandomDirectionIfPlayerOutOfRange() {
    }

    @Test
    void testArrowShootsRandomDirectionIfPlayerInvisible() {

    }

    @Test
    void testArrowUpdatesIfAlive() {

    }

    @Test
    void testNoActionIfAttackFrameNotReached() {

    }



}