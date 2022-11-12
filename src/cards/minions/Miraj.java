package cards.minions;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Miraj extends Minion {

    public Miraj(final CardInput cards) {
        super(cards, true, true, false);
    }

    /**
     * Miraj -> swap his health with another minion
     * @param board current game board
     * @param target get minion's coordinated that the action will take effect on
     */
    @Override
    public void action(final ArrayList<ArrayList<Minion>> board, final Coordinates target) {
        Minion targetMinion = board.get(target.getX()).get(target.getY());
        int tmp = this.getHealth();
        this.setHealth(targetMinion.getHealth());
        targetMinion.setHealth(tmp);
    }
}
