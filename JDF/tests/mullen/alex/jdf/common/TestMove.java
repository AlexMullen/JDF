package mullen.alex.jdf.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertNotNull(move.from);
        assertNotNull(move.to);
        assertEquals(fromPos, move.from);
        assertEquals(toPos, move.to);
    }
//    /**
//     * Tests {@link Move#jumps} never returns <code>null</code> when there
//     * is no jumps for the move.
//     */
//    @SuppressWarnings("static-method")
//    @Test
//    public final void testGetJumpsNeverReturnsNull() {
//        final BoardPosition fromPos = new BoardPosition(1, 3);
//        final BoardPosition toPos = new BoardPosition(1, 5);
//        // Test implicit null jump list constructor.
//        Move move = new Move(fromPos, toPos);
//        assertNotNull(move.jumps);
//        // Test explicit null jump list constructor.
//        move = new Move(fromPos, toPos, null);
//        assertNotNull(move.jumps);
//    }
//    /**
//     * Tests {@link Move#toString()} does not throw an NPE when attempting to
//     * print out a <code>null</code> {@link Move#jumps} reference.
//     */
//    @SuppressWarnings("static-method")
//    @Test
//    public final void testToStringWithNullJumpList() {
//        final BoardPosition fromPos = new BoardPosition(1, 3);
//        final BoardPosition toPos = new BoardPosition(1, 5);
//        // Test implicit null jump list constructor.
//        Move move = new Move(fromPos, toPos);
//        assertNotNull(move.toString());
//        // Test explicit null jump list constructor.
//        move = new Move(fromPos, toPos, null);
//        assertNotNull(move.toString());
//    }
}
