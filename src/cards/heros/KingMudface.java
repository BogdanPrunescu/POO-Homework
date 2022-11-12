package cards.heros;

import fileio.CardInput;
import cards.minions.Minion;

import java.util.ArrayList;

public class KingMudface extends Hero {

    public KingMudface(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * King Mudface -> +1 HP for every mininon on affectedRow
     * @param board current game board
     * @param affectedRow get row that the hero action will take effect on
     */
    @Override
    public void heroAction(final ArrayList<ArrayList<Minion>> board, final int affectedRow) {
        // board = GameManager.instance.board;
        ArrayList<Minion> row = board.get(affectedRow);
        for (int i = 0; i < row.size(); i++) {
            row.get(i).setHealth(row.get(i).getHealth() + 1);
        }
    }
}
