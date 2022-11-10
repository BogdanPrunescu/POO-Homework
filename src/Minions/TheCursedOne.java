package Minions;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class TheCursedOne extends Minion {

    public TheCursedOne(CardInput cards) {
        super(cards, true, false, false);
    }

    @Override
    public void Action(ArrayList<ArrayList<Minion>> board, Coordinates target) {
        int tmp = board.get(target.getX()).get(target.getY()).attackDamage;
        board.get(target.getX()).get(target.getY()).attackDamage = board.get(target.getX()).get(target.getY()).health;
        board.get(target.getX()).get(target.getY()).health = tmp;
    }
}
