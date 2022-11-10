package Heros;

import Minions.Minion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileio.CardInput;
import fileio_copy.Card;

import java.util.ArrayList;

@JsonPropertyOrder( {"mana", "description", "colors", "name", "health"} )
public class Hero extends Card {

    public int health = 30;

    @JsonIgnore
    public int playingMana;

    public Hero(CardInput card) {
        super(card, false, false);
    }

    public void heroAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {}
}
