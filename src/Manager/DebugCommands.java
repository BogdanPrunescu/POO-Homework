package Manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio_copy.Card;

import java.util.ArrayList;

public class DebugCommands {

    public static void printOutput(ActionsInput action, ArrayNode output) {

        Card hero;
        PrintOutput printOutput;
        ArrayList<Card> deck;

        switch (action.getCommand()) {
            case "getPlayerDeck":
                deck = GameManager.instance.gameDecks.get(action.getPlayerIdx() - 1);
                printOutput = new PrintOutput("getPlayerDeck", action.getPlayerIdx(), deck);
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
        }
    }
}
