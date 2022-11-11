package Manager;

import Environment.Environment;
import Environment.Firestorm;
import Environment.HeardHound;
import Environment.Winterfell;
import Heros.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.*;
import fileio_copy.*;
import Minions.*;
import org.xml.sax.ErrorHandler;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.min;
import static java.util.Collections.shuffle;

public class GameManager {

    public final int INVALID_CASE = -1;
    public final int maxCardsRow = 5;
    public static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    ArrayList<ArrayList<Card>> gameDecks = new ArrayList<>();
    ArrayList<Card> heroes = new ArrayList<>();
    ArrayList<ArrayList<Minion>> board = new ArrayList<>() {
        {
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
        }
    };
    ArrayList<Integer> playerHasTank = new ArrayList<>() {
        {
            add(0);
            add(0);
        }
    };

    ArrayList<ArrayList<Card>> hands = new ArrayList<>() {
        {
            add(new ArrayList<Card>());
            add(new ArrayList<Card>());
        }
    };

    int[] mana = new int[]{0, 0};
    int startingPlayer;

    int currentPlayer;

    int currentRoundMana = 0;

    public void prepareGame(StartGameInput startGameInput, DecksInput playerOneDecks,
                                   DecksInput playerTwoDecks, ArrayNode output) {

        int pOneIdx = startGameInput.getPlayerOneDeckIdx();
        int pTwoIdx = startGameInput.getPlayerTwoDeckIdx();

        startingPlayer = startGameInput.getStartingPlayer();

        // shifting startingPlayer number to work well with gameDecks array
        startingPlayer = startingPlayer - 1;

        ArrayList<DecksInput> playerDecks = new ArrayList<DecksInput>() {
            {
                add(playerOneDecks);
                add(playerTwoDecks);
            }
        };
        // assign the decks of each player
        for (int i = 0; i < 2; i++) {
            ArrayList<Card> tmp = new ArrayList<>();
            for (CardInput cards : playerDecks.get(i).getDecks().get(i == 0 ? pOneIdx : pTwoIdx)) {
                switch (cards.getName()) {
                    // Simple minions
                    case "Sentinel" -> tmp.add(new Sentinel(cards));
                    case "Berserker" -> tmp.add(new Berserker(cards));
                    case "Goliath" -> tmp.add(new Goliath(cards));
                    case "Warden" -> tmp.add(new Warden(cards));


                    // Minions with abilities
                    case "Miraj" -> tmp.add(new Miraj(cards));
                    case "The Ripper" -> tmp.add(new TheRipper(cards));
                    case "Disciple" -> tmp.add(new Disciple(cards));
                    case "The Cursed One" -> tmp.add(new TheCursedOne(cards));


                    // Environment cards
                    case "Firestorm" -> tmp.add(new Firestorm(cards));
                    case "Winterfell" -> tmp.add(new Winterfell(cards));
                    case "Heart Hound" -> tmp.add(new HeardHound(cards));

                }
            }
            gameDecks.add(tmp);
        }
        // shuffling decks
        shuffle(gameDecks.get(0), new Random(startGameInput.getShuffleSeed()));
        shuffle(gameDecks.get(1), new Random(startGameInput.getShuffleSeed()));

        CardInput hero = startGameInput.getPlayerOneHero();
        switch (startGameInput.getPlayerOneHero().getName()) {
            case "Lord Royce" -> heroes.add(new LordRoyce(hero));
            case "Empress Thorina" -> heroes.add(new EmpressThorina(hero));
            case "King Mudface" -> heroes.add(new KingMudface(hero));
            case "General Kocioraw" -> heroes.add(new GeneralKocioraw(hero));
        }

        hero = startGameInput.getPlayerTwoHero();
        switch (startGameInput.getPlayerTwoHero().getName()) {
            case "Lord Royce" -> heroes.add(new LordRoyce(hero));
            case "Empress Thorina" -> heroes.add(new EmpressThorina(hero));
            case "King Mudface" -> heroes.add(new KingMudface(hero));
            case "General Kocioraw" -> heroes.add(new GeneralKocioraw(hero));
        }

        currentRoundMana = 1;
    }

    public void startGame(ArrayList<ActionsInput> actions, ArrayNode output) {

        currentPlayer = startingPlayer;
        // get first card for both players
        hands.get(0).add(gameDecks.get(0).get(0));
        gameDecks.get(0).remove(0);

        hands.get(1).add(gameDecks.get(1).get(0));
        gameDecks.get(1).remove(0);

        mana[0] = currentRoundMana;
        mana[1] = currentRoundMana;

        for (ActionsInput action : actions) {
            // System.out.println(hands);
            // System.out.println(board);
            // System.out.println(action.getCommand());
            switch (action.getCommand()) {
                case "endPlayerTurn":
                    // both players ended their turn
                    if (currentPlayer != startingPlayer) {
                        currentRoundMana = Math.min(currentRoundMana + 1, 10);
                        mana[0] = mana[0] + currentRoundMana;
                        mana[1] = mana[1] + currentRoundMana;

                        resetMinionstates();
                        hands.get(0).add(gameDecks.get(0).get(0));
                        gameDecks.get(0).remove(0);

                        hands.get(1).add(gameDecks.get(1).get(0));
                        gameDecks.get(1).remove(0);
                    }
                    currentPlayer = Math.abs(currentPlayer - 1);
                    break;

 // !!!!! este foarte prost. trebuie fragmentat !!!!!
                case "placeCard":
                    Card card = hands.get(currentPlayer).get(action.getHandIdx());
                    if (!card.isMinion) {
                        PrintError(action, output, "Cannot place environment card on table.",
                                action.getPlayerIdx(), null, null);
                    } else if (card.mana > mana[currentPlayer]) {
                        PrintError(action, output, "Not enough mana to place card on table.",
                                action.getPlayerIdx(), null, null);
                    } else {

                        if (((Minion) card).isTank) {
                            playerHasTank.set(currentPlayer, 1);
                        }

                        if (((Minion) card).frontLiner) {
                            if (currentPlayer == 1) {
                                if (board.get(1).size() == maxCardsRow) {
                                    // Cannot place card on table since row is full.
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            action.getPlayerIdx(), null, null);
                                } else {
                                    board.get(1).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            } else if (currentPlayer == 0){
                                if (board.get(2).size() == maxCardsRow) {
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            action.getPlayerIdx(), null, null);
                                } else {
                                    board.get(2).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            }
                        } else {
                            if (currentPlayer == 1) {
                                if (board.get(0).size() == maxCardsRow) {
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            action.getPlayerIdx(), null, null);
                                } else {
                                    board.get(0).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            } else if (currentPlayer == 0){
                                if (board.get(3).size() == maxCardsRow) {
                                    // Cannot place card on table since row is full.
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            action.getPlayerIdx(), null, null);
                                } else {
                                    board.get(3).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            }
                        }
                    }
                    break;
                case "cardUsesAttack":
                    Coordinates attackerCard = action.getCardAttacker();
                    Coordinates attackedCard = action.getCardAttacked();
                    Minion attackerMinion = board.get(attackerCard.getX()).get(attackedCard.getY());
                    Minion attackedMinion = board.get(attackedCard.getX()).get(attackedCard.getY());

                    if (GameExceptions.TestforMinionstate(attackerMinion) != INVALID_CASE) {
                    } else {
                        boolean ownerisEnemy = isCurrentPlayersCard(attackedCard, currentPlayer);
                        if (ownerisEnemy) {
                            // Attacked card does not belong to the enemy.
                            if (attackedCard.getX() >= 2) {

                            } else {
                                // Attacked card is not of type 'Tank’.
                                if (playerHasTank.get(1) == 1 && !attackedMinion.isTank) {

                                } else {
                                    attackedMinion.health = attackedMinion.health - attackerMinion.health;
                                    if (attackedMinion.health <= 0) {
                                        board.get(attackedCard.getX()).remove(attackedCard.getY());
                                    }
                                }
                            }
                        } else {
                            // Attacked card does not belong to the enemy.
                            if (attackedCard.getX() < 2) {

                            } else {
                                // Attacked card is not of type 'Tank’.
                                if (playerHasTank.get(0) == 1 && !attackedMinion.isTank) {

                                } else {
                                    attackedMinion.health = attackedMinion.health - attackerMinion.health;
                                    if (attackedMinion.health <= 0) {
                                        board.get(attackedCard.getX()).remove(attackedCard.getY());
                                    }
                                }
                            }
                        }
                    }
                    break;
                case "cardUsesAbility":
                    attackerCard = action.getCardAttacker();
                    attackedCard = action.getCardAttacked();
                    attackerMinion = board.get(attackerCard.getX()).get(attackedCard.getY());
                    attackedMinion = board.get(attackedCard.getX()).get(attackedCard.getY());

                    // Attacker card is frozen.
                    if (attackerMinion.isFrozen) {

                        // Attacker card has already attacked this turn.
                    } else if (attackerMinion.hasAttacked) {

                    } else {

                    }
                    break;
                case "useEnvironmentCard" :
                    card = hands.get(currentPlayer).get(action.getHandIdx());
                    if (!card.isEnvironment) {
                        PrintError(action, output, "Chosen card is not of type environment.", null, action.getHandIdx(), action.getAffectedRow());
                    } else if (card.mana > mana[currentPlayer]) {
                        PrintError(action, output, "Not enough mana to use environment card.", null, action.getHandIdx(), action.getAffectedRow());
                    } else {
                        if (currentPlayer == 1) {
                            if (action.getAffectedRow() < 2) {
                                PrintError(action, output, "Chosen row does not belong to the enemy.", null, action.getHandIdx(), action.getAffectedRow());
                            } else {
                                if (card.getName().equals("Heart Hound")) {
                                    if (board.get(3 - action.getAffectedRow()).size() == 5) {
                                        PrintError(action, output, "Cannot steal enemy card since the player's row is full.", null, action.getHandIdx(), action.getAffectedRow());
                                    } else {
                                        ((HeardHound) card).houndAction(board, action.getAffectedRow());

                                        hands.get(currentPlayer).remove(action.getHandIdx());
                                        mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                    }
                                } else {
                                    ((Environment) card).Action(board, action.getAffectedRow());

                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            }
                        } else if (currentPlayer == 0){
                            if (action.getAffectedRow() >= 2) {
                                PrintError(action, output, "Chosen row does not belong to the enemy.", null, action.getHandIdx(), action.getAffectedRow());
                            } else {
                                if (card.getName().equals("Heart Hound")) {
                                    if (board.get(3 - action.getAffectedRow()).size() == 5) {
                                        PrintError(action, output, "Cannot steal enemy card since the player's row is full.", null, action.getHandIdx(), action.getAffectedRow());
                                    } else {
                                        ((HeardHound) card).houndAction(board, action.getAffectedRow());

                                        hands.get(currentPlayer).remove(action.getHandIdx());
                                        mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                    }
                                } else {
                                    ((Environment) card).Action(board, action.getAffectedRow());

                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            }
                        }
                    }
                default:
                DebugCommands.printOutput(action, output);
            }
        }
    }

    /*
    If the card belongs to the player return true, else return false
    */
    public boolean isCurrentPlayersCard(Coordinates cardCoords, int Player) {
        if (Player == 1) {
            if (cardCoords.getX() >= 2) {
                return true;
            } else return false;
        } else {
            if (cardCoords.getX() < 2) {
                return true;
            } else return false;
        }
    }

    public void resetMinionstates() {
        for (int i = 0; i < 3; i++) {
            for (Minion minion : board.get(i)) {
                minion.isFrozen = false;
                minion.hasAttacked = false;
            }
        }
    }

    public void PrintError(ActionsInput action, ArrayNode output, String error, Integer playerIdx, Integer handIdx, Integer affectedRow) {
        PrintOutput printOutput = new PrintOutput(action.getCommand(), playerIdx, handIdx, affectedRow, error);
        output.addPOJO(printOutput);
    }
}
