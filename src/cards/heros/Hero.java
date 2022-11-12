package cards.heros;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import manager.GameExceptions;
import manager.GameManager;
import manager.ShowErrors;
import cards.minions.Minion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileio.CardInput;
import cards.Card;

import java.util.ArrayList;

@JsonPropertyOrder({"mana", "description", "colors", "name", "health"})
public class Hero extends Card {

    private int health;

    @JsonIgnore
    private int playingMana;

    @JsonIgnore
    private boolean hasAttacked = false;

    public Hero(final CardInput card) {
        super(card, false, false);
    }

    public Hero(final Hero hero) {
        super(hero);
        this.setHealth(hero.getHealth());
    }

    /**
     *  This method is used to run the specific action of each hero
     *
     * @param board current game board
     * @param affectedRow get row that the hero action will take effect on
     */
    public void heroAction(final ArrayList<ArrayList<Minion>> board, final int affectedRow) { }

    /**
     * Tests every invalid case for "UseHeroAbility" command. If there are no
     * errors then executes the command.
     * @param action Action that takes place
     * @param output Where errors are sent
     */
    public static void tryUseHeroAbility(final ActionsInput action, final ArrayNode output) {

        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();
        ArrayList<Hero> heroes = GameManager.getInstance().getHeroes();
        int currentPlayer = GameManager.getInstance().getCurrentPlayer();
        int affectedRow = action.getAffectedRow();

        if (!GameExceptions.playerHasEnoughMana(heroes.get(currentPlayer),
                heroes.get(currentPlayer))) {
            ShowErrors.throwUseHeroAbilityError(action,
                    "Not enough mana to use hero's ability.", output);

        } else if (heroes.get(currentPlayer).hasAttacked()) {
            ShowErrors.throwUseHeroAbilityError(action,
                    "Hero has already attacked this turn.", output);

        } else {
            if (heroes.get(currentPlayer).getName().equals("Lord Royce")
                    || heroes.get(currentPlayer).getName().equals("Empress Thorina")) {

                if (!GameExceptions.testIfRowBelongsToEnemy(currentPlayer, affectedRow)) {
                    ShowErrors.throwUseHeroAbilityError(action,
                            "Selected row does not belong to the enemy.", output);

                } else {
                    int heroMana = heroes.get(currentPlayer).getPlayingMana();
                    int heroAbilityMana = heroes.get(currentPlayer).getMana();

                    heroes.get(currentPlayer).heroAction(board, affectedRow);
                    heroes.get(currentPlayer).setPlayingMana(heroMana - heroAbilityMana);
                    heroes.get(currentPlayer).setHasAttacked(true);
                }
            } else {
                if (GameExceptions.testIfRowBelongsToEnemy(currentPlayer, affectedRow)) {
                    ShowErrors.throwUseHeroAbilityError(action,
                            "Selected row does not belong to the current player.", output);

                } else {
                    int heroMana = heroes.get(currentPlayer).getPlayingMana();
                    int heroAbilityMana = heroes.get(currentPlayer).getMana();

                    heroes.get(currentPlayer).heroAction(board, affectedRow);
                    heroes.get(currentPlayer).setPlayingMana(heroMana - heroAbilityMana);
                    heroes.get(currentPlayer).setHasAttacked(true);
                }
            }

        }
    }

    /**
     * Safe to use for other heroes
     */
    public int getHealth() {
        return health;
    }

    /**
     * Safe to use for other heroes
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Safe to use for other heroes
     */
    public int getPlayingMana() {
        return playingMana;
    }

    /**
     * Safe to use for other heroes
     */
    public void setPlayingMana(final int playingMana) {
        this.playingMana = playingMana;
    }

    /**
     * Safe to use for other heroes
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Safe to use for other heroes
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
}
