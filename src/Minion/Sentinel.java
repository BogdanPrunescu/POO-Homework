package Minion;

import java.util.ArrayList;

public class Sentinel extends Minion{
    public Sentinel(int mana, int health,
                    int attackDamage, String description,
                    String colors, ArrayList<String> name) {
        super(mana, health, attackDamage, description, colors, name, false);
    }
}
