package Manager;

import Environment.Environment;
import Environment.Firestorm;
import Environment.HeardHound;
import Environment.Winterfell;
import Minion.Hero.EmpressThorina;
import Minion.Hero.GeneralKocioraw;
import Minion.Hero.KingMudface;
import Minion.Hero.LordRoyce;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.*;
import fileio_copy.*;
import Minion.*;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public class GameManager {

    public static GameManager instance = new GameManager();
    private GameManager() {}

    ArrayList<ArrayList<Card>> gameDecks = new ArrayList<ArrayList<Card>>();
    ArrayList<Card> heroes = new ArrayList<Card>();
    ArrayList<Integer> mana = new ArrayList<Integer>();
    int currentMana = 0;

    ArrayList<ArrayList<Card>> board = new ArrayList<ArrayList<Card>>();

    int startingPlayer;

    public void startGame(GameInput game, DecksInput playerOneDecks,
                          DecksInput playerTwoDecks, ArrayNode output) {

        int pOneIdx = game.getStartGame().getPlayerOneDeckIdx();
        int pTwoIdx = game.getStartGame().getPlayerTwoDeckIdx();

        startingPlayer = game.getStartGame().getStartingPlayer();

        // shifting startingPlayer number to work well with gameDecks array
        startingPlayer = startingPlayer - 1;

        // assign each card their respective class
        for (int i = 0; i < 2; i++) {
            ArrayList<Card> tmp = new ArrayList<Card>();
            for (CardInput cards : playerOneDecks.getDecks().get(i == 0 ? pOneIdx : pTwoIdx)) {
                switch (cards.getName()) {
                    // Simple minions
                    case "Sentinel":
                        tmp.add(new Sentinel(cards));
                        break;
                    case "Berserker":
                        tmp.add(new Berserker(cards));
                        break;
                    case "Goliath":
                        tmp.add(new Goliath(cards));
                        break;
                    case "Warden":
                        tmp.add(new Warden(cards));
                        break;

                    // Minions with abilities
                    case "Miraj":
                        tmp.add(new Miraj(cards));
                        break;
                    case "The Ripper":
                        tmp.add(new TheRipper(cards));
                        break;
                    case "Discipline":
                        tmp.add(new Discipline(cards));
                        break;
                    case "The Cursed One":
                        tmp.add(new TheCursedOne(cards));
                        break;

                    // Environment cards
                    case "Firestorm":
                        tmp.add(new Firestorm(cards));
                        break;
                    case "Winterfell":
                        tmp.add(new Winterfell(cards));
                        break;
                    case "Heart Hound":
                        tmp.add(new HeardHound(cards));
                        break;

                    // Heroes
                    case "Lord Royce":
                        heroes.add(i, new LordRoyce(cards));
                        break;
                    case "Empress Thorina":
                        heroes.add(i, new EmpressThorina(cards));
                        break;
                    case "King Mudface":
                        heroes.add(i, new KingMudface(cards));
                        break;
                    case "General Kocioraw":
                        heroes.add(i, new GeneralKocioraw(cards));
                        break;
                }
            }
            gameDecks.add(tmp);
        }

        // shuffling decks
        Random random = new Random(game.getStartGame().getShuffleSeed());
        shuffle(gameDecks.get(0), random);
        shuffle(gameDecks.get(1), random);

        currentMana = 1;
        actionManager(game);
    }

    public void actionManager(GameInput game) {

        int currentPlayer = startingPlayer;

        for (ActionsInput action : game.getActions()) {

            switch (action.getCommand()) {
                case "endPlayerTurn":
                    // both players ended their turn
                    if (currentPlayer != startingPlayer)
                        currentMana = currentPlayer + 1;
                    if (currentPlayer == 0)
                        currentPlayer = 1;
                    else currentPlayer = 0;
                    break;
                case "placeCard":
                    Card card = gameDecks.get(currentPlayer).get(action.getHandIdx());
                    if (!card.isEnvironment) {
                        // Cannot place environment card on table.
                    } else if (card.mana > mana.get(currentPlayer)) {
                        // Not enough mana to place card on table.
                    } else if ((Minion) card.frontLiner)
            }
        }
    }
}
