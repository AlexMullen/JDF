package uk.ac.tees.aia.mullen.draughts.common.evaluation;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.aia.mullen.draughts.common.Board;
import uk.ac.tees.aia.mullen.draughts.common.BoardPosition;
import uk.ac.tees.aia.mullen.draughts.common.Piece;
import uk.ac.tees.aia.mullen.draughts.common.Player;

/**
 * A basic board evaluator implementation that simply returns the difference in
 * the scores given to how many pieces and crowned pieces each player has.
 *
 * @author  Alex Mullen
 *
 */
public class BasicBoardEvaluator implements BoardEvaluator {
    /** The number of points given for each piece. */
    static final int POINTS_FOR_PIECE = 1;
    /** The number of points given for each crowned piece. */
    static final int POINTS_FOR_PIECE_CROWNED = 2;
    /**
     * Creates a new instance.
     */
    public BasicBoardEvaluator() {
        // Empty constructor.
    }
    @Override
    public final float evaluate(final Board board, final Player owner) {
//        final List<BoardPosition> piecePositions = new ArrayList<>();
        int owner_pieceCount = 0;
        int owner_pieceCrownedCount = 0;
        
        int opponent_pieceCount = 0;
        int opponent_pieceCrownedCount = 0;


        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                final Piece foundPiece = board.getPieceAt(x, y);
                if (foundPiece != null) {
                    
//                    piecePositions.add(new BoardPosition(x, y));
                    
                    if (foundPiece.getOwner().equals(owner)) {
                        // Owner piece.
                        if (foundPiece.isCrowned()) {
                            owner_pieceCrownedCount++;
                        } else {
                            owner_pieceCount++;
                        }
                    } else {
                        // Opponent piece.
                        if (foundPiece.isCrowned()) {
                            opponent_pieceCrownedCount++;
                        } else {
                            opponent_pieceCount++;
                        }
                    }
                }
            }
        }
        
        
//        float totalX = 0, totalY = 0;
//        // Calculate the total X and Y positions.
//        for (final BoardPosition position : piecePositions) {
//            totalX += position.getX();
//            totalY += position.getY();
//        }
//        
//        // Calculate the mean of the X and Y positions.
//        float averageX = totalX / piecePositions.size();
//        float averageY = totalY / piecePositions.size();
//        
//        float absDeviationX = 0.0f, absDeviationY = 0.0f;
//        // Calculate the absolute deviation of X and Y.
//        for (final BoardPosition position : piecePositions) {
//            absDeviationX += Math.abs(position.getX() - averageX);
//            absDeviationY += Math.abs(position.getY() - averageY);
//        }
//
//        float totalAbsDeviation = absDeviationX + absDeviationY;
////        System.out.println(totalAbsDeviation);
        return ((owner_pieceCount + owner_pieceCrownedCount) - (opponent_pieceCount + opponent_pieceCrownedCount)) * 0.95f;
//        return ((owner_pieceCount + owner_pieceCrownedCount) - (opponent_pieceCount + opponent_pieceCrownedCount)) * 0.95f - (totalAbsDeviation * 0.05f);
    }
    @Override
    public final String toString() {
        return "BasicBoardEvaluator []";
    }
}
