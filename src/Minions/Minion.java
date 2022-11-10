package Minions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fileio.CardInput;
import fileio.Coordinates;
import fileio_copy.Card;

import java.util.ArrayList;


@JsonPropertyOrder( {"mana", "attackDamage", "health", "description", "colors", "name"} )
public abstract class Minion extends Card {

    public int attackDamage;
    public int health;
    @JsonIgnore
    public boolean hasAttacked;
    @JsonIgnore
    public boolean isFrozen;
    @JsonIgnore
    public boolean hasAbility;
    @JsonIgnore
    public boolean frontLiner;

    @JsonIgnore
    public boolean isTank = false;

    public void Action(ArrayList<ArrayList<Minion>> board, Coordinates target) {}

    public Minion(CardInput card, boolean hasAbility, boolean frontLiner, boolean isTank) {
        super(card, false, true);
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.hasAbility = hasAbility;
        this.frontLiner = frontLiner;
        this.isTank = isTank;
    }
}
