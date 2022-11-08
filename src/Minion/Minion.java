package Minion;

import java.util.ArrayList;

public abstract class Minion {

    public int mana;
    public int health;
    public int attackDamage;

    public String description;
    public String colors;
    public ArrayList<String> name;

    public boolean hasAttacked = false;
    public boolean isFrozen = false;
    public boolean hasAbility = false;

    public void Action() {}
    public void Exceptions() {

    }

    public Minion(int mana, int health, int attackDamage,
                  String description, String colors,
                  ArrayList<String> name, boolean hasAbility) {
        this.mana = mana;
        this.health = health;
        this.attackDamage = attackDamage;
        this.description = description;
        this.colors = colors;
        this.name = name;
        this.hasAbility = hasAbility;
    }
}
