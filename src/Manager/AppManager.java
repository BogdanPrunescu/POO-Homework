package Manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.DecksInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

import fileio_copy.*;

public class AppManager {

    public static AppManager instance = null;
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }

        return instance;
    }

    private Input input;
    private ArrayNode output;

    private DecksInput playerOneDecks;
    private DecksInput playerTwoDecks;

    private ArrayList<GameInput> games = new ArrayList<GameInput>();

    public void startApp(Input input, ArrayNode output) {

        playerOneDecks = input.getPlayerOneDecks();
        playerTwoDecks = input.getPlayerTwoDecks();

        for (GameInput games_elem : input.getGames()) {
            games.add(games_elem);
        }

        for (GameInput game : games) {
            GameManager instance = GameManager.getInstance();
            GameManager.instance.prepareGame(game.getStartGame(), playerOneDecks, playerTwoDecks, output);
            GameManager.instance.startGame(game.getActions(), output);
            GameManager.instance = null;
        }
    }
}
