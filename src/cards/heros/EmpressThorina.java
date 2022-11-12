package cards.heros;

import cards.minions.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public class EmpressThorina extends Hero {

    public EmpressThorina(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Empress Thorina -> kill the minion with highest health on the affectedRow
     * @param board current game board
     * @param affectedRow get row that the hero action will take effect on
     */
    @Override
    public void heroAction(final ArrayList<ArrayList<Minion>> board, final int affectedRow) {
        int maxHealth = -1;
        int maxHPMinionIdx = -1;
        int idx = 0;
        for (Minion minion : board.get(affectedRow)) {
            if (minion.getHealth() > maxHealth) {
                maxHealth = minion.getHealth();
                maxHPMinionIdx = idx;
            }
            idx++;
        }
        board.get(affectedRow).remove(maxHPMinionIdx);
    }
}
