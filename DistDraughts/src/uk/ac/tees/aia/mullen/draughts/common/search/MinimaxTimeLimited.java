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
 * A move iterative deepening depth first search that uses a Minimax search
 * algorithm that stops the search after a specified amount of time has
 * elapsed.
 *
 * @author  Alex Mullen
 *
 */
public class MinimaxTimeLimited implements MoveSearch {
    /** The board evaluator to use for evaluating board state. */
    private final BoardEvaluator boardEvaluator;
    /** Holds the maximum time allowed to search for in milliseconds. */
    private final long searchTime;
    /**
     * Creates a new instance that uses the specified board evaluator
     * and searches for the specified amount of time in milliseconds.
     *
     * @param evaluator  the board evaluator to use
     * @param time       the time allowed for searches
     *
     * @throws IllegalArgumentException  if <code>time</code> < 1
     * @throws NullPointerException      if <code>evaluator</code> is
     *                                   <code>null</code>
     */
    public MinimaxTimeLimited(final BoardEvaluator evaluator, final long time) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        if (time < 1) {
            throw new IllegalArgumentException("time(" + time + ") < 1");
        }
        searchTime = time;
    }
    @Override
    public final Move search(final Game game, final Player owner,
            final Player opponent) {
        int currentBestScore = Integer.MIN_VALUE;
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
            System.out.println("depth = " + depth);
            for (final Move currentMove : moves) {
                final PerformedMove performedMove =
                        game.getMovePerformer().perform(currentMove, board);
                // This is depth 0, so call min at depth 1.
                final int currentMoveValue =
                        minimax(board, depth, timeToStopAt, false, game, owner,
                                opponent);
                if (currentBestScore < currentMoveValue) {
                    currentBestScore = currentMoveValue;
                    bestMoves.clear();
                    bestMoves.add(currentMove);
                } else if (currentBestScore == currentMoveValue) {
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
     * @return                  the value of the current board state N moves
     *                          ahead
     */
    private int minimax(final Board board, final int depth,
            final long timeToStopAt,
            final boolean maximisingPlayer, final Game game,
            final Player owner, final Player opponent) {
        if (depth == 0 || System.currentTimeMillis() > timeToStopAt) {
            return boardEvaluator.evaluate(board, owner);
        } else {
            if (maximisingPlayer) {
                int currentBestScore = Integer.MIN_VALUE;
                for (final Move currentMove
                        : game.getMoveGenerator().findMoves(board, owner)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final int currentMoveValue =
                            minimax(board, depth - 1, timeToStopAt, false, game,
                                    owner, opponent);
                    currentBestScore =
                            Math.max(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                return currentBestScore;
            } else {
                int currentBestScore = Integer.MAX_VALUE;
                for (final Move currentMove
                        : game.getMoveGenerator().findMoves(board, opponent)) {
                    final PerformedMove performedMove =
                            game.getMovePerformer().perform(currentMove, board);
                    final int currentMoveValue =
                            minimax(board, depth - 1, timeToStopAt, true, game,
                                    owner, opponent);
                    currentBestScore =
                            Math.min(currentBestScore, currentMoveValue);
                    performedMove.undo();
                }
                return currentBestScore;
            }
        }
    }
}
