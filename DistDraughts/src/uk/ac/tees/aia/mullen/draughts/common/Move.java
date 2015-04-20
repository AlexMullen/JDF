package uk.ac.tees.aia.mullen.draughts.common;

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
    /** Holds the original position before the move. */
    private final BoardPosition from;
    /** Holds the destination position for the move. */
    private final BoardPosition to;
    /** Holds the list of jumps for this move if any. */
    private final List<Jump> jumps;
    /**
     * Creates a new instance using the specified source and destination
     * position.
     *
     * @param fromPosition     the source position of the move
     * @param toPosition       the destination position of the move
     *
     * @see #Move(BoardPosition, BoardPosition, List)
     */
    public Move(final BoardPosition fromPosition,
            final BoardPosition toPosition) {
        this(fromPosition, toPosition, Collections.emptyList());
    }
    /**
     * Creates a new instance using the specified source, destination
     * and jumped positions.
     *
     * @param fromPosition     the source position of the move
     * @param toPosition       the destination position of the move
     * @param jumpedPositions  the list of any jumps to be made - can be
     *                         <code>null</code> if there are no jumps
     *
     * @see #Move(BoardPosition, BoardPosition)
     */
    public Move(final BoardPosition fromPosition,
            final BoardPosition toPosition, final List<Jump> jumpedPositions) {
        from = fromPosition;
        to = toPosition;
        jumps = jumpedPositions;
    }
    /**
     * Gets the source position before the move.
     *
     * @return  the source position
     */
    public final BoardPosition getFrom() {
        return from;
    }
    /**
     * Gets the destination position after the move completes.
     *
     * @return  the destination position.
     */
    public final BoardPosition getTo() {
        return to;
    }
    /**
     * Gets the jumps for this move.
     *
     * @return  the list of jumps in sequence order or <code>null</code> if
     *          there are no jumps for this move
     */
    public final List<Jump> getJumps() {
        return jumps;
    }
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Move [getFrom()=").append(getFrom())
                .append(", getTo()=").append(getTo()).append(", getJumps()=");
        for (final Jump jump : getJumps()) {
            sb.append("\n    ").append(jump);
        }
        return sb.append("]").toString();
    }
}
