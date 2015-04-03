package uk.ac.tees.aia.mullen.draughts.backend;

/**
 * An interface that defines an Artificial computer player.
 *
 * @author  Alex Mullen
 *
 */
public interface ArtificialPlayer extends PieceOwner {
    /**
     * Performs a move from this player on the specified board.
     *
     * @param board  the board to perform the move on
     */
    void performMove(final Board board);
}
