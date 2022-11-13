package manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import fileio.Coordinates;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class PrintOutput {

    private String gameEnded = null;

    private String command = null;

    private Coordinates cardAttacker = null;

    private Coordinates cardAttacked = null;

    private Integer playerIdx = null;

    private Integer handIdx = null;

    private Integer affectedRow = null;

    private Integer x = null;

    private Integer y = null;

    private Object output = null;

    private String error = null;

    public PrintOutput(final String command, final int playerIdx, final Object output) {
        this.setCommand(command);
        this.setPlayerIdx(playerIdx);
        this.setOutput(output);
    }

    public PrintOutput(final String command, final Object output) {
        this.setCommand(command);
        this.setOutput(output);
    }

    public PrintOutput(final String command, final Integer playerIdx, final Integer handIdx,
                       final Integer affectedRow, final String error) {
        this.setCommand(command);
        this.setPlayerIdx(playerIdx);
        this.setError(error);
        this.setHandIdx(handIdx);
        this.setAffectedRow(affectedRow);
    }

    public PrintOutput(final String command, final Coordinates cardAttacker,
                       final Coordinates cardAttacked, final String error) {
        this.setCommand(command);
        this.setCardAttacker(cardAttacker);
        this.setCardAttacked(cardAttacked);
        this.setError(error);
    }

    public PrintOutput(final String command, final Integer x,
                       final Integer y, final Object object) {
        this.setCommand(command);
        this.setx(x);
        this.sety(y);
        this.setOutput(object);
    }

    public PrintOutput(final String gameEnded) {
        this.setGameEnded(gameEnded);
    }

    public String getGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(final String gameEnded) {
        this.gameEnded = gameEnded;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public Integer getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final Integer playerIdx) {
        this.playerIdx = playerIdx;
    }

    public Integer getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(final Integer handIdx) {
        this.handIdx = handIdx;
    }

    public Integer getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(final Integer affectedRow) {
        this.affectedRow = affectedRow;
    }

    public Object getOutput() {
        return output;
    }

    public void setOutput(final Object output) {
        this.output = output;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }

    public Integer getx() {
        return x;
    }

    public void setx(final Integer x) {
        this.x = x;
    }

    public Integer gety() {
        return y;
    }

    public void sety(final Integer y) {
        this.y = y;
    }
}
