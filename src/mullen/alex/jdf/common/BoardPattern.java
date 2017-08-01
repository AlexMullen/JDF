package mullen.alex.jdf.common;

/**
 * An interface for defining a black and white pattern for a board.
 *
 * @author  Alex Mullen
 *
 */
public interface BoardPattern {
    /** Represents a white square on a board. */
    int WHITE_SQUARE = 0;
    /** Represents a black square on a board. */
    int BLACK_SQUARE = 1;
    /**
     * Gets the colour of the square at the specified coordinates.
     * <p>
     * If the coordinate specified is out of bounds for the pattern then
     * {@link #WHITE_SQUARE} is returned by default.
     *
     * @param x  the X position (left-to-right)
     * @param y  the Y position (top-to-bottom)
     * @return   Either {@link #WHITE_SQUARE} or
     *           {@link #BLACK_SQUARE}
     */
    int getColourAt(int x, int y);
}
