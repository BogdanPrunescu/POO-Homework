package cards.environment;

import manager.GameManager;
import cards.minions.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public class HeardHound extends Environment {

    public HeardHound(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * HeartHound -> steal a minion with the hightest health on the affectedRow
     * @param affectedRow get affectedRow that the action will take effect on
     */
    @Override
    public void action(final int affectedRow) {
        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();
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

        board.get(board.size() - affectedRow).add(board.get(affectedRow).get(maxHPMinionIdx));
        board.get(affectedRow).remove(maxHPMinionIdx);
    }
}
