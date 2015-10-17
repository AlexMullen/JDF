package mullen.alex.draughts.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import mullen.alex.draughts.common.BoardPosition;
import mullen.alex.draughts.common.Move;

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
//    /**
//     * Tests the constructor with the <code>fromPosition</code> argument being
//     * <code>null</code>.
//     */
//    @SuppressWarnings({ "static-method", "unused" })
//    @Test (expected = NullPointerException.class)
//    public final void testConstructorWithNullFromPosition() {
//        final BoardPosition toPos = new BoardPosition(1, 5);
//        new Move(null, toPos);
//        fail("NPE should have been thrown!");
//    }
//    /**
//     * Tests the constructor with the <code>toPosition</code> argument being
//     * <code>null</code>.
//     */
//    @SuppressWarnings({ "static-method", "unused" })
//    @Test (expected = NullPointerException.class)
//    public final void testConstructorWithNullToPosition() {
//        final BoardPosition fromPos = new BoardPosition(1, 3);
//        new Move(fromPos, null);
//        fail("NPE should have been thrown!");
//    }
//    /**
//     * Tests the constructor with the <code>fromPosition</code> and the
//     * <code>toPosition</code> arguments both being <code>null</code>.
//     */
//    @SuppressWarnings({ "static-method", "unused" })
//    @Test (expected = NullPointerException.class)
//    public final void testConstructorWithNullFromPositionAndToPosition() {
//        new Move(null, null);
//        fail("NPE should have been thrown!");
//    }
    /**
     * Tests {@link Move#toString()} does not throw an NPE when attempting to
     * print out a <code>null</code> {@link Move#getJumps()} reference.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testToStringWithNullJumpList() {
        final BoardPosition fromPos = new BoardPosition(1, 3);
        final BoardPosition toPos = new BoardPosition(1, 5);
        // Test implicit null jump list constructor.
        Move move = new Move(fromPos, toPos);
        assertNotNull(move.toString());
        // Test explicit null jump list constructor.
        move = new Move(fromPos, toPos);
        assertNotNull(move.toString());
    }
}
