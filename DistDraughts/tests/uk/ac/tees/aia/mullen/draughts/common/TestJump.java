package uk.ac.tees.aia.mullen.draughts.common;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import uk.ac.tees.aia.mullen.draughts.common.BoardPosition;
import uk.ac.tees.aia.mullen.draughts.common.Jump;

/**
 * Unit tests for {@link Jump}.
 *
 * @author  Alex Mullen
 */
public class TestJump {
    /**
     * Tests the object with some typical expected arguments.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testWithValidArgs() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        final BoardPosition jumpedPosition = new BoardPosition(3, 7);
        final Jump move =
                new Jump(fromPosition, toPosition, jumpedPosition);
        assertEquals(fromPosition, move.getFrom());
        assertEquals(toPosition, move.getTo());
        assertEquals(jumpedPosition, move.getJumped());
    }
//    /**
//     * Tests the constructor with the parameter <code>jumpedPosition</code>
//     * being <code>null</code> which should not be allowed.
//     */
//    @SuppressWarnings({ "static-method", "unused" })
//    @Test (expected = NullPointerException.class)
//    public final void testConstructorWithNullJumpedPositionArgument() {
//        final BoardPosition fromPosition = new BoardPosition(5, 3);
//        final BoardPosition toPosition = new BoardPosition(3, 5);
//        new Jump(fromPosition, toPosition, null);
//        fail("NPE should have been thrown!");
//    }
}
