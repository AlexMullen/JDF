package mullen.alex.jdf.common.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.Game;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.Player;
import mullen.alex.jdf.common.MovePerformer.PerformedMove;
import mullen.alex.jdf.common.evaluation.BoardEvaluator;

public class IterativeMinimaxAB implements MoveSearch {
    /** Holds the maximum absolute range that alpha or beta can be. */
    private static final int MAX_ABS_AB_RANGE = 1000;
    /** The board evaluator to use for evaluating board states. */
    private final BoardEvaluator boardEvaluator;
    /** Holds the maximum depth to search to. */
    private final int maxSearchDepth;
    /** The random number generator to use when choosing equal moves. */
//    private final Random rng;
    public IterativeMinimaxAB(final BoardEvaluator evaluator,
            final int depth) {
        boardEvaluator = Objects.requireNonNull(evaluator);
        if (depth < 1) {
            throw new IllegalArgumentException("depth(" + depth + ") < 1");
        }
        maxSearchDepth = depth;
//        rng = new Random();
    }

    @Override
    public final Move search(final Game game, final Player maxPlayer,
            final Player minPlayer) {
        System.out.println("---------------------------------------------------"
                + "---------------------------------------------------");
        final Board board = game.getBoard();
        final List<ScoredMove> moves = movesToScoredMoves(
                game.getMoveGenerator().findMoves(board, maxPlayer));
        // Search each depth broadly.
        int firstGuess = 0;
        for (int depth = 0; depth < maxSearchDepth; depth++) {
            System.out.println("---------------------------------------------------"
                    + "---------------------------------------------------");
            // Sort by highest scoring first.
            Collections.sort(moves, DESCENDING_SCOREDMOVE_COMPARATOR);
            for (final ScoredMove currentMove : moves) {
                final PerformedMove performedMove =
                        game.getMovePerformer().perform(
                                currentMove.move, board);
                // This is depth 0, so call min at depth 1.
                final int previousMoveScore = currentMove.score;
                firstGuess = previousMoveScore;
                currentMove.score = mtdf(board, game, true, maxPlayer,
                        minPlayer, depth, firstGuess);
//                currentMove.score = alphabeta(board, game, false, maxPlayer,
//                        minPlayer, depth, -MAX_ABS_AB_RANGE, MAX_ABS_AB_RANGE, pv);
                performedMove.undo();
                System.out.println(currentMove.move + " | depth=" + depth + " | score = " + currentMove.score + 
                        "  (" + (currentMove.score > previousMoveScore ? "+" : "-") + Math.abs(currentMove.score - previousMoveScore) + ")");
            }
        }
        // TODO Auto-generated method stub
        Collections.sort(moves, DESCENDING_SCOREDMOVE_COMPARATOR);
        return moves.get(0).move;
    }
    private int alphabeta(final Board board, final Game game,
            final boolean maximisingPlayer, final Player maxPlayer,
            final Player minPlayer, final int depth,
            int alpha, int beta) {
        if (depth == 0) {
            return boardEvaluator.evaluate(board, maxPlayer);
        }
        if (maximisingPlayer) {
            int currentBestValue = -MAX_ABS_AB_RANGE;
            final List<Move> moves =
                    game.getMoveGenerator().findMoves(board, maxPlayer);
            for (final Move currentMove : moves) {
                final PerformedMove performedMove =
                        game.getMovePerformer().perform(currentMove, board);
                final int score = alphabeta(board, game, false, maxPlayer,
                                minPlayer, depth - 1, alpha, beta);
                performedMove.undo();
                if (score >= beta) {
                    return score;
                }
                if (score > currentBestValue) {
                    currentBestValue = score;
                }
                if (score > alpha) {
                    alpha = score;
                }
            }
            return currentBestValue;
        } else {
            // Minimising player.
            int currentBestScore = MAX_ABS_AB_RANGE;
            final List<Move> moves =
                    game.getMoveGenerator().findMoves(board, minPlayer);
            for (final Move currentMove : moves) {
                final PerformedMove performedMove =
                        game.getMovePerformer().perform(currentMove, board);
                final int score = alphabeta(board, game, true, maxPlayer,
                                minPlayer, depth - 1, alpha, beta);
                performedMove.undo();
                if (score <= alpha) {
                    return score;
                }
                if (score < currentBestScore) {
                    currentBestScore = score;
                }
                if (score < beta) {
                    beta = score;
                }
            }
            return currentBestScore;
        }
    }
    
    private int mtdf(final Board board, final Game game, final boolean maximisingPlayer, 
            final Player maxPlayer,
            final Player minPlayer, final int depth, final int firstGuess) {
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
            g = alphabeta(board, game, !maximisingPlayer, maxPlayer, minPlayer, depth, beta - 1, beta);
            if (g < beta) {
                upperbound = g;
            } else {
                lowerbound = g;
            }
        } while (lowerbound < upperbound);
        return g;
    }
//    private static void printPv(final Deque<Move> pv) {
//        final Iterator<Move> iter = pv.descendingIterator();
//        System.out.println("============================pv=============================");
//        while (iter.hasNext()) {
//            final Move m = iter.next();
//            System.out.println("(" + m.from.x + "," + m.from.y + ") ====> (" + m.to.x + "," + m.to.y + ")");
//        }
//        System.out.println("============================pv=============================");
//    }
    private static List<ScoredMove> movesToScoredMoves(final List<Move> moves) {
        final List<ScoredMove> scoredMoves = new ArrayList<>(moves.size());
        for (final Move m : moves) {
            scoredMoves.add(new ScoredMove(m));
        }
        return scoredMoves;
    }
    private static class ScoredMove {
        public final Move move;
        public int score;
        protected ScoredMove(final Move m) {
            move = m;
        }
    }
    /** The comparator to use for sorting moves in descending order. */
    private static final Comparator<ScoredMove> DESCENDING_SCOREDMOVE_COMPARATOR =
            new Comparator<ScoredMove>() {
        @Override
        public int compare(final ScoredMove o1, final ScoredMove o2) {
            return o2.score - o1.score;
        }
    };
//    /** The comparator to use for sorting jumps in descending order. */
//    private static final Comparator<Move> DESCENDING_JUMP_COMPARATOR =
//            new Comparator<Move>() {
//        @Override
//        public int compare(final Move o1, final Move o2) {
//            return o2.jumps.size() - o1.jumps.size();
//        }
//    };
}
