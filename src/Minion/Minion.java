package Minion;

import fileio.CardInput;
import fileio.Coordinates;
import fileio_copy.Card;

import java.util.ArrayList;

public abstract class Minion extends Card {


    public int attackDamage;
    public int health;

    public boolean hasAttacked = false;
    public boolean isFrozen = false;
    public boolean hasAbility = false;
    public boolean frontLiner;

    public void Action(ArrayList<ArrayList<Card>> board, Coordinates target) {}

    public Minion(CardInput card, boolean hasAbility, boolean frontLiner) {
        super(card, false, true);
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
        this.health = card.getHealth();
        this.hasAbility = hasAbility;
        this.frontLiner = frontLiner;
    }
}
