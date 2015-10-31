package mullen.alex.jdf.common.evaluation;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.Piece;
import mullen.alex.jdf.common.Player;

/**
 * A basic board evaluator implementation that simply returns the difference in
 * the scores given to how many pieces and crowned pieces each player has.
 *
 * @author  Alex Mullen
 *
 */
public class MaterialDifferenceBoardEvaluator implements BoardEvaluator {
    @Override
    public final float evaluate(final Board board, final Player owner) {
        int ownerPieceCrownedCount = 0;
        int ownerPieceNonCrownedCount = 0;
        int opponentPieceCrownedCount = 0;
        int opponentNonCrownedPieceCount = 0;
        // Go through each square and count the pieces.
        final Piece[] pieces = board.pieces;
        final int piecesArrayLength = pieces.length;
        for (int i = 0; i < piecesArrayLength; i++) {
            final Piece foundPiece = pieces[i];
            if (foundPiece != null) {
                if (foundPiece.owner == owner) {
                    // Owner piece.
                    if (foundPiece.isCrowned()) {
                        ownerPieceCrownedCount++;
                    } else {
                        ownerPieceNonCrownedCount++;
                    }
                } else {
                    // Opponent piece.
                    if (foundPiece.isCrowned()) {
                        opponentPieceCrownedCount++;
                    } else {
                        opponentNonCrownedPieceCount++;
                    }
                }
            }
        }
        final int ownerTotalPieceCount =
                (ownerPieceNonCrownedCount + ownerPieceCrownedCount);
        final int opponentTotalPieceCount =
                (opponentNonCrownedPieceCount + opponentPieceCrownedCount);
        return ownerTotalPieceCount - opponentTotalPieceCount;
    }
    @Override
    public final String toString() {
        return getClass().getSimpleName() + " []";
    }
}
