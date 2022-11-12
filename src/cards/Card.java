package cards;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CardInput;

import java.util.ArrayList;

public abstract class Card {

    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    @JsonIgnore
    private boolean isEnvironment;
    @JsonIgnore
    private boolean isMinion;

    public Card(final CardInput card, final boolean isEnvironment, final boolean isMinion) {
        this.description = card.getDescription();
        this.colors = card.getColors();
        this.name = card.getName();
        this.mana = card.getMana();
        this.isEnvironment = isEnvironment;
        this.isMinion = isMinion;
    }

    public Card() { }

    public Card(final Card card) {
        this.mana = card.getMana();
        this.colors = card.getColors();
        this.isMinion = card.isMinion();
        this.isEnvironment = card.isEnvironment();
        this.description = card.getDescription();
        this.name = card.getName();
    }

    /**
     * Description getter. Safe to use for minion and environment children
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Colors getter. Safe to use for minion and environment children
     * @return
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Name getter. Safe to use for minion and environment children
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Mana getter. Safe to use for minion and environment children
     * @return
     */
    public int getMana() {
        return mana;
    }

    /**
     * isEnvironment getter. Safe to use for minion and environment children
     * @return
     */
    @JsonIgnore
    public boolean isEnvironment() {
        return isEnvironment;
    }

    /**
     * isMinion getter. Safe to use for minion and environment children
     * @return
     */
    @JsonIgnore
    public boolean isMinion() {
        return isMinion;
    }
}
