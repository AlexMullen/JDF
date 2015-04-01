package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.List;
import java.util.Objects;

/**
 * A class that represents a multiple jump move sequence where more than one
 * jump is made.
 *
 * @author  Alex Mullen
 *
 */
public class JumpMoveSequence extends Move {
    /** Holds the list of jumps that make up this multiple jump move. */
    private final List<JumpMove> jumps;
    /**
     * Creates a new instance using the specified source, destination and jumped
     * positions.
     *
     * @param fromPosition   the source position of the move
     * @param toPosition     the destination position of the move
     * @param jumpPositions  the list of jumps to be made
     *
     * @throws IllegalArgumentException  if <code>jumpPositions</code> is
     *                                   empty
     * @throws NullPointerException  if either <code>fromPosition</code>,
     *                               <code>toPosition</code> or
     *                               <code>jumpPositions</code> is
     *                               <code>null</code>
     */
    public JumpMoveSequence(
            final BoardPosition fromPosition,
            final BoardPosition toPosition,
            final List<JumpMove> jumpPositions) {
        super(fromPosition, toPosition);
        jumps = Objects.requireNonNull(jumpPositions);
        if (jumps.isEmpty()) {
            throw new IllegalArgumentException("An empty jump sequence?");
        }
    }
    /**
     * Gets the jumps that make up this move sequence.
     *
     * @return  the list of jumps - in order
     */
    public final List<JumpMove> getJumps() {
        return jumps;
    }
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("JumpMoveSequence [getFrom()=").append(getFrom())
                .append(", getTo()=").append(getTo()).append(", getJumps()=");
        for (final JumpMove jump : getJumps()) {
            sb.append("\n    ").append(jump);
        }
        return sb.append("]").toString();
    }
}
