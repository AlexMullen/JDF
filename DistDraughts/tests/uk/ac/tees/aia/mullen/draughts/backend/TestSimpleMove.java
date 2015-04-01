package uk.ac.tees.aia.mullen.draughts.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit tests for {@link Move}.
 *
 * @author  Alex Mullen
 */
public class TestSimpleMove {
    /**
     * Tests the object with some typical expected arguments.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testWithValidArgs() {
        final BoardPosition fromPos = new BoardPosition(1, 3);
        final BoardPosition toPos = new BoardPosition(1, 5);
        final Move move = new SimpleMove(fromPos, toPos);
        assertNotNull(move.getFrom());
        assertNotNull(move.getTo());
        assertEquals(fromPos, move.getFrom());
        assertEquals(toPos, move.getTo());
    }
    /**
     * Tests the constructor with the <code>fromPosition</code> argument being
     * <code>null</code>.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullFromPosition() {
        final BoardPosition toPos = new BoardPosition(1, 5);
        new SimpleMove(null, toPos);
        fail("NPE should have been thrown!");
    }
    /**
     * Tests the constructor with the <code>toPosition</code> argument being
     * <code>null</code>.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullToPosition() {
        final BoardPosition fromPos = new BoardPosition(1, 3);
        new SimpleMove(fromPos, null);
        fail("NPE should have been thrown!");
    }
    /**
     * Tests the constructor with the <code>fromPosition</code> and the
     * <code>toPosition</code> arguments both being <code>null</code>.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullFromPositionAndToPosition() {
        new SimpleMove(null, null);
        fail("NPE should have been thrown!");
    }
    /**
     * Tests the constructor with the same <code>BoardPosition</code> reference
     * for both the <code>fromPosition</code> and <code>fromPosition</code>
     * arguments.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testConstructorWithSameFromAndToPositionReference() {
        final BoardPosition pos = new BoardPosition(1, 3);
        new SimpleMove(pos, pos);
        fail("The fromPosition and toPosition arguments should not be allowed"
                + " to be the same!");
    }
    /**
     * Tests the constructor with the same <code>fromPosition</code> and
     * <code>toPosition</code> but with two different object references to test
     * that they are being compared for equality rather by identity.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testConstructorWithSameFromAndToPosition() {
        final BoardPosition fromPos = new BoardPosition(1, 1);
        final BoardPosition toPos = new BoardPosition(1, 1);
        new SimpleMove(fromPos, toPos);
        fail("The fromPosition and toPosition arguments should not be allowed"
                + " to be the same position!");
    }
}
