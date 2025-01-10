package domain.agent;

import domain.Game;
import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest{


    /**
     * DamageTest: Test whether player gets damaged
     * Expect: player starts with max health,
     *         player's health decreases by 1 each time
     */
    @Test
    void damageTest(){

        //Init player
        Player player = new Player();

        //Be sure that player starts with 3 health
        assertEquals(3, player.getHealth());

        //Takes the first damage
        player.reduceHealth();
        assertEquals(2, player.getHealth());

        //Takes the second damage
        player.reduceHealth();
        assertEquals(1, player.getHealth());

        //Takes the third damage
        player.reduceHealth();
        assertEquals(0, player.getHealth());
    }

    /**
     * collectEnchantmentTest: Test whether player collects enchantments
     * Expect: player starts with 0 enchantments in his bag
     *         later player collects the given collectible enchantment
     *         and player only has 1 of each collectible enchantment
     */
    @Test
    void collectibleEnchantmentTest() {

        //init players and enchantments
        Player player = new Player();
        Enchantment lure = new Enchantment(EnchantmentType.Luring);
        Enchantment cloak = new Enchantment(EnchantmentType.Cloak);
        Enchantment reveal = new Enchantment(EnchantmentType.Reveal);

        //Be sure that player starts with 0 collectable enchantment
        assertFalse(player.getBag().containsEnchantment(lure));
        assertFalse(player.getBag().containsEnchantment(cloak));
        assertFalse(player.getBag().containsEnchantment(reveal));

        //Add 1 for each collectable enchantment to player's bag
        player.collectEnchantment(lure);
        player.collectEnchantment(cloak);
        player.collectEnchantment(reveal);

        //Since for each enchantment, collect method called once
        //Bag should contain 1 enchantment for each
        assertEquals(1, player.getBag().getEnchantmentCounter().get(lure.getType()));
        assertEquals(1, player.getBag().getEnchantmentCounter().get(reveal.getType()));
        assertEquals(1, player.getBag().getEnchantmentCounter().get(cloak.getType()));

    }

    /**
     * lifeEnchantmentEffectTest(): Test whether life enchantment works properly
     * Expect: player starts with 3 health
     *         whenever life enchantment used when player has maximum health
     *         players life should stay the same (3)
     *         if life smaller than 3, then health has to increase by one
     */
    @Test
    void lifeEnchantmentEffectTest() {

        //Init player and enchantment
        Player player = new Player();
        Enchantment life = new Enchantment(EnchantmentType.Life);

        //Be sure that player starts with 3 health
        assertEquals(3, player.getHealth());

        //If health is 3 then player takes extra life
        player.useEnchantment(life);
        assertEquals(3, player.getHealth());

        //Reduce the health of player
        //Be sure that player indeed has 2 health
        player.reduceHealth();
        assertEquals(2, player.getHealth());

        //Use the life enchantment on player
        //Be sure that life is indeed increased by 1
        player.useEnchantment(life);
        assertEquals(3, player.getHealth());

    }

    /**
     * cloakEffectTest(): Test whether clock enchantment works properly
     * Expect: player takes damage when not invisible
     *         after using cloak, player should gain invisibility
     *         and should take no damage
     */
    @Test
    void cloakEffectTest() {

        //Init player and enchantment
        Player player = new Player();
        Enchantment cloak = new Enchantment(EnchantmentType.Cloak);

        //Be sure that player is invisible
        assertFalse(player.isInvisible());

        //Try to reduce the health of the player
        //Be sure that when not invisible player takes damage
        //And it's health is 2 now
        player.reduceHealth();
        assertEquals(2, player.getHealth());

        //Add enchantment to the bag, collect works from the prev test
        player.collectEnchantment(cloak);

        //Use enchantment on player
        //Now has to be
        player.useEnchantment(cloak);
        assertTrue(player.isInvisible());

        //try to reduce the health
        //it shouldn't decrease since player is invisible
        player.reduceHealth();
        assertEquals(2, player.getHealth());

    }
}