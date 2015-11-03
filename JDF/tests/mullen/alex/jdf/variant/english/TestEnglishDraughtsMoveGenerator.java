package mullen.alex.jdf.variant.english;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.BoardPosition;
import mullen.alex.jdf.common.Jump;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MoveGenerator;
import mullen.alex.jdf.common.Piece;
import mullen.alex.jdf.common.Piece.MoveDirection;

import org.junit.Test;

/**
 * Unit tests for {@link EnglishDraughtsMoveGenerator}.
 *
 * @author  Alex Mullen
 */
public class TestEnglishDraughtsMoveGenerator {
    /** The move generator to use for each test. */
    private final MoveGenerator moveGenerator =
            new EnglishDraughtsMoveGenerator();
    /** The dark pieces used for testing. */
    private final Piece darkPiece =
            new Piece(Piece.DARK, MoveDirection.DOWN);
    /** The light pieces used for testing. */
    private final Piece lightPiece =
            new Piece(Piece.LIGHT, MoveDirection.UP);
    /**
     * A test for making sure a simple move can be properly found.
     */
    @Test
    public final void testFindMovesWithNoJumps1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAndGetAt(3, 7, lightPiece));
        assertNull(board.setPieceAndGetAt(4, 6, darkPiece));
        assertNull(board.setPieceAndGetAt(5, 5, darkPiece));
        assertNull(board.setPieceAndGetAt(5, 7, darkPiece));
        final List<Move> foundMoves =
                moveGenerator.findMoves(board, Piece.LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundMove = foundMoves.get(0);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
        // Assert the expected move positions.
        assertEquals(new BoardPosition(3, 7), foundMove.from);
        assertEquals(new BoardPosition(2, 6), foundMove.to);
    }
    /**
     * A test for making sure a simple move can be properly found.
     */
    @Test
    public final void testFindMovesWithNoJumps2() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAndGetAt(3, 1, lightPiece));
        final List<Move> foundMoves =
                moveGenerator.findMoves(board, Piece.LIGHT);
        // There should only be two found.
        assertEquals(2, foundMoves.size());
        final Move firstfoundMove = foundMoves.get(0);
        // There should be no jumps.
        assertEquals(0, firstfoundMove.jumps.size());
        assertEquals(new BoardPosition(3, 1), firstfoundMove.from);
        assertEquals(new BoardPosition(2, 0), firstfoundMove.to);
        // Check the second move is what we expect.
        final Move secondfoundMove = foundMoves.get(1);
        // There should be no jumps.
        assertEquals(0, secondfoundMove.jumps.size());
        assertEquals(new BoardPosition(3, 1), secondfoundMove.from);
        assertEquals(new BoardPosition(4, 0), secondfoundMove.to);
    }
    /**
     * A test for making sure no moves are returned when there isn't any.
     */
    @Test
    public final void testFindMovesWithNoMovesOrJumps1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAndGetAt(0, 1, lightPiece));
        assertNull(board.setPieceAndGetAt(1, 0, lightPiece));
        final List<Move> foundMoves =
                moveGenerator.findMoves(board, Piece.LIGHT);
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
        assertNull(board.setPieceAndGetAt(3, 7, lightPiece));
        assertNull(board.setPieceAndGetAt(2, 6, darkPiece));
        final List<Move> foundMoves =
                moveGenerator.findMoves(board, Piece.LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move jumpMove = foundMoves.get(0);
        final BoardPosition expectedFromPosition = new BoardPosition(3, 7);
        final BoardPosition expectedtoPosition = new BoardPosition(1, 5);
        final BoardPosition expectedJumpPosition = new BoardPosition(2, 6);
        assertEquals(expectedFromPosition, jumpMove.from);
        assertEquals(expectedtoPosition, jumpMove.to);
        // There should be one jump in the sequence.
        assertEquals(1, jumpMove.jumps.size());
        assertEquals(expectedFromPosition, jumpMove.jumps.get(0).from);
        assertEquals(expectedtoPosition, jumpMove.jumps.get(0).to);
        assertEquals(expectedJumpPosition, jumpMove.jumps.get(0).jumped);
    }
    /**
     * A test for making sure a double jump in the same diagonal direction works
     * returns the correct move.
     */
    @Test
    public final void testFindMovesWithDoubleJumpSameDirection1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAndGetAt(0, 7, lightPiece));
        assertNull(board.setPieceAndGetAt(1, 6, darkPiece));
        assertNull(board.setPieceAndGetAt(3, 4, darkPiece));
        final List<Move> foundMoves =
                moveGenerator.findMoves(board, Piece.LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundJumpMove = foundMoves.get(0);
        // There should be two jumps in the sequence.
        assertEquals(2, foundJumpMove.jumps.size());
        // Check the first jump in the sequence is what we expect.
        final Jump firstJump = foundJumpMove.jumps.get(0);
        assertEquals(new BoardPosition(0, 7), firstJump.from);
        assertEquals(new BoardPosition(2, 5), firstJump.to);
        assertEquals(new BoardPosition(1, 6), firstJump.jumped);
        // Check the second jump in the sequence is what we expect.
        final Jump secondJump = foundJumpMove.jumps.get(1);
        assertEquals(new BoardPosition(2, 5), secondJump.from);
        assertEquals(new BoardPosition(4, 3), secondJump.to);
        assertEquals(new BoardPosition(3, 4), secondJump.jumped);
    }
    /**
     * A test for making sure a triple jump in the same diagonal direction works
     * returns the correct move.
     */
    @Test
    public final void testFindMovesWithTripleJumpSameDirection1() {
        final Board board = new Board(8, 8);
        assertNull(board.setPieceAndGetAt(0, 7, lightPiece));
        assertNull(board.setPieceAndGetAt(1, 6, darkPiece));
        assertNull(board.setPieceAndGetAt(3, 4, darkPiece));
        assertNull(board.setPieceAndGetAt(5, 2, darkPiece));
        final List<Move> foundMoves =
                moveGenerator.findMoves(board, Piece.LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundJumpMove = foundMoves.get(0);
        // There should be two jumps in the sequence.
        assertEquals(3, foundJumpMove.jumps.size());
        // Check the first jump in the sequence is what we expect.
        final Jump firstJump = foundJumpMove.jumps.get(0);
        assertEquals(new BoardPosition(0, 7), firstJump.from);
        assertEquals(new BoardPosition(2, 5), firstJump.to);
        assertEquals(new BoardPosition(1, 6), firstJump.jumped);
        // Check the second jump in the sequence is what we expect.
        final Jump secondJump = foundJumpMove.jumps.get(1);
        assertEquals(new BoardPosition(2, 5), secondJump.from);
        assertEquals(new BoardPosition(4, 3), secondJump.to);
        assertEquals(new BoardPosition(3, 4), secondJump.jumped);
        // Check the third jump in the sequence is what we expect.
        final Jump thirdJump = foundJumpMove.jumps.get(2);
        assertEquals(new BoardPosition(4, 3), thirdJump.from);
        assertEquals(new BoardPosition(6, 1), thirdJump.to);
        assertEquals(new BoardPosition(5, 2), thirdJump.jumped);
    }
}
