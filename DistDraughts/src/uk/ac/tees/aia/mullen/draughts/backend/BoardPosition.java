package uk.ac.tees.aia.mullen.draughts.backend;

/**
 * A class that represents and holds values that describe a position on a
 * {@link Board}.
 *
 * @author  Alex Mullen
 *
 */
public class BoardPosition {
    /** Holds the X position (left-to-right). */
    private final int x;
    /** Holds the Y position (top-to-bottom). */
    private final int y;
    /**
     * Creates a new instance that represents the specified position.
     *
     * @param posX  the X position (left-to-right)
     * @param posY  the Y position (top-to-bottom)
     *
     * @throws IllegalArgumentException  if either <code>posX</code> or
     *                                   <code>posY</code> are negative
     */
    public BoardPosition(final int posX, final int posY) {
        if (posX < 0 || posY < 0) {
            throw new IllegalArgumentException("A negative position? owwway");
        }
        x = posX;
        y = posY;
    }
    /**
     * Gets the X value of this position.
     * <p>
     * The X values go from left-to-right.
     *
     * @return  the X position
     */
    public final int getX() {
        return x;
    }
    /**
     * Gets the Y value of this position.
     * <p>
     * The Y values go from top-to-bottom.
     *
     * @return  the Y position
     */
    public final int getY() {
        return y;
    }
    /**
     * Indicates whether one board position is equivalent to another.
     * <p>
     * Two positions are considered equivalent if:
     * <p>
     * <code>this.</code>{@link #getX()} == <code>obj.</code>{@link #getX()}
     * && <code>this.</code>{@link #getY()} == <code>obj.</code>{@link #getY()}
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoardPosition other = (BoardPosition) obj;
        if (x != other.x || y != other.y) {
            return false;
        }
        return true;
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
