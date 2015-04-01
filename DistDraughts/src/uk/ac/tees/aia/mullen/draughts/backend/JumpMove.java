package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.Objects;

/**
 * A class that represents a single jump move where one piece is jumped over.
 *
 * @author  Alex Mullen
 *
 */
public class JumpMove extends Move {
    /** Holds the position that was jumped over. */
    private final BoardPosition jumped;
    /**
     * Creates a new instance using the specified source, destination
     * and jumped position.
     *
     * @param fromPosition    the source position of the move
     * @param toPosition      the destination position of the move
     * @param jumpedPosition  the position that was jumped over
     *
     * @throws IllegalArgumentException  if any of <code>fromPosition</code>,
     *                                   <code>toPosition</code> or
     *                                   <code>jumpedPosition</code> point to
     *                                   the same position
     * @throws NullPointerException  if either <code>fromPosition</code>,
     *                               <code>toPosition</code> or
     *                               <code>jumpedPosition</code> is
     *                               <code>null</code>
     */
    public JumpMove(
            final BoardPosition fromPosition,
            final BoardPosition toPosition,
            final BoardPosition jumpedPosition) {
        super(fromPosition, toPosition);
        jumped = Objects.requireNonNull(jumpedPosition);
        if (jumped.equals(fromPosition) || jumped.equals(toPosition)) {
            throw new IllegalArgumentException(
                    "Jumping over its start or end position?");
        }
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
        return "JumpMove [getFrom()=" + getFrom() + ", getTo()=" + getTo()
                + ", getJumped()=" + getJumped() + "]";
    }

}
