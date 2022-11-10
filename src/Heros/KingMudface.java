package Heros;

import fileio.CardInput;
import Minions.*;

import java.util.ArrayList;

public class KingMudface extends Hero {

    public KingMudface(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void heroAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {
        ArrayList<Minion> row = board.get(affectedRow);
        for (int i = 0; i < row.size(); i++) {
            row.get(i).health = row.get(i).health + 1;
        }
    }
}
