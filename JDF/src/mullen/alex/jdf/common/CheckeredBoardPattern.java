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
        if (firstColour != BoardPattern.WHITE_SQUARE
                && firstColour != BoardPattern.BLACK_SQUARE) {
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
        if (x < 0 || y < 0 || x >= width || y >= height) {
            // The coordinates are out of range so return white by default.
            return BoardPattern.WHITE_SQUARE;
        } else if (initialColour == BoardPattern.WHITE_SQUARE) {
            if ((x + y) % 2 == 0) {
                return BoardPattern.WHITE_SQUARE;
            } else {
                return BoardPattern.BLACK_SQUARE;
            }
        } else if (initialColour == BoardPattern.BLACK_SQUARE) {
            if ((x + y) % 2 == 0) {
                return BoardPattern.BLACK_SQUARE;
            } else {
                return BoardPattern.WHITE_SQUARE;
            }
        } else {
            // Should not happen.
            throw new IllegalStateException("Unknown board colour!");
        }
    }
}
