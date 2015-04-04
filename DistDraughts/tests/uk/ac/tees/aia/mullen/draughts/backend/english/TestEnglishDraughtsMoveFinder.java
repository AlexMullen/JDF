package uk.ac.tees.aia.mullen.draughts.backend.english;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import uk.ac.tees.aia.mullen.draughts.backend.Board;
import uk.ac.tees.aia.mullen.draughts.backend.BoardPosition;
import uk.ac.tees.aia.mullen.draughts.backend.Jump;
import uk.ac.tees.aia.mullen.draughts.backend.Move;
import uk.ac.tees.aia.mullen.draughts.backend.MoveFinder;
import uk.ac.tees.aia.mullen.draughts.backend.Piece;
import uk.ac.tees.aia.mullen.draughts.backend.PieceOwner;
import uk.ac.tees.aia.mullen.draughts.backend.Piece.MoveDirection;
import uk.ac.tees.aia.mullen.draughts.english.EnglishDraughtsMoveFinder;

/**
 * Unit tests for {@link EnglishDraughtsMoveFinder}.
 *
 * @author  Alex Mullen
 */
public class TestEnglishDraughtsMoveFinder {
    /** The move finder to use for each test. */
    private final MoveFinder moveFinder = new EnglishDraughtsMoveFinder();
    /** The dark piece owner used for testing. */
    private final PieceOwner darkPieceOwner = new EmptyMockPieceOwner();
    /** The light piece owner used for testing. */
    private final PieceOwner lightPieceOwner = new EmptyMockPieceOwner();
    /** The dark pieces used for testing. */
    private final Piece darkPiece =
            new Piece(darkPieceOwner, MoveDirection.DOWN);
    /** The light pieces used for testing. */
    private final Piece lightPiece =
            new Piece(lightPieceOwner, MoveDirection.UP);
    /**
     * A test for making sure a simple move can be properly found.
     */
    @Test
    public final void testFindMovesWithNoJumps1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAt(3, 7, lightPiece));
        assertNull(board.setPieceAt(4, 6, darkPiece));
        assertNull(board.setPieceAt(5, 5, darkPiece));
        assertNull(board.setPieceAt(5, 7, darkPiece));
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundMove = foundMoves.get(0);
        // There should be no jumps.
        assertNull(foundMove.getJumps());
        // Assert the expected move positions.
        assertEquals(new BoardPosition(3, 7), foundMove.getFrom());
        assertEquals(new BoardPosition(2, 6), foundMove.getTo());
    }
    /**
     * A test for making sure a simple move can be properly found.
     */
    @Test
    public final void testFindMovesWithNoJumps2() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAt(3, 1, lightPiece));
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        // There should only be two found.
        assertEquals(2, foundMoves.size());
        final Move firstfoundMove = foundMoves.get(0);
        assertNull(firstfoundMove.getJumps());
        assertEquals(new BoardPosition(3, 1), firstfoundMove.getFrom());
        assertEquals(new BoardPosition(2, 0), firstfoundMove.getTo());
        // Check the second move is what we expect.
        final Move secondfoundMove = foundMoves.get(1);
        assertNull(secondfoundMove.getJumps());
        assertEquals(new BoardPosition(3, 1), secondfoundMove.getFrom());
        assertEquals(new BoardPosition(4, 0), secondfoundMove.getTo());
    }
    /**
     * A test for making sure no moves are returned when there isn't any.
     */
    @Test
    public final void testFindMovesWithNoMovesOrJumps1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAt(0, 1, lightPiece));
        assertNull(board.setPieceAt(1, 0, lightPiece));
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        // There should be no moves found.
        assertEquals(0, foundMoves.size());
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
        assertNull(board.setPieceAt(3, 7, lightPiece));
        assertNull(board.setPieceAt(2, 6, darkPiece));
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move jumpMove = foundMoves.get(0);
        final BoardPosition expectedFromPosition = new BoardPosition(3, 7);
        final BoardPosition expectedtoPosition = new BoardPosition(1, 5);
        final BoardPosition expectedJumpPosition = new BoardPosition(2, 6);
        assertEquals(expectedFromPosition, jumpMove.getFrom());
        assertEquals(expectedtoPosition, jumpMove.getTo());
        // There should be one jump in the sequence.
        assertEquals(1, jumpMove.getJumps().size());
        assertEquals(expectedFromPosition,
                jumpMove.getJumps().get(0).getFrom());
        assertEquals(expectedtoPosition,
                jumpMove.getJumps().get(0).getTo());
        assertEquals(expectedJumpPosition,
                jumpMove.getJumps().get(0).getJumped());
    }
    /**
     * A test for making sure a double jump in the same diagonal direction works
     * returns the correct move.
     */
    @Test
    public final void testFindMovesWithDoubleJumpSameDirection1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAt(0, 7, lightPiece));
        assertNull(board.setPieceAt(1, 6, darkPiece));
        assertNull(board.setPieceAt(3, 4, darkPiece));
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundJumpMove = foundMoves.get(0);
        // There should be two jumps in the sequence.
        assertEquals(2, foundJumpMove.getJumps().size());
        // Check the first jump in the sequence is what we expect.
        final Jump firstJump = foundJumpMove.getJumps().get(0);
        assertEquals(new BoardPosition(0, 7), firstJump.getFrom());
        assertEquals(new BoardPosition(2, 5), firstJump.getTo());
        assertEquals(new BoardPosition(1, 6), firstJump.getJumped());
        // Check the second jump in the sequence is what we expect.
        final Jump secondJump = foundJumpMove.getJumps().get(1);
        assertEquals(new BoardPosition(2, 5), secondJump.getFrom());
        assertEquals(new BoardPosition(4, 3), secondJump.getTo());
        assertEquals(new BoardPosition(3, 4), secondJump.getJumped());
    }
    /**
     * A test for making sure a triple jump in the same diagonal direction works
     * returns the correct move.
     */
    @Test
    public final void testFindMovesWithTripleJumpSameDirection1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAt(0, 7, lightPiece));
        assertNull(board.setPieceAt(1, 6, darkPiece));
        assertNull(board.setPieceAt(3, 4, darkPiece));
        assertNull(board.setPieceAt(5, 2, darkPiece));
        final List<Move> foundMoves =
                moveFinder.findMoves(board, lightPieceOwner);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundJumpMove = foundMoves.get(0);
        // There should be two jumps in the sequence.
        assertEquals(3, foundJumpMove.getJumps().size());
        // Check the first jump in the sequence is what we expect.
        final Jump firstJump = foundJumpMove.getJumps().get(0);
        assertEquals(new BoardPosition(0, 7), firstJump.getFrom());
        assertEquals(new BoardPosition(2, 5), firstJump.getTo());
        assertEquals(new BoardPosition(1, 6), firstJump.getJumped());
        // Check the second jump in the sequence is what we expect.
        final Jump secondJump = foundJumpMove.getJumps().get(1);
        assertEquals(new BoardPosition(2, 5), secondJump.getFrom());
        assertEquals(new BoardPosition(4, 3), secondJump.getTo());
        assertEquals(new BoardPosition(3, 4), secondJump.getJumped());
        // Check the third jump in the sequence is what we expect.
        final Jump thirdJump = foundJumpMove.getJumps().get(2);
        assertEquals(new BoardPosition(4, 3), thirdJump.getFrom());
        assertEquals(new BoardPosition(6, 1), thirdJump.getTo());
        assertEquals(new BoardPosition(5, 2), thirdJump.getJumped());
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
