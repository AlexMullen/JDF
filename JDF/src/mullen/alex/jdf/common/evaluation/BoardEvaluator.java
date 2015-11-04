package mullen.alex.jdf.common.evaluation;

import mullen.alex.jdf.common.Board;


/**
 * An implementation that defines a class that evaluates and gives a board a
 * score from a specific colour's perspective.
 *
 * @author     Alex Mullen
 *
 */
public interface BoardEvaluator {
    /**
     * Evaluates the specified board from the specified
     * colour's perspective and returns a score value.
     * <p>
     * The higher the value, the better the state of the board is for the
     * given colour.
     *
     * @param board   the board to evaluate
     * @param colour  the colour the score applies to
     * @return        the score given
     */
    int evaluate(Board board, int colour);
}
