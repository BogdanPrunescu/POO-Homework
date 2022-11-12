package cards.minions;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class TheCursedOne extends Minion {

    public TheCursedOne(final CardInput cards) {
        super(cards, true, false, false);
    }

    /**
     * The CursedOne -> swap a minion's health and attack
     * @param board current game board
     * @param target get minion's coordinated that the action will take effect on
     */
    @Override
    public void action(final ArrayList<ArrayList<Minion>> board, final Coordinates target) {
        Minion targetMinion = board.get(target.getX()).get(target.getY());
        int tmp = targetMinion.getAttackDamage();

        targetMinion.setAttackDamage(targetMinion.getHealth());

        board.get(target.getX()).get(target.getY()).setHealth(tmp);
    }
}
