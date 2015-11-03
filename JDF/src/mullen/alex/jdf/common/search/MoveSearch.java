package mullen.alex.jdf.common.search;

import mullen.alex.jdf.common.Game;
import mullen.alex.jdf.common.Move;

/**
 * An interface for defining a search algorithm that searches for the best
 * move to take.
 *
 * @author  Alex Mullen
 *
 */
public interface MoveSearch {
    /**
     * Performs a search of the specified board from the for the specified
     * colour.
     *
     * @param game      the game context to find the best move for
     * @param colour    the colour to find the best move for
     * @return          the best move or <code>null</code> if there are no
     *                  available moves
     */
    Move search(Game game, int colour);
}
