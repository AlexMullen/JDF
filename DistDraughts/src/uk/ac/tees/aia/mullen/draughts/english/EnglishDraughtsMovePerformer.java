package uk.ac.tees.aia.mullen.draughts.english;

import java.util.ArrayList;
import java.util.List;

import uk.ac.tees.aia.mullen.draughts.common.Board;
import uk.ac.tees.aia.mullen.draughts.common.BoardPosition;
import uk.ac.tees.aia.mullen.draughts.common.Jump;
import uk.ac.tees.aia.mullen.draughts.common.Move;
import uk.ac.tees.aia.mullen.draughts.common.MovePerformer;
import uk.ac.tees.aia.mullen.draughts.common.Piece;

/**
 * A move performer implementation for English draughts rules.
 *
 * @author  Alex Mullen
 *
 */
public class EnglishDraughtsMovePerformer implements MovePerformer {
    /**
     * Creates a new instance.
     */
    public EnglishDraughtsMovePerformer() {
        // Empty constructor.
    }
    @Override
    public final PerformedMove perform(final Move move, final Board board) {
        final List<UndoOperation> undoOperations = new ArrayList<>(8);
        final Piece pieceToMove = board.getPieceAt(move.getFrom());
        undoOperations.add(setPieceAt(board, move.getFrom(), null));
        undoOperations.add(setPieceAt(board, move.getTo(), pieceToMove));
        for (final Jump currentJump : move.getJumps()) {
            undoOperations.add(
                    setPieceAt(board, currentJump.getJumped(), null));
        }
        if (board.isKingsRow(move.getTo().getY()) && !pieceToMove.isCrowned()) {
            /*
             * Create a copy of the uncrowned piece, crown it and then place it
             * into the board whilst saving the original for undoing.
             */
            final Piece pieceToMoveCopy = new Piece(pieceToMove);
            pieceToMoveCopy.crown();
            undoOperations.add(
                    setPieceAt(board, move.getTo(), pieceToMoveCopy));
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
        private SetUndoOperation(final Board board,
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
