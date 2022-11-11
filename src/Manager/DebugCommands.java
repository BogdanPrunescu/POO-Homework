package Manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio_copy.Card;

import java.util.ArrayList;

public class DebugCommands {

    public static void printOutput(ActionsInput action, ArrayNode output) {

        Card hero;
        PrintOutput printOutput;
        ArrayList<Card> cardArrayList;
        int targetPlayer;

        switch (action.getCommand()) {
            case "getPlayerDeck":
                cardArrayList = (ArrayList<Card>) GameManager.instance.gameDecks.get(action.getPlayerIdx() - 1).clone();
                printOutput = new PrintOutput("getPlayerDeck", action.getPlayerIdx(), cardArrayList);
                output.addPOJO(printOutput);
                break;
            case "getPlayerHero":
                hero = GameManager.instance.heroes.get(action.getPlayerIdx() - 1);
                printOutput = new PrintOutput("getPlayerHero", action.getPlayerIdx(), hero);
                output.addPOJO(printOutput);
                break;

            case "getPlayerTurn":
                int playerTurn = GameManager.instance.currentPlayer;
                printOutput = new PrintOutput("getPlayerTurn", playerTurn + 1);
                output.addPOJO(printOutput);
                break;
            case "getCardsInHand":
                targetPlayer = action.getPlayerIdx();
                cardArrayList = (ArrayList<Card>) GameManager.instance.hands.get(action.getPlayerIdx() - 1).clone();
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
                ArrayList<ArrayList<Card>> board_copy = (ArrayList<ArrayList<Card>>) GameManager.getInstance().board.clone();
                printOutput = new PrintOutput("getCardsOnTable", board_copy);
                output.addPOJO(printOutput);
        }
    }
}
