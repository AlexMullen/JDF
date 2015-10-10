package uk.ac.tees.aia.mullen.draughts.common;

import java.util.List;

/**
 * An interface for defining a class that generates the valid moves for a
 * specified position on a board.
 *
 * @author     Alex Mullen
 *
 */
public interface MoveGenerator {
    /**
     * Generates all the available moves on the specified board for the
     * specified player.
     *
     * @param board   the board
     * @param player  the player
     * @return        a list of all the available moves; if there are no moves
     *                an empty list should be returned rather than
     *               <code>null</code>.
     */
    List<Move> findMoves(Board board, Player player);
    /**
     * Checks if the specified player has any moves on the given board.
     * <p>
     * Using this for checking if there are any moves is preferable than
     * invoking {@link #findMoves(Board, Player)} for efficiency as this can
     * stop searching when at least one move is found.
     *
     * @param board   the board
     * @param player  the player
     * @return        <code>true</code> if there is at least <code>1</code> move
     *                available for the player; <code>false</code> otherwise
     */
    boolean hasAnyMoves(Board board, Player player);
}
