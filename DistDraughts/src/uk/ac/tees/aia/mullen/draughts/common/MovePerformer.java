package uk.ac.tees.aia.mullen.draughts.common;

/**
 * An interface for defining a class that can apply a move to a board.
 *
 * @author  Alex Mullen
 *
 */
public interface MovePerformer {
    /**
     * Performs the specified move to the specified board.
     *
     * @param move   the move to perform
     * @param board  the board to perform the move on
     * @return       a <code>PerformedMove</code> instance that represents the
     *               move just performed
     */
    PerformedMove perform(Move move, Board board);
    /**
     * An interface for defining a class that represents the action of a
     * performed move.
     *
     * @author  Alex Mullen
     */
    public interface PerformedMove {
        /**
         * Undoes the performed move on the board the move was performed on.
         */
        void undo();
    }
}
