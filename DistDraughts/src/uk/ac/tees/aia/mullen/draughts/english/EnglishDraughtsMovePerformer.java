package uk.ac.tees.aia.mullen.draughts.english;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import uk.ac.tees.aia.mullen.draughts.backend.Board;
import uk.ac.tees.aia.mullen.draughts.backend.BoardPosition;
import uk.ac.tees.aia.mullen.draughts.backend.Jump;
import uk.ac.tees.aia.mullen.draughts.backend.Move;
import uk.ac.tees.aia.mullen.draughts.backend.MovePerformer;
import uk.ac.tees.aia.mullen.draughts.backend.Piece;

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
        final Deque<UndoOperation> undoOperations = new ArrayDeque<>();
        final Piece pieceToMove = board.getPieceAt(move.getFrom());
        undoOperations.addFirst(setPieceAt(board, move.getFrom(), null));
        undoOperations.addFirst(setPieceAt(board, move.getTo(), pieceToMove));
        if (move.getJumps() != null) {
            for (final Jump currentJump : move.getJumps()) {
                undoOperations.addFirst(
                        setPieceAt(board, currentJump.getJumped(), null));
            }
        }
        if (!pieceToMove.isCrowned() && board.isKingsRow(move.getTo().getY())) {
            /*
             * Create a copy of the uncrowned piece, crown it and then place it
             * into the board whilst saving the original for undoing.
             */
            final Piece pieceToMoveCopy = new Piece(pieceToMove);
            pieceToMoveCopy.crown();
            undoOperations.addFirst(
                    setPieceAt(board, move.getTo(), pieceToMoveCopy));
        }
        return new PerformedMove() {
            @Override
            public void undo() {
                while (!undoOperations.isEmpty()) {
                    undoOperations.removeFirst().undo();
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
                board.setPieceAt(position, piece));
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
            boardToSet = Objects.requireNonNull(board);
            positionToSet = Objects.requireNonNull(position);
            pieceToSet = piece;
        }
        @Override
        public void undo() {
            boardToSet.setPieceAt(positionToSet, pieceToSet);
        }
    }
}
