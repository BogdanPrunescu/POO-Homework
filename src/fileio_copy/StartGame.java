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

    public int getPlayerOneDeckIdx() {
        return playerOneDeckIdx;
    }

    public void setPlayerOneDeckIdx(int playerOneDeckIdx) {
        this.playerOneDeckIdx = playerOneDeckIdx;
    }

    public int getPlayerTwoDeckIdx() {
        return playerTwoDeckIdx;
    }

    public void setPlayerTwoDeckIdx(int playerTwoDeckIdx) {
        this.playerTwoDeckIdx = playerTwoDeckIdx;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public Card getPlayerOneHero() {
        return playerOneHero;
    }

    public void setPlayerOneHero(Card playerOneHero) {
        this.playerOneHero = playerOneHero;
    }

    public Card getPlayerTwoHero() {
        return playerTwoHero;
    }

    public void setPlayerTwoHero(Card playerTwoHero) {
        this.playerTwoHero = playerTwoHero;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
