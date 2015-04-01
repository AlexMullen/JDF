package uk.ac.tees.aia.mullen.draughts.backend;


/**
 * An interface for defining an evaluator that evaluates how good or bad a
 * move is.
 *
 * @author     Alex Mullen
 *
 */
public interface MoveEvaluator {
    /**
     * Evaluates and returns how good the specified move is for the specified
     * board.
     *
     * @param board  the board to use
     * @param move   the move
     * @return       a score indicating how good the move is with a higher value
     *               relative to other moves being deemed good
     */
    int evaluate(Board board, Move move);
}
