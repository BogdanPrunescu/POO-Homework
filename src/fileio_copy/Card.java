package fileio_copy;

import fileio.CardInput;

import java.util.ArrayList;

public class Card {

    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public Card(CardInput card) {
        this.mana = card.getMana();
        this.attackDamage = attackDamage;
        this.health = health;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }
}
