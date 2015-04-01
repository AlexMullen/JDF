package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.Objects;

/**
 * An abstract class that describes a move a piece could, or has done, from one
 * position to another.
 *
 * @author  Alex Mullen
 *
 */
public abstract class Move {
    /** Holds the original position before the move. */
    private final BoardPosition from;
    /** Holds the destination position for the move. */
    private final BoardPosition to;
    /**
     * Creates a new instance using the specified source and destination
     * positions.
     *
     * @param fromPosition  the source position of the move
     * @param toPosition    the destination position of the move
     *
     * @throws IllegalArgumentException  if <code>fromPosition</code> and
     *                                   <code>toPosition</code> point to the
     *                                   same position
     * @throws NullPointerException  if either <code>fromPosition</code> or
     *                               <code>toPosition</code> is
     *                               <code>null</code>
     */
    public Move(final BoardPosition fromPosition,
            final BoardPosition toPosition) {
        from = Objects.requireNonNull(fromPosition, "eessh");
        to = Objects.requireNonNull(toPosition, "to nowhere? idiots");
        if (from.equals(to)) {
            throw new IllegalArgumentException("Moving to itself?");
        }
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
}
