package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.Objects;

/**
 * An abstract implementation that represents a piece on a board.
 *
 * @author  Alex Mullen
 *
 */
public class Piece {
    /** Holds the owner of this piece. */
    private final Player player;
    /** Holds the movement direction this piece can do. */
    private MoveDirection moveDirection;
    /**
     * Creates a new instance that belongs to the specified owner and is set to
     * only be allowed to move in the specified direction.
     * <p>
     * A <code>direction</code> value of {@link MoveDirection#BOTH} effectively
     * crowns this piece and {@link #isCrowned()} will return <code>true</code>.
     *
     * @param owner      the <code>PieceOwner</code> the piece will belong to
     * @param direction  the direction this piece is allowed to move in
     *
     * @throws NullPointerException  if <code>owner</code> or
     *                               <code>direction</code> is <code>null</code>
     */
    public Piece(final Player owner, final MoveDirection direction) {
        player = Objects.requireNonNull(owner, "Who owns dis?");
        moveDirection = Objects.requireNonNull(direction, "Move nowhere?");
    }
    /**
     * Creates a new instance that is a copy of the specified piece.
     *
     * @param piece  the piece to copy
     * @throws NullPointerException  if <code>piece</code> is <code>null</code>
     *
     * @see #Piece(Player, MoveDirection)
     */
    public Piece(final Piece piece) {
        player = piece.player;
        moveDirection = piece.moveDirection;
    }
    /**
     * Gets the {@link Player} instance that this piece belongs to.
     *
     * @return  the owner
     */
    public final Player getOwner() {
        return player;
    }
    /**
     * Gets the direction this piece is allowed to move in.
     *
     * @return  the allowed direction
     */
    public final MoveDirection getMoveDirection() {
        return moveDirection;
    }
    /**
     * Gets whether this piece has been crowned.
     *
     * @return  <code>true</code> if this piece is crowned; <code>false</code>
     *          otherwise
     * @see     #crown()
     */
    public final boolean isCrowned() {
        return moveDirection == MoveDirection.BOTH;
    }
    /**
     * Crowns this piece.
     *
     * @see  #isCrowned()
     */
    public final void crown() {
        if (isCrowned()) {
            throw new IllegalStateException(
                    "Crowning an already crowned piece? owwway");
        }
        moveDirection = MoveDirection.BOTH;
    }
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + moveDirection.hashCode();
        result = prime * result + player.hashCode();
        return result;
    }
    /**
     * Indicates whether this piece is equal to the specified piece.
     * <p>
     * Two pieces are equal if they have the same identity or they have the same
     * owner and move direction.
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
        final Piece other = (Piece) obj;
        if (moveDirection != other.moveDirection) {
            return false;
        }
        if (!player.equals(other.player)) {
            return false;
        }
        return true;
    }
    @Override
    public final String toString() {
        return "Piece [getOwner()=" + getOwner() + ", getMoveDirection()="
                + getMoveDirection() + ", isCrowned()=" + isCrowned() + "]";
    }
    /**
     * Defines movement directions a draughts piece can move in.
     *
     * @author  Alex Mullen
     */
    public enum MoveDirection {
        /** The piece can only move upwards in 2D space. */
        UP,
        /** The piece can only move downwards in 2D space. */
        DOWN,
        /** The piece can move up and down (crowned) in 2D space. */
        BOTH
    }
}
