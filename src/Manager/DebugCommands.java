package Manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

public class DebugCommands {

    public static void printOutput(ActionsInput action, ArrayNode output) {
        switch (action.getCommand()) {
            case "getPlayerDeck":

                break;
            case "getPlayerHero":
                    output.addPOJO(GameManager.instance.heroes.get(GameManager.instance.currentPlayer));
                break;

            case "getPlayerTurn":

        }
    }
}
