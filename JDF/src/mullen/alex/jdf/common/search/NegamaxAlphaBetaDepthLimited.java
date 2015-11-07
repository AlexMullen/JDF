package mullen.alex.jdf.common.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.Game;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.Piece;
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
    /** Holds the current number of nodes searched. */
    private int nodesEvaluated;
    /** The comparator to use for sorting jumps in descending order. */
    private static final Comparator<Move> DESCENDING_JUMP_COMPARATOR =
            new Comparator<Move>() {
        @Override
        public int compare(final Move o1, final Move o2) {
            return o2.jumps.size() - o1.jumps.size();
        }
    };

    private final Map<Integer, TranspositionEntry> tt = new HashMap<>(1000000);
    private static class TranspositionEntry {
        public static final int EXACT = 0;
        public static final int UPPER = 1;
        public static final int LOWER = 2;
//        public Move move;
        public int depth;
        public int score;
        public int flag;
        public TranspositionEntry(final int d, final int s, final int f) {
//            move = m;
            depth = d;
            score = s;
            flag = f;
        }
    }
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
    public final Move search(final Game game, final int maxPlayer) {
        tt.clear();
        System.out.println("---------------------------------------------------"
                + "---------------------------------------------------");
        final Board board = game.getBoard();
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        final int moveCount = moves.size();
        if (moveCount == 1) {
            // No point evaluating only one move.
            System.out.println(moves.get(0));
            return moves.get(0);
        }
        nodesEvaluated = 0;
        int bestMoveScore = -MAX_ABS_AB_RANGE;
        final List<Move> bestMoves = new ArrayList<>(20);
        for (int i = 0; i < moveCount; i++) {
            final Move currentMove = moves.get(i);
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(currentMove, board);
            final int currentMoveValue = -mtdf(board, game,
                    Piece.getOpposingColourOf(maxPlayer), maxPlayer,
                    maxSearchDepth - 1, bestMoveScore);
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
        System.out.println("nodes evaluated: " + nodesEvaluated);
        return bestMoves.isEmpty() ? null
                : bestMoves.get(rng.nextInt(bestMoves.size()));
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
            final int maxPlayer, final int minPlayer, final int depth,
            int alpha, int beta) {
//        final TranspositionEntry entry = tt.get(Integer.valueOf(board.hashCode()));
//        if (entry != null && depth <= entry.depth) {
//            switch (entry.flag) {
//                case TranspositionEntry.EXACT:
//                    return entry.score;
//                case TranspositionEntry.LOWER:
//                    if (alpha < entry.score) {
//                        alpha = entry.score;
//                    }
//                    break;
//                case TranspositionEntry.UPPER:
//                    if (beta < entry.score) {
//                        beta = entry.score;
//                    }
//                    break;
//                default:
//                    break;
//            }
//            if (alpha >= beta) {
//                return entry.score;
//            }
//        }
        int a = alpha;
        int b = beta;
        if (depth == 0) {
            // Perform quiescence search until the position is 'quiet'.
//            final int score =
//                    quiescence(board, game, maxPlayer, minPlayer, a, b);
//            return score;
            nodesEvaluated++;
            return boardEvaluator.evaluate(board, maxPlayer);
        }
        int bestScore = -MAX_ABS_AB_RANGE;
        // Generate the legal moves for the current player.
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        final int moveCount = moves.size();
        // If the moves are jumps, sort them in descending order.
        if (moveCount > 1 && !moves.get(0).jumps.isEmpty()) {
            Collections.sort(moves, DESCENDING_JUMP_COMPARATOR);
        }
        // Pre-calculate and cache some parameters to further calls.
        final int nextDepth = depth - 1;
        // Check each successive state.
        for (int i = 0; i < moveCount; i++) {
            // Apply the move.
            final PerformedMove performedMove =
                    game.getMovePerformer().perform(moves.get(i), board);
            // Evaluate it.
            final int moveVal = -negamax(board, game, minPlayer, maxPlayer,
                    nextDepth, -b, -a);
            // Revert the move.
            performedMove.undo();
            bestScore = Math.max(bestScore, moveVal);
            a = Math.max(a, moveVal);
            if (a >= b) {
                break;
            }
        }
//        if (bestScore >= beta && tt.size() <= 1000000) {
//            final TranspositionEntry existingEntry = tt.get(
//                    Integer.valueOf(board.hashCode()));
//            if (existingEntry != null) {
//                if (existingEntry.depth < depth) {
//                    final TranspositionEntry newEntry =
//                            new TranspositionEntry(depth, bestScore,
//                                    TranspositionEntry.LOWER);
//                    tt.put(Integer.valueOf(board.hashCode()), newEntry);
//                }
//            } else {
//                final TranspositionEntry newEntry =
//                        new TranspositionEntry(depth, bestScore,
//                                TranspositionEntry.LOWER);
//                tt.put(Integer.valueOf(board.hashCode()), newEntry);
//            }
//        } else if (bestScore <= alpha && tt.size() <= 1000000) {
//            final TranspositionEntry existingEntry = tt.get(
//                    Integer.valueOf(board.hashCode()));
//            if (existingEntry != null) {
//                if (existingEntry.depth < depth) {
//                    final TranspositionEntry newEntry =
//                            new TranspositionEntry(depth, bestScore,
//                                    TranspositionEntry.UPPER);
//                    tt.put(Integer.valueOf(board.hashCode()), newEntry);
//                }
//            } else {
//                final TranspositionEntry newEntry =
//                        new TranspositionEntry(depth, bestScore,
//                                TranspositionEntry.UPPER);
//                tt.put(Integer.valueOf(board.hashCode()), newEntry);
//            }
//        }
//        else {
//            if (tt.size() <= 1000000) {
//                final TranspositionEntry existingEntry =
//                        tt.get(Integer.valueOf(board.hashCode()));
//                if (existingEntry != null) {
//                    if (existingEntry.depth < depth) {
//                        final TranspositionEntry newEntry =
//                                new TranspositionEntry(depth, bestScore,
//                                        TranspositionEntry.EXACT);
//                        tt.put(Integer.valueOf(board.hashCode()), newEntry);
//                    }
//                } else {
//                    final TranspositionEntry newEntry =
//                            new TranspositionEntry(depth, bestScore,
//                                    TranspositionEntry.EXACT);
//                    tt.put(Integer.valueOf(board.hashCode()), newEntry);
//                }
//            }
//        }

        return bestScore;
    }
    private int mtdf(final Board board, final Game game, final int maxPlayer,
            final int minPlayer, final int depth, final int firstGuess) {
        int g = firstGuess;
        int upperbound = MAX_ABS_AB_RANGE;
        int lowerbound = -MAX_ABS_AB_RANGE;
        int beta;
        do {
            if (g == lowerbound) {
                beta = g + 1;
            } else {
                beta = g;
            }
            g = negamax(board, game, maxPlayer, minPlayer, depth,
                    beta - 1, beta);
            if (g < beta) {
                upperbound = g;
            } else {
                lowerbound = g;
            }
        } while (lowerbound < upperbound);
        return g;
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
            final int maxPlayer, final int minPlayer, int alpha,
            int beta) {
        final int standPat = boardEvaluator.evaluate(board, maxPlayer);
        nodesEvaluated++;
        if (standPat >= beta) {
            return standPat;
        }
        if (alpha < standPat) {
            alpha = standPat;
        }
        final List<Move> moves =
                game.getMoveGenerator().findMoves(board, maxPlayer);
        // Make sure there are no captures.
        final int moveCount = moves.size();
        if (moveCount >= 1 && !moves.get(0).jumps.isEmpty()) {
//            Collections.sort(moves, DESCENDING_JUMP_COMPARATOR);
            int bestScore = -MAX_ABS_AB_RANGE;
            for (int i = 0; i < moveCount; i++) {
                final PerformedMove performedMove =
                        game.getMovePerformer().perform(moves.get(i), board);
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
