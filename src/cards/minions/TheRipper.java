package cards.minions;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class TheRipper extends Minion {

    public TheRipper(final CardInput cards) {
        super(cards, true, true, false);
    }

    /**
     * TheRipper -> -2 ATK to a minion
     * @param board current game board
     * @param target get minion's coordinated that the action will take effect on
     */
    @Override
    public void action(final ArrayList<ArrayList<Minion>> board, final Coordinates target) {
        Minion targetMinion = board.get(target.getX()).get(target.getY());
        targetMinion.setAttackDamage(Math.max(targetMinion.getAttackDamage() - 2, 0));
    }
}
