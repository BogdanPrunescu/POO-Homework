package fileio_copy;

import fileio.ActionsInput;
import fileio.GameInput;

import java.util.ArrayList;

public class Game {
    private StartGame startGame;
    private ArrayList<Actions> actions;

    public Game(GameInput gameInput) {
        this.startGame = new StartGame(gameInput.getStartGame());
        for (ActionsInput actions : gameInput.getActions()) {
            this.actions.add(new Actions(actions));
        }
    }

    public StartGame getStartGame() {
        return startGame;
    }

    public void setStartGame() {
        this.startGame = startGame;
    }

    public ArrayList<Actions> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Actions> actions) {
        this.actions = actions;
    }
}
