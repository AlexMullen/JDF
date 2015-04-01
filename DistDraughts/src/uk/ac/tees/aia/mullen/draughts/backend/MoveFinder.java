package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.List;

/**
 * An interface for defining a class that retrieves the valid moves for a
 * specified position on a board.
 *
 * @author     Alex Mullen
 *
 */
public interface MoveFinder {
    /**
     * Finds and returns all the available moves on the specified board for the
     * specified piece owner.
     *
     * @param board  the board
     * @param owner  the piece owner
     * @return       a list of all the available moves
     */
    List<Move> findMoves(Board board, PieceOwner owner);
}
