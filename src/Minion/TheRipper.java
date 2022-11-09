package Minion;

import fileio.CardInput;
import fileio.Coordinates;
import fileio_copy.Card;

import java.util.ArrayList;

public class TheRipper extends Minion{

    public TheRipper(CardInput cards) {
        super(cards, true, true);
    }

    @Override
    public void Action(ArrayList<ArrayList<Card>> board, Coordinates target) {

    }
}
