package domain.agent.monster;
import domain.level.Dungeon;
import domain.level.Hall;
import domain.util.Coordinate;
import org.junit.jupiter.api.BeforeEach;

import domain.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WizardBehaviorStrategyTest {
    private Wizard wizard;
    private Game game;

    @BeforeEach
    public void setUp() {
        game = Game.getInstance();
        wizard = new Wizard();

        // Manually initialize dungeon and halls
        Dungeon dungeon = new Dungeon();
        Hall[] halls = new Hall[4];  // Assuming 4 halls
        for (int i = 0; i < halls.length; i++) {
            halls[i] = new Hall("TestHall" + i, 10);// Each hall has an initial object capacity of 10
            // Manually add rune locations
            halls[i].setSpecificRuneLocation(new Coordinate(4, 2));

        }
        dungeon.setHalls(halls);
        game.setDungeon(dungeon);  // Inject the initialized dungeon into the game

        // Set current hall to the first one and start its timer
        dungeon.getCurrentHall().getTimer().reset();
    }

    @Test
    void testPlayerRelocationStrategy() {
        WizardBehaviorStrategy behavior = new PlayerRelocationStrategy();
        Coordinate initialPlayerLocation = game.getPlayer().getLocation();

        // Simulate < 30% time remaining
        game.getDungeon().getCurrentHall().getTimer().increaseTimeRemaining(-70);
        behavior.execute(wizard);

        // Verify that the player was teleported
        assertNotEquals(initialPlayerLocation, game.getPlayer().getLocation());
        assertFalse(game.getAgents().contains(wizard));
    }

    @Test
    void testRuneRelocationStrategy() throws InterruptedException {
        WizardBehaviorStrategy behavior = new RuneRelocationStrategy();
        game.getDungeon().getCurrentHall().setSpecificRuneLocation(new Coordinate(2, 2));
    Coordinate initialruneLocation = game.getDungeon().getCurrentHall().getRuneLocation();

        // Simulate > 70% time remaining
        game.getDungeon().getCurrentHall().getTimer().reset();
        behavior.execute(wizard);

        // Verify that the rune's location has changed
        assertNotEquals(initialruneLocation, game.getDungeon().getCurrentHall().getRuneLocation());
    }

    @Test
    void testIdleStrategy() throws InterruptedException {
        WizardBehaviorStrategy behavior = new IdleStrategy();
        Coordinate wizardLocation = wizard.getLocation();

        // Simulate 50% time remaining
        game.getDungeon().getCurrentHall().getTimer().increaseTimeRemaining(-40);
        behavior.execute(wizard);
        Thread.sleep(2100);  // Wait 2.1 seconds

        // Verify that the wizard stays idle but disappears after 2 seconds
        assertEquals(wizardLocation, wizard.getLocation());
        assertFalse(game.getAgents().contains(wizard));
    }
}
