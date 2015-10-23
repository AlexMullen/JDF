package mullen.alex.draughts.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import mullen.alex.draughts.common.Piece.MoveDirection;

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
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testBoard() {
        final int width = 8;
        final int height = 8;
        final Board board = new Board(width, height, true);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
    }
    /**
     * Tests the copy constructor to make sure it produces a copy that is
     * exactly the same and does not change its state when the original
     * changes.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testBoardCopyConstructor() {
        // Check an empty board copy is the same.
        final Board board1 = new Board(7, 8, true);
        final Board board1Copy = new Board(board1);
        assertEquals(board1, board1Copy);
        /*
         * Check a placed piece is equal to its copied counterpart but does not
         * share the same reference.
         */
        final Piece piece1 = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        final Board board2 = new Board(5, 8, true);
        final int piecePosX = 2;
        final int piecePosY = 3;
        assertNull(board2.setPieceAndGetAt(piecePosX, piecePosY, piece1));
        final Board board2Copy = new Board(board2);
        // They should equal each other...
        assertTrue(board2.equals(board2Copy));
        assertTrue(board2Copy.equals(board2));
        final Piece board2Piece = board2.getPieceAt(piecePosX, piecePosY);
        final Piece board2CopyPiece =
                board2Copy.getPieceAt(piecePosX, piecePosY);
        assertEquals(board2Piece, board2CopyPiece);
        // ... but not be the same reference.
        assertNotSame(board2Piece, board2CopyPiece);
        // Crowning one of them should mean both boards are no longer equal.
        board2Piece.crown();
        assertFalse(board2.equals(board2Copy));
        assertFalse(board2Copy.equals(board2));
        // Crowning the other one should make them equal again.
        board2CopyPiece.crown();
        assertTrue(board2.equals(board2Copy));
        assertTrue(board2Copy.equals(board2));
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
        Board board = new Board(width, height, true);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
        // Test lower height.
        width = 8;
        height = 1;
        board = new Board(width, height, true);
        assertEquals(width, board.getWidth());
        assertEquals(height, board.getHeight());
        // Test lower width and height.
        width = 1;
        height = 1;
        board = new Board(width, height, true);
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
        final Board board = new Board(width, height, true);
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
        final Board board = new Board(width, height, true);
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
        final Board board = new Board(width, height, true);
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
        final Board board = new Board(width, height, true);
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
        final Board board = new Board(width, height, true);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a piece there yet.
        assertNull(retrievedPiece);
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
    public final void testGetPieceAtNullBoardPositionWithOutOfBoundPosition() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = (width + 1);
        final int positionPlacedY = (height + 1);
        final Board board = new Board(width, height, true);
        board.getPieceAt(new BoardPosition(positionPlacedX, positionPlacedY));
        fail("The Y value should not be accepted!");
    }
    /**
     * Tests {@link Board#setPieceAndGetAt(int, int, Piece)} behaves as
     * expected when setting and clearing positions.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testSetPieceAt() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 7;
        final int positionPlacedY = 2;
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
    public final void testSetPieceAtBoardPosition() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = 7;
        final int positionPlacedY = 2;
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
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
    public final void testSetPieceAtNullBoardPosition() {
        final int width = 8;
        final int height = 8;
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAndGetAt(null, piece);
        fail("NPE should have been thrown!");
    }
    /**
     * Tests {@link Board#setPieceAndGetAt(int, int, Piece)} throws an exception
     * when passed in an out-of-bounds position.
     */
    @SuppressWarnings("static-method")
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public final void testSetPieceAtOutOfBounds() {
        final int width = 8;
        final int height = 8;
        final int positionPlacedX = -1;
        final int positionPlacedY = 8;
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
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
        final Board board = new Board(width, height, true);
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        Piece retrievedPiece;
        // There should be no piece at the position yet.
        assertFalse(board.isPieceAt(positionPlacedX, positionPlacedY));
        retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, piece);
        // This should be null as there should not be a previous piece there.
        assertNull(retrievedPiece);
        // There should now be a piece there.
        assertTrue(board.isPieceAt(positionPlacedX, positionPlacedY));
        retrievedPiece =
                board.setPieceAndGetAt(positionPlacedX, positionPlacedY, null);
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
        final Board board = new Board(width, height, true);
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
        final Board board = new Board(width, height, true);
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
     * Tests that {@link Board#equals(Object)} tests for equality correctly.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testEquals() {
        final Board board1 = new Board(8, 8, true);
        final Board board2 = new Board(8, 8, true);
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
        final Piece piece1 =
                new Piece(new EmptyMockPieceOwner(), MoveDirection.DOWN);
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
        final Board board88 = new Board(8, 8, true);
        final Board board78 = new Board(7, 8, true);
        final Board board87 = new Board(8, 7, true);
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
        final Piece piece =
                new Piece(new EmptyMockPieceOwner(), MoveDirection.DOWN);
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
     * An empty mock <code>Player</code> instance used as a place holder
     * for testing.
     *
     * @author  Alex Mullen
     */
    private static class EmptyMockPieceOwner implements Player {
        @Override
        public boolean isArtificial() {
            return false;
        }
    }
}
