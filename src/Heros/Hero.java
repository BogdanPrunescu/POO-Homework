package Heros;

import Minions.Minion;
import fileio.CardInput;
import fileio_copy.Card;

import java.util.ArrayList;

public class Hero extends Minion {

    public int playingMana;

    public Hero(CardInput card) {
        super(card, true, false, false);
    }

    public void heroAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {}
}
