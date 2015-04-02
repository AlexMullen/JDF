package uk.ac.tees.aia.mullen.draughts.backend;


/**
 * A class that represents a simple non-jump draughts move.
 *
 * @author  Alex Mullen
 *
 */
public class SimpleMove extends Move {
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
    public SimpleMove(final BoardPosition fromPosition,
            final BoardPosition toPosition) {
        super(fromPosition, toPosition);
    }
    @Override
    public final String toString() {
        return "SimpleMove [getFrom()=" + getFrom() + ", getTo()=" + getTo()
                + "]";
    }
}
