package manager;

import cards.environment.Environment;
import cards.environment.Firestorm;
import cards.environment.HeardHound;
import cards.environment.Winterfell;
import cards.Card;
import cards.heros.*;
import com.fasterxml.jackson.databind.node.ArrayNode;

import fileio.*;
import cards.minions.*;
import fileio.StartGameInput;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.shuffle;

public final class GameManager {

    private static GameManager instance;

    /**
     * return a new instance or return the current instance if it already exists
     */
    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    private ArrayList<ArrayList<Card>> gameDecks = new ArrayList<>();
    private ArrayList<Hero> heroes = new ArrayList<>();
    private ArrayList<ArrayList<Minion>> board = new ArrayList<>() {
        {
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
        }
    };

    private ArrayList<ArrayList<Card>> hands = new ArrayList<>() {
        {
            add(new ArrayList<Card>());
            add(new ArrayList<Card>());
        }
    };
    private int startingPlayer;

    private int currentPlayer;

    private int currentRoundMana = 0;

    private static final int HEROHEALTH = 30;

    private static final int MAXMANA = 10;

    public static void setInstance(final GameManager instance) {
        GameManager.instance = instance;
    }

    /**
     * Make preparations for every game
     * @param startGameInput details about the game that will run
     * @param playerOneDecks decks of the first player
     * @param playerTwoDecks decks of the second player
     * @param output
     */
    public void prepareGame(final StartGameInput startGameInput, final DecksInput playerOneDecks,
                            final DecksInput playerTwoDecks, final ArrayNode output) {

        int pOneIdx = startGameInput.getPlayerOneDeckIdx();
        int pTwoIdx = startGameInput.getPlayerTwoDeckIdx();

        setStartingPlayer(startGameInput.getStartingPlayer());

        // shifting startingPlayer number to work well with implementation
        setStartingPlayer(getStartingPlayer() - 1);

        ArrayList<DecksInput> playerDecks = new ArrayList<DecksInput>() {
            {
                add(playerOneDecks);
                add(playerTwoDecks);
            }
        };
        // assign the decks of each player
        for (int i = 0; i < 2; i++) {
            ArrayList<Card> tmp = new ArrayList<>();
            for (CardInput cards : playerDecks.get(i).getDecks().get(i == 0 ? pOneIdx : pTwoIdx)) {
                switch (cards.getName()) {
                    // Simple cards.minions
                    case "Sentinel" -> tmp.add(new Sentinel(cards));
                    case "Berserker" -> tmp.add(new Berserker(cards));
                    case "Goliath" -> tmp.add(new Goliath(cards));
                    case "Warden" -> tmp.add(new Warden(cards));


                    // Minions with abilities
                    case "Miraj" -> tmp.add(new Miraj(cards));
                    case "The Ripper" -> tmp.add(new TheRipper(cards));
                    case "Disciple" -> tmp.add(new Disciple(cards));
                    case "The Cursed One" -> tmp.add(new TheCursedOne(cards));


                    // Environment cards
                    case "Firestorm" -> tmp.add(new Firestorm(cards));
                    case "Winterfell" -> tmp.add(new Winterfell(cards));
                    case "Heart Hound" -> tmp.add(new HeardHound(cards));
                    default -> { }
                }
            }
            getGameDecks().add(tmp);
        }
        // shuffling decks
        shuffle(getGameDecks().get(0), new Random(startGameInput.getShuffleSeed()));
        shuffle(getGameDecks().get(1), new Random(startGameInput.getShuffleSeed()));

        //System.out.println("Starting Decks:" + gameDecks);

        CardInput hero = startGameInput.getPlayerOneHero();
        switch (startGameInput.getPlayerOneHero().getName()) {
            case "Lord Royce" -> getHeroes().add(new LordRoyce(hero));
            case "Empress Thorina" -> getHeroes().add(new EmpressThorina(hero));
            case "King Mudface" -> getHeroes().add(new KingMudface(hero));
            case "General Kocioraw" -> getHeroes().add(new GeneralKocioraw(hero));
            default -> { }
        }

        hero = startGameInput.getPlayerTwoHero();
        switch (startGameInput.getPlayerTwoHero().getName()) {
            case "Lord Royce" -> getHeroes().add(new LordRoyce(hero));
            case "Empress Thorina" -> getHeroes().add(new EmpressThorina(hero));
            case "King Mudface" -> getHeroes().add(new KingMudface(hero));
            case "General Kocioraw" -> getHeroes().add(new GeneralKocioraw(hero));
            default -> { }
        }

        setCurrentRoundMana(1);
    }

    /**
     * Starts the current game
     * @param actions get all the actions for the current game
     * @param output
     */
    public void startGame(final ArrayList<ActionsInput> actions, final ArrayNode output) {

        setCurrentPlayer(getStartingPlayer());
        // get first card for both players
        getHands().get(0).add(getGameDecks().get(0).get(0));
        getGameDecks().get(0).remove(0);

        getHands().get(1).add(getGameDecks().get(1).get(0));
        getGameDecks().get(1).remove(0);

        getHeroes().get(0).setPlayingMana(getCurrentRoundMana());
        getHeroes().get(1).setPlayingMana(getCurrentRoundMana());

        getHeroes().get(0).setHealth(HEROHEALTH);
        getHeroes().get(1).setHealth(HEROHEALTH);

        for (ActionsInput action : actions) {
            switch (action.getCommand()) {

                case "endPlayerTurn":
                    endPlayerTurn();
                    break;

                case "placeCard":
                    Card card = getHands().get(getCurrentPlayer()).get(action.getHandIdx());
                    Minion.tryPlaceCard(card, action, output);
                    break;

                case "cardUsesAttack":
                    Minion.tryAttackCard(action, output);
                    break;

                case "cardUsesAbility":
                    Minion.tryCardUsesAbility(action, output);
                    break;

                case "useEnvironmentCard" :
                    Card environment = getHands().get(getCurrentPlayer()).get(action.getHandIdx());
                    Environment.tryUseEnvironmentCard(environment, action, output);
                    break;

                case "useAttackHero":
                    Minion.tryAttackHero(action, output);
                    break;

                case "useHeroAbility":
                    Hero.tryUseHeroAbility(action, output);
                    break;

                default:
                DebugCommands.printOutput(action, output);
            }
        }
    }

    /**
     * Resets minions status (isFrozen and hasAttacked) for the player that
     * ends their turn
     * @param player currentPlayer
     */
    public void resetMinionStatesForPlayer(final int player) {
        if (player == 0) {
            for (int i = 2; i < 4; i++) {
                for (Minion minion : getBoard().get(i)) {
                    minion.setIsFrozen(false);
                    minion.setHasAttacked(false);
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                for (Minion minion : getBoard().get(i)) {
                    minion.setIsFrozen(false);
                    minion.setHasAttacked(false);
                }
            }
        }
    }

    /**
     * At the end of a turn, reset minion states and switch currentPlayer
     * to the next player. If both players ended their turn draw a card
     * for every player and give the players mana.
     */
    public void endPlayerTurn() {
        // both players ended their turn
        if (getCurrentPlayer() != getStartingPlayer()) {
            setCurrentRoundMana(Math.min(getCurrentRoundMana() + 1, MAXMANA));

            getHeroes().get(0).setPlayingMana(getHeroes().get(0).
                    getPlayingMana() + getCurrentRoundMana());
            getHeroes().get(1).setPlayingMana(getHeroes().get(1).
                    getPlayingMana() + getCurrentRoundMana());

            if (getGameDecks().get(0).size() != 0) {
                getHands().get(0).add(getGameDecks().get(0).get(0));
                getGameDecks().get(0).remove(0);
            }

            if (getGameDecks().get(1).size() != 0) {
                getHands().get(1).add(getGameDecks().get(1).get(0));
                getGameDecks().get(1).remove(0);
            }
        }

        resetMinionStatesForPlayer(getCurrentPlayer());
        getHeroes().get(0).setHasAttacked(false);
        getHeroes().get(1).setHasAttacked(false);

        setCurrentPlayer(Math.abs(getCurrentPlayer() - 1));
    }

    public ArrayList<ArrayList<Card>> getGameDecks() {
        return gameDecks;
    }

    public void setGameDecks(final ArrayList<ArrayList<Card>> gameDecks) {
        this.gameDecks = gameDecks;
    }

    public ArrayList<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(final ArrayList<Hero> heroes) {
        this.heroes = heroes;
    }

    public ArrayList<ArrayList<Minion>> getBoard() {
        return board;
    }

    public ArrayList<ArrayList<Card>> getHands() {
        return hands;
    }

    public void setHands(final ArrayList<ArrayList<Card>> hands) {
        this.hands = hands;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(final int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public int getCurrentRoundMana() {
        return currentRoundMana;
    }

    public void setCurrentRoundMana(final int currentRoundMana) {
        this.currentRoundMana = currentRoundMana;
    }
}
