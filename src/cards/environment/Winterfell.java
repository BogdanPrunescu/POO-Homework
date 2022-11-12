package cards.environment;

import manager.GameManager;
import cards.minions.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public class Winterfell extends Environment {

    public Winterfell(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Winterfell -> freeze all cards.minions on a row
     * @param affectedRow get affectedRow that the action will take effect on
     */
    @Override
    public void action(final int affectedRow) {
        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();
        for (Minion minion : board.get(affectedRow)) {
            minion.setIsFrozen(true);
        }
    }
}
