package Manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.DecksInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

import fileio_copy.*;

public class AppManager {

    public static AppManager instance = new AppManager();
    private AppManager() {}

    private Input input;
    private ArrayNode output;

    private Decks playerOneDecks;
    private Decks playerTwoDecks;

    private ArrayList<Game> games = new ArrayList<Game>();

    public void startApp(Input input, ArrayNode output) {

        playerOneDecks = new Decks(input.getPlayerOneDecks());
        playerTwoDecks = new Decks(input.getPlayerTwoDecks());

        for (GameInput games_elem : input.getGames()) {
            games.add(new Game(games_elem));
        }

        for (Game game : games) {
            GameManager.instance.startGame(game, playerOneDecks, playerTwoDecks, output);
        }
    }

}
