package mullen.alex.jdf.common;

import java.util.Objects;

import mullen.alex.jdf.common.search.MoveSearch;

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
     * Gets the name assigned to this.
     *
     * @return  the name
     */
    public final String getName() {
        return name;
    }
    /**
     * Gets the {@link MoveSearch} this uses.
     *
     * @return  the <code>MoveSearch</code> instance
     */
    public final MoveSearch getMoveSearch() {
        return moveSearch;
    }
    /**
     * Requests the move to be performed by this artificial player.
     *
     * @param game  the game context
     * @return      the move to perform for this player
     */
    public final Move requestMove(final Game game) {
        final long startTime = System.nanoTime();
        final int nsInMs = 1000000;
        final Move moveToPerform =
                moveSearch.search(game, this, game.getOpponent(this));
        long timeTakenMs = (System.nanoTime() - startTime) / nsInMs;
        System.out.println("Searched in " + timeTakenMs + "ms ");
        return moveToPerform;
    }
    @Override
    public final String toString() {
        return "ArtificialPlayer [name=" + name + ", search="
                + moveSearch + "]";
    }
}
