package cards.environment;

import manager.GameManager;
import cards.minions.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public class Firestorm extends Environment {

    public Firestorm(final CardInput cardInput) {
        super(cardInput);
    }

    /**
     * Firestrom ability -> -1 ATK to all the cards.minions on a row
     * @param affectedRow get affectedRow that the action will take effect on
     */
    @Override
    public void action(final int affectedRow) {
        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();
        ArrayList<Minion> row = board.get(affectedRow);
        for (int i = 0; i < row.size(); i++) {
            row.get(i).setHealth(row.get(i).getHealth() - 1);
            if (row.get(i).getHealth() == 0) {
                row.remove(i);
                i--;
            }
        }
    }
}
