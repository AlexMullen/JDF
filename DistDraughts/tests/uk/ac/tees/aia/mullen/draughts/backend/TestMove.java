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
public class TestMove {
    /**
     * Tests the object with some typical expected arguments.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testWithValidArgs() {
        final BoardPosition fromPos = new BoardPosition(1, 3);
        final BoardPosition toPos = new BoardPosition(1, 5);
        final Move move = new Move(fromPos, toPos);
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
        new Move(null, toPos);
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
        new Move(fromPos, null);
        fail("NPE should have been thrown!");
    }
    /**
     * Tests the constructor with the <code>fromPosition</code> and the
     * <code>toPosition</code> arguments both being <code>null</code>.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = NullPointerException.class)
    public final void testConstructorWithNullFromPositionAndToPosition() {
        new Move(null, null);
        fail("NPE should have been thrown!");
    }
}
