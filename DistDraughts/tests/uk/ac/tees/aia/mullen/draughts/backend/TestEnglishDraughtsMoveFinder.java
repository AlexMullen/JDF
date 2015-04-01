package uk.ac.tees.aia.mullen.draughts.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import uk.ac.tees.aia.mullen.draughts.backend.Piece.MoveDirection;

/**
 * Unit tests for {@link EnglishDraughtsMoveFinder}.
 *
 * @author  Alex Mullen
 */
public class TestEnglishDraughtsMoveFinder {
    /** The move finder to use for each test. */
    private final EnglishDraughtsMoveFinder moveFinder =
            new EnglishDraughtsMoveFinder();
    /** The dark piece owner used for testing. */
    private final PieceOwner darkPieceOwner = new EmptyMockPieceOwner();
    /** The light piece owner used for testing. */
    private final PieceOwner lightPieceOwner = new EmptyMockPieceOwner();
    /**
     * A test for making sure a simple move can be properly found.
     */
    @Test
    public final void testFindMovesWithNoJumps1() {
        final Board board = new Board(8, 8);
        final Piece darkPiece = new Piece(darkPieceOwner, MoveDirection.DOWN);
        final Piece lightPiece = new Piece(lightPieceOwner, MoveDirection.UP);
        board.setPieceAt(3, 7, lightPiece);
        board.setPieceAt(4, 6, darkPiece);
        board.setPieceAt(5, 5, darkPiece);
        board.setPieceAt(5, 7, darkPiece);
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        if (foundMoves.get(0) instanceof SimpleMove) {
            final SimpleMove foundMove = (SimpleMove) foundMoves.get(0);
            final BoardPosition expectedFromPosition = new BoardPosition(3, 7);
            final BoardPosition expectedtoPosition = new BoardPosition(2, 6);
            assertEquals(expectedFromPosition, foundMove.getFrom());
            assertEquals(expectedtoPosition, foundMove.getTo());
        } else {
            fail("Expected a SimpleMove instance!");
        }
    }
    /**
     * A test for making sure a simple jump move can be properly found.
     * <p>
     * This test also gives the attacking piece an opportunity to move without
     * jumping but this should not be returned as there is a jump opportunity
     * which must be taken according to English Draughts rules.
     */
    @Test
    public final void testFindMovesWithSingleJump1() {
        final Board board = new Board(8, 8);
        final Piece darkPiece = new Piece(darkPieceOwner, MoveDirection.DOWN);
        final Piece lightPiece = new Piece(lightPieceOwner, MoveDirection.UP);
        board.setPieceAt(3, 7, lightPiece);
        board.setPieceAt(2, 6, darkPiece);
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        assertEquals(1, foundMoves.size());
        if (foundMoves.get(0) instanceof JumpMoveSequence) {
            final JumpMoveSequence jumpMoveSeq =
                    (JumpMoveSequence) foundMoves.get(0);
            final BoardPosition expectedFromPosition = new BoardPosition(3, 7);
            final BoardPosition expectedtoPosition = new BoardPosition(1, 5);
            final BoardPosition expectedJumpPosition = new BoardPosition(2, 6);
            assertEquals(expectedFromPosition, jumpMoveSeq.getFrom());
            assertEquals(expectedtoPosition, jumpMoveSeq.getTo());
            // There should be one jump in the sequence.
            assertEquals(1, jumpMoveSeq.getJumps().size());
            assertEquals(expectedFromPosition,
                    jumpMoveSeq.getJumps().get(0).getFrom());
            assertEquals(expectedtoPosition,
                    jumpMoveSeq.getJumps().get(0).getTo());
            assertEquals(expectedJumpPosition,
                    jumpMoveSeq.getJumps().get(0).getJumped());
        } else {
            fail("Expected a JumpMoveSequence instance!");
        }
    }
    /**
     * An empty mock <code>PieceOwner</code> instance used as a place holder
     * for testing.
     *
     * @author  Alex Mullen
     */
    private static class EmptyMockPieceOwner implements PieceOwner {
        /*
         * Nothing to implement.
         */
    }
}
