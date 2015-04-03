package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.Objects;

import uk.ac.tees.aia.mullen.draughts.backend.MovePerformer.PerformedMove;

/**
 * A move evaluator that uses a Minimax search algorithm.
 *
 * @author Alex
 *
 */
public class MinimaxArtificialPlayer implements ArtificialPlayer {
    /** The board evaluator to use for evaluating the current board state. */
    private final BoardEvaluator boardEvaluator;
    /** The move finder to use for generating legal moves. */
    private final MoveFinder moveFinder;
    /** The mover performer for performing moves. */
    private final MovePerformer movePerformer;
    /** The opposing piece owner. */
    private final PieceOwner opposingPieceOwner;
    /** Holds the maximum depth to search to. */
    private final int maxSearchDepth;
    /**
     * Creates a new instance that uses the specified board evaluator,
     * move finder and searches to the specified depth.
     *
     * @param evaluator  the board evaluator to use
     * @param finder     the move finder to use
     * @param performer  the move performer to use
     * @param opponent   the opposing piece owner
     * @param depth      the depth to search
     *
     * @throws IllegalArgumentException  if <code>depth</code> < 1
     * @throws NullPointerException      if <code>evaluator</code>,
     *                                   <code>finder</code>,
     *                                   <code>performer</code> or
     *                                   <code>opponent</code> is
     *                                   <code>null</code>
     */
    public MinimaxArtificialPlayer(
            final BoardEvaluator evaluator,
            final MoveFinder finder,
            final MovePerformer performer,
            final PieceOwner opponent,
            final int depth) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        moveFinder = Objects.requireNonNull(finder);
        movePerformer = Objects.requireNonNull(performer);
        opposingPieceOwner = Objects.requireNonNull(opponent);
        if (depth < 1) {
            throw new IllegalArgumentException("depth(" + depth + ") < 1");
        }
        maxSearchDepth = depth;
    }
    @Override
    public final void performMove(final Board board) {
        int currentBestScore = Integer.MIN_VALUE;
        Move currentBestMove = null;
        for (final Move currentMove
                : moveFinder.findMoves(board, this)) {
            final PerformedMove performedMove =
                    movePerformer.perform(currentMove, board);
            final int currentMoveValue =
                    minimax(board, 1, false);
            if (currentBestScore < currentMoveValue) {
                currentBestScore = currentMoveValue;
                currentBestMove = currentMove;
            }
            performedMove.undo();
        }
        if (currentBestMove != null) {
            System.out.println(currentBestMove);
            movePerformer.perform(currentBestMove, board);
        }
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
     * @return                  the value of the current board state N moves
     *                          ahead
     */
    private int minimax(final Board board, final int currentDepth,
            final boolean maximisingPlayer) {
        if (currentDepth == maxSearchDepth) {
            return boardEvaluator.evaluate(board, this);
        } else {
            if (maximisingPlayer) {
                int currentBestScore = Integer.MIN_VALUE;
                for (final Move currentMove
                        : moveFinder.findMoves(board, this)) {
                    final PerformedMove performedMove =
                            movePerformer.perform(currentMove, board);
                    final int currentMoveValue =
                            minimax(board, currentDepth + 1, false);
                    currentBestScore =
                            Math.max(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                return currentBestScore;
            } else {
                int currentBestScore = Integer.MAX_VALUE;
                for (final Move currentMove
                        : moveFinder.findMoves(board, opposingPieceOwner)) {
                    final PerformedMove performedMove =
                            movePerformer.perform(currentMove, board);
                    final int currentMoveValue =
                            minimax(board, currentDepth + 1, true);
                    currentBestScore =
                            Math.min(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                return currentBestScore;
            }
        }
    }
}
