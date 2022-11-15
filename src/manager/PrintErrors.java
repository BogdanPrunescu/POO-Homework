package manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Coordinates;

public final class PrintErrors {

    private PrintErrors() { }

    /**
     * Function that sends to output any errors that happened when a card was being used.
     * @param action the action given at input
     * @param errorType the error that occurred
     * @param output where the error will be sent
     */
    public static void throwCardError(final ActionsInput action,
                                     final String errorType, final ArrayNode output) {
        PrintOutput printOutput;

        Integer handIdx = action.getHandIdx();

        Integer affectedRow = action.getAffectedRow();

        String command = action.getCommand();

        if (action.getCommand().equals("placeCard")) {
            printOutput = new PrintOutput(command, null, handIdx, null, errorType);
            output.addPOJO(printOutput);
        } else if (action.getCommand().equals("useEnvironmentCard")) {
            printOutput = new PrintOutput(command, null, handIdx, affectedRow, errorType);
            output.addPOJO(printOutput);
        }
    }

    /**
     * Function that sends to output any errors that happened when a card has attacked.
     * @param action the action given at input
     * @param errorType the error that occurred
     * @param output where the error will be sent
     */
    public static void throwATKError(final ActionsInput action,
                                     final String errorType, final ArrayNode output) {
        PrintOutput printOutput;

        Coordinates cardAttacked = action.getCardAttacked();

        Coordinates cardAttacker = action.getCardAttacker();

        String command = "cardUsesAttack";

        printOutput = new PrintOutput(command, cardAttacker, cardAttacked, errorType);
        output.addPOJO(printOutput);
    }

    /**
     * Function that sends to output any errors that happened when
     * a card has used its ability.
     * @param action the action given at input
     * @param errorType the error that occurred
     * @param output where the error will be sent
     */
    public static void throwAbilityError(final ActionsInput action,
                                         final String errorType, final ArrayNode output) {
        PrintOutput printOutput;

        Coordinates cardAttacked = action.getCardAttacked();

        Coordinates cardAttacker = action.getCardAttacker();

        String command = "cardUsesAbility";

        printOutput = new PrintOutput(command, cardAttacker, cardAttacked, errorType);
        output.addPOJO(printOutput);
    }

    /**
     * Function that sends to output any errors that happened when a card has attacked a hero.
     * @param action the action given at input
     * @param errorType the error that occurred
     * @param output where the error will be sent
     */
    public static void throwATKHeroError(final ActionsInput action,
                                         final String errorType, final ArrayNode output) {
        PrintOutput printOutput;

        Coordinates cardAttacked = action.getCardAttacked();

        Coordinates cardAttacker = action.getCardAttacker();

        String command = "useAttackHero";

        printOutput = new PrintOutput(command, cardAttacker, cardAttacked, errorType);
        output.addPOJO(printOutput);
    }

    /**
     * Function that sends to output any errors that happened when hero has used its ability.
     * @param action the action given at input
     * @param errorType the error that occurred
     * @param output where the error will be sent
     */
    public static void throwUseHeroAbilityError(final ActionsInput action,
                                                final String errorType, final ArrayNode output) {
        PrintOutput printOutput;

        Integer affectedRow = action.getAffectedRow();

        String command = "useHeroAbility";

        printOutput = new PrintOutput(command, null, null, affectedRow, errorType);
        output.addPOJO(printOutput);
    }
}
