package uk.ac.tees.aia.mullen.draughts.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit tests for {@link JumpMove}.
 *
 * @author  Alex Mullen
 */
public class TestJumpMove {
    /**
     * Tests the object with some typical expected arguments.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testWithValidArgs() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        final BoardPosition jumpedPosition = new BoardPosition(3, 7);
        final JumpMove move =
                new JumpMove(fromPosition, toPosition, jumpedPosition);
        assertEquals(fromPosition, move.getFrom());
        assertEquals(toPosition, move.getTo());
        assertEquals(jumpedPosition, move.getJumped());
    }
    /**
     * Tests the constructor with the parameter <code>jumpedPosition</code>
     * being <code>null</code> which should not be allowed.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullJumpedPositionArgument() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        new JumpMove(fromPosition, toPosition, null);
        fail("NPE should have been thrown!");
    }
    /**
     * Tests the constructor does not allow the <code>fromPosition</code> and
     * <code>jumpedPosition</code> to be the same reference.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testConstructorWithSameJumpedPosAsFromPosArgReference() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        new JumpMove(fromPosition, toPosition, fromPosition);
        fail("Should not allow fromPosition and jumpedPosition parameter being"
                + " the same reference");
    }
    /**
     * Tests the constructor does not allow the <code>fromPosition</code> and
     * <code>jumpedPosition</code> to be the same position.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testConstructorWithSameJumpedPosAsFromPosArg() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        final BoardPosition jumpedPosition = new BoardPosition(5, 3);
        new JumpMove(fromPosition, toPosition, jumpedPosition);
        fail("Should not allow fromPosition and jumpedPosition parameter being"
                + " the same position");
    }
    /**
     * Tests the constructor does not allow the <code>toPosition</code> and
     * <code>jumpedPosition</code> to be the same reference.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testConstructorWithSameJumpedPosAsToPosArgReference() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        new JumpMove(fromPosition, toPosition, toPosition);
        fail("Should not allow toPosition and jumpedPosition parameter being"
                + " the same reference");
    }
    /**
     * Tests the constructor does not allow the <code>toPosition</code> and
     * <code>jumpedPosition</code> to be the same position.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testConstructorWithSameJumpedPosAsToPosArg() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        final BoardPosition jumpedPosition = new BoardPosition(3, 5);
        new JumpMove(fromPosition, toPosition, jumpedPosition);
        fail("Should not allow toPosition and jumpedPosition parameter being"
                + " the same position");
    }
}
