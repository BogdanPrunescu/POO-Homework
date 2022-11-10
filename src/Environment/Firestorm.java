package Environment;

import Minions.Minion;
import fileio.CardInput;
import fileio_copy.Card;

import java.util.ArrayList;

public class Firestorm extends Environment {

    public Firestorm(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void Action(ArrayList<ArrayList<Minion>> board, int affectedRow) {
        ArrayList<Minion> row = board.get(affectedRow);
        for (int i = 0; i < row.size(); i++) {
            row.get(i).health = row.get(i).health - 1;
            if (row.get(i).health == 0) {
                row.remove(i);
                i--;
            }
        }
    }
}
