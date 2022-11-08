package fileio_copy;

import fileio.ActionsInput;
import fileio.Coordinates;

public class Actions {

    private String command;
    private int handIdx;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private int affectedRow;
    private int playerIdx;
    private int x;
    private int y;

    public Actions(ActionsInput actionsInput) {
        this.command = actionsInput.getCommand();
        this.handIdx = actionsInput.getHandIdx();
        this.cardAttacker = actionsInput.getCardAttacker();
        this.cardAttacked = actionsInput.getCardAttacked();
        this.affectedRow = actionsInput.getAffectedRow();
        this.playerIdx = actionsInput.getPlayerIdx();
        this.x = actionsInput.getX();
        this.y = actionsInput.getY();
    }
}
