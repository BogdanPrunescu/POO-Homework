package cards.minions;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Disciple extends Minion {

    public Disciple(final CardInput cards) {
        super(cards, true, false, false);
    }

    /**
     * Discille -> +2 HP for a minion
     * @param board current game board
     * @param target get minion's coordinated that the action will take effect on
     */
    @Override
    public void action(final ArrayList<ArrayList<Minion>> board, final Coordinates target) {
        Minion targetMinion = board.get(target.getX()).get(target.getY());
        targetMinion.setHealth(targetMinion.getHealth() + 2);

    }
}
