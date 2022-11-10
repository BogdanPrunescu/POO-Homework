package Minions;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Discipline extends Minion {

    public Discipline(CardInput cards) {
        super(cards, true, false, false);
    }

    @Override
    public void Action(ArrayList<ArrayList<Minion>> board, Coordinates target) {
        board.get(target.getX()).get(target.getY()).health = board.get(target.getX()).get(target.getY()).health + 2;

    }
}
