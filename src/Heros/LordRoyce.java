package Heros;

import Minions.*;
import fileio.CardInput;

import java.util.ArrayList;

public class LordRoyce extends Hero {

    public LordRoyce(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void heroAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {
        int maxAttack = -1;
        int maxATKMinionIdx = -1;
        int idx = 0;
        for (Minion minion : board.get(affectedRow)) {
            if (minion.attackDamage > maxAttack) {
                maxAttack = minion.health;
                maxATKMinionIdx = idx;
            }
            idx++;
        }
        board.get(affectedRow).get(maxATKMinionIdx).isFrozen = true;
    }
}
