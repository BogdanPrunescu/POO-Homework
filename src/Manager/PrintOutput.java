package Manager;

import com.fasterxml.jackson.annotation.JsonInclude;
import fileio.Coordinates;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrintOutput {


    public String command = null;

    public Coordinates cardAttacker = null;

    public Coordinates cardAttacked = null;

    public Integer playerIdx = null;

    public Integer handIdx = null;

    public Integer affectedRow = null;

    public Object output = null;

    public String error = null;

    public PrintOutput(String command, int playerIdx, Object output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public PrintOutput(String command, Object output) {
        this.command = command;
        this.output = output;
    }

    public PrintOutput(String command, Integer playerIdx, Integer handIdx, Integer affectedRow, String error) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.error = error;
        this.handIdx = handIdx;
        this.affectedRow = affectedRow;
    }

    public PrintOutput(String command, Coordinates cardAttacker, Coordinates cardAttacked, String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.cardAttacked = cardAttacked;
        this.error = error;
    }
}
