package Heros;

import Minions.Minion;
import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class EmpressThorina extends Hero {

    public EmpressThorina(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void heroAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {
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
        board.get(affectedRow).remove(maxHPMinionIdx);
    }
}
