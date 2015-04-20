package uk.ac.tees.aia.mullen.draughts.common.search;

import uk.ac.tees.aia.mullen.draughts.common.Game;
import uk.ac.tees.aia.mullen.draughts.common.Move;
import uk.ac.tees.aia.mullen.draughts.common.Player;

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
     * piece owner.
     *
     * @param game      the game context to find the best move for
     * @param player    the player to find the best move for
     * @param opponent  the opposing player for <code>player</code>
     * @return          the best move or <code>null</code> if there are no
     *                  available moves
     */
    Move search(Game game, Player player, Player opponent);
}
