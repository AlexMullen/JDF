package mullen.alex.jdf.common;

/**
 * A class that represents and holds values that describe a position on a
 * {@link Board}.
 *
 * @author  Alex Mullen
 *
 */
public class BoardPosition {
    /** Holds the X position (left-to-right). */
    public final int x;
    /** Holds the Y position (top-to-bottom). */
    public final int y;
    /**
     * Creates a new instance that represents the specified position.
     *
     * @param posX  the X position (left-to-right)
     * @param posY  the Y position (top-to-bottom)
     *
     */
    public BoardPosition(final int posX, final int posY) {
        x = posX;
        y = posY;
    }
    /**
     * Indicates whether one board position is equivalent to another.
     * <p>
     * Two positions are considered equivalent if:
     * <p>
     * <code>this.</code>{@link #x} == <code>obj.</code>{@link #x}
     * && <code>this.</code>{@link #y} == <code>obj.</code>{@link #y}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final BoardPosition other = (BoardPosition) obj;
        return x == other.x && y == other.y;
    }
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }
    @Override
    public final String toString() {
        return "BoardPosition [x=" + x + ", y=" + y + "]";
    }
}
