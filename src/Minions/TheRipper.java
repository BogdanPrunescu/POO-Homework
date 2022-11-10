package Minions;

import fileio.CardInput;
import fileio.Coordinates;
import fileio_copy.Card;

import java.util.ArrayList;

public class TheRipper extends Minion{

    public TheRipper(CardInput cards) {
        super(cards, true, true, false);
    }

    // Weak Knees
    @Override
    public void Action(ArrayList<ArrayList<Minion>> board, Coordinates target) {
        board.get(target.getX()).get(target.getY()).attackDamage = board.get(target.getX()).get(target.getY()).attackDamage - 2;
    }
}
