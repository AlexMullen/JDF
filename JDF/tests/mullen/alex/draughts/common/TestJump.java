package mullen.alex.draughts.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

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
    public final void testWithTypicalArgs() {
        final BoardPosition fromPosition = new BoardPosition(5, 3);
        final BoardPosition toPosition = new BoardPosition(3, 5);
        final BoardPosition jumpedPosition = new BoardPosition(3, 7);
        final Jump move =
                new Jump(fromPosition, toPosition, jumpedPosition);
        assertEquals(fromPosition, move.getFrom());
        assertEquals(toPosition, move.getTo());
        assertEquals(jumpedPosition, move.getJumped());
    }
}
