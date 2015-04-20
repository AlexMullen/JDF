package uk.ac.tees.aia.mullen.draughts.common;

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
    public final int evaluate(final Board board, final Player owner) {
        int ownerScore = 0;
        int opponentScore = 0;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                final Piece foundPiece = board.getPieceAt(x, y);
                if (foundPiece != null) {
                    if (foundPiece.getOwner().equals(owner)) {
                        // Owner piece.
                        if (foundPiece.isCrowned()) {
                            ownerScore += POINTS_FOR_PIECE_CROWNED;
                        } else {
                            ownerScore += POINTS_FOR_PIECE;
                        }
                    } else {
                        // Opponent piece.
                        if (foundPiece.isCrowned()) {
                            opponentScore += POINTS_FOR_PIECE_CROWNED;
                        } else {
                            opponentScore += POINTS_FOR_PIECE;
                        }
                    }
                }
            }
        }
//        if (opponentScore == 0) {
//            System.out.println("Win guaranteed!");
//        }
//        System.out.println("ownerScore(" + ownerScore + ") - "
//        +  "opponentScore(" + opponentScore + ") = "
//                + (ownerScore - opponentScore));
        return ownerScore - opponentScore;
    }
}
