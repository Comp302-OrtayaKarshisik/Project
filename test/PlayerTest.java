import controllers.KeyHandler;
import domain.Game;
import domain.agent.Player;
import domain.collectables.Enchantment;
import domain.collectables.EnchantmentType;
import domain.level.GridDesign;
import domain.objects.ObjectType;
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

        Game game = Game.getInstance();
        Player player = new Player();
        KeyHandler keyHandler = new KeyHandler();
        // setting up random locations to test
        int testRow = Game.random.nextInt(2,14);
        int testColumn = Game.random.nextInt(2, 14);

        // setting up the grid for play mode
        GridDesign[] gridDesigns = new GridDesign[4];
        for(int i = 0; i < 4; i++) {
            gridDesigns[i] = new GridDesign( 16, 16, 2);
            gridDesigns[i].placeObject(testRow, testColumn, ObjectType.CHEST_CLOSED);
            gridDesigns[i].placeObject(15-testRow, 15-testColumn, ObjectType.CHEST_CLOSED);
        }
        game.initPlayMode(gridDesigns);
        game.setKeyHandler(keyHandler);
        //Assert player starts at the correct direction
        assertEquals(new Coordinate(0,0),player.getLocation());
        keyHandler.goUp = true;
        player.move();
        assertEquals(new Coordinate(0,1),player.getLocation());

    }


}