package manager;

import cards.environment.Environment;
import cards.Card;
import cards.heros.Hero;
import cards.minions.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;

public final class DebugCommands {

    private DebugCommands() { }

    /**
     * prints Debug commands output
     * @param action current command
     * @param output
     */
    public static void printOutput(final ActionsInput action, final ArrayNode output) {

        Card card;
        PrintOutput printOutput;
        ArrayList<Card> cardArrayList;
        int targetPlayer;

        switch (action.getCommand()) {
            case "getPlayerDeck":
                cardArrayList = new ArrayList<>(GameManager.getInstance().
                        getGameDecks().get(action.getPlayerIdx() - 1));
                printOutput = new PrintOutput("getPlayerDeck",
                        action.getPlayerIdx(), cardArrayList);
                output.addPOJO(printOutput);
                break;
            case "getPlayerHero":
                card = new Hero(GameManager.getInstance().
                        getHeroes().get(action.getPlayerIdx() - 1));
                printOutput = new PrintOutput("getPlayerHero", action.getPlayerIdx(), card);
                output.addPOJO(printOutput);
                break;

            case "getPlayerTurn":
                int playerTurn = GameManager.getInstance().getCurrentPlayer();
                printOutput = new PrintOutput("getPlayerTurn", playerTurn + 1);
                output.addPOJO(printOutput);
                break;
            case "getCardsInHand":
                targetPlayer = action.getPlayerIdx();
                cardArrayList = new ArrayList<>();
                for (Card cards
                        : GameManager.getInstance().getHands().get(action.getPlayerIdx() - 1)) {
                    if (cards.isMinion()) {
                        card = new Minion((Minion) cards);
                        cardArrayList.add(card);
                    } else {
                        card = new Environment((Environment) cards);
                        cardArrayList.add(card);
                    }
                }
                printOutput = new PrintOutput("getCardsInHand", targetPlayer, cardArrayList);
                output.addPOJO(printOutput);
                break;
            case "getPlayerMana":
                targetPlayer = action.getPlayerIdx();
                Hero hero = GameManager.getInstance().getHeroes().get(targetPlayer - 1);
                Integer mana = hero.getPlayingMana();
                printOutput = new PrintOutput("getPlayerMana", targetPlayer, mana);
                output.addPOJO(printOutput);
                break;
            case "getCardsOnTable":
                ArrayList<ArrayList<Minion>> boardCopy =
                        copyBoard(GameManager.getInstance().getBoard());
                printOutput = new PrintOutput("getCardsOnTable", boardCopy);
                output.addPOJO(printOutput);
                break;
            case "getEnvironmentCardsInHand":
                cardArrayList = new ArrayList<>();
                ArrayList<Card> hand =
                        GameManager.getInstance().getHands().get(action.getPlayerIdx() - 1);
                for (Card cards : hand) {
                    if (cards.isEnvironment()) {
                        cardArrayList.add(cards);
                    }
                }
                printOutput = new PrintOutput("getEnvironmentCardsInHand",
                        action.getPlayerIdx(), cardArrayList);
                output.addPOJO(printOutput);
                break;
            case "getCardAtPosition":
                card = new Minion(GameManager.getInstance().
                        getBoard().get(action.getX()).get(action.getY()));
                printOutput = new PrintOutput("getCardAtPosition", card);
                output.addPOJO(printOutput);
                break;
            case "getFrozenCardsOnTable":
                cardArrayList = new ArrayList<>();
                for (ArrayList<Minion> rows : GameManager.getInstance().getBoard()) {
                    for (Minion minion : rows) {
                        if (minion.isFrozen()) {
                            cardArrayList.add(minion);
                        }
                    }
                }
                printOutput = new PrintOutput("getFrozenCardsOnTable", cardArrayList);
                output.addPOJO(printOutput);
                break;
            case "getPlayerOneWins":
                printOutput = new PrintOutput("getPlayerOneWins",
                        AppManager.getInstance().getPlayerOneWins());
                output.addPOJO(printOutput);
                break;
            case "getPlayerTwoWins":
                printOutput = new PrintOutput("getPlayerTwoWins",
                        AppManager.getInstance().getPlayerTwoWins());
                output.addPOJO(printOutput);
                break;
            case "getTotalGamesPlayed":
                int gamesPlayed = AppManager.getInstance().getPlayerOneWins()
                        + AppManager.getInstance().getPlayerTwoWins();
                printOutput = new PrintOutput("getTotalGamesPlayed", gamesPlayed);
                output.addPOJO(printOutput);
                break;
            default:
        }
    }

    /**
     * makes a clone of a board
     * @param src source
     * @return clone
     */
    public static ArrayList<ArrayList<Minion>> copyBoard(final ArrayList<ArrayList<Minion>> src) {

        ArrayList dest = new ArrayList<>();
        for (ArrayList<Minion> rows : src) {
            ArrayList<Minion> tmp = new ArrayList<>();
            for (Minion minion : rows) {
                tmp.add(new Minion(minion));
            }
            dest.add(tmp);
        }
        return dest;
    }
}
