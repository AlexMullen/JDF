package mullen.alex.draughts.research;

import java.util.ArrayList;
import java.util.List;

import mullen.alex.draughts.common.ArtificialPlayer;
import mullen.alex.draughts.common.Game;
import mullen.alex.draughts.common.Move;
import mullen.alex.draughts.common.Player;
import mullen.alex.draughts.common.Game.GameResult;
import mullen.alex.draughts.common.evaluation.MaterialDifferenceBoardEvaluator;
import mullen.alex.draughts.common.search.MinimaxDepthLimited;
import mullen.alex.draughts.common.search.MoveSearch;
import mullen.alex.draughts.common.search.NegamaxDepthLimited;
import mullen.alex.draughts.english.EnglishDraughtsGame;

/**
 * A class used for automating the benchmarking of two AI {@link MoveSearch}
 * algorithms.
 *
 * @author  Alex Mullen
 *
 */
public final class SearchComparisonBenchmark {
    /** Holds the number of games to play in the benchmark. */
    private static final int GAME_ITERATIONS_COUNT = 10;
    /** Holds the maximum number of moves allowed in a game. */
    private static final int MAX_TURNS = 100;
    /**
     * Creates a new instance.
     */
    private SearchComparisonBenchmark() {
        // Empty.
    }
    /**
     * Main program entry point.
     *
     * @param args  supplied program arguments
     */
    public static void main(final String... args) {
        final MoveSearch searchAlgo1 =
                new MinimaxDepthLimited(
                        new MaterialDifferenceBoardEvaluator(), 5);
        final MoveSearch searchAlgo2 =
                new NegamaxDepthLimited(
                        new MaterialDifferenceBoardEvaluator(), 5);

        final ArtificialPlayer ai1 =
                new ArtificialPlayer(searchAlgo1, "AI-1");
        final ArtificialPlayer ai2 =
                new ArtificialPlayer(searchAlgo2, "AI-2");

        final List<GameStatistics> gameStatsList = new ArrayList<>();

        for (int currentGameIteration = 0;
                currentGameIteration < GAME_ITERATIONS_COUNT;
                currentGameIteration++) {
            /*
             * Create a statistics object for holding statistics about the
             * current game.
             */
            final GameStatistics stats =
                    new GameStatistics(currentGameIteration);
            gameStatsList.add(stats);
            // Create the next game.
            final Game gameContext = new EnglishDraughtsGame(ai1, ai2);

            // Play it until it finishes or the max number of turns is reached.
            int gameTurn = 0;
            while (gameContext.getResult() == null && gameTurn++ < MAX_TURNS) {
                /*
                 * Ask the current AI player for its move then input it into
                 * the game.
                 */
                final ArtificialPlayer aiTurnOwner =
                        (ArtificialPlayer) gameContext.getTurnOwner();
                // Record the time the move was requested at.
                final long gameStartTime = System.currentTimeMillis();
                final Move aiMove = aiTurnOwner.getMove(gameContext);
                // Increment the number of moves made.
                if (gameContext.getTurnOwner() == ai1) {
                    stats.light_moves_made++;
                    stats.white_move_time +=
                            System.currentTimeMillis() - gameStartTime;
                } else {
                    stats.dark_moves_made++;
                    stats.dark_move_time +=
                            System.currentTimeMillis() - gameStartTime;
                }
                gameContext.performMove(aiMove);
            }
            // Check if it was a draw
            if (gameContext.getResult() == null) {
                stats.setDraw(true);
            } else {
                // The game has ended so print the result.
                final GameResult result = gameContext.getResult();
                stats.setWinner(result.getWinner());
                stats.setLoser(gameContext.getOpponent(result.getWinner()));
            }
            System.out.println(stats);
        }
        calculateAndPrintStats(gameStatsList, ai1, ai2);
    }

    private static void calculateAndPrintStats(final List<GameStatistics> statsList,
            final Player whitePlayer, final Player darkPlayer) {
        int white_games_won = 0, dark_games_won = 0;
        int white_moves_in_won_games = 0, dark_moves_in_won_games = 0;
        int white_moves_total_time_in_won_games = 0, dark_moves_total_time_in_won_games = 0;
        int games_drew = 0;
        for (final GameStatistics gameStats : statsList) {
            // Check for draw.
            if (gameStats.isDraw()) {
                games_drew++;
            } else {
                // Check who won.
                if (gameStats.getWinner() == whitePlayer) {
                    white_games_won++;
                    white_moves_in_won_games += gameStats.light_moves_made;
                    white_moves_total_time_in_won_games += gameStats.white_move_time;
                } else if (gameStats.getWinner() == darkPlayer) {
                    dark_games_won++;
                    dark_moves_in_won_games += gameStats.dark_moves_made;
                    dark_moves_total_time_in_won_games += gameStats.dark_move_time;
                }
            }
        }

        final double white_avg_moves_in_won_games = white_moves_in_won_games / (double) white_games_won;
        final double dark_avg_moves_in_won_games = dark_moves_in_won_games / (double) dark_games_won;

        System.out.println("=================================================");
        System.out.println("Total Games drew: " + games_drew);
        System.out.println("=================================================");
        System.out.println("Player (white) games won: " + white_games_won);
        System.out.println("Player (white) avg moves in won games: " + white_avg_moves_in_won_games);
        System.out.println("Player (white) avg move time in won games: " + white_moves_total_time_in_won_games / white_avg_moves_in_won_games);
        System.out.println("=================================================");
        System.out.println("Player (dark) games won: " + dark_games_won);
        System.out.println("Player (dark) avg moves in won games: " + dark_avg_moves_in_won_games);
        System.out.println("Player (dark) avg move time in won games: " + dark_moves_total_time_in_won_games / dark_avg_moves_in_won_games);
    }
}
