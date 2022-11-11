package Manager;

import Minions.Minion;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
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
                cardArrayList = new ArrayList<>(GameManager.instance.hands.get(action.getPlayerIdx() - 1));
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
                ArrayList<ArrayList<Minion>> board_copy = new ArrayList<>();
                copy_board(GameManager.instance.board, board_copy);
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
                        if (minion.isFrozen == true) {
                            cardArrayList.add(minion);
                        }
                    }
                }
                printOutput = new PrintOutput("getFrozenCardsOnTable", cardArrayList);
                output.addPOJO(printOutput);
                break;

        }
    }

    public static void copy_board(ArrayList<ArrayList<Minion>> src, ArrayList<ArrayList<Minion>> dest) {

        int idx = 0;
        for (ArrayList<Minion> rows : src) {
            dest.add(new ArrayList<Minion>());
            dest.get(idx).addAll(src.get(idx));
            idx++;
        }
    }
}
