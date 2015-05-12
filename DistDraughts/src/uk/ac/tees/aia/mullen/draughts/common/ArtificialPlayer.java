package uk.ac.tees.aia.mullen.draughts.common;

import java.util.Objects;

import uk.ac.tees.aia.mullen.draughts.common.search.MoveSearch;

/**
 * An artificial Player implementation.
 *
 * @author  Alex Mullen
 *
 */
public class ArtificialPlayer implements Player {
    /** The search algorithm implementation to use for finding the best move. */
    private final MoveSearch moveSearch;
    /** The name of this AI. */
    private final String name;
    /**
     * Creates a new instance using the specified search
     * algorithm.
     *
     * @param search    the search algorithm
     * @param aiName    the name to give this
     */
    public ArtificialPlayer(final MoveSearch search, final String aiName) {
        moveSearch = Objects.requireNonNull(search);
        name = aiName;
    }
    @Override
    public final boolean isArtificial() {
        return true;
    }
    /**
     * Gets the move to be performed by this artificial player.
     *
     * @param game  the game context
     * @return      the move to perform for this player
     */
    public final Move getMove(final Game game) {
        return moveSearch.search(game, ArtificialPlayer.this,
                        game.getOpponent(ArtificialPlayer.this));
    }
    @Override
    public final String toString() {
        return "ArtificialPlayer [name=" + name + ", search="
                + moveSearch + "]";
    }
}
