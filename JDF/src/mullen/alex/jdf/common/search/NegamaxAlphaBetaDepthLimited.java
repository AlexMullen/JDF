package mullen.alex.jdf.common.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.Game;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MovePerformer;
import mullen.alex.jdf.common.Player;
import mullen.alex.jdf.common.MovePerformer.PerformedMove;
import mullen.alex.jdf.common.evaluation.BoardEvaluator;

/**
 * A move search that uses a Negamax search algorithm that is depth limited and
 * uses alpha-beta pruning.
 *
 * @author  Alex Mullen
 *
 */
public class NegamaxAlphaBetaDepthLimited implements MoveSearch {
    /** Holds the maximum absolute range that alpha or beta can be. */
    private static final int MAX_ABS_AB_RANGE = 1000;
    /** The board evaluator to use for evaluating board states. */
    private final BoardEvaluator boardEvaluator;
    /** Holds the maximum depth to search to. */
    private final int maxSearchDepth;
    /** The random number generator to use when choosing equal moves. */
    private final Random rng;
    /** The comparator to use for sorting jumps in descending order. */
    private static final Comparator<Move> DESCENDING_JUMP_COMPARATOR =
            new Comparator<Move>() {
        @Override
        public final int compare(final Move o1, final Move o2) {
            return o2.jumps.size() - o1.jumps.size();
        }
    };
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
    public NegamaxAlphaBetaDepthLimited(final BoardEvaluator evaluator,
            final int depth) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        if (depth < 1) {
            throw new IllegalArgumentException("depth(" + depth + ") < 1");
        }
        maxSearchDepth = depth;
        rng = new Random();
    }
    @Override
    public final Move search(final Game game, final Player maxPlayer,
            final Player minPlayer) {
        System.out.println("---------------------------------------------------"
                + "---------------------------------------------------");
        final Board board = game.getBoard();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        final int moveCount = moves.size();
        if (moves.size() == 1) {
            // No point evaluating only one move.
            System.out.println(moves.get(0));
            return moves.get(0);
        }
        int bestMoveScore = -MAX_ABS_AB_RANGE;
        final List<Move> bestMoves = new ArrayList<>(20);
        for (int i = 0; i < moveCount; i++) {
            final Move currentMove = moves.get(i);
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final int currentMoveValue =
                    -negamax(board, game, minPlayer, maxPlayer,
                            maxSearchDepth - 1, -MAX_ABS_AB_RANGE,
                            MAX_ABS_AB_RANGE);
            performedMove.undo();
            System.out.println(currentMove + " = " + currentMoveValue);
            if (currentMoveValue > bestMoveScore) {
                bestMoveScore = currentMoveValue;
                bestMoves.clear();
                bestMoves.add(currentMove);
            } else if (currentMoveValue == bestMoveScore) {
                bestMoves.add(currentMove);
            }
        }
        return bestMoves.get(rng.nextInt(bestMoves.size()));
    }
    /**
     * Performs a recursive negamax search.
     *
     * @param board      the board
     * @param game       the game context
     * @param maxPlayer  the current max player
     * @param minPlayer  the current min player
     * @param depth      the depth counter
     * @param alpha      the current alpha value
     * @param beta       the current beta value
     * @return           the score of the search
     */
    private int negamax(final Board board, final Game game,
            final Player maxPlayer, final Player minPlayer, final int depth,
            int alpha, int beta) {
        if (depth == 0) {
            // Perform quiescence search until the position is 'quiet'.
            return quiescence(board, game, maxPlayer, minPlayer, alpha, beta);
//            return boardEvaluator.evaluate(board, maxPlayer);
        }
        int bestScore = -MAX_ABS_AB_RANGE;
        // Generate the legal moves for the current player.
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        final int moveCount = moves.size();
        // If the moves are jumps, sort them in descending order.
        if (moveCount > 1 && moves.get(0).jumps.size() > 0) {
            Collections.sort(moves, DESCENDING_JUMP_COMPARATOR);
        }
        // Check each successive state.
        final MovePerformer movePerformer = game.getMovePerformer();
        for (int i = 0; i < moveCount; i++) {
            // Apply the move.
            final PerformedMove performedMove =
                    movePerformer.perform(moves.get(i), board);
            // Evaluate it.
            final int moveVal = -negamax(board, game, minPlayer, maxPlayer,
                    depth - 1, -beta, -alpha);
            // Revert the move.
            performedMove.undo();
            bestScore = Math.max(bestScore, moveVal);
            alpha = Math.max(alpha, moveVal);
            if (alpha >= beta) {
                break;
            }
        }
        return bestScore;
    }
    /**
     * Performs a quiescence negamax search that stops when it reaches a 'quiet'
     * position.
     * <p>
     * A quiet position is defined as one where there are no capture
     * opportunities.
     *
     * @param board      the board
     * @param game       the game context
     * @param maxPlayer  the current max player
     * @param minPlayer  the current min player
     * @param alpha      the current alpha value
     * @param beta       the current beta value
     * @return           the score of the search
     */
    private int quiescence(final Board board, final Game game,
            final Player maxPlayer, final Player minPlayer, int alpha,
            int beta) {
        final int standPat = boardEvaluator.evaluate(board, maxPlayer);
        if (standPat >= beta) {
            return standPat;
        }
//        if (alpha < stand_pat) {
//            alpha = stand_pat;
//        }
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        // Make sure there are no captures.
        final int moveCount = moves.size();
        final MovePerformer movePerformer = game.getMovePerformer();
        if (moveCount >= 1 && !moves.get(0).jumps.isEmpty()) {
            int bestScore = -MAX_ABS_AB_RANGE;
            for (int i = 0; i < moveCount; i++) {
                final PerformedMove performedMove =
                        movePerformer.perform(moves.get(i), board);
                final int moveVal =
                        -quiescence(board, game, minPlayer, maxPlayer,
                                -beta, -alpha);
                performedMove.undo();
                bestScore = Math.max(bestScore, moveVal);
                alpha = Math.max(alpha, moveVal);
                if (alpha >= beta) {
                    break;
                }
            }
            return bestScore;
        } else {
            return standPat;
        }
    }
}
