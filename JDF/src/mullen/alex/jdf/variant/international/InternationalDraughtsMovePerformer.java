package mullen.alex.jdf.variant.international;

import java.util.ArrayList;
import java.util.List;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.BoardPosition;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MovePerformer;
import mullen.alex.jdf.common.Piece;
import mullen.alex.jdf.common.Piece.MoveDirection;

/**
 * A move performer implementation for English draughts rules.
 *
 * @author  Alex Mullen
 *
 */
public class InternationalDraughtsMovePerformer implements MovePerformer {
    @Override
    public final PerformedMove perform(final Move move, final Board board) {
        final int jumpsSize = move.jumps.size();
        final List<UndoOperation> undoOperations =
                new ArrayList<>(3 + jumpsSize);
        final Piece pieceToMove = board.getPieceAt(move.from);
        undoOperations.add(setPieceAt(board, move.from, null));
        undoOperations.add(setPieceAt(board, move.to, pieceToMove));
        for (int i = 0; i < jumpsSize; i++) {
            undoOperations.add(
                    setPieceAt(board, move.jumps.get(i).jumped, null));
        }
        if (!pieceToMove.isCrowned() && isPieceInOpposingKingsRow(board,
                move.to.y, pieceToMove.getMoveDirection())) {
            /*
             * Create a copy of the uncrowned piece, crown it and then place it
             * into the board whilst saving the original for undoing.
             */
            final Piece pieceToMoveCopy = new Piece(pieceToMove);
            pieceToMoveCopy.crown();
            undoOperations.add(setPieceAt(board, move.to, pieceToMoveCopy));
        }
        return new PerformedMove() {
            @Override
            public void undo() {
                // Go backwards.
                for (int i = undoOperations.size() - 1; i >= 0; i--) {
                    undoOperations.get(i).undo();
                }
            }
        };
    }
    /**
     * Gets whether the specified Y position is the opposing kings row for a
     * piece.
     *
     * @param board         the board to check on
     * @param y             the Y position (top-to-bottom)
     * @param pieceMoveDir  the direction the piece is moving in
     * @return              <code>true</code> if the specified position is in
     *                      the opposing kings row; <code>false</code> if it is
     *                      not
     */
    public static final boolean isPieceInOpposingKingsRow(
            final Board board, final int y, final MoveDirection pieceMoveDir) {
        if (pieceMoveDir == MoveDirection.UP) {
            return y == 0;
        } else if (pieceMoveDir == MoveDirection.DOWN) {
            return y == (board.height - 1);
        } else {
            throw new IllegalStateException("Passed in a unhandled direction");
        }
    }
    /**
     * Sets the piece at the specified position on the given board and returns
     * a <code>UndoOperation</code> for it so that it can be rolled-back.
     *
     * @param board     the board to set on
     * @param position  the position to set at
     * @param piece     the piece to set at the position
     * @return          an <code>UndoOperation</code> that can rollback this
     *                  operation
     */
    private static UndoOperation setPieceAt(final Board board,
            final BoardPosition position, final Piece piece) {
        return new SetUndoOperation(board, position,
                board.setPieceAndGetAt(position, piece));
    }
    /**
     * An interface that represents an operation that will undo a single
     * modification made to a board.
     *
     * @author  Alex Mullen
     */
    private interface UndoOperation {
        /**
         * Undoes an operation.
         */
        void undo();
    }
    /**
     * A class that represents an operation to undo a set operation on a board.
     *
     * @author  Alex Mullen
     */
    private static final class SetUndoOperation implements UndoOperation {
        /** Holds the piece to set at the position. */
        private final Piece pieceToSet;
        /** Holds the board to perform the operation on. */
        private final Board boardToSet;
        /** Holds the position to set the piece back at. */
        private final BoardPosition positionToSet;
        /**
         * Creates a new operation instance that sets the specified piece on the
         * given board at the specified position.
         *
         * @param board     the board to set on
         * @param position  the position to set at
         * @param piece     the piece to set
         */
        protected SetUndoOperation(final Board board,
                final BoardPosition position, final Piece piece) {
            boardToSet = board;
            positionToSet = position;
            pieceToSet = piece;
        }
        @Override
        public void undo() {
            boardToSet.setPieceAt(positionToSet, pieceToSet);
        }
    }
}
