package Environment;

import Minions.Minion;
import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class HeardHound extends Environment{

    public HeardHound(CardInput cardInput) {
        super(cardInput);
    }

    // don't forget to check if there is space to add the minion to player's row
    @Override
    public void houndAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {
        int maxHealth = -1;
        int maxHPMinionIdx = -1;
        int idx = 0;
        for (Minion minion : board.get(affectedRow)) {
            if (minion.health > maxHealth) {
                maxHealth = minion.health;
                maxHPMinionIdx = idx;
            }
            idx++;
        }

        board.get(3 - affectedRow).add(board.get(affectedRow).get(maxHPMinionIdx));
        board.get(affectedRow).remove(maxHPMinionIdx);
    }
}
