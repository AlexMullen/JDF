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
 * A move search that uses a Negamax search algorithm that is depth limited and
 * uses alpha-beta pruning.
 *
 * @author  Alex Mullen
 *
 */
public class NegamaxAlphaBetaDepthLimited implements MoveSearch {
    /** Holds the maximum absolute range that alpha or beta can be. */
    private static final float MAX_ABS_AB_RANGE = 1000.0f;
    /** The board evaluator to use for evaluating board states. */
    private final BoardEvaluator boardEvaluator;
    /** Holds the maximum depth to search to. */
    private final int maxSearchDepth;
    /** The random number generator to use when choosing equal moves. */
    private final Random rng;
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
long startTime = System.nanoTime();
        final Board board = game.getBoard();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        if (moves.size() == 1) {
            // No point evaluating only one move.
System.out.println(moves.get(0));
            return moves.get(0);
        }
        float bestMoveScore = -MAX_ABS_AB_RANGE;
        final List<Move> bestMoves = new ArrayList<>(20);
        for (int i = 0; i < moves.size(); i++) {
            final Move currentMove = moves.get(i);
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final float currentMoveValue =
                    -negamax(board, game, minPlayer, maxPlayer,
                            maxSearchDepth - 1, -MAX_ABS_AB_RANGE, MAX_ABS_AB_RANGE);
System.out.println(currentMove + " = " + currentMoveValue);
            performedMove.undo();
            if (currentMoveValue > bestMoveScore) {
                bestMoveScore = currentMoveValue;
                bestMoves.clear();
                bestMoves.add(currentMove);
            } else if (Math.abs(currentMoveValue - bestMoveScore) < 0.000000001) {
                bestMoves.add(currentMove);
            }
        }
long timeTakenMs = (System.nanoTime() - startTime) / 1000000;
System.out.println("Searched in " + timeTakenMs + "ms ");
//        return bestMoves.get(0);
        return bestMoves.get(rng.nextInt(bestMoves.size()));
    }
    private float negamax(final Board board, final Game game,
            final Player maxPlayer, final Player minPlayer, final int depth,
            float alpha, float beta) {
        if (depth == 0) {
            // Perform quiescence search until the position is 'quiet'.
            return quiescence(board, game, maxPlayer, minPlayer, alpha, beta);
//            return boardEvaluator.evaluate(board, maxPlayer);
        }
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        // Check for terminal node (when there is no more moves).
        final int moveCount = moves.size();
        if (moveCount == 0) {
            // Terminal node.
            return boardEvaluator.evaluate(board, maxPlayer);
        }
        float bestScore = -MAX_ABS_AB_RANGE;
        for (int i = 0; i < moveCount; i++) {
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(moves.get(i), board);
            final float moveVal = -negamax(board, game, minPlayer, maxPlayer,
                    depth - 1, -beta, -alpha);
            performedMove.undo();
            bestScore = Math.max(bestScore, moveVal);
            alpha = Math.max(alpha, moveVal);
//            if (alpha >= beta) { // Need better evaluation function for this.
            if (alpha >= beta) {
                break;
            }
        }
        return bestScore;
    }
    private float quiescence(final Board board, final Game game,
            final Player maxPlayer, final Player minPlayer, float alpha,
            float beta) {
        final float standPat = boardEvaluator.evaluate(board, maxPlayer);
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
        if (moveCount >= 1 && !moves.get(0).jumps.isEmpty()) {
            float bestScore = -MAX_ABS_AB_RANGE;
            for (int i = 0; i < moveCount; i++) {
                final PerformedMove performedMove =
                        game.getMovePerformer().perform(moves.get(i), board);
                final float moveVal =
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
