package mullen.alex.jdf.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link BoardPosition}.
 *
 * @author  Alex Mullen
 */
public class TestBoardPosition {
    /**
     * Tests the object with some typical expected arguments.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testWithTypicalArgs() {
        final int x = 5;
        final int y = 3;
        final BoardPosition boardPosition = new BoardPosition(x, y);
        assertEquals(x, boardPosition.x);
        assertEquals(y, boardPosition.y);
    }
    /**
     * Tests the {@link #equals(Object)} method with identity and state.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testEquals() {
        final BoardPosition boardPosition1 = new BoardPosition(5, 3);
        final BoardPosition boardPosition2 = new BoardPosition(5, 3);
        final BoardPosition boardPosition3 = new BoardPosition(5, 1);
        final BoardPosition boardPosition4 = new BoardPosition(1, 3);
        // Test null.
        assertFalse(boardPosition1.equals(null));
        // Self equality test.
        assertTrue(boardPosition1.equals(boardPosition1));
        // Symmetry test.
        assertTrue(boardPosition1.equals(boardPosition2));
        assertTrue(boardPosition2.equals(boardPosition1));
        // Values test.
        assertFalse(boardPosition1.equals(boardPosition3)); // (5,3) != (5,1)
        assertFalse(boardPosition3.equals(boardPosition1)); // (5,1) != (5,3)
        assertFalse(boardPosition2.equals(boardPosition4)); // (5,3) != (1,3)
        assertFalse(boardPosition4.equals(boardPosition2)); // (1,3) != (5,3)
    }
}
