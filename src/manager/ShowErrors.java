package manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Coordinates;

public class ShowErrors {

    public static void throwCardError(ActionsInput action, String errorType, ArrayNode output) {
        PrintOutput printOutput;

        Integer playerIdx = action.getPlayerIdx();

        Integer handIdx = action.getHandIdx();

        Integer affectedRow = action.getAffectedRow();

        String command = action.getCommand();

        if (action.getCommand().equals("placeCard")) {
            printOutput = new PrintOutput(command, null, handIdx, null, errorType);
            output.addPOJO(printOutput);
        } else {
            printOutput = new PrintOutput(command, null, handIdx, affectedRow, errorType);
            output.addPOJO(printOutput);
        }
    }

    public static void throwATKError(ActionsInput action, String errorType, ArrayNode output) {
        PrintOutput printOutput;

        Coordinates cardAttacked = action.getCardAttacked();

        Coordinates cardAttacker = action.getCardAttacker();

        String command = "cardUsesAttack";

        printOutput = new PrintOutput(command, cardAttacker, cardAttacked, errorType);
        output.addPOJO(printOutput);
    }

    public static void throwAbilityError(ActionsInput action, String errorType, ArrayNode output) {
        PrintOutput printOutput;

        Coordinates cardAttacked = action.getCardAttacked();

        Coordinates cardAttacker = action.getCardAttacker();

        String command = "cardUsesAbility";

        printOutput = new PrintOutput(command, cardAttacker, cardAttacked, errorType);
        output.addPOJO(printOutput);
    }

    public static void throwATKHeroError(ActionsInput action, String errorType, ArrayNode output) {
        PrintOutput printOutput;

        Coordinates cardAttacked = action.getCardAttacked();

        Coordinates cardAttacker = action.getCardAttacker();

        String command = "useAttackHero";

        printOutput = new PrintOutput(command, cardAttacker, cardAttacked, errorType);
        output.addPOJO(printOutput);
    }

    public static void throwUseHeroAbilityError(ActionsInput action, String errorType, ArrayNode output) {
        PrintOutput printOutput;

        Integer affectedRow = action.getAffectedRow();

        String command = "useHeroAbility";

        printOutput = new PrintOutput(command,null, null, affectedRow, errorType);
        output.addPOJO(printOutput);
    }
}
