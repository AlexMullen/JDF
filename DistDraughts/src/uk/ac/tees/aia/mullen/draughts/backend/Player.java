package uk.ac.tees.aia.mullen.draughts.backend;

/**
 * An interface that defines a player in a game.
 *
 * @author  Alex Mullen
 *
 */
public interface Player {
    /**
     * Invoked when it is this player's turn.
     *
     * @param game  the game context
     */
    void onTurn(Game game);
    /**
     * Invoked when the game ends.
     *
     * @param game    the game context
     * @param winner  the winning player
     */
    void onGameEnded(final Game game, final Player winner);
}
