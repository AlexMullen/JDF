package mullen.alex.jdf.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static mullen.alex.jdf.common.Piece.*;

import org.junit.Test;

/**
 * Unit tests for {@link Board}.
 *
 * @author  Alex Mullen
 */
public class TestBoard {
    /**
     * Tests the constructor with some typical expected arguments.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testBoard() {
        final int width = 8;
        final int height = 7;
        final Board board = new Board(width, height);
        assertEquals(width, board.width);
        assertEquals(height, board.height);
    }
    /**
     * Tests that the backing piece array is of the expected length.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testBoardPieceArrayLength() {
        final int width = 7;
        final int height = 8;
        final Board board = new Board(width, height);
        final int expectedPieceArrayLength = width * height;
        assertEquals(expectedPieceArrayLength, board.pieces.length);
    }
    /**
     * Tests that the backing position array is of the expected length.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testBoardPositionArrayLength() {
        final int width = 6;
        final int height = 8;
        final Board board = new Board(width, height);
        final int expectedPieceArrayLength = width * height;
        assertEquals(expectedPieceArrayLength, board.positions.length);
    }
    /**
     * Tests that a copy of an empty board is equal to the original.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testBoardCopyConstructorWithEmptyBoard() {
        final Board board = new Board(7, 8);
        final Board boardCopy = new Board(board);
        assertEquals(board, boardCopy);
    }
    /**
     * Tests the copy constructor to make sure it produces a copy that is
     * exactly the same and does not change its state when the original
     * changes.
     * <p>
     * This also tests that a piece changing state on one board is not mirrored
     * on copies of that board to make sure references of the pieces are not
     * being shared.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testBoardCopyConstructor() {
        /*
         * Check a placed piece is equal to its copied counterpart but does not
         * share the same reference.
         */
        final Board board = new Board(5, 8);
        final int piecePosX = 2;
        final int piecePosY = 3;
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAndGetAt(piecePosX, piecePosY, piece);
        // Create a copy.
        final Board boardCopy = new Board(board);
        // Both boards should be equal to each other...
        assertTrue(board.equals(boardCopy));
        assertTrue(boardCopy.equals(board));
        // Retrieve references to the same positioned piece on each board.
        final Piece boardCopyPiece1 = board.getPieceAt(piecePosX, piecePosY);
        final Piece boardCopyPiece2 =
                boardCopy.getPieceAt(piecePosX, piecePosY);
        // They should be equal to each other...
        assertEquals(boardCopyPiece1, boardCopyPiece2);
        // ... but should not be the same reference.
        assertNotSame(boardCopyPiece1, boardCopyPiece2);
        // Crowning one of them should mean both boards are no longer equal.
        boardCopyPiece1.crown();
        assertFalse(board.equals(boardCopy));
        assertFalse(boardCopy.equals(board));
        // Crowning the other one should make them equal again.
        boardCopyPiece2.crown();
        assertTrue(board.equals(boardCopy));
        assertTrue(boardCopy.equals(board));
    }
    /**
     * Tests that the copy constructor throws an NPE if <code>null</code> is
     * passed into it.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testBoardCopyConstructorWithNull() {
        new Board(null);
    }
    /**
     * Tests the constructor with the lowest allowed width and height a board
     * can to test the validation check allows them.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testBoardConstructorWithValidBorderlineWidthAndHeight() {
        // Test lower width.
        int width = 1;
        int height = 8;
        Board board = new Board(width, height);
        assertEquals(width, board.width);
        assertEquals(height, board.height);
        // Test lower height.
        width = 8;
        height = 1;
        board = new Board(width, height);
        assertEquals(width, board.width);
        assertEquals(height, board.height);
        // Test lower width and height.
        width = 1;
        height = 1;
        board = new Board(width, height);
        assertEquals(width, board.width);
        assertEquals(height, board.height);
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
        assertEquals(width, board.width);
        assertEquals(height, board.height);
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
        assertEquals(width, board.width);
        assertEquals(height, board.height);
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
        assertEquals(width, board.width);
        assertEquals(height, board.height);
    }
    /**
     * Tests {@link Board#width} returns the same value passed into the
     * constructor. An unusual width value will be used to make sure the most
     * common value is not being hard-coded.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testWidth() {
        final int width = 24;
        final int height = 8;
        final Board board = new Board(width, height);
        assertEquals(width, board.width);
    }
    /**
     * Tests {@link Board#height} returns the same value passed into the
     * constructor. An unusual height value will be used to make sure the most
     * common value is not being hard-coded.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testHeight() {
        final int width = 8;
        final int height = 645;
        final Board board = new Board(width, height);
        assertEquals(height, board.height);
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
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        final Piece retrievedPiece =
                board.getPieceAt(positionPlacedX, positionPlacedY);
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
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        final Piece retrievedPiece =
                board.getPieceAt(positionPlacedX, positionPlacedY);
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
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        final Piece retrievedPiece =
                board.getPieceAt(positionPlacedX, positionPlacedY);
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
        final int positionPlacedX = width - 1;
        final int positionPlacedY = 3;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        final Piece retrievedPiece =
                board.getPieceAt(positionPlacedX, positionPlacedY);
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
        final int positionPlacedY = height - 1;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        final Piece retrievedPiece =
                board.getPieceAt(positionPlacedX, positionPlacedY);
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
        board.getPieceAt(positionPlacedX, positionPlacedY);
        fail("The X value should not be accepted!");
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
        board.getPieceAt(positionPlacedX, positionPlacedY);
        fail("The X value should not be accepted!");
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
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        final Piece retrievedPiece = board.getPieceAt(
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
    public final void testGetPieceAtWithNullBoardPositionParameter() {
        final int width = 8;
        final int height = 8;
        final Board board = new Board(width, height);
        // This should throw an NPE.
        board.getPieceAt(null);
        fail("NPE should have been thrown!");
    }
    /**
     * Tests {@link Board#getPieceAt(int, int)} with the first highest
     * disallowed Y value.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testGetPieceAtWithOutOfBoundBoardPositionParameter() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = width + 1;
        final int positionPlacedY = height + 1;
        final Board board = new Board(width, height);
        // This should throw an exception.
        board.getPieceAt(new BoardPosition(positionPlacedX, positionPlacedY));
        fail("The Y value should not be accepted!");
    }
    /**
     * Tests {@link Board#setPieceAndGetAt(int, int, Piece)} behaves as
     * expected when setting and clearing positions.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testSetPieceAndGetAt() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 7;
        final int positionPlacedY = 2;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(DARK, DOWN);
        // The current position should be null.
        assertNull(board.setPieceAndGetAt(
                positionPlacedX, positionPlacedY, piece));
        final Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, null);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#setPieceAndGetAt(BoardPosition, Piece)} sets the
     * piece at the correct position.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testSetPieceAndGetAtBoardPosition() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 7;
        final int positionPlacedY = 2;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(DARK, DOWN);
        assertNull(board.setPieceAndGetAt(
                new BoardPosition(positionPlacedX, positionPlacedY), piece));
        final Piece retrievedPiece =
                board.getPieceAt(positionPlacedX, positionPlacedY);
        // This should be the same one we placed.
        assertEquals(piece, retrievedPiece);
    }
    /**
     * Tests {@link Board#setPieceAndGetAt(BoardPosition, Piece)} throws an
     * exception if passed in a <code>null</code> position.
     */
    @SuppressWarnings("static-method")
    @Test (expected = NullPointerException.class)
    public final void testSetPieceAndGetAtWithNullBoardPositionParameter() {
        final int width = 8;
        final int height = 8;
        final Board board = new Board(width, height);
        final Piece piece = new Piece(DARK, DOWN);
        board.setPieceAndGetAt(null, piece);
        fail("NPE should have been thrown!");
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
        final Piece piece = new Piece(DARK, DOWN);
        // There should be no piece at the position yet.
        assertFalse(board.isPieceAt(positionPlacedX, positionPlacedY));
        // Place a piece.
        board.setPieceAt(positionPlacedX, positionPlacedY, piece);
        // There should now be a piece there.
        assertTrue(board.isPieceAt(positionPlacedX, positionPlacedY));
        // Retrieve and remove the piece.
        final Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, null);
        // The retrieved piece should be the same one we placed.
        assertEquals(piece, retrievedPiece);
        // The position should now be vacant again.
        assertFalse(board.isPieceAt(positionPlacedX, positionPlacedY));
    }
    /**
     * Tests {@link Board#isPositionWithinBounds(int, int)} on every position of
     * a mock board and also tests the bounds.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testIsPositionWithinBounds() {
        final int width = 8;
        final int height = 8;
        final Board board = new Board(width, height);
        // Check all valid positions.
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                assertTrue(board.isPositionWithinBounds(x, y));
            }
        }
        // Check lower bounds (all invalid).
        // Invalid, Valid
        assertFalse(board.isPositionWithinBounds(-1, 0));
        // Valid, Invalid
        assertFalse(board.isPositionWithinBounds(0, -1));
        // Invalid, Invalid
        assertFalse(board.isPositionWithinBounds(-1, -1));
        // Check upper bounds (all invalid).
        // Invalid, Valid
        assertFalse(board.isPositionWithinBounds(width, height - 1));
        // Valid, Invalid
        assertFalse(board.isPositionWithinBounds(width - 1, height));
        // Invalid, Invalid
        assertFalse(board.isPositionWithinBounds(width, height));
    }
    /**
     * Tests that {@link Board#equals(Object)} tests for equality correctly.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testEquals() {
        final Board board1 = new Board(8, 8);
        final Board board2 = new Board(8, 8);
        // Test null.
        assertFalse(board1.equals(null));
        assertFalse(board2.equals(null));
        // Test identity equality.
        assertTrue(board1.equals(board1));
        assertTrue(board2.equals(board2));
        // Test non-identity equality (the boards are currently empty).
        assertTrue(board1.equals(board2));
        assertTrue(board2.equals(board1));
        // Test non-identity equality when adding a piece to a board.
        final Piece piece1 = new Piece(DARK, DOWN);
        final Piece piece1Copy = new Piece(piece1);
        // Add a piece to board 1 and assert board1 and board2 are not equal.
        assertNull(board1.setPieceAndGetAt(0, 7, piece1));
        assertFalse(board1.equals(board2));
        assertFalse(board2.equals(board1));
        // Add that same piece to board 2 at the same position.
        assertNull(board2.setPieceAndGetAt(0, 7, piece1));
        assertTrue(board1.equals(board2));
        assertTrue(board2.equals(board1));
        // Move the piece to a different position on board 2.
        assertSame(piece1, board2.setPieceAndGetAt(0, 7, null));
        board2.setPieceAndGetAt(7, 5, piece1);
        assertFalse(board1.equals(board2));
        assertFalse(board2.equals(board1));
        // Remove that piece.
        assertSame(piece1, board2.setPieceAndGetAt(7, 5, null));
        assertFalse(board1.equals(board2));
        assertFalse(board2.equals(board1));
        // Place a copy of piece1 in the same position piece1 is at on board1.
        assertNull(board2.setPieceAndGetAt(0, 7, piece1Copy));
        assertTrue(board1.equals(board2));
        assertTrue(board2.equals(board1));
        // Crown piece1 which should make the boards unequal.
        piece1.crown();
        assertFalse(board1.equals(board2));
        assertFalse(board2.equals(board1));
        // Crowning the copy should make them equal again.
        piece1Copy.crown();
        assertTrue(board1.equals(board2));
        assertTrue(board2.equals(board1));
    }
    /**
     * Tests that {@link Board#equals(Object)} tests for equality correctly when
     * given boards of a different size.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testEqualsWithDifferentSizeBoard() {
        final Board board88 = new Board(8, 8);
        final Board board78 = new Board(7, 8);
        final Board board87 = new Board(8, 7);
        // They should all not be equal to each other.
        assertFalse(board88.equals(board78));
        assertFalse(board88.equals(board87));
        assertFalse(board78.equals(board88));
        assertFalse(board78.equals(board87));
        assertFalse(board87.equals(board88));
        assertFalse(board87.equals(board78));
        /*
         * Adding the same piece to the same position on them all should make
         * no difference.
         */
        final Piece piece = new Piece(DARK, DOWN);
        assertNull(board88.setPieceAndGetAt(1, 2, piece));
        assertNull(board78.setPieceAndGetAt(1, 2, piece));
        assertNull(board87.setPieceAndGetAt(1, 2, piece));
        // They should still not equal each other.
        assertFalse(board88.equals(board78));
        assertFalse(board88.equals(board87));
        assertFalse(board78.equals(board88));
        assertFalse(board78.equals(board87));
        assertFalse(board87.equals(board88));
        assertFalse(board87.equals(board78));
    }
    /**
     * Tests that {@link Board#hashCode()} returns a different hashcode value
     * for different sized empty boards.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testHashCodeIsDifferentForDifferentSizedEmptyBoards() {
        final Board board88 = new Board(8, 8);
        final Board board78 = new Board(7, 8);
        final Board board87 = new Board(8, 7);
        // It should have the same hashcode when called twice.
        assertTrue(board88.hashCode() == board88.hashCode());
        // It should have a different hashcode to the other boards.
        assertTrue(board88.hashCode() != board78.hashCode());
        assertTrue(board88.hashCode() != board87.hashCode());
    }
}
