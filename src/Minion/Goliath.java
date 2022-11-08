package Minion;

import java.util.ArrayList;

public class Goliath extends Minion{

    public Goliath(int mana, int health,
                   int attackDamage, String description,
                   String colors, ArrayList<String> name, boolean hasAbility) {
        super(mana, health, attackDamage, description, colors, name, hasAbility);
    }
}
