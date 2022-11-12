package cards.heros;

import cards.minions.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {

    public GeneralKocioraw(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * General Kocioraw -> +1 ATK for all minions on affectedRow
     * @param board current game board
     * @param affectedRow get row that the hero action will take effect on
     */
    @Override
    public void heroAction(final ArrayList<ArrayList<Minion>> board, final int affectedRow) {
        ArrayList<Minion> row = board.get(affectedRow);
        for (int i = 0; i < row.size(); i++) {
            row.get(i).setAttackDamage(row.get(i).getAttackDamage() + 1);
        }
    }
}
