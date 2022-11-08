package fileio_copy;

import fileio.CardInput;
import fileio.StartGameInput;

public class StartGame {
    private int playerOneDeckIdx;
    private int playerTwoDeckIdx;
    private int shuffleSeed;
    private Card playerOneHero;
    private Card playerTwoHero;
    private int startingPlayer;

    public StartGame(StartGameInput startGameInput) {
        this.playerOneDeckIdx = startGameInput.getPlayerOneDeckIdx();
        this.playerTwoDeckIdx = startGameInput.getPlayerTwoDeckIdx();
        this.shuffleSeed = startGameInput.getShuffleSeed();
        this.playerOneHero =  new Card(startGameInput.getPlayerOneHero());
        this.playerTwoHero = new Card(startGameInput.getPlayerTwoHero());
        this.startingPlayer = startGameInput.getStartingPlayer();

    }
}
