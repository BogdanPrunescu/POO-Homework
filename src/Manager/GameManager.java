package Manager;

import Environment.Environment;
import Environment.Firestorm;
import Environment.HeardHound;
import Environment.Winterfell;
import Heros.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.*;
import fileio_copy.*;
import Minions.*;

import java.util.ArrayList;
import java.util.Random;

import static java.util.Collections.min;
import static java.util.Collections.shuffle;

public class GameManager {

    public final int INVALID_CASE = -1;
    public final int maxCardsRow = 5;
    public static GameManager instance;

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    ArrayList<ArrayList<Card>> gameDecks = new ArrayList<>();
    ArrayList<Hero> heroes = new ArrayList<>();
    ArrayList<ArrayList<Minion>> board = new ArrayList<>() {
        {
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
            add(new ArrayList<Minion>());
        }
    };
    ArrayList<Integer> playerHasTank = new ArrayList<>() {
        {
            add(0);
            add(0);
        }
    };

    ArrayList<ArrayList<Card>> hands = new ArrayList<>() {
        {
            add(new ArrayList<Card>());
            add(new ArrayList<Card>());
        }
    };

    int[] mana = new int[]{0, 0};
    int startingPlayer;

    int currentPlayer;

    int currentRoundMana = 0;

    public void prepareGame(StartGameInput startGameInput, DecksInput playerOneDecks,
                                   DecksInput playerTwoDecks, ArrayNode output) {

        int pOneIdx = startGameInput.getPlayerOneDeckIdx();
        int pTwoIdx = startGameInput.getPlayerTwoDeckIdx();

        startingPlayer = startGameInput.getStartingPlayer();

        // shifting startingPlayer number to work well with gameDecks array
        startingPlayer = startingPlayer - 1;

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
                    // Simple minions
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

                }
            }
            gameDecks.add(tmp);
        }
        // shuffling decks
        shuffle(gameDecks.get(0), new Random(startGameInput.getShuffleSeed()));
        shuffle(gameDecks.get(1), new Random(startGameInput.getShuffleSeed()));

        //System.out.println("Starting Decks:" + gameDecks);

        CardInput hero = startGameInput.getPlayerOneHero();
        switch (startGameInput.getPlayerOneHero().getName()) {
            case "Lord Royce" -> heroes.add(new LordRoyce(hero));
            case "Empress Thorina" -> heroes.add(new EmpressThorina(hero));
            case "King Mudface" -> heroes.add(new KingMudface(hero));
            case "General Kocioraw" -> heroes.add(new GeneralKocioraw(hero));
        }

        hero = startGameInput.getPlayerTwoHero();
        switch (startGameInput.getPlayerTwoHero().getName()) {
            case "Lord Royce" -> heroes.add(new LordRoyce(hero));
            case "Empress Thorina" -> heroes.add(new EmpressThorina(hero));
            case "King Mudface" -> heroes.add(new KingMudface(hero));
            case "General Kocioraw" -> heroes.add(new GeneralKocioraw(hero));
        }

        currentRoundMana = 1;
    }

    public void startGame(ArrayList<ActionsInput> actions, ArrayNode output) {

        currentPlayer = startingPlayer;
        // get first card for both players
        hands.get(0).add(gameDecks.get(0).get(0));
        gameDecks.get(0).remove(0);

        hands.get(1).add(gameDecks.get(1).get(0));
        gameDecks.get(1).remove(0);

        mana[0] = currentRoundMana;
        mana[1] = currentRoundMana;

        heroes.get(0).health = 30;
        heroes.get(1).health = 30;

        for (ActionsInput action : actions) {
            //System.out.println("---HANDS AND BOARD STATUS---");
            //System.out.println(hands);
            //System.out.println(board);
            //System.out.println("----------------------------");
            //System.out.println(action.getCommand());
            switch (action.getCommand()) {
                case "endPlayerTurn":
                    //System.out.println("Ended turn for player " + (currentPlayer + 1));
                    // both players ended their turn
                    if (currentPlayer != startingPlayer) {
                        currentRoundMana = Math.min(currentRoundMana + 1, 10);
                        mana[0] = mana[0] + currentRoundMana;
                        mana[1] = mana[1] + currentRoundMana;

                        if (gameDecks.get(0).size() != 0) {
                            hands.get(0).add(gameDecks.get(0).get(0));
                            gameDecks.get(0).remove(0);
                        }

                        if (gameDecks.get(1).size() != 0) {
                            hands.get(1).add(gameDecks.get(1).get(0));
                            gameDecks.get(1).remove(0);
                        }
                    }
                    resetMinionStatesForPlayer(currentPlayer);
                    heroes.get(0).hasAttacked = false;
                    heroes.get(1).hasAttacked = false;
                    currentPlayer = Math.abs(currentPlayer - 1);
                    break;

 // !!!!! este foarte prost. trebuie fragmentat !!!!!
                case "placeCard":
                    Card card = hands.get(currentPlayer).get(action.getHandIdx());
                    if (!card.isMinion) {
                        PrintError(action, output, "Cannot place environment card on table.",
                                null, action.getPlayerIdx(), null);
                    } else if (card.mana > mana[currentPlayer]) {
                        PrintError(action, output, "Not enough mana to place card on table.",
                                null, action.getPlayerIdx(), null);
                    } else {

                        if (((Minion) card).isTank) {
                            playerHasTank.set(currentPlayer, 1);
                        }

                        if (((Minion) card).frontLiner) {
                            if (currentPlayer == 1) {
                                if (board.get(1).size() == maxCardsRow) {
                                    // Cannot place card on table since row is full.
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            null, action.getPlayerIdx(), null);
                                } else {
                                    board.get(1).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            } else if (currentPlayer == 0){
                                if (board.get(2).size() == maxCardsRow) {
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            null, action.getPlayerIdx(), null);
                                } else {
                                    board.get(2).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            }
                        } else {
                            if (currentPlayer == 1) {
                                if (board.get(0).size() == maxCardsRow) {
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            null, action.getPlayerIdx(), null);
                                } else {
                                    board.get(0).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            } else if (currentPlayer == 0){
                                if (board.get(3).size() == maxCardsRow) {
                                    // Cannot place card on table since row is full.
                                    PrintError(action, output, "Cannot place card on table since row is full.",
                                            null, action.getPlayerIdx(), null);
                                } else {
                                    board.get(3).add((Minion) card);
                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;
                                }
                            }
                        }
                    }
                    break;
                case "cardUsesAttack":
                    Coordinates attackerCard = action.getCardAttacker();
                    Coordinates attackedCard = action.getCardAttacked();
                    Minion attackerMinion = board.get(attackerCard.getX()).get(attackerCard.getY());
                    Minion attackedMinion = board.get(attackedCard.getX()).get(attackedCard.getY());

                    if (attackerMinion.isFrozen) {
                        PrintOutput printOutput = new PrintOutput("cardUsesAttack", attackerCard, attackedCard, "Attacker card is frozen.");
                        output.addPOJO(printOutput);
                    } else if (attackerMinion.hasAttacked) {
                        PrintOutput printOutput = new PrintOutput("cardUsesAttack", attackerCard, attackedCard, "Attacker card has already attacked this turn.");
                        output.addPOJO(printOutput);
                    } else {
                        if (currentPlayer == 0) {
                            // Attacked card does not belong to the enemy.
                            if (attackedCard.getX() >= 2 && attackerCard.getX() >= 2) {
                                PrintOutput printOutput = new PrintOutput("cardUsesAttack", attackerCard, attackedCard, "Attacked card does not belong to the enemy.");
                                output.addPOJO(printOutput);
                            } else {
                                // Attacked card is not of type 'Tank’.
                                if (playerHasTank.get(1) == 1 && !attackedMinion.isTank) {
                                    PrintOutput printOutput = new PrintOutput("cardUsesAttack", attackerCard, attackedCard, "Attacked card is not of type 'Tank'.");
                                    output.addPOJO(printOutput);
                                } else {
                                    attackedMinion.health = attackedMinion.health - attackerMinion.attackDamage;
                                    if (attackedMinion.health <= 0) {
                                        board.get(attackedCard.getX()).remove(attackedCard.getY());
                                    }
                                    attackerMinion.hasAttacked = true;
                                }
                            }
                        } else {
                            // Attacked card does not belong to the enemy.
                            if (attackedCard.getX() < 2 && attackerCard.getX() < 2) {
                                PrintOutput printOutput = new PrintOutput("cardUsesAttack", attackerCard, attackedCard, "Attacked card does not belong to the enemy.");
                                output.addPOJO(printOutput);
                            } else {
                                // Attacked card is not of type 'Tank’.
                                if (playerHasTank.get(0) == 1 && !attackedMinion.isTank) {
                                    PrintOutput printOutput = new PrintOutput("cardUsesAttack", attackerCard, attackedCard, "Attacked card is not of type 'Tank'.");
                                    output.addPOJO(printOutput);
                                } else {
                                    attackedMinion.health = attackedMinion.health - attackerMinion.attackDamage;
                                    if (attackedMinion.health <= 0) {
                                        board.get(attackedCard.getX()).remove(attackedCard.getY());
                                    }
                                    attackerMinion.hasAttacked = true;
                                }
                            }
                        }
                    }
                    break;
                case "cardUsesAbility":
                    attackerCard = action.getCardAttacker();
                    attackedCard = action.getCardAttacked();
                    attackerMinion = board.get(attackerCard.getX()).get(attackerCard.getY());
                    attackedMinion = board.get(attackedCard.getX()).get(attackedCard.getY());
                    // Attacker card is frozen.
                    if (attackerMinion.isFrozen) {
                        PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacker card is frozen.");
                        output.addPOJO(printOutput);
                        // Attacker card has already attacked this turn.
                    } else if (attackerMinion.hasAttacked) {
                        PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacker card has already attacked this turn.");
                        output.addPOJO(printOutput);
                    } else {
                        if (attackerMinion.name.equals("Disciple")) {
                            if (currentPlayer == 0) {
                                if (attackedCard.getX() < 2) {
                                    PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacked card does not belong to the current player.");
                                    output.addPOJO(printOutput);
                                } else {
                                    attackerMinion.Action(board, attackedCard);
                                    attackerMinion.hasAttacked = true;
                                }
                            } else {
                                if (attackedCard.getX() >= 2) {
                                    PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacked card does not belong to the current player.");
                                    output.addPOJO(printOutput);
                                } else {
                                    attackerMinion.Action(board, attackedCard);
                                    attackerMinion.hasAttacked = true;
                                }
                            }
                        } else {
                            if (currentPlayer == 0) {
                                if (attackedCard.getX() >= 2) {
                                    PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacked card does not belong to the enemy.");
                                    output.addPOJO(printOutput);
                                } else {
                                    if (playerHasTanks(1) && !attackedMinion.isTank) {
                                        PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacked card is not of type 'Tank'.");
                                        output.addPOJO(printOutput);
                                    } else {
                                        attackerMinion.Action(board, attackedCard);
                                        attackerMinion.hasAttacked = true;
                                        if (attackedMinion.health <= 0) {
                                            board.get(attackedCard.getX()).remove(attackedCard.getY());
                                        }
                                    }
                                }
                            } else {
                                if (attackedCard.getX() < 2) {
                                    PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacked card does not belong to the enemy.");
                                    output.addPOJO(printOutput);
                                } else {
                                    if (playerHasTanks(0) && !attackedMinion.isTank) {
                                        PrintOutput printOutput = new PrintOutput("cardUsesAbility", attackerCard, attackedCard, "Attacked card is not of type 'Tank'.");
                                        output.addPOJO(printOutput);
                                    } else {
                                        attackerMinion.Action(board, attackedCard);
                                        attackerMinion.hasAttacked = true;
                                        if (attackedMinion.health <= 0) {
                                            board.get(attackedCard.getX()).remove(attackedCard.getY());
                                        }
                                    }
                                }
                            }
                        }

                    }
                    break;
                case "useEnvironmentCard" :
                    card = hands.get(currentPlayer).get(action.getHandIdx());
                    if (!card.isEnvironment) {
                        PrintError(action, output, "Chosen card is not of type environment.", null, action.getHandIdx(), action.getAffectedRow());
                    } else if (card.mana > mana[currentPlayer]) {
                        PrintError(action, output, "Not enough mana to use environment card.", null, action.getHandIdx(), action.getAffectedRow());
                    } else {
                        if (currentPlayer == 1) {
                            if (action.getAffectedRow() < 2) {
                                PrintError(action, output, "Chosen row does not belong to the enemy.", null, action.getHandIdx(), action.getAffectedRow());
                            } else {
                                if (card.getName().equals("Heart Hound")) {
                                    if (board.get(3 - action.getAffectedRow()).size() == 5) {
                                        PrintError(action, output, "Cannot steal enemy card since the player's row is full.", null, action.getHandIdx(), action.getAffectedRow());
                                    } else {
                                        ((HeardHound) card).houndAction(board, action.getAffectedRow());

                                        hands.get(currentPlayer).remove(action.getHandIdx());
                                        mana[currentPlayer] = mana[currentPlayer] - card.mana;

                                    }
                                } else {
                                    ((Environment) card).Action(board, action.getAffectedRow());

                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;

                                }
                            }
                        } else if (currentPlayer == 0){
                            if (action.getAffectedRow() >= 2) {
                                PrintError(action, output, "Chosen row does not belong to the enemy.", null, action.getHandIdx(), action.getAffectedRow());
                            } else {
                                if (card.getName().equals("Heart Hound")) {
                                    if (board.get(3 - action.getAffectedRow()).size() == 5) {
                                        PrintError(action, output, "Cannot steal enemy card since the player's row is full.", null, action.getHandIdx(), action.getAffectedRow());
                                    } else {
                                        ((HeardHound) card).houndAction(board, action.getAffectedRow());

                                        hands.get(currentPlayer).remove(action.getHandIdx());
                                        mana[currentPlayer] = mana[currentPlayer] - card.mana;

                                    }
                                } else {
                                    ((Environment) card).Action(board, action.getAffectedRow());

                                    hands.get(currentPlayer).remove(action.getHandIdx());
                                    mana[currentPlayer] = mana[currentPlayer] - card.mana;

                                }
                            }
                        }
                    }
                    break;
                case "useAttackHero":
                    attackerCard = action.getCardAttacker();
                    attackerMinion = board.get(attackerCard.getX()).get(attackerCard.getY());

                    if (attackerMinion.isFrozen) {
                        PrintOutput printOutput = new PrintOutput("useAttackHero", attackerCard, null, "Attacker card is frozen.");
                        output.addPOJO(printOutput);
                    } else if (attackerMinion.hasAttacked) {
                        PrintOutput printOutput = new PrintOutput("useAttackHero", attackerCard, null, "Attacker card has already attacked this turn.");
                        output.addPOJO(printOutput);
                    } else {
                        if (currentPlayer == 0) {
                            if (playerHasTanks(1)) {
                                PrintOutput printOutput = new PrintOutput("useAttackHero", attackerCard, null, "Attacked card is not of type 'Tank'.");
                                output.addPOJO(printOutput);
                            } else {
                                heroes.get(1).health = heroes.get(1).health - attackerMinion.attackDamage;
                                attackerMinion.hasAttacked = true;
                                if (heroes.get(1).health <= 0) {
                                    PrintOutput printOutput = new PrintOutput("Player one killed the enemy hero.");
                                    output.addPOJO(printOutput);
                                    AppManager.instance.playerOneWins++;
                                }
                            }
                        } else {
                            if (playerHasTanks(0)) {
                                PrintOutput printOutput = new PrintOutput("useAttackHero", attackerCard, null, "Attacked card is not of type 'Tank'.");
                                output.addPOJO(printOutput);
                            } else {
                                heroes.get(0).health = heroes.get(0).health - attackerMinion.attackDamage;
                                attackerMinion.hasAttacked = true;
                                if (heroes.get(0).health <= 0) {
                                    PrintOutput printOutput = new PrintOutput("Player two killed the enemy hero.");
                                    output.addPOJO(printOutput);
                                    AppManager.instance.playerTwoWins++;
                                }
                            }
                        }
                    }
                    break;
                case "useHeroAbility":
                    int affectedRow = action.getAffectedRow();
                    if (mana[currentPlayer] < heroes.get(currentPlayer).mana) {
                        // Not enough mana to use hero's ability.
                        PrintOutput printOutput = new PrintOutput("useHeroAbility", null, null, affectedRow, "Not enough mana to use hero's ability.");
                        output.addPOJO(printOutput);
                    } else if (heroes.get(currentPlayer).hasAttacked){
                        // Hero has already attacked this turn.
                        PrintOutput printOutput = new PrintOutput("useHeroAbility", null, null, affectedRow, "Hero has already attacked this turn.");
                        output.addPOJO(printOutput);
                    } else {
                        if (heroes.get(currentPlayer).name.equals("Lord Royce") || heroes.get(currentPlayer).name.equals("Empress Thorina")) {
                            if (currentPlayer == 0) {
                                if (affectedRow >= 2) {
                                    // Selected row does not belong to the enemy.
                                    PrintOutput printOutput = new PrintOutput("useHeroAbility", null, null, affectedRow, "Selected row does not belong to the enemy.");
                                    output.addPOJO(printOutput);
                                } else {
                                    heroes.get(currentPlayer).heroAction(board, affectedRow);
                                    mana[currentPlayer] -= heroes.get(currentPlayer).mana;
                                    heroes.get(currentPlayer).hasAttacked = true;
                                }
                            } else {
                                if (affectedRow < 2) {
                                    // Selected row does not belong to the enemy.
                                    PrintOutput printOutput = new PrintOutput("useHeroAbility", null, null, affectedRow, "Selected row does not belong to the enemy.");
                                    output.addPOJO(printOutput);
                                } else {
                                    heroes.get(currentPlayer).heroAction(board, affectedRow);
                                    mana[currentPlayer] -= heroes.get(currentPlayer).mana;
                                    heroes.get(currentPlayer).hasAttacked = true;
                                }
                            }
                        } else {
                            if (currentPlayer == 0) {
                                if (affectedRow < 2) {
                                    // Selected row does not belong to the current player.
                                    PrintOutput printOutput = new PrintOutput("useHeroAbility", null, null, affectedRow, "Selected row does not belong to the current player.");
                                    output.addPOJO(printOutput);
                                } else {
                                    heroes.get(currentPlayer).heroAction(board, affectedRow);
                                    mana[currentPlayer] -= heroes.get(currentPlayer).mana;
                                    heroes.get(currentPlayer).hasAttacked = true;
                                }
                            } else {
                                if (affectedRow >= 2) {
                                    // Selected row does not belong to the current player.
                                    PrintOutput printOutput = new PrintOutput("useHeroAbility", null, null, affectedRow, "Selected row does not belong to the current player.");
                                    output.addPOJO(printOutput);
                                } else {
                                    heroes.get(currentPlayer).heroAction(board, affectedRow);
                                    mana[currentPlayer] -= heroes.get(currentPlayer).mana;
                                    heroes.get(currentPlayer).hasAttacked = true;
                                }
                            }
                        }
                    }
                    break;
                default:
                DebugCommands.printOutput(action, output);
            }
        }
    }

    public void resetMinionStatesForPlayer(int currentPlayer) {
        if (currentPlayer == 0) {
            for (int i = 2; i < 4; i++) {
                for (Minion minion : board.get(i)) {
                    minion.isFrozen = false;
                    minion.hasAttacked = false;
                }
            }
        } else {
            for (int i = 0; i < 2; i++) {
                for (Minion minion : board.get(i)) {
                    minion.isFrozen = false;
                    minion.hasAttacked = false;
                }
            }
        }
    }

    public boolean playerHasTanks(int Player) {
        if (Player == 0) {
            for (Minion cards : board.get(2)) {
                if (cards.isTank) {
                    return true;
                }
            }
        } else {
            for (Minion cards : board.get(1)) {
                if (cards.isTank) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void PrintError(ActionsInput action, ArrayNode output, String error, Integer playerIdx, Integer handIdx, Integer affectedRow) {
        PrintOutput printOutput = new PrintOutput(action.getCommand(), playerIdx, handIdx, affectedRow, error);
        output.addPOJO(printOutput);
    }
}
