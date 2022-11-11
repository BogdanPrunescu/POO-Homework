package Environment;

import Minions.Minion;
import fileio.CardInput;
import fileio.Coordinates;
import fileio_copy.Card;

import java.util.ArrayList;
import java.util.Collections;

public class Environment extends Card {
    public Environment(CardInput cardInput) {
        super(cardInput, true, false);
    }
    public void Action(ArrayList<ArrayList<Minion>> board, int affectedRow) {}

    public void houndAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {}
}
