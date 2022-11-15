package cards.environment;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import cards.Card;
import fileio.CardInput;
import cards.heros.Hero;
import manager.Conditions;
import manager.GameManager;
import manager.PrintErrors;
import cards.minions.Minion;

import java.util.ArrayList;

public class Environment extends Card {

    public Environment(final CardInput cardInput) {
        super(cardInput, true, false);
    }

    public Environment(final Card card) {
        super(card);
    }

    public Environment() {
        super();
    }

    /**
     * Tries to test if the command is valid. If so, run it. If there are
     * invalid cases, throw an error.
     * @param card Card that is being used.
     * @param action Command used to perform the action.
     * @param output Where to throw the error in case there is one.
     */
    public static void tryUseEnvironmentCard(final Card card,
                                             final ActionsInput action, final ArrayNode output) {

        int currentPlayer = GameManager.getInstance().getCurrentPlayer();

        int affectedRow = action.getAffectedRow();

        Hero currentHero = GameManager.getInstance().getHeroes().get(currentPlayer);

        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();

        ArrayList<Card> playerHand = GameManager.getInstance().getHands().get(currentPlayer);

        if (Conditions.testCardType(card) != 1) {
            PrintErrors.throwCardError(action, "Chosen card is not of type environment.",
                    output);

        } else if (!Conditions.playerHasEnoughMana(card, currentHero)) {
            PrintErrors.throwCardError(action, "Not enough mana to use environment card.", output);

        } else {
            if (!Conditions.testIfRowBelongsToEnemy(currentPlayer, affectedRow)) {
                PrintErrors.throwCardError(action, "Chosen row does not belong to the enemy.",
                        output);

            } else {
                if (card.getName().equals("Heart Hound")
                        && !Conditions.testHeartHound(board, affectedRow)) {
                    PrintErrors.throwCardError(action,
                            "Cannot steal enemy card since the player's row is full.", output);

                } else {
                    ((Environment) card).action(action.getAffectedRow());
                    playerHand.remove(action.getHandIdx());

                    Hero hero = GameManager.getInstance().getHeroes().get(currentPlayer);
                    int heroMana = hero.getPlayingMana();

                    currentHero.setPlayingMana(heroMana - card.getMana());

                }
            }
        }
    }

    /**
     *  This method is used to run the specific action of each environment card
     *  except the HeardHound spell, which is treated separately.
     *
     * @param affectedRow get affectedRow that the action will take effect on
     */
    public void action(final int affectedRow) { }
}
