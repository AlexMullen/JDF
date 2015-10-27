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
 * A move search that uses a Negamax search algorithm that is depth limited.
 *
 * @author  Alex Mullen
 *
 */
public class NegamaxDepthLimited implements MoveSearch {
    /** Holds the maximum absolute range that alpha or beta can be. */
    private static final float MAX_ABS_AB_RANGE = 1000.0f;
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
    public NegamaxDepthLimited(final BoardEvaluator evaluator,
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
        System.out.println("---------------------------------------------------"
                + "---------------------------------------------------");
        
        
long startTime = System.nanoTime();
        
        
        
        final Board board = game.getBoard();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, owner);
        if (moves.size() == 1) {
            // No point evaluating only one move.
            System.out.println(moves.get(0));
            return moves.get(0);
        }
        float alpha = -MAX_ABS_AB_RANGE;
        final List<Move> bestMoves = new ArrayList<>();
        for (final Move currentMove : moves) {
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final float currentMoveValue =
                    -negamax(board, game, owner, opponent, 1, false);
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
        
System.out.println((System.nanoTime() - startTime) / 1000000 + "ms");
        
//        return bestMoves.get(0);
        return bestMoves.get(new Random().nextInt(bestMoves.size()));
    }
    private float negamax(final Board board, final Game game,
            final Player owner, final Player opponent,
            final int currentDepth, final boolean isOwner) {
        if (currentDepth == maxSearchDepth) {
            if (isOwner) {
                return boardEvaluator.evaluate(board, owner);
            } else {
                return boardEvaluator.evaluate(board, opponent);
            }
        }
        List<Move> moves;
        if (isOwner) {
            moves = game.getMoveGenerator().findMoves(board, owner);
        } else {
            moves = game.getMoveGenerator().findMoves(board, opponent);
        }
        float bestScore = -MAX_ABS_AB_RANGE;
        for (final Move currentMove : moves) {
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final float moveVal = -negamax(board, game, owner, opponent,
                    currentDepth + 1, !isOwner);
            performedMove.undo();
            if (moveVal > bestScore) {
                bestScore = moveVal;
            }
        }
        return bestScore;
    }
}
