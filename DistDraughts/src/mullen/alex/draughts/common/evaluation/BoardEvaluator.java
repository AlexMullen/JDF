package mullen.alex.draughts.common.evaluation;

import mullen.alex.draughts.common.Board;
import mullen.alex.draughts.common.Player;


/**
 * An implementation that defines a class that evaluates and gives a board a
 * score from a specific <code>PieceOwner</code>'s perspective.
 *
 * @author     Alex Mullen
 *
 */
public interface BoardEvaluator {
    /**
     * Evaluates the specified board from the specified
     * <code>PieceOwner</code>'s perspective and returns a score value.
     * <p>
     * The higher the value, the better the state of the board is for the
     * given owner.
     *
     * @param board  the board to evaluate
     * @param owner  the piece owner the score applies to
     * @return       the score given
     */
    float evaluate(Board board, Player owner);
}
