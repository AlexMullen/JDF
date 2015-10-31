package mullen.alex.jdf.common;

import java.util.Collections;
import java.util.List;

/**
 * A class that describes a move a piece could, or has done, from one
 * position to another.
 *
 * @author  Alex Mullen
 *
 */
public class Move {
    /** The original position before the move. */
    public final BoardPosition from;
    /** The destination position for the move. */
    public final BoardPosition to;
    /** The jumps for this move if any. */
    public final List<Jump> jumps;
    /**
     * Creates a new instance using the specified source and destination
     * position.
     * <p>
     * An empty list is assigned to {@link #jumps}.
     *
     * @param fromPosition     the source position of the move
     * @param toPosition       the destination position of the move
     *
     * @see #Move(BoardPosition, BoardPosition, List)
     */
    public Move(final BoardPosition fromPosition,
            final BoardPosition toPosition) {
        from = fromPosition;
        to = toPosition;
        jumps = Collections.emptyList();
    }
    /**
     * Creates a new instance using the specified source, destination
     * and jumped positions.
     *
     * @param fromPosition     the source position of the move
     * @param toPosition       the destination position of the move
     * @param jumpedPositions  the list of any jumps to be made
     *
     * @see #Move(BoardPosition, BoardPosition)
     */
    public Move(final BoardPosition fromPosition,
            final BoardPosition toPosition, final List<Jump> jumpedPositions) {
        from = fromPosition;
        to = toPosition;
        jumps = jumpedPositions;
//        // If the move has no jumps then assign an empty list.
//        jumps = (jumpedPositions == null ?
//                Collections.<Jump>emptyList() : jumpedPositions);
    }
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder(256);
        sb.append("Move [from=").append(from)
                .append(", to=").append(to).append(", jumps=");
        for (final Jump jump : jumps) {
            sb.append("\n    ").append(jump);
        }
        return sb.append("]").toString();
    }
}
