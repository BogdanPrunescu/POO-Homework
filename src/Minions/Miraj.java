package Minions;

import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class Miraj extends Minion{

    public Miraj(CardInput cards) {
        super(cards, true, true, false);
    }

    @Override
    public void Action(ArrayList<ArrayList<Minion>> board, Coordinates target) {
        int tmp = this.health;
        this.health = board.get(target.getX()).get(target.getY()).health;
        board.get(target.getX()).get(target.getY()).health = tmp;
    }
}
