package cards.minions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.CardInput;
import fileio.Coordinates;
import cards.Card;
import cards.heros.Hero;

import manager.AppManager;
import manager.GameExceptions;
import manager.GameManager;
import manager.PrintOutput;
import manager.ShowErrors;

import java.util.ArrayList;


@JsonPropertyOrder({"mana", "attackDamage", "health", "description", "colors", "name"})
public class Minion extends Card {

    private int attackDamage;
    private int health;
    @JsonIgnore
    private boolean hasAttacked;
    @JsonIgnore
    private boolean isFrozen;
    @JsonIgnore
    private boolean hasAbility;
    @JsonIgnore
    private boolean frontLiner;

    @JsonIgnore
    private boolean isTank;

    /**
     *  This method is used to run the specific action of each minion card
     *
     * @param board current game board
     * @param target get minion's coordinated that the action will take effect on
     */
    public void action(final ArrayList<ArrayList<Minion>> board, final Coordinates target) { }

    public Minion(final CardInput card,
                  final boolean hasAbility, final boolean frontLiner, final boolean isTank) {
        super(card, false, true);
        this.setAttackDamage(card.getAttackDamage());
        this.setHealth(card.getHealth());
        this.setHasAbility(hasAbility);
        this.setIsFrontLiner(frontLiner);
        this.setIsTank(isTank);
    }
    public Minion() {
        super();
    }

    public Minion(final Minion minion) {
        super(minion);
        this.setAttackDamage(minion.getAttackDamage());
        this.setHealth(minion.getHealth());
        this.setHasAttacked(minion.hasAttacked());
        this.setIsFrozen(minion.isFrozen());
        this.setHasAbility(minion.hasAbility());
        this.setIsFrontLiner(minion.isFrontLiner());
        this.setIsTank(minion.isTank());
    }

    /**
     * tries if PlaceCard command gives any errors. If not, then run the command
     * @param card card that needs to be placed
     * @param action given command
     * @param output where the error will be sent
     */
    public static void tryPlaceCard(final Card card, final ActionsInput action,
                                    final ArrayNode output) {

        int currentPlayer = GameManager.getInstance().getCurrentPlayer();

        Hero hero = GameManager.getInstance().getHeroes().get(currentPlayer);

        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();

        ArrayList<ArrayList<Card>> hands = GameManager.getInstance().getHands();

        if (GameExceptions.testCardType(card) != 0) {
            ShowErrors.throwCardError(action, "Cannot place environment card on table.", output);

        } else if (!GameExceptions.playerHasEnoughMana(card, hero)) {
            ShowErrors.throwCardError(action, "Not enough mana to place card on table.", output);

        } else {
            int rowIdx = GameExceptions.placeCard((Minion) card, board, currentPlayer);
            if (rowIdx == -1) {
                ShowErrors.throwCardError(action,
                        "Cannot place card on table since row is full.", output);

            } else {
                board.get(rowIdx).add((Minion) card);
                hands.get(currentPlayer).remove(action.getHandIdx());
                hero.setPlayingMana(hero.getPlayingMana() - card.getMana());
            }
        }
    }

    /**
     * tries if tryAttackCard command gives any errors. If not, then run the command
     * @param action given command
     * @param output where the error will be sent
     */
    public static void tryAttackCard(final ActionsInput action, final ArrayNode output) {

        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();
        int currentPlayer = GameManager.getInstance().getCurrentPlayer();

        Coordinates attackerCard = action.getCardAttacker();
        Coordinates attackedCard = action.getCardAttacked();
        Minion attackerMinion = board.get(attackerCard.getX()).get(attackerCard.getY());
        Minion attackedMinion = board.get(attackedCard.getX()).get(attackedCard.getY());

        if (attackerMinion.isFrozen()) {
            ShowErrors.throwATKError(action, "Attacker card is frozen.", output);
        } else if (attackerMinion.hasAttacked()) {
            ShowErrors.throwATKError(action,
                    "Attacker card has already attacked this turn.", output);

        } else {
            if (!GameExceptions.testIfRowBelongsToEnemy(currentPlayer, attackedCard.getX())) {
                ShowErrors.throwATKError(action,
                        "Attacked card does not belong to the enemy.", output);

            } else {
                int enemyPlayer = Math.abs(currentPlayer - 1);
                if (GameExceptions.playerHasTanks(enemyPlayer) && !attackedMinion.isTank()) {
                    ShowErrors.throwATKError(action,
                            "Attacked card is not of type 'Tank'.", output);

                } else {
                    int targetHealth = attackedMinion.getHealth();
                    attackedMinion.setHealth(targetHealth - attackerMinion.getAttackDamage());
                    if (attackedMinion.getHealth() <= 0) {
                        board.get(attackedCard.getX()).remove(attackedCard.getY());
                    }
                    attackerMinion.setHasAttacked(true);
                }
            }
        }
    }

    /**
     * tries if tryCardUsesAbility command gives any errors. If not, then run the command
     * @param action given command
     * @param output where the error will be sent
     */
    public static void tryCardUsesAbility(final ActionsInput action, final ArrayNode output) {

        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();
        int currentPlayer = GameManager.getInstance().getCurrentPlayer();

        Coordinates attackerCard = action.getCardAttacker();
        Coordinates attackedCard = action.getCardAttacked();
        Minion attackerMinion = board.get(attackerCard.getX()).get(attackerCard.getY());
        Minion attackedMinion = board.get(attackedCard.getX()).get(attackedCard.getY());
        if (attackerMinion.isFrozen()) {
            ShowErrors.throwAbilityError(action, "Attacker card is frozen.", output);

        } else if (attackerMinion.hasAttacked()) {
            ShowErrors.throwAbilityError(action,
                    "Attacker card has already attacked this turn.", output);

        } else {
            if (attackerMinion.getName().equals("Disciple")) {
                if (GameExceptions.testIfRowBelongsToEnemy(currentPlayer, attackedCard.getX())) {
                    ShowErrors.throwAbilityError(action,
                            "Attacked card does not belong to the current player.", output);

                } else {
                    attackerMinion.action(board, attackedCard);
                    attackerMinion.setHasAttacked(true);
                }
            } else {
                if (!GameExceptions.testIfRowBelongsToEnemy(currentPlayer, attackedCard.getX())) {
                    ShowErrors.throwAbilityError(action,
                            "Attacked card does not belong to the enemy.", output);

                } else {
                    int enemyPlayer = Math.abs(currentPlayer - 1);
                    if (GameExceptions.playerHasTanks(enemyPlayer) && !attackedMinion.isTank()) {
                        ShowErrors.throwAbilityError(action,
                                "Attacked card is not of type 'Tank'.", output);

                    } else {
                        attackerMinion.action(board, attackedCard);
                        attackerMinion.setHasAttacked(true);

                        if (attackedMinion.getHealth() <= 0) {
                            board.get(attackedCard.getX()).remove(attackedCard.getY());
                        }
                    }
                }
            }
        }
    }

    /**
     * tries if tryAttackHero command gives any errors. If not, then run the command
     * @param action given command
     * @param output where the error will be sent
     */
    public static void tryAttackHero(final ActionsInput action, final ArrayNode output) {

        PrintOutput gameWinner;

        ArrayList<ArrayList<Minion>> board = GameManager.getInstance().getBoard();
        int currentPlayer = GameManager.getInstance().getCurrentPlayer();
        int enemyPlayer = Math.abs(currentPlayer - 1);
        Hero enemyHero = GameManager.getInstance().getHeroes().get(enemyPlayer);

        Coordinates attackerCard = action.getCardAttacker();
        Minion attackerMinion = board.get(attackerCard.getX()).get(attackerCard.getY());

        if (attackerMinion.isFrozen()) {
            ShowErrors.throwATKHeroError(action, "Attacker card is frozen.", output);
        } else if (attackerMinion.hasAttacked()) {
            ShowErrors.throwATKHeroError(action,
                    "Attacker card has already attacked this turn.", output);
        } else {

            if (GameExceptions.playerHasTanks(enemyPlayer)) {
                ShowErrors.throwATKHeroError(action,
                        "Attacked card is not of type 'Tank'.", output);
            } else {
                enemyHero.setHealth(enemyHero.getHealth() - attackerMinion.getAttackDamage());
                attackerMinion.setHasAttacked(true);
                if (enemyHero.getHealth() <= 0) {
                    if (currentPlayer == 0) {
                        gameWinner = new PrintOutput("Player one killed the enemy hero.");
                        int pOneWins = AppManager.getInstance().getPlayerOneWins();
                        AppManager.getInstance().setPlayerOneWins(pOneWins + 1);
                    } else {
                        gameWinner = new PrintOutput("Player two killed the enemy hero.");
                        int pTwoWins = AppManager.getInstance().getPlayerTwoWins();
                        AppManager.getInstance().setPlayerTwoWins(pTwoWins + 1);
                    }
                    output.addPOJO(gameWinner);
                }
            }
        }
    }

    /**
     * Safe to use for every minion
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * Safe to use for every minion
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * Safe to use for every minion
     */
    public int getHealth() {
        return health;
    }

    /**
     * Safe to use for every minion
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public boolean isFrozen() {
        return isFrozen;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public void setIsFrozen(final boolean frozen) {
        isFrozen = frozen;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public boolean hasAbility() {
        return hasAbility;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public void setHasAbility(final boolean hasAbility) {
        this.hasAbility = hasAbility;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public boolean isFrontLiner() {
        return frontLiner;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public void setIsFrontLiner(final boolean setFrontLiner) {
        this.frontLiner = setFrontLiner;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public boolean isTank() {
        return isTank;
    }

    /**
     * Safe to use for every minion
     */
    @JsonIgnore
    public void setIsTank(final boolean tank) {
        isTank = tank;
    }
}
