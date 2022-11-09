package fileio_copy;

import fileio.CardInput;

import java.util.ArrayList;

public class Card {
    private String description;
    private ArrayList<String> colors;
    private String name;

    public int mana;
    public boolean isEnvironment;
    public boolean isMinion;

    public Card(CardInput card, boolean isEnvironment, boolean isMinion) {
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.mana = card.getMana();
        this.isEnvironment = isEnvironment;
        this.isMinion = isMinion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
