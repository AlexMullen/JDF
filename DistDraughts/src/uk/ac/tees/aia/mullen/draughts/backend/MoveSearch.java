package uk.ac.tees.aia.mullen.draughts.backend;

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
     * @param owner     the owner to find the best move for
     * @param opponent  the opponent for <code>owner</code>
     * @return          the best move or <code>null</code> if there are no
     *                  available moves
     */
    Move search(Game game, Player owner, Player opponent);
}
