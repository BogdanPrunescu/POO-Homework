package Manager;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio_copy.*;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public class GameManager {

    public static GameManager instance = new GameManager();
    private GameManager() {}

    ArrayList<ArrayList<Card>> gameDecks = new ArrayList<ArrayList<Card>>();

    public void startGame(Game game, Decks playerOneDecks,
                          Decks playerTwoDecks, ArrayNode output) {

        int pOneIdx = game.getStartGame().getPlayerOneDeckIdx();
        int pTwoIdx = game.getStartGame().getPlayerTwoDeckIdx();

        gameDecks.add(playerOneDecks.getDecks().get(pOneIdx));
        gameDecks.add(playerTwoDecks.getDecks().get(pTwoIdx));

        int startingPlayer = game.getStartGame().getStartingPlayer();

        // shifting startingPlayer number to work well with gameDecks array
        startingPlayer = startingPlayer - 1;

        Random random = new Random(game.getStartGame().getShuffleSeed());
        shuffle(gameDecks.get(0), random);
        shuffle(gameDecks.get(1), random);

        for (Actions action : game.getActions()) {

            switch (action.getCommand()) {
                case "endPlayerTurn":
                    if (startingPlayer == 0)
                        startingPlayer = 1;
                    else startingPlayer = 0;
                    break;
                case "placeCard":

            }
        }
    }
}
