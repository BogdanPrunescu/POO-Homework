package Heros;

import fileio.CardInput;
import Minions.*;

import java.util.ArrayList;

public class GeneralKocioraw extends Hero {

    public GeneralKocioraw(CardInput cardInput) {
        super(cardInput);
    }

    @Override
    public void heroAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {
        ArrayList<Minion> row = board.get(affectedRow);
        for (int i = 0; i < row.size(); i++) {
            row.get(i).attackDamage = row.get(i).attackDamage + 1;
        }
    }
}
