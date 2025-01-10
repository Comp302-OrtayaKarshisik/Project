package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Mock or real classes, depending on your setup
import domain.Game;
import domain.level.Dungeon;
import domain.level.Hall;
import domain.level.CountDownTimer;
import domain.agent.Player;
import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;
import domain.agent.Bag;

public class PlayerEnchantmentTest {

    private Player player;
    private Hall testHall;
    private CountDownTimer hallTimer;
    private Bag bag;
    private Game game;

    @BeforeEach
    void setUp() {
        //trying to get a blank slate for each test (empty hall with player.)
        game = Game.getInstance();
        player = game.getPlayer();
        bag = player.getBag();
        testHall = new Hall("testHall", 5); // means 5 objects => 5 * 5 = 25 seconds initially
        hallTimer = testHall.getTimer();
        Dungeon dungeon = new Dungeon();
        Hall[] halls = new Hall[]{ testHall };
        dungeon.setHalls(halls);
        dungeon.setCurrentHall(0);  // index 0 => testHall
        game.setDungeon(dungeon);
        game.getAgents().clear();
        hallTimer.start();
    }

    /**
     * Test 1: Using a Life enchantment -> calls increaseHealth() immediately.
     * Expect: Player's health increases by 1 (up to a max of 3 if code enforces that).
     */
    @Test
    public void testUseEnchantment_life() {

        int initialHealth = player.getHealth();

        // Create a Life enchantment
        Enchantment life = new Enchantment(EnchantmentType.Life);

        // Call useEnchantment
        player.useEnchantment(life);

        // If the player’s health was at 3, it should remain at 3.
        // Otherwise, it should increase by 1.
        int expected = Math.min(initialHealth + 1, 3);

        assertEquals(expected, player.getHealth(),
                "Player's health should increase by 1 (unless already at max 3).");
    }

    /**
     * Test 2: Using a Time enchantment -> adds 5 seconds to the current hall’s timer.
     * Expect: hallTimer’s timeRemaining increases by 5.
     */
    @Test
    public void testUseEnchantment_time() {
        //since the hall has 5 objects, the initial time should be 5 * 5 = 25
        float initialTime = hallTimer.getTimeRemaining();
        Enchantment time = new Enchantment(EnchantmentType.Time);

        player.useEnchantment(time);

        // Should be initialTime + 5
        assertEquals(initialTime + 5, hallTimer.getTimeRemaining(),
                "Time enchantment should add 5 seconds to the hall timer.");
    }

    /**
     * Test 3: Using a Cloak enchantment -> must be in the bag, then removed from bag, calls gainInvisibility().
     * Expect: Cloak is removed from bag, player's invisible=true.
     */
    @Test
    public void testUseEnchantment_cloak() {
        Enchantment cloak = new Enchantment(EnchantmentType.Cloak);

        // first collect it so that it can be used.
        player.collectEnchantment(cloak);
        assertTrue(player.getBag().containsEnchantment(cloak),
                "Cloak should be in the bag before using it.");

        // Player should start as visible
        assertFalse(player.isInvisible(), "Player should not be invisible initially.");

        // Use the enchantment
        player.useEnchantment(cloak);

        //cloak should be removed from bag.
        assertFalse(player.getBag().containsEnchantment(cloak),
                "Cloak should be removed from bag after using it.");

        // Player should be invisible
        assertTrue(player.isInvisible(), "Player should become invisible after using Cloak enchantment.");
    }

    /**
     * Test 4: Using a Reveal enchantment -> must be in the bag, then removed, calls highlightRune().
     * Expect: The method highlightRune() is triggered.
     */
    @Test
    public void testUseEnchantment_reveal() {
        Enchantment reveal = new Enchantment(EnchantmentType.Reveal);

        //enchantment should be in the bag before using it.
        player.collectEnchantment(reveal);
        assertTrue(player.getBag().containsEnchantment(reveal),
                "Reveal enchantment should be in the bag before using it.");


        player.useEnchantment(reveal);

        //also once its used it should be removed from the bag
        assertFalse(player.getBag().containsEnchantment(reveal),
                "Reveal enchantment should be removed from bag after using it.");

        //we need a way to check if the rune is highlighted, we didn't implement it yet.
        // assertTrue(testHall.isRuneHighlighted(), "Rune should be highlighted in the hall.");
    }
}
