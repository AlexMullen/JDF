package mullen.alex.draughts.common;

/**
 * A class that represents a single jump move where one piece is jumped over.
 *
 * @author  Alex Mullen
 *
 */
public class Jump {
    /** Holds the original position before the jump. */
    private final BoardPosition from;
    /** Holds the destination position for the jump. */
    private final BoardPosition to;
    /** Holds the position that was jumped over. */
    private final BoardPosition jumped;
    /**
     * Creates a new instance using the specified source, destination
     * and jumped position.
     *
     * @param fromPosition    the source position of the jump
     * @param toPosition      the destination position of the jump
     * @param jumpedPosition  the position that was jumped over
     *
     */
    public Jump(
            final BoardPosition fromPosition,
            final BoardPosition toPosition,
            final BoardPosition jumpedPosition) {
        from = fromPosition;
        to = toPosition;
        jumped = jumpedPosition;
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
     * Gets the position that was jumped.
     *
     * @return  the jumped position
     */
    public final BoardPosition getJumped() {
        return jumped;
    }
    @Override
    public final String toString() {
        return "Jump [getFrom()=" + getFrom() + ", getTo()=" + getTo()
                + ", getJumped()=" + getJumped() + "]";
    }
}
