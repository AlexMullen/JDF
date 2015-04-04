package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import uk.ac.tees.aia.mullen.draughts.backend.MovePerformer.PerformedMove;

/**
 * A move search that uses a Minimax search algorithm.
 *
 * @author  Alex Mullen
 *
 */
public class MinimaxMoveSearch implements MoveSearch {
    /** The board evaluator to use for evaluating the current board state. */
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
    public MinimaxMoveSearch(final BoardEvaluator evaluator, final int depth) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        if (depth < 1) {
            throw new IllegalArgumentException("depth(" + depth + ") < 1");
        }
        maxSearchDepth = depth;
    }
    @Override
    public final Move search(final Game game, final PieceOwner owner,
            final PieceOwner opponent) {
        int currentBestScore = Integer.MIN_VALUE;
        Move currentBestMove = null;
        final Board board = game.getBoard();
        final List<Move> moves = game.getMoveFinder().findMoves(board, owner);
        for (final Move currentMove
                : moves) {
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final int currentMoveValue =
                    minimax(board, 1, false, game, owner, opponent);
            if (currentBestScore < currentMoveValue) {
                currentBestScore = currentMoveValue;
                currentBestMove = currentMove;
            }
            performedMove.undo();
        }
        if (currentBestMove == null && !moves.isEmpty()) {
            System.out.println("fucked");
            /*
             * Basically we're fucked, no matter what move we take we'll lose
             * so lets pick a random one.
             */
            currentBestMove = moves.get(new Random().nextInt(moves.size()));
        }
        return currentBestMove;
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
     * @return                  the value of the current board state N moves
     *                          ahead
     */
    private int minimax(final Board board, final int currentDepth,
            final boolean maximisingPlayer, final Game game,
            final PieceOwner owner, final PieceOwner opponent) {
        if (currentDepth == maxSearchDepth) {
            return boardEvaluator.evaluate(board, owner);
        } else {
            if (maximisingPlayer) {
                int currentBestScore = Integer.MIN_VALUE;
                for (final Move currentMove
                        : game.getMoveFinder().findMoves(board, owner)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final int currentMoveValue =
                            minimax(board, currentDepth + 1, false, game, owner,
                                    opponent);
                    currentBestScore =
                            Math.max(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                return currentBestScore;
            } else {
                int currentBestScore = Integer.MAX_VALUE;
                for (final Move currentMove
                        : game.getMoveFinder().findMoves(board, opponent)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final int currentMoveValue =
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
}
