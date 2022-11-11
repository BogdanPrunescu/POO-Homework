package Manager;

import Environment.*;
import Minions.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio_copy.Card;

import java.util.ArrayList;

public class DebugCommands {

    public static void printOutput(ActionsInput action, ArrayNode output) {

        Card card;
        PrintOutput printOutput;
        ArrayList<Card> cardArrayList;
        int targetPlayer;

        switch (action.getCommand()) {
            case "getPlayerDeck":
                cardArrayList = new ArrayList<>(GameManager.instance.gameDecks.get(action.getPlayerIdx() - 1));
                printOutput = new PrintOutput("getPlayerDeck", action.getPlayerIdx(), cardArrayList);
                output.addPOJO(printOutput);
                break;
            case "getPlayerHero":
                card = GameManager.instance.heroes.get(action.getPlayerIdx() - 1);
                printOutput = new PrintOutput("getPlayerHero", action.getPlayerIdx(), card);
                output.addPOJO(printOutput);
                break;

            case "getPlayerTurn":
                int playerTurn = GameManager.instance.currentPlayer;
                printOutput = new PrintOutput("getPlayerTurn", playerTurn + 1);
                output.addPOJO(printOutput);
                break;
            case "getCardsInHand":
                targetPlayer = action.getPlayerIdx();
                cardArrayList = new ArrayList<>();
                for (Card cards : GameManager.instance.hands.get(action.getPlayerIdx() - 1)) {
                    if (cards.isMinion) {
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
                int mana = GameManager.instance.mana[targetPlayer - 1];
                printOutput = new PrintOutput("getPlayerMana", targetPlayer, mana);
                output.addPOJO(printOutput);
                break;
            case "getCardsOnTable":
                ArrayList<ArrayList<Minion>> board_copy = copy_board(GameManager.instance.board);
                printOutput = new PrintOutput("getCardsOnTable", board_copy);
                output.addPOJO(printOutput);
                break;
            case "getEnvironmentCardsInHand":
                cardArrayList = new ArrayList<>();
                ArrayList<Card> hand = GameManager.instance.hands.get(action.getPlayerIdx() - 1);
                for (Card cards : hand) {
                    if (cards.isEnvironment) {
                        cardArrayList.add(cards);
                    }
                }
                printOutput = new PrintOutput("getEnvironmentCardsInHand", action.getPlayerIdx() , cardArrayList);
                output.addPOJO(printOutput);
                break;
            case "getCardAtPosition":
                card = new Minion(GameManager.instance.board.get(action.getX()).get(action.getY()));
                printOutput = new PrintOutput("getCardAtPosition", card);
                output.addPOJO(printOutput);
                break;
            case "getFrozenCardsOnTable":
                cardArrayList = new ArrayList<>();
                for (ArrayList<Minion> rows : GameManager.instance.board) {
                    for (Minion minion : rows) {
                        if (minion.isFrozen) {
                            cardArrayList.add(minion);
                        }
                    }
                }
                printOutput = new PrintOutput("getFrozenCardsOnTable", cardArrayList);
                output.addPOJO(printOutput);
                break;

        }
    }

    public static ArrayList<ArrayList<Minion>> copy_board(ArrayList<ArrayList<Minion>> src) {

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
