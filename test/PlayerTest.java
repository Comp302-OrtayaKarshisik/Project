import controllers.KeyHandler;
import domain.Game;
import domain.agent.Player;
import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;
import domain.level.GridDesign;
import domain.util.Coordinate;
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
        assertFalse(player.getBag().containsEnchantment(EnchantmentType.Luring));
        assertFalse(player.getBag().containsEnchantment(EnchantmentType.Cloak));
        assertFalse(player.getBag().containsEnchantment(EnchantmentType.Reveal));

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

    @Test
    void moveTest() {

        Player player = new Player();
        Game game = Game.getInstance();
        KeyHandler keyHandler = new KeyHandler();
        GridDesign[] designs = new GridDesign[4];
        designs[0] = new GridDesign(16,16,2);
        designs[1] = new GridDesign(16,16,3);
        designs[2] = new GridDesign(16,16,3);
        designs[3] = new GridDesign(16,16,3);
        game.getDungeon().loadDesigns(designs);

        game.setKeyHandler(keyHandler);

        //Assert player starts at the correct direction
        assertEquals(new Coordinate(0,0),player.getLocation());

        keyHandler.goUp = true;
        player.move();
        assertEquals(new Coordinate(0,1),player.getLocation());



    }


}