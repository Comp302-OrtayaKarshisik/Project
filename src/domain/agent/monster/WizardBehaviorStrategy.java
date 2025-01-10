package domain.agent.monster;
/**
 * Requires: The game must have a valid dungeon with an active timer and a valid current hall.
 * Modifies: This method modifies the current behavior of the wizard and may teleport the player or move the rune.
 * Effects:
 * - Chooses the behavior based on the remaining game time:
 *   - If time remaining is less than 30%, it teleports the player and makes the wizard disappear.
 *   - If time remaining is more than 70%, it moves the rune every 3 seconds.
 *   - If time remaining is between 30% and 70%, it makes the wizard stay idle and disappear after 2 seconds.
 */
public interface WizardBehaviorStrategy {
    void execute(Wizard wizard);

}
