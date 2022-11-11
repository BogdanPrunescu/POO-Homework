package Manager;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrintOutput {

    public String command = null;

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int playerIdx = 0;

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

    /*
    public PrintOutput(String command, int playerIdx, String error) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.error = error;
    }
     */
}
