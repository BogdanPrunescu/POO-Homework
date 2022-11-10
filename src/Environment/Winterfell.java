package Environment;

import Minions.Minion;
import fileio.CardInput;

import java.util.ArrayList;

public class Winterfell extends Environment{

    public Winterfell(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void Action(ArrayList<ArrayList<Minion>> board, int affectedRow) {
        for (Minion minion : board.get(affectedRow)) {
            minion.isFrozen = true;
        }
    }
}
