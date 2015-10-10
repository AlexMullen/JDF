package uk.ac.tees.aia.mullen.draughts.common.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import uk.ac.tees.aia.mullen.draughts.common.Board;
import uk.ac.tees.aia.mullen.draughts.common.Game;
import uk.ac.tees.aia.mullen.draughts.common.Move;
import uk.ac.tees.aia.mullen.draughts.common.Player;
import uk.ac.tees.aia.mullen.draughts.common.MovePerformer.PerformedMove;
import uk.ac.tees.aia.mullen.draughts.common.evaluation.BoardEvaluator;

/**
 * A move search that uses a Minimax search algorithm with Alpha-Beta pruning
 * and is depth limited.
 *
 * @author  Alex Mullen
 *
 */
public class MinimaxAlphaBetaDepthLimited implements MoveSearch {
    /** The board evaluator to use for evaluating board states. */
    private final BoardEvaluator boardEvaluator;
    /** Holds the maximum depth to search to. */
    private final int maxSearchDepth;
    /** The threshold to use for alpha-beta pruning. */
    private final int threshold;
    /**
     * Creates a new instance that uses the specified board evaluator
     * and searches to the specified depth.
     * <p>
     * A threshold for alpha-beta pruning can also be specified.
     *
     * @param evaluator    the board evaluator to use
     * @param depth        the depth to search
     * @param abThreshold  the threshold to use for alpha-beta pruning
     *
     * @throws IllegalArgumentException  if <code>depth</code> < 1
     * @throws NullPointerException      if <code>evaluator</code> is
     *                                   <code>null</code>
     */
    public MinimaxAlphaBetaDepthLimited(final BoardEvaluator evaluator,
            final int depth, final int abThreshold) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        if (depth < 1) {
            throw new IllegalArgumentException("depth(" + depth + ") < 1");
        }
        maxSearchDepth = depth;
        threshold = abThreshold;
    }
    @Override
    public final Move search(final Game game, final Player owner,
            final Player opponent) {
        System.out.println("------------------------------------------------------------------------------------------------------");
        float alpha = -1000.0f;
        final Board board = game.getBoard();
        final List<Move> bestMoves = new ArrayList<>();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, owner);
        if (moves.size() == 1) {
            // No point evaluating only one move.
            System.out.println(moves.get(0));
            return moves.get(0);
        }
        for (final Move currentMove : moves) {
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final float currentMoveValue =
                    minimax(board, 1, false, game, owner, opponent,
                            alpha, 1000.0f);
            System.out.println(currentMove + " = " + currentMoveValue);
            performedMove.undo();
            if (currentMoveValue > alpha) {
                alpha = currentMoveValue;
                bestMoves.clear();
                bestMoves.add(currentMove);
            } else if (currentMoveValue == alpha) {
                bestMoves.add(currentMove);
            }
        }
//        return bestMoves.get(0);
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }
    /**
     * Performs a recursive minimax search to the specified depth for the
     * current board state.
     *
     * @param board             the current board state
     * @param currentDepth      holds the value of the current depth the search
     *                          is at
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
    private float minimax(final Board board, final int currentDepth,
            final boolean maximisingPlayer, final Game game,
            final Player owner, final Player opponent, float alpha, float beta) {
        if (currentDepth == maxSearchDepth) {
            return boardEvaluator.evaluate(board, owner);
        } else {
            if (maximisingPlayer) {
                float currentBestValue = -1000.0f;
                final List<Move> moves =
                        game.getMoveGenerator().findMoves(board, owner);
                for (final Move currentMove : moves) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    currentBestValue = Math.max(currentBestValue,
                            minimax(board, currentDepth + 1, false, game, owner,
                                    opponent, alpha, beta));
                    performedMove.undo();
                    alpha = Math.max(alpha, currentBestValue);
                    if (beta + threshold <= alpha) {
                        // Prune.
                        break;
                    }
                }
                return currentBestValue;
            } else {
                // Minimising player.
                float currentBestValue = 1000.0f;
                final List<Move> moves =
                        game.getMoveGenerator().findMoves(board, opponent);
                for (final Move currentMove : moves) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    currentBestValue = Math.min(currentBestValue,
                            minimax(board, currentDepth + 1, true, game, owner,
                                    opponent, alpha, beta));
                    performedMove.undo();
                    beta = Math.min(beta, currentBestValue);
                    if (beta + threshold <= alpha) {
                        // Prune.
                        break;
                    }
                }
                return currentBestValue;
            }
        }
    }
    @Override
    public final String toString() {
        return "MinimaxAlphaBetaDepthLimited [maxSearchDepth="
                + maxSearchDepth + "]";
    }
}
