package fileio_copy;

import fileio.ActionsInput;
import fileio.GameInput;
import fileio.StartGameInput;

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
}
