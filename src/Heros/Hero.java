package Heros;

import Minions.Minion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileio.CardInput;
import fileio_copy.Card;

import java.util.ArrayList;

@JsonPropertyOrder( {"mana", "description", "colors", "name", "health"} )
public class Hero extends Card {

    public int health;

    public void setPlayingMana(int playingMana) {
        this.playingMana = playingMana;
    }
    @JsonIgnore
    public int playingMana;

    @JsonIgnore
    public boolean hasAttacked = false;

    public Hero(CardInput card) {
        super(card, false, false);
    }

    public Hero(Hero hero) {
        super(hero);
        this.health = hero.health;
    }

    public void heroAction(ArrayList<ArrayList<Minion>> board, int affectedRow) {}
}
