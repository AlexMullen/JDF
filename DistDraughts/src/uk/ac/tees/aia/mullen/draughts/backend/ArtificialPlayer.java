package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * An artificial Player implementation.
 *
 * @author  Alex Mullen
 *
 */
public class ArtificialPlayer implements Player {
    /** The search algorithm implementation to use for finding the best move. */
    private final MoveSearch moveSearch;
    /** The executor to use as a thread. */
    private final ExecutorService executor;
    /** The name of this AI. */
    private final String name;
    /**
     * Creates a new instance using the specified search
     * algorithm.
     *
     * @param search    the search algorithm
     * @param service   the executor service to use
     * @param aiName    the name to give this
     */
    public ArtificialPlayer(final MoveSearch search,
            final ExecutorService service, final String aiName) {
        moveSearch = Objects.requireNonNull(search);
        executor = service;
        name = aiName;
    }
    @Override
    public final void onTurn(final Game game) {
        // Our turn.
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final Move bestMoveFound =
                        moveSearch.search(game, ArtificialPlayer.this,
                                game.getOpponent(ArtificialPlayer.this));
                if (bestMoveFound == null) {
                    System.out.println(game.getBoard());
                    System.out.println(ArtificialPlayer.this);
                }
                game.performMove(bestMoveFound);
            }
        });
    }
    @Override
    public final void onGameEnded(final Game game, final Player winner) {
        System.out.println("Game ended!: " + winner + " won");
        System.out.println(game.getBoard());
        executor.shutdown();
    }
    @Override
    public final String toString() {
        return "ArtificialPlayer [name=" + name + "]";
    }
}
