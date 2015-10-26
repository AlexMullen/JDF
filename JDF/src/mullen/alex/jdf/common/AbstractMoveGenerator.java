package mullen.alex.jdf.common;

import java.util.Collection;

/**
 * An abstract move generator implementation that provides many primitive helper
 * methods for generating moves so as to minimise the effort required for
 * implementors.
 *
 * @author  Alex Mullen
 *
 */
public abstract class AbstractMoveGenerator implements MoveGenerator {
    /**
     * Constructor.
     */
    protected AbstractMoveGenerator() {
        // Intentionally empty.
    }
    /**
     * Gets any potential single move to the above left space of the specified
     * board position.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    protected static Move getMoveAboveLeft(
            final Board board,
            final BoardPosition position) {
        final int spaceX = position.getX() - 1;
        final int spaceY = position.getY() - 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            return new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return null;
    }
    /**
     * Gets any potential single move to the above right space of the specified
     * board position.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    protected static Move getMoveAboveRight(
            final Board board,
            final BoardPosition position) {
        final int spaceX = position.getX() + 1;
        final int spaceY = position.getY() - 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            return new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return null;
    }
    /**
     * Gets any potential single move to the bottom left space of the specified
     * board position.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    protected static Move getMoveBottomLeft(
            final Board board,
            final BoardPosition position) {
        final int spaceX = position.getX() - 1;
        final int spaceY = position.getY() + 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            return new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return null;
    }
    /**
     * Gets any potential single move to the bottom right space of the specified
     * board position.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    protected static Move getMoveBottomRight(
            final Board board,
            final BoardPosition position) {
        final int spaceX = position.getX() + 1;
        final int spaceY = position.getY() + 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            return new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return null;
    }
    /**
     * Gets any potential single jump move over any enemy piece to the above
     * left of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @return              the jump move or <code>null</code> if there is no
     *                      jump
     */
    protected static Jump getJumpAboveLeft(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        final int landPosX = fromPosition.getX() - 2;
        final int landPosY = fromPosition.getY() - 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.getX() - 1;
            final int jumpedPosY = fromPosition.getY() - 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null
                    && owner != pieceToJumpOver.getOwner()) {
                // This is a jump move.
                return new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return null;
    }
    /**
     * Gets any potential single jump move over any enemy piece to the above
     * right of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @return              the jump move or <code>null</code> if there is no
     *                      jump
     */
    protected static Jump getJumpAboveRight(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        final int landPosX = fromPosition.getX() + 2;
        final int landPosY = fromPosition.getY() - 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.getX() + 1;
            final int jumpedPosY = fromPosition.getY() - 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null
                    && owner != pieceToJumpOver.getOwner()) {
                // This is a jump move.
                return new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return null;
    }
    /**
     * Gets any potential single jump move over any enemy piece to the bottom
     * left of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @return              the jump move or <code>null</code> if there is no
     *                      jump
     */
    protected static Jump getJumpBottomLeft(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        final int landPosX = fromPosition.getX() - 2;
        final int landPosY = fromPosition.getY() + 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.getX() - 1;
            final int jumpedPosY = fromPosition.getY() + 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null
                    && owner != pieceToJumpOver.getOwner()) {
                // This is a jump move.
                return new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return null;
    }
    /**
     * Gets any potential single jump move over any enemy piece to the bottom
     * right of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @return              the jump move or <code>null</code> if there is no
     *                      jump
     */
    protected static Jump getJumpBottomRight(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        final int landPosX = fromPosition.getX() + 2;
        final int landPosY = fromPosition.getY() + 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.getX() + 1;
            final int jumpedPosY = fromPosition.getY() + 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null
                    && owner != pieceToJumpOver.getOwner()) {
                // This is a jump move.
                return new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return null;
    }
    
    
    protected static void getKingMovesAboveLeft(final Board board,
            final BoardPosition fromPosition,
            final Collection<Move> outMoves) {
        int currentLandPosX = fromPosition.getX();
        int currentLandPosY = fromPosition.getY();

        while (board.isPositionWithinBounds(
                --currentLandPosX, --currentLandPosY)) {
            outMoves.add(new Move(fromPosition,
                    new BoardPosition(currentLandPosX, currentLandPosY)));
        }
    }
    
    /**
     * A static utility method for adding a move to a list if the move is not
     * <code>null</code>. This allows much of the <code>null</code> checking
     * boilerplate to be removed when getting the moves for a piece.
     *
     * @param moves         the collection to add the move to if is is not
     *                      <code>null</code>
     * @param possibleMove  the move to potentially add to <code>moves</code>
     */
    protected static void addMoveIfNotNull(final Collection<Move> moves,
            final Move possibleMove) {
        if (possibleMove != null) {
            moves.add(possibleMove);
        }
    }
    /**
     * A static utility method for adding a jump to a list if the jump is not
     * <code>null</code>. This allows much of the <code>null</code> checking
     * boilerplate to be removed when getting the jumps for a piece.
     *
     * @param jumps         the collection to add the jump to if is is not
     *                      <code>null</code>
     * @param possibleJump  the jump to potentially add to <code>jumps</code>
     */
    protected static void addJumpIfNotNull(final Collection<Jump> jumps,
            final Jump possibleJump) {
        if (possibleJump != null) {
            jumps.add(possibleJump);
        }
    }
}
