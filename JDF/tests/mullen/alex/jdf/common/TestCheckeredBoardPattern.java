/**
 * 
 */
package mullen.alex.jdf.common;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Unit tests for {@link CheckeredBoardPattern}.
 *
 * @author  Alex Mullen
 */
public class TestCheckeredBoardPattern {
    /**
     * Tests the constructor with some typical arguments to make sure no
     * exceptions are thrown.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testCheckeredBoardPattern() {
        final int width = 8;
        final int height = 7;
        final BoardPattern pattern =
                new CheckeredBoardPattern(BoardPattern.WHITE_SQUARE,
                        width, height);
    }
    /**
     * Tests {@link CheckeredBoardPattern#getWidth()} returns the expected
     * size.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetWidth() {
        final int width = 9;
        final int height = 7;
        final BoardPattern pattern =
                new CheckeredBoardPattern(BoardPattern.WHITE_SQUARE,
                        width, height);
        assertEquals(width, pattern.getWidth());
    }
    /**
     * Tests {@link CheckeredBoardPattern#getHeight()} returns the expected
     * size.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetHeight() {
        final int width = 6;
        final int height = 10;
        final BoardPattern pattern =
                new CheckeredBoardPattern(BoardPattern.WHITE_SQUARE,
                        width, height);
        assertEquals(width, pattern.getWidth());
        assertEquals(height, pattern.getHeight());
    }
    /**
     * Tests {@link CheckeredBoardPattern#getColourAt(int, int)} returns the
     * expected size.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetColourAt() {
        fail("Not yet implemented");
    }
}
