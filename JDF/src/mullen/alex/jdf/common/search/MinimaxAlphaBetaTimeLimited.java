package mullen.alex.jdf.common.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.Game;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.Player;
import mullen.alex.jdf.common.MovePerformer.PerformedMove;
import mullen.alex.jdf.common.evaluation.BoardEvaluator;

/**
 * A move iterative deepening depth first search that uses a Minimax search
 * algorithm that stops the search after a specified amount of time has
 * elapsed. Alpha-Beta pruning is also used to prune branches that need not be
 * explored.
 *
 * @author  Alex Mullen
 *
 */
public class MinimaxAlphaBetaTimeLimited implements MoveSearch {
    /** Holds the maximum absolute range that alpha or beta can be. */
    private static final float MAX_ABS_AB_RANGE = 1000.0f;
    /** The board evaluator to use for evaluating board state. */
    private final BoardEvaluator boardEvaluator;
    /** Holds the maximum time allowed to search for in milliseconds. */
    private final long searchTime;
    /** The threshold to use for alpha-beta pruning. */
    private final int threshold;
    /**
     * Creates a new instance that uses the specified board evaluator
     * and searches for the specified amount of time in milliseconds.
     * <p>
     * A threshold for alpha-beta pruning can also be specified.
     *
     * @param evaluator    the board evaluator to use
     * @param time         the time allowed for searches
     * @param abThreshold  the threshold to use for alpha-beta pruning
     *
     * @throws IllegalArgumentException  if <code>time</code> < 1
     * @throws NullPointerException      if <code>evaluator</code> is
     *                                   <code>null</code>
     */
    public MinimaxAlphaBetaTimeLimited(final BoardEvaluator evaluator,
            final long time, final int abThreshold) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        if (time < 1) {
            throw new IllegalArgumentException("time(" + time + ") < 1");
        }
        searchTime = time;
        threshold = abThreshold;
    }
    @Override
    public final Move search(final Game game, final Player owner,
            final Player opponent) {
        float alpha = -MAX_ABS_AB_RANGE;
        final List<Move> bestMoves = new ArrayList<>();
        final Board board = game.getBoard();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, owner);
        // No point evaluating only one move.
        if (moves.size() == 1) {
            return moves.get(0);
        }
        final long timeToStopAt = System.currentTimeMillis() + searchTime;
        for (int depth = 1; System.currentTimeMillis() < timeToStopAt;
                depth++) {
            //System.out.println("depth = " + depth);
            for (final Move currentMove : moves) {
                final PerformedMove performedMove =
                        game.getMovePerformer().perform(currentMove, board);
                // This is depth 0, so call min at depth 1.
                final float currentMoveValue =
                        minimax(board, depth, timeToStopAt, false, game, owner,
                                opponent, alpha, MAX_ABS_AB_RANGE);
                if (currentMoveValue > alpha) {
                    alpha = currentMoveValue;
                    bestMoves.clear();
                    bestMoves.add(currentMove);
                } else if (currentMoveValue == alpha) {
                    bestMoves.add(currentMove);
                }
                performedMove.undo();
            }
        }
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }
    /**
     * Performs a recursive Minimax search to the specified depth for the
     * current board state.
     *
     * @param board             the current board state
     * @param depth             holds the value of the depth of the search
     * @param timeToStopAt      holds the to stop searching at
     * @param maximisingPlayer  set to <code>true</code> if the current
     *                          level is the maximising player's (this)
     *                          turn; <code>false</code> if it is the opponents
     *                          turn (minimising player)
     * @param game              the game context
     * @param owner             the maximising player
     * @param opponent          the opponent (minimising player)
     * @param alpha             the current alpha value
     * @param beta              the current beta value
     * @return                  the value of the current board state N moves
     *                          ahead
     */
    private float minimax(final Board board, final int depth,
            final long timeToStopAt,
            final boolean maximisingPlayer, final Game game,
            final Player owner, final Player opponent, float alpha,
            float beta) {
        if (depth == 0 || System.currentTimeMillis() > timeToStopAt) {
            return boardEvaluator.evaluate(board, owner);
        } else {
            if (maximisingPlayer) {
                float currentBestScore = -MAX_ABS_AB_RANGE;
                for (final Move currentMove
                        : game.getMoveGenerator().findMoves(board, owner)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final float currentMoveValue =
                            minimax(board, depth - 1, timeToStopAt, false, game,
                                    owner, opponent, alpha, beta);
                    currentBestScore =
                            Math.max(currentBestScore, currentMoveValue);
                    performedMove.undo();
                    if (beta + threshold <= alpha) {
                        // Prune.
//                        break;
                        return MAX_ABS_AB_RANGE;
                    }
                }
                return currentBestScore;
            } else {
                float currentBestScore = MAX_ABS_AB_RANGE;
                for (final Move currentMove
                        : game.getMoveGenerator().findMoves(board, opponent)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final float currentMoveValue =
                            minimax(board, depth - 1, timeToStopAt, true, game,
                                    owner, opponent, alpha, beta);
                    currentBestScore =
                            Math.min(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                if (beta + threshold <= alpha) {
                    // Prune.
//                    break;
                    return MAX_ABS_AB_RANGE;
                }
                return currentBestScore;
            }
        }
    }
    @Override
    public final String toString() {
        return "MinimaxAlphaBetaTimeLimited [searchTime=" + searchTime + "]";
    }
}
