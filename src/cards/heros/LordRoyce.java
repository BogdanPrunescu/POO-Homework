package cards.heros;

import cards.minions.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public class LordRoyce extends Hero {

    public LordRoyce(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Lord Royce -> freeze minion with the biggest attack on the affectedRow
     * @param board current game board
     * @param affectedRow get row that the hero action will take effect on
     */
    @Override
    public void heroAction(final ArrayList<ArrayList<Minion>> board, final int affectedRow) {
        int maxAttack = -1;
        int maxATKMinionIdx = -1;
        int idx = 0;
        for (Minion minion : board.get(affectedRow)) {
            if (minion.getAttackDamage() > maxAttack) {
                maxAttack = minion.getHealth();
                maxATKMinionIdx = idx;
            }
            idx++;
        }
        board.get(affectedRow).get(maxATKMinionIdx).setIsFrozen(true);
    }
}
