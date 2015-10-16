package mullen.alex.draughts.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import mullen.alex.draughts.common.BoardPosition;

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
    public final void testWithValidArgs() {
        final int x = 5;
        final int y = 3;
        final BoardPosition boardPosition = new BoardPosition(x, y);
        assertEquals(x, boardPosition.getX());
        assertEquals(y, boardPosition.getY());
    }
//    /**
//     * Tests the constructor with the <code>posX</code> argument being
//     * a negative number which should not be allowed.
//     */
//    @SuppressWarnings({ "static-method", "unused" })
//    @Test (expected = IllegalArgumentException.class)
//    public final void testConstructorWithNegativePosXArgument() {
//        final int posX = -1;
//        final int posY = 5;
//        new BoardPosition(posX, posY);
//        fail("Should not allow a negative posX argument!");
//    }
//    /**
//     * Tests the constructor with the <code>posY</code> argument being
//     * a negative number which should not be allowed.
//     */
//    @SuppressWarnings({ "static-method", "unused" })
//    @Test (expected = IllegalArgumentException.class)
//    public final void testConstructorWithNegativePosYArgument() {
//        final int posX = 5;
//        final int posY = -1;
//        new BoardPosition(posX, posY);
//        fail("Should not allow a negative posY argument!");
//    }
//    /**
//     * Tests the constructor with the <code>posX</code> and <code>posY</code>
//     * arguments both being negative which should not be allowed.
//     */
//    @SuppressWarnings({ "static-method", "unused" })
//    @Test (expected = IllegalArgumentException.class)
//    public final void testConstructorWithAllNegativeArgs() {
//        final int posX = -1;
//        final int posY = -1;
//        new BoardPosition(posX, posY);
//        fail("Should not allow a negative posX or posY argument!");
//    }
    /**
     * Tests the constructor with the lowest bound valid argument for the
     * <code>posX</code> argument.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testConstructorWithValidLowerBoundPosXArgument() {
        final int posX = 0;
        final int posY = 5;
        final BoardPosition boardPosition = new BoardPosition(posX, posY);
        assertEquals(posX, boardPosition.getX());
        assertEquals(posY, boardPosition.getY());
    }
    /**
     * Tests the constructor with the lowest bound valid argument for the
     * <code>posY</code> argument.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testConstructorWithValidLowerBoundPosYArgument() {
        final int posX = 5;
        final int posY = 0;
        final BoardPosition boardPosition = new BoardPosition(posX, posY);
        assertEquals(posX, boardPosition.getX());
        assertEquals(posY, boardPosition.getY());
    }
    /**
     * Tests the constructor with the <code>posX</code> and <code>posY</code>
     * arguments both being the lowest bound valid value they can be which
     * should be allowed.
     */
    @SuppressWarnings({ "static-method" })
    @Test
    public final void testConstructorWithAllLowerBoundArgs() {
        final int posX = 0;
        final int posY = 0;
        final BoardPosition boardPosition = new BoardPosition(posX, posY);
        assertEquals(posX, boardPosition.getX());
        assertEquals(posY, boardPosition.getY());
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
