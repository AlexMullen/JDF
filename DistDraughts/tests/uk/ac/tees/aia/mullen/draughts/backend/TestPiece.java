package uk.ac.tees.aia.mullen.draughts.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import uk.ac.tees.aia.mullen.draughts.backend.Piece.MoveDirection;

/**
 * Unit tests for {@link Piece}.
 *
 * @author  Alex Mullen
 */
public class TestPiece {
    /**
     * Tests the constructor with the parameter <code>owner</code> being
     * <code>null</code>. This should not be accepted.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullOwnerArg() {
        new Piece(null, MoveDirection.DOWN);
    }
    /**
     * Tests the constructor with the parameter <code>direction</code> being
     * <code>null</code>. This should not be accepted.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullDirectionArg() {
        new Piece(new EmptyMockPieceOwner(), null);
    }
    /**
     * Tests the constructor with the parameters <code>owner</code> and
     * <code>direction</code> being <code>null</code>. This should not be
     * accepted.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullOwnerAndNullDirectionArgs() {
        new Piece(null, null);
    }
    /**
     * Tests that {@link Piece#getMoveDirection()} returns the expected
     * value.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetMoveDirection() {
        final MoveDirection expectedMoveDirection = MoveDirection.DOWN;
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), expectedMoveDirection);
        assertEquals(expectedMoveDirection, piece.getMoveDirection());
    }
    /**
     * Tests that {@link Piece#getMoveDirection()} returns the expected
     * value after crowning the piece.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetMoveDirectionAfterCrown() {
        final MoveDirection originalMoveDirection = MoveDirection.DOWN;
        final MoveDirection expectedMoveDirection = MoveDirection.BOTH;
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), originalMoveDirection);
        piece.crown();
        assertEquals(expectedMoveDirection, piece.getMoveDirection());
    }
    /**
     * Tests that {@link Piece#isCrowned()} returns <code>false</code>
     * when the piece has not yet been crowned.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testIsCrowned() {
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.UP);
        assertFalse(piece.isCrowned());
        /*
         * A piece that is not crowned should not be able to move in both
         * directions.
         */
        assertNotSame(MoveDirection.BOTH, piece.getMoveDirection());
    }
    /**
     * Tests that {@link Piece#isCrowned()} returns <code>true</code>
     * when the piece has been crowned.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testIsCrownedAfterCrown() {
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.UP);
        // Should not be crowned yet.
        assertFalse(piece.isCrowned());
        piece.crown();
        // Should be now crowned.
        assertTrue(piece.isCrowned());
        // A piece that is crowned should be able to move in both directions.
        assertEquals(MoveDirection.BOTH, piece.getMoveDirection());
    }
    /**
     * Tests that {@link Piece#isCrowned()} returns <code>true</code>
     * when the piece has been crowned by setting the <code>direction</code>
     * parameter in the
     * {@link Piece#Piece(PieceOwner, MoveDirection)}
     * constructor to {@link MoveDirection#BOTH}.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testIsCrownedAfterCrownedInConstructor() {
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.BOTH);
        // Should be already crowned.
        assertTrue(piece.isCrowned());
        // A piece that is crowned should be able to move in both directions.
        assertEquals(MoveDirection.BOTH, piece.getMoveDirection());
    }
    /**
     * Tests that {@link Piece#crown()} causes
     * {@link Piece#isCrowned()} to return <code>true</code> and
     * {@link Piece#getMoveDirection()} to return
     * {@link MoveDirection#BOTH}.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testCrown() {
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.UP);
        // Should not be crowned yet.
        assertFalse(piece.isCrowned());
        piece.crown();
        // Should be now crowned.
        assertTrue(piece.isCrowned());
        // A piece that is crowned should be able to move in both directions.
        assertEquals(MoveDirection.BOTH, piece.getMoveDirection());
    }
    /**
     * Tests that calling {@link Piece#crown()} twice throws an
     * exception as it does not make sense to crown an already crowned piece
     * which may be due to a logic error.
     */
    @SuppressWarnings("static-method")
    @Test (expected = IllegalStateException.class)
    public final void testCrownTwice() {
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.DOWN);
        piece.crown();
        piece.crown();
        fail("Should not allow a piece to crowned more than once.");
    }
    /**
     * Tests that calling {@link Piece#crown()} twice throws an
     * exception as it does not make sense to crown an already crowned piece
     * which may be due to a logic error.
     */
    @SuppressWarnings("static-method")
    @Test (expected = IllegalStateException.class)
    public final void testCrownAfterCrownedInConstructor() {
        final Piece piece = new Piece(
                new EmptyMockPieceOwner(), MoveDirection.BOTH);
        piece.crown();
        fail("Should not allow a piece to crowned if it already is crowned.");
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
