package mullen.alex.jdf.common;

/**
 * Represents a typical checkered pattern.
 *
 * @author  Alex Mullen
 *
 */
public class CheckeredBoardPattern implements BoardPattern {
    /** Holds the width the pattern extends to. */
    private final int width;
    /** Holds the height the pattern extends to. */
    private final int height;
    /** Holds the colour of the initial square (top-left). */
    private final int initialColour;
    /**
     * Creates a new instance that starts with the specified colour and is of
     * the specified dimensions.
     * <p>
     * The first square is deemed as coordinates (0,0) on a {@link Board}. This
     * is often displayed on the top-left.
     *
     * @param firstColour    either {@link BoardPattern#WHITE_SQUARE} or
     *                       {@link BoardPattern#BLACK_SQUARE}
     * @param patternWidth   the width of the pattern
     * @param patternHeight  the height of the pattern
     */
    public CheckeredBoardPattern(final int firstColour, final int patternWidth,
            final int patternHeight) {
        // Initial colour validation.
        if (firstColour != WHITE_SQUARE && firstColour != BLACK_SQUARE) {
            throw new IllegalArgumentException("firstColour needs to be "
                    + "BoardPattern.WHITE_SQUARE or BoardPattern.WHITE_SQUARE");
        }
        // Width and height validation.
        if (patternWidth < 1 || patternHeight < 1) {
            throw new IllegalArgumentException(
                    "pattern dimensions need to > 0");
        }
        initialColour = firstColour;
        width = patternWidth;
        height = patternHeight;
    }
    @Override
    public final int getColourAt(final int x, final int y) {
        int squareColour = WHITE_SQUARE;
        if (!areCoordinatesOutOfBounds(x, y)) {
            if (initialColour == WHITE_SQUARE) {
                squareColour = ((x + y) % 2 == 0) ? WHITE_SQUARE : BLACK_SQUARE;
            } else if (initialColour == BLACK_SQUARE) {
                squareColour = ((x + y) % 2 == 0) ? BLACK_SQUARE : WHITE_SQUARE;
            } else {
                // Should not happen.
                throw new IllegalStateException("Unknown board colour!");
            }
        }
        return squareColour;
    }
    /**
     * Determines whether some coordinates are out of the pattern bounds.
     *
     * @param x  the X position (left-to-right)
     * @param y  the Y position (top-to-bottom)
     * @return   <code>true</code> if out of bounds; <code>false</code> if
     *           within bounds
     */
    private boolean areCoordinatesOutOfBounds(final int x, final int y) {
        return x < 0 || y < 0 || x >= width || y >= height;
    }
}
