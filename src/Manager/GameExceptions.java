package Manager;

import Minions.*;

public class GameExceptions {

    private GameExceptions() {}

    public static int TestforMinionstate(Minion minion) {
        if (minion.isFrozen) {
            // Attacker card is frozen.
            return -1;
        } else if (minion.hasAttacked) {
            // Attacker card has already attacked this turn.
            return -1;
        }
        return 0;
    }

    public static int TestsforcardOwner() {return 0;}

    public static int TestAttackonEnemy() {return 0;}

    public int TankTest(Minion minion, int currentPlayer) {
        return 0;
    }
}
