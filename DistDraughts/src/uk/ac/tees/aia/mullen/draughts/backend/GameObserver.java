package uk.ac.tees.aia.mullen.draughts.backend;

/**
 * An interface for defining an observer of a game.
 *
 * @author  Alex Mullen
 *
 */
public interface GameObserver {
    /**
     * Invoked when it is the next players turn.
     *
     * @param game   the game context
     * @param owner  the player whose turn it is
     */
    void onTurnChange(final Game game, final PieceOwner owner);
    /**
     * Invoked when the game ends.
     *
     * @param game    the game context
     * @param winner  the player who won
     */
    void onGameEnded(final Game game, final PieceOwner winner);
}
