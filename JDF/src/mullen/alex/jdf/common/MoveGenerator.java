package mullen.alex.jdf.common;

import java.util.List;

/**
 * An interface for defining a class that generates the valid moves given a
 * board configuration.
 *
 * @author  Alex Mullen
 *
 */
public interface MoveGenerator {
    /**
     * Generates all the available moves on the specified board for the
     * specified colour.
     *
     * @param board   the board
     * @param colour  the colour
     * @return        a list of all the available moves; if there are no moves
     *                an empty list should be returned rather than
     *               <code>null</code>.
     */
    List<Move> findMoves(Board board, int colour);
    /**
     * Checks if the specified colour has any moves on the given board.
     * <p>
     * Using this for checking if there are any moves is preferable than
     * invoking {@link #findMoves(Board, int)} for efficiency as this can
     * stop searching when at least one move is found.
     *
     * @param board   the board
     * @param colour  the colour
     * @return        <code>true</code> if there is at least <code>1</code> move
     *                available for the colour; <code>false</code> otherwise
     */
    boolean hasAnyMoves(Board board, int colour);
}
