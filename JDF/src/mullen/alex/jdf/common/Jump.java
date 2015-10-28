package mullen.alex.jdf.common;

/**
 * A class that represents a single jump move where one piece is jumped over.
 *
 * @author  Alex Mullen
 *
 */
public class Jump {
    /** Holds the original position before the jump. */
    public final BoardPosition from;
    /** Holds the destination position for the jump. */
    public final BoardPosition to;
    /** Holds the position that was jumped over. */
    public final BoardPosition jumped;
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
    @Override
    public final String toString() {
        return "Jump [from=" + from + ", to=" + to + ", jumped=" + jumped + "]";
    }
}
