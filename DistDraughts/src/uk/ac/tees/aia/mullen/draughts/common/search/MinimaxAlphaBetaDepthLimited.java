package uk.ac.tees.aia.mullen.draughts.common.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import uk.ac.tees.aia.mullen.draughts.common.Board;
import uk.ac.tees.aia.mullen.draughts.common.BoardEvaluator;
import uk.ac.tees.aia.mullen.draughts.common.Game;
import uk.ac.tees.aia.mullen.draughts.common.Move;
import uk.ac.tees.aia.mullen.draughts.common.Player;
import uk.ac.tees.aia.mullen.draughts.common.MovePerformer.PerformedMove;

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
    public MinimaxAlphaBetaDepthLimited(final BoardEvaluator evaluator,
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
        int alpha = Integer.MIN_VALUE;
        final Board board = game.getBoard();
        final List<Move> bestMoves = new ArrayList<>();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, owner);
        // No point evaluating only one move.
        if (moves.size() == 1) {
            return moves.get(0);
        }
        for (final Move currentMove : moves) {
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final int currentMoveValue =
                    minimax(board, 1, false, game, owner, opponent,
                            alpha, Integer.MAX_VALUE);
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

        System.out.println(bestMoves.size() + " / " + moves.size()
                + "[" + alpha + "]");
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
    private int minimax(final Board board, final int currentDepth,
            final boolean maximisingPlayer, final Game game,
            final Player owner, final Player opponent, int alpha, int beta) {
        if (currentDepth == maxSearchDepth) {
            return boardEvaluator.evaluate(board, owner);
        } else {
            if (maximisingPlayer) {
                int currentBestValue = Integer.MIN_VALUE;
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
                    if (beta <= alpha) {
                        // Prune.
//                        break;
                        return Integer.MAX_VALUE;
                    }
                }
                return currentBestValue;
            } else {
                int currentBestValue = Integer.MAX_VALUE;
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
                    if (beta <= alpha) {
                        // Prune.
//                        break;
                        return Integer.MIN_VALUE;
                    }
                }
                return currentBestValue;
            }
        }
    }
}
