package Environment;

import fileio.CardInput;
import fileio_copy.Card;

import java.util.ArrayList;

public class Environment extends Card {
    public Environment(CardInput cardInput) {
        super(cardInput, true, false);
    }
    void Action(ArrayList<ArrayList<Card>> board, int affectedRow) {}
}
