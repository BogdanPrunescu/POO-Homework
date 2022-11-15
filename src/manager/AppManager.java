package manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.DecksInput;
import fileio.GameInput;
import fileio.Input;

import java.util.ArrayList;

public final class AppManager {

    private static AppManager instance = null;

    /**
     * get AppManager reference
     */
    public static AppManager getInstance() {
        if (instance == null) {
            setInstance(new AppManager());
        }
        return instance;
    }

    public static void setInstance(final AppManager instance) {
        AppManager.instance = instance;
    }

    private DecksInput playerOneDecks;
    private DecksInput playerTwoDecks;

    private ArrayList<GameInput> games = new ArrayList<GameInput>();

    private int totalGamesPlayed = 0;

    private int playerOneWins = 0;
    private int playerTwoWins = 0;

    /**
     * Start the program
     * @param input program input
     * @param output games output
     */
    public void startApp(final Input input, final ArrayNode output) {

        playerOneDecks = input.getPlayerOneDecks();
        playerTwoDecks = input.getPlayerTwoDecks();

        for (GameInput gamesElem : input.getGames()) {
            games.add(gamesElem);
        }

        for (GameInput game : games) {
            GameManager.getInstance().prepareGame(game.getStartGame(),
                    playerOneDecks, playerTwoDecks, output);
            GameManager.getInstance().startGame(game.getActions(), output);
            GameManager.setInstance(null);
            setTotalGamesPlayed(0);
        }
    }
    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(final int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getPlayerOneWins() {
        return playerOneWins;
    }

    public void setPlayerOneWins(final int playerOneWins) {
        this.playerOneWins = playerOneWins;
    }

    public int getPlayerTwoWins() {
        return playerTwoWins;
    }

    public void setPlayerTwoWins(final int playerTwoWins) {
        this.playerTwoWins = playerTwoWins;
    }
}
