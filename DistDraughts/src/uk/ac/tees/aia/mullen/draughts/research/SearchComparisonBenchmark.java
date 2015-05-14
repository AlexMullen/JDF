package uk.ac.tees.aia.mullen.draughts.research;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.aia.mullen.draughts.common.ArtificialPlayer;
import uk.ac.tees.aia.mullen.draughts.common.BasicBoardEvaluator;
import uk.ac.tees.aia.mullen.draughts.common.Game;
import uk.ac.tees.aia.mullen.draughts.common.Move;
import uk.ac.tees.aia.mullen.draughts.common.Game.GameResult;
import uk.ac.tees.aia.mullen.draughts.common.Player;
import uk.ac.tees.aia.mullen.draughts.common.search.
                                            MinimaxAlphaBetaDepthLimited;
import uk.ac.tees.aia.mullen.draughts.common.search.MinimaxAlphaBetaTimeLimited;
import uk.ac.tees.aia.mullen.draughts.common.search.MinimaxDepthLimited;
import uk.ac.tees.aia.mullen.draughts.common.search.MoveSearch;
import uk.ac.tees.aia.mullen.draughts.english.EnglishDraughtsGame;

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
                        new BasicBoardEvaluator(), 10);
        final MoveSearch searchAlgo2 =
                new MinimaxAlphaBetaDepthLimited(
                        new BasicBoardEvaluator(), 1, 0);

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
                    stats.player1_moves_made++;
                    stats.player1_move_time += System.currentTimeMillis() - gameStartTime;
                } else {
                    stats.player2_moves_made++;
                    stats.player2_move_time += System.currentTimeMillis() - gameStartTime;
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
            final Player p1, final Player p2) {
        int p1_games_won = 0, p2_games_won = 0;
        int p1_moves_in_won_games = 0, p2_moves_in_won_games = 0;
        int p1_moves_total_time_in_won_games = 0, p2_moves_total_time_in_won_games = 0;
        int games_drew = 0;
        for (final GameStatistics gameStats : statsList) {
            // Check for draw.
            if (gameStats.isDraw()) {
                games_drew++;
            } else {
                // Check who won.
                if (gameStats.getWinner() == p1) {
                    p1_games_won++;
                    p1_moves_in_won_games += gameStats.player1_moves_made;
                    p1_moves_total_time_in_won_games += gameStats.player1_move_time;
                } else {
                    p2_games_won++;
                    p2_moves_in_won_games += gameStats.player2_moves_made;
                    p2_moves_total_time_in_won_games += gameStats.player2_move_time;
                }
            }
        }
        
        final double p1_avg_moves_in_won_games = p1_moves_in_won_games / (double) p1_games_won;
        final double p2_avg_moves_in_won_games = p2_moves_in_won_games / (double) p2_games_won;
        
        System.out.println("=================================================");
        System.out.println("Total Games drew: " + games_drew);
        System.out.println("=================================================");
        System.out.println("Player 1 games won: " + p1_games_won);
        System.out.println("Player 1 avg moves in won games: " + p1_avg_moves_in_won_games);
        System.out.println("Player 1 avg move time in won games: " + p1_moves_total_time_in_won_games / p1_avg_moves_in_won_games);
        System.out.println("=================================================");
        System.out.println("Player 2 games won: " + p2_games_won);
        System.out.println("Player 2 avg moves in won games: " + p2_avg_moves_in_won_games);
        System.out.println("Player 2 avg move time in won games: " + p2_moves_total_time_in_won_games / p2_avg_moves_in_won_games);
    }
}
