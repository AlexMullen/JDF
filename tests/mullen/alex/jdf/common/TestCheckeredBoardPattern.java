package mullen.alex.jdf.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static mullen.alex.jdf.common.BoardPattern.WHITE_SQUARE;
import static mullen.alex.jdf.common.BoardPattern.BLACK_SQUARE;

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
    @SuppressWarnings({ "static-method", "unused" })
    @Test
    public final void testCheckeredBoardPatternWithValidArgs() {
        final int width = 8;
        final int height = 7;
        final int initialColour = WHITE_SQUARE;
        new CheckeredBoardPattern(initialColour, width, height);
    }
    /**
     * Tests the constructor with an invalid initial colour argument to make
     * sure an exception is thrown.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testCheckeredBoardPatternWithInvalidInitialColour() {
        final int width = 8;
        final int height = 8;
        final int badInitialColour = 454738953;
        new CheckeredBoardPattern(badInitialColour, width, height);
    }
    /**
     * Tests the constructor accepts the lowest valid width without throwing
     * an exception.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test
    public final void testCheckeredBoardPatternWithBorderlineValidWidth() {
        final int width = 1;
        final int height = 8;
        final int initialColour = BLACK_SQUARE;
        new CheckeredBoardPattern(initialColour, width, height);
    }
    /**
     * Tests the constructor throws an exception when passed the first invalid
     * borderline width value to make sure the check is not off by one.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testCheckeredBoardPatternWithBorderlineInvalidWidth() {
        final int width = 0;
        final int height = 8;
        final int initialColour = WHITE_SQUARE;
        new CheckeredBoardPattern(initialColour, width, height);
    }
    /**
     * Tests the constructor accepts the lowest valid height without throwing
     * an exception.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test
    public final void testCheckeredBoardPatternWithBorderlineValidHeight() {
        final int width = 8;
        final int height = 1;
        final int initialColour = BLACK_SQUARE;
        new CheckeredBoardPattern(initialColour, width, height);
    }
    /**
     * Tests the constructor throws an exception when passed the first invalid
     * borderline height value to make sure the check is not off by one.
     */
    @SuppressWarnings({ "static-method", "unused" })
    @Test (expected = IllegalArgumentException.class)
    public final void testCheckeredBoardPatternWithBorderlineInvalidHeight() {
        final int width = 8;
        final int height = 0;
        final int initialColour = WHITE_SQUARE;
        new CheckeredBoardPattern(initialColour, width, height);
    }
    /**
     * Tests {@link CheckeredBoardPattern#getColourAt(int, int)} returns the
     * expected colour on a 1x1 board that is set initially to white.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetColourAtWithOneByOnePatternInitiallyWhite() {
        final int width = 1;
        final int height = 1;
        final int initialColour = WHITE_SQUARE;
        final BoardPattern pattern =
                new CheckeredBoardPattern(initialColour, width, height);
        assertEquals(initialColour, pattern.getColourAt(0, 0));
    }
    /**
     * Tests {@link CheckeredBoardPattern#getColourAt(int, int)} returns the
     * expected colour on a 1x1 board that is set initially to black.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetColourAtWithOneByOnePatternInitiallyBlack() {
        final int width = 1;
        final int height = 1;
        final int initialColour = BLACK_SQUARE;
        final BoardPattern pattern =
                new CheckeredBoardPattern(initialColour, width, height);
        assertEquals(initialColour, pattern.getColourAt(0, 0));
    }
    /**
     * Tests {@link CheckeredBoardPattern#getColourAt(int, int)} returns the
     * expected colours on a 3x3 board that is set initially to white.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetColourAtWithThreeByThreePatternInitiallyWhite() {
        final int width = 3;
        final int height = 3;
        final int initialColour = WHITE_SQUARE;
        final BoardPattern pattern =
                new CheckeredBoardPattern(initialColour, width, height);
        // First row.
        assertEquals(WHITE_SQUARE, pattern.getColourAt(0, 0));
        assertEquals(BLACK_SQUARE, pattern.getColourAt(1, 0));
        assertEquals(WHITE_SQUARE, pattern.getColourAt(2, 0));
        // Second row.
        assertEquals(BLACK_SQUARE, pattern.getColourAt(0, 1));
        assertEquals(WHITE_SQUARE, pattern.getColourAt(1, 1));
        assertEquals(BLACK_SQUARE, pattern.getColourAt(2, 1));
        // Third row.
        assertEquals(WHITE_SQUARE, pattern.getColourAt(0, 2));
        assertEquals(BLACK_SQUARE, pattern.getColourAt(1, 2));
        assertEquals(WHITE_SQUARE, pattern.getColourAt(2, 2));
    }
    /**
     * Tests {@link CheckeredBoardPattern#getColourAt(int, int)} returns the
     * expected colours on a 3x3 board that is set initially to black.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetColourAtWithThreeByThreePatternInitiallyBlack() {
        final int width = 3;
        final int height = 3;
        final int initialColour = BLACK_SQUARE;
        final BoardPattern pattern =
                new CheckeredBoardPattern(initialColour, width, height);
        // First row.
        assertEquals(BLACK_SQUARE, pattern.getColourAt(0, 0));
        assertEquals(WHITE_SQUARE, pattern.getColourAt(1, 0));
        assertEquals(BLACK_SQUARE, pattern.getColourAt(2, 0));
        // Second row.
        assertEquals(WHITE_SQUARE, pattern.getColourAt(0, 1));
        assertEquals(BLACK_SQUARE, pattern.getColourAt(1, 1));
        assertEquals(WHITE_SQUARE, pattern.getColourAt(2, 1));
        // Third row.
        assertEquals(BLACK_SQUARE, pattern.getColourAt(0, 2));
        assertEquals(WHITE_SQUARE, pattern.getColourAt(1, 2));
        assertEquals(BLACK_SQUARE, pattern.getColourAt(2, 2));
    }
    /**
     * Tests {@link CheckeredBoardPattern#getColourAt(int, int)} returns white
     * for each borderline invalid corner coordinate.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testGetColourAtReturnsWhiteForOutOfBoundCoordinates() {
        final int width = 6;
        final int height = 8;
        final int initialColour = BLACK_SQUARE;
        final BoardPattern pattern =
                new CheckeredBoardPattern(initialColour, width, height);
        // Above left
        assertEquals(WHITE_SQUARE, pattern.getColourAt(-1, -1));
        // Above right
        assertEquals(WHITE_SQUARE, pattern.getColourAt(width, -1));
        // Bottom left
        assertEquals(WHITE_SQUARE, pattern.getColourAt(-1, height));
        // Bottom right
        assertEquals(WHITE_SQUARE, pattern.getColourAt(width, height));
    }
}
