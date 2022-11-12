package manager;

import cards.Card;
import cards.heros.Hero;
import cards.minions.Minion;

import java.util.ArrayList;

public final class GameExceptions {

    public static final int MAX_CARDS_ON_ROW = 5;
    public static final int NR_ROWS = 3;
    private GameExceptions() { }

    /**
     * Checks if a card is an Environment or a Minion card
     * @param card the card that needs to be checked
     * @return 1 if a card is an Environment or 0 if a card is a Minion
     */
    public static int testCardType(final Card card) {
        if (card.isEnvironment()) {
            return 1;
        }
        return 0;
    }

    /**
     * Tests whether a card does belong to the enemy player
     * @param currentPlayer The player that is attacking.
     * @param row Where on the board is the target card is located.
     * @return True if the row where the card is targeted does belong to the enemy player.
     */
    public static boolean testIfRowBelongsToEnemy(final int currentPlayer, final int row) {
        if (currentPlayer == 0) {
            return row < 2;
        } else {
            return row >= 2;
        }
    }

    /**
     *  Tests if a HeartHound spell has room for the stolen card to be added to the
     *  current player's side.
     * @param board Playing board
     * @param row Row where the environment card will take effect
     * @return True if the use of the spell is valid
     */
    public static boolean testHeartHound(final ArrayList<ArrayList<Minion>> board, final int row) {
        return board.get(NR_ROWS - row).size() != MAX_CARDS_ON_ROW;
    }

    /**
     * Tests whether the player has a tank minion on the board
     * @param player
     * @return true if the player has a tank
     */
    public static boolean playerHasTanks(final int player) {

        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();

        if (player == 0) {
            for (Minion cards : board.get(2)) {
                if (cards.isTank()) {
                    return true;
                }
            }
        } else {
            for (Minion cards : board.get(1)) {
                if (cards.isTank()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Tests if the player has enough mana to play a card of it's ability
     * @param card Card/HeroAbility that needs checking
     * @param hero the hero that is playing the card
     * @return true is the player has enough mana to play the card
     */
    public static boolean playerHasEnoughMana(final Card card, final Hero hero) {
        return hero.getPlayingMana() >= card.getMana();
    }

    /**
     * Get row where the minion should be placed.
     * @param card Card that needs to be placed.
     * @param player The player that is playing the card.
     * @return Index to the row where the card must be placed or -1
     * if there is no room for the card to be placed.
     */
    public static int placeCard(final Minion card,
                                final ArrayList<ArrayList<Minion>> board, final int player) {

        if (card.isFrontLiner()) {
            if (player == 1) {
                if (board.get(1).size() == MAX_CARDS_ON_ROW) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                if (board.get(2).size() == MAX_CARDS_ON_ROW) {
                    return -1;
                } else {
                    return 2;
                }
            }
        } else {
            if (player == 1) {
                if (board.get(0).size() == MAX_CARDS_ON_ROW) {
                    return -1;
                } else {
                    return 0;
                }
            } else {
                if (board.get(NR_ROWS).size() == MAX_CARDS_ON_ROW) {
                    return -1;
                } else {
                    return NR_ROWS;
                }
            }
        }
    }
}
