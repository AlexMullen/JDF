package uk.ac.tees.aia.mullen.draughts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import uk.ac.tees.aia.mullen.draughts.backend.ArtificialPlayer;
import uk.ac.tees.aia.mullen.draughts.backend.BasicBoardEvaluator;
import uk.ac.tees.aia.mullen.draughts.backend.Game;
import uk.ac.tees.aia.mullen.draughts.backend.MinimaxMoveSearch;
import uk.ac.tees.aia.mullen.draughts.backend.MoveSearch;
import uk.ac.tees.aia.mullen.draughts.english.EnglishDraughtsGame;

/**
 * The main loader for the application.
 *
 * @author  Alex Mullen
 *
 */
public final class Loader {
    /**
     * Private constructor to prevent this being instantiated.
     */
    private Loader() {
        // Constructor is intentionally empty.
    }
    /**
     * Main program entry point.
     *
     * @param args  supplied program arguments
     */
    public static void main(final String... args) {
        final MoveSearch searchAlgo1 =
                new MinimaxMoveSearch(new BasicBoardEvaluator(), 8);
        final MoveSearch searchAlgo2 =
                new MinimaxMoveSearch(new BasicBoardEvaluator(), 3);

        final ExecutorService executor = Executors.newSingleThreadExecutor();

        final ArtificialPlayer ai1 =
                new ArtificialPlayer(searchAlgo1, executor, "AI-1");
        final ArtificialPlayer ai2 =
                new ArtificialPlayer(searchAlgo2, executor, "AI-2");

        final Game draughtsGame = new EnglishDraughtsGame(ai1, ai2);
        draughtsGame.addObserver(ai1);
        draughtsGame.addObserver(ai2);
        draughtsGame.start();
    }
}
