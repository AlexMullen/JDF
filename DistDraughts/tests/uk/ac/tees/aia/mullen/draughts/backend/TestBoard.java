package uk.ac.tees.aia.mullen.draughts.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.tees.aia.mullen.draughts.backend.Piece.MoveDirection;

/**
 * Unit tests for {@link Board}.
 *
 * @author  Alex Mullen
 */
public class TestBoard {
    /**
     * Tests the constructor with some typical expected arguments.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testBoard() {
        final int width = 8;
        final int height = 8;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests the constructor with the lowest allowed width a board can be to
     * test the validation check.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testBoardWithLowestAllowedWidth() {
        final int width = 1;
        final int height = 8;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests the constructor with the lowest allowed height a board can be to
     * test the validation check.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testBoardWithLowestAllowedHeight() {
        final int width = 8;
        final int height = 1;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests the constructor with the lowest allowed width and height a board
     * can to test the validation check.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testBoardWithLowestAllowedWidthAndHeight() {
        final int width = 1;
        final int height = 1;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests the constructor with the lowest disallowed width a board can be to
     * test the validation check.
     */
    @SuppressWarnings({ "static-method" })
    @Test (expected = IllegalArgumentException.class)
    public final void testBoardWithFirstLowestDisallowedWidth() {
        final int width = 0;
        final int height = 8;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests the constructor with the lowest disallowed height a board can be to
     * test the validation check.
     */
    @SuppressWarnings({ "static-method" })
    @Test (expected = IllegalArgumentException.class)
    public final void testBoardWithFirstLowestDisallowedHeight() {
        final int width = 8;
        final int height = 0;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests the constructor with the lowest disallowed width and height a board
     * can be to test the validation check.
     */
    @SuppressWarnings({ "static-method" })
    @Test (expected = IllegalArgumentException.class)
    public final void testBoardWithFirstLowestDisallowedWidthAndHeight() {
        final int width = 0;
        final int height = 0;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests {@link Board#getWidth()} returns the same value passed into the
     * constructor. An unusual width value will be used to make sure the most
     * common value is not being hard-coded.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetWidth() {
        final int width = 24;
        final int height = 8;
        final Board board = new Board(width, height);
        assertEquals(width, board.getWidth());
    }
    /**
     * Tests {@link Board#getHeight()} returns the same value passed into the
     * constructor. An unusual height value will be used to make sure the most
     * common value is not being hard-coded.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetHeight() {
        final int width = 8;
        final int height = 645;
        final Board board = new Board(width, height);
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} returns expected values.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetPieceAtIntInt() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 4;
        final int positionPlacedY = 3;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
        retrievedPiece = board.getPieceAt(positionPlacedX, positionPlacedY);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} returns expected values when the
     * the lowest allowed X value is used.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetPieceAtIntIntWithLowestAllowedX() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 0;
        final int positionPlacedY = 3;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
        retrievedPiece = board.getPieceAt(positionPlacedX, positionPlacedY);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} returns expected values when the
     * the lowest allowed Y value is used.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetPieceAtIntIntWithLowestAllowedY() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 4;
        final int positionPlacedY = 0;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
        retrievedPiece = board.getPieceAt(positionPlacedX, positionPlacedY);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} returns expected values when the
     * the highest allowed X value is used.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetPieceAtIntIntWithHighestAllowedX() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = (width - 1);
        final int positionPlacedY = 3;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
        retrievedPiece = board.getPieceAt(positionPlacedX, positionPlacedY);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} returns expected values when the
     * the highest allowed Y value is used.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetPieceAtIntIntWithHighestAllowedY() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 4;
        final int positionPlacedY = (height - 1);
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
        retrievedPiece = board.getPieceAt(positionPlacedX, positionPlacedY);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} with the first lowest disallowed
     * X value.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testGetPieceAtIntIntWithFirstLowestDisallowedX() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = -1;
        final int positionPlacedY = 5;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        fail("The X value should not be accepted!");
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} with the first lowest disallowed
     * Y value.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testGetPieceAtIntIntWithFirstLowestDisallowedY() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 3;
        final int positionPlacedY = -1;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        fail("The Y value should not be accepted!");
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} with the first highest
     * disallowed X value.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testGetPieceAtIntIntWithFirstHighestDisallowedX() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = width;
        final int positionPlacedY = 5;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        fail("The X value should not be accepted!");
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} with the first highest
     * disallowed Y value.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testGetPieceAtIntIntWithFirstHighestDisallowedY() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 3;
        final int positionPlacedY = height;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        fail("The Y value should not be accepted!");
    }
    /**
     * Tests {@link Board#getPieceAt(BoardPosition)} returns expected values.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetPieceAtBoardPosition() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 4;
        final int positionPlacedY = 3;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
        retrievedPiece = board.getPieceAt(
                new BoardPosition(positionPlacedX, positionPlacedY));
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#getPieceAt(BoardPosition)} throws an NPE when passed
     * in a value of <code>null</code>.
     */
    @SuppressWarnings("static-method")
    @Test (expected = NullPointerException.class)
    public final void testGetPieceAtNullBoardPosition() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 4;
        final int positionPlacedY = 3;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
        assertNull(board.getPieceAt(null));
        fail("NPE should have been thrown!");
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} with the first highest
     * disallowed Y value.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testGetPieceAtNullBoardPositionWithOutOfBoundPosition() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = (width + 1);
        final int positionPlacedY = (height + 1);
        final Board board = new Board(width, height);
        assertNull(board.getPieceAt(
                new BoardPosition(positionPlacedX, positionPlacedY)));
        fail("The Y value should not be accepted!");
    }
    /**
     * Tests {@link Board#setPieceAt(int, int, Piece)} behaves as expected when
     * setting and clearing positions.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testSetPieceAt() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 7;
        final int positionPlacedY = 2;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, null);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#setPieceAt(int, int, Piece)} throws an exception when
     * passed in an out-of-bounds position.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testSetPieceAtOutOfBounds() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = -1;
        final int positionPlacedY = 8;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        fail("Exception should have been thrown!");
    }
    /**
     * Tests {@link Board#isPieceAt(int, int)} behaves as expected when setting
     * and clearing positions.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testIsPieceAt() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 7;
        final int positionPlacedY = 2;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        // There should be no piece at the position yet.
        assertFalse(board.isPieceAt(positionPlacedX, positionPlacedY));
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a previous piece there.
        assertNull(retrievedPiece);
        // There should now be a piece there.
        assertTrue(board.isPieceAt(positionPlacedX, positionPlacedY));
        retrievedPiece =
                board.setPieceAt(positionPlacedX, positionPlacedY, null);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
        // The position should now be vacant again.
        assertFalse(board.isPieceAt(positionPlacedX, positionPlacedY));
    }
    /**
     * Tests {@link Board#isPositionValid(int, int)} on every position of a
     * mock board and also tests the bounds.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testIsPositionValid() {
        final int width = 8;
        final int height = 8;
        final Board board = new Board(width, height);
        // Check all valid positions.
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                assertTrue(board.isPositionValid(x, y));
            }
        }
        // Check lower bounds (all invalid).
        assertFalse(board.isPositionValid(-1, 0));      // Invalid, Valid
        assertFalse(board.isPositionValid(0, -1));      // Valid, Invalid
        assertFalse(board.isPositionValid(-1, -1));     // Invalid, Invalid
        // Check upper bounds (all invalid).
        assertFalse(board.isPositionValid(width, height - 1)); // Invalid, Valid
        assertFalse(board.isPositionValid(width - 1, height)); // Valid, Invalid
        assertFalse(board.isPositionValid(width, height));   // Invalid, Invalid
    }
    /**
     * Tests {@link Board#isKingsRow(int)} detects the kings' rows correctly.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testIsKingsRow() {
        final int width = 8;
        final int height = 13;
        final Board board = new Board(width, height);
        // All these rows in between the bottom and top should not be king rows.
        for (int y = 1; y < (height - 1); y++) {
            assertFalse(board.isKingsRow(y));
        }
        // Top king row.
        assertTrue(board.isKingsRow(0));
        // Bottom king row.
        assertTrue(board.isKingsRow(height - 1));
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
