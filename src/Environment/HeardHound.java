package Environment;

import Minions.Minion;
import fileio.CardInput;
import fileio.Coordinates;

import java.util.ArrayList;

public class HeardHound extends Environment{

    public HeardHound(CardInput cardInput) {
        super(cardInput);
    }

    // don't forget to check if there is space to add the minion to player's row
    @Override
    public void houndAction(ArrayList<ArrayList<Minion>> board, Coordinates target) {
        Minion enemyMinion = board.get(target.getX()).get(target.getY());
        board.get(3 - target.getX()).add(enemyMinion);
        board.get(target.getX()).remove(target.getY());
    }
}
