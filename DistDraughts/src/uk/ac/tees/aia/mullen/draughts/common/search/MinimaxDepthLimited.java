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
 * A move search that uses a Minimax search algorithm that is depth limited.
 *
 * @author  Alex Mullen
 *
 */
public class MinimaxDepthLimited implements MoveSearch {
    /** The board evaluator to use for evaluating board states. */
    private final BoardEvaluator boardEvaluator;
    /** Holds the maximum depth to search to. */
    private final int maxSearchDepth;
    /**
     * Creates a new instance that uses the specified board evaluator
     * and searches to the specified depth.
     *
     * @param evaluator  the board evaluator to use
     * @param depth      the depth to search
     *
     * @throws IllegalArgumentException  if <code>depth</code> < 1
     * @throws NullPointerException      if <code>evaluator</code> is
     *                                   <code>null</code>
     */
    public MinimaxDepthLimited(final BoardEvaluator evaluator,
            final int depth) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        if (depth < 1) {
            throw new IllegalArgumentException("depth(" + depth + ") < 1");
        }
        maxSearchDepth = depth;
    }
    @Override
    public final Move search(final Game game, final Player owner,
            final Player opponent) {
        float currentBestScore = Integer.MIN_VALUE;
        final List<Move> bestMoves = new ArrayList<>();
        final Board board = game.getBoard();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, owner);
        // No point evaluating only one move.
        if (moves.size() == 1) {
            return moves.get(0);
        }
        for (final Move currentMove : moves) {
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            // This is depth 0, so call min at depth 1.
            final float currentMoveValue =
                    minimax(board, 1, false, game, owner, opponent);
            if (currentBestScore < currentMoveValue) {
                currentBestScore = currentMoveValue;
                bestMoves.clear();
                bestMoves.add(currentMove);
            } else if (currentBestScore == currentMoveValue) {
                bestMoves.add(currentMove);
            }
            performedMove.undo();
        }
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }
    /**
     * Performs a recursive Minimax search to the specified depth for the
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
     * @return                  the value of the current board state N moves
     *                          ahead
     */
    private float minimax(final Board board, final int currentDepth,
            final boolean maximisingPlayer, final Game game,
            final Player owner, final Player opponent) {
        if (currentDepth == maxSearchDepth) {
            return boardEvaluator.evaluate(board, owner);
        } else {
            if (maximisingPlayer) {
                float currentBestScore = Integer.MIN_VALUE;
                for (final Move currentMove
                        : game.getMoveGenerator().findMoves(board, owner)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final float currentMoveValue =
                            minimax(board, currentDepth + 1, false, game, owner,
                                    opponent);
                    currentBestScore =
                            Math.max(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                return currentBestScore;
            } else {
                float currentBestScore = Integer.MAX_VALUE;
                for (final Move currentMove
                        : game.getMoveGenerator().findMoves(board, opponent)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final float currentMoveValue =
                            minimax(board, currentDepth + 1, true, game, owner,
                                    opponent);
                    currentBestScore =
                            Math.min(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                return currentBestScore;
            }
        }
    }
    @Override
    public final String toString() {
        return "MinimaxDepthLimited [maxSearchDepth=" + maxSearchDepth + "]";
    }
}
