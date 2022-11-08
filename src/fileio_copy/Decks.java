package fileio_copy;

import fileio.CardInput;
import fileio.DecksInput;

import java.util.ArrayList;

public class Decks {

    private int nrCardsInDeck;
    private int nrDecks;
    private ArrayList<ArrayList<Card>> decks;

    public Decks(DecksInput decksInput) {
        this.nrCardsInDeck = decksInput.getNrCardsInDeck();
        this.nrDecks = decksInput.getNrDecks();

        decks = new ArrayList<ArrayList<Card>>();

        for (ArrayList<CardInput> decks_cnt : decksInput.getDecks()) {
            ArrayList<Card> tmp = new ArrayList<Card>();
            for (CardInput cards : decks_cnt) {
                tmp.add(new Card(cards));
            }
            decks.add(tmp);
        }
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }

    public int getNrDecks() {
        return nrDecks;
    }

    public void setNrDecks(int nrDecks) {
        this.nrDecks = nrDecks;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public void setDecks(ArrayList<ArrayList<Card>> decks) {
        this.decks = decks;
    }
}
