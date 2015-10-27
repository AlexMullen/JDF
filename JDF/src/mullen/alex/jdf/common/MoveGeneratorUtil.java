package mullen.alex.jdf.common;

import java.util.Collection;

/**
 * A utility class that provides helper methods for generating moves for most
 * variants of draughts.
 *
 * @author  Alex Mullen
 *
 */
public final class MoveGeneratorUtil {
    /**
     * Private constructor to prevent instantiation.
     */
    private MoveGeneratorUtil() {
        // Intentionally empty.
    }
    /**
     * Gets any potential single move to the above left space of the specified
     * board position if it is valid and vacant.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    public static Move findMoveAboveLeft(final Board board,
            final BoardPosition position) {
        Move foundMove = null;
        final int spaceX = position.getX() - 1;
        final int spaceY = position.getY() - 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            foundMove = new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return foundMove;
    }
    /**
     * Gets any potential single move to the above right space of the specified
     * board position if it is valid and vacant.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    public static Move findMoveAboveRight(
            final Board board,
            final BoardPosition position) {
        Move foundMove = null;
        final int spaceX = position.getX() + 1;
        final int spaceY = position.getY() - 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            foundMove = new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return foundMove;
    }
    /**
     * Gets any potential single move to the bottom left space of the specified
     * board position if it is valid and vacant.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    public static Move findMoveBottomLeft(
            final Board board,
            final BoardPosition position) {
        Move foundMove = null;
        final int spaceX = position.getX() - 1;
        final int spaceY = position.getY() + 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            foundMove = new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return foundMove;
    }
    /**
     * Gets any potential single move to the bottom right space of the specified
     * board position if it is valid and vacant.
     *
     * @param board     the board to use
     * @param position  the position
     * @return          the move or <code>null</code> if there is no move
     */
    public static Move findMoveBottomRight(
            final Board board,
            final BoardPosition position) {
        Move foundMove = null;
        final int spaceX = position.getX() + 1;
        final int spaceY = position.getY() + 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            foundMove = new Move(position, new BoardPosition(spaceX, spaceY));
        }
        // No move.
        return foundMove;
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
    public static Jump findJumpAboveLeft(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        Jump foundJump = null;
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
                foundJump = new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return foundJump;
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
    public static Jump findJumpAboveRight(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        Jump foundJump = null;
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
                foundJump = new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return foundJump;
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
    public static Jump findJumpBottomLeft(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        Jump foundJump = null;
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
                foundJump = new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return foundJump;
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
    public static Jump findJumpBottomRight(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner) {
        Jump foundJump = null;
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
                foundJump = new Jump(
                        fromPosition,
                        new BoardPosition(landPosX, landPosY),
                        new BoardPosition(jumpedPosX, jumpedPosY));
            }
        }
        return foundJump;
    }
    /**
     * Gets every move position that is diagonally above-left until either a
     * piece is reached or the bounds of the board are reached.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param outMoves      the collection any found moves are added to
     */
    public static void findFlyingMovesAboveLeft(final Board board,
            final BoardPosition fromPosition,
            final Collection<Move> outMoves) {
        int landPosX = fromPosition.getX();
        int landPosY = fromPosition.getY();
        while (board.isPositionWithinBounds(--landPosX, --landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition, new BoardPosition(
                    landPosX, landPosY)));
        }
    }
    /**
     * Gets every move position that is diagonally above-right until either a
     * piece is reached or the bounds of the board are reached.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param outMoves      the collection any found moves are added to
     */
    public static void findFlyingMovesAboveRight(final Board board,
            final BoardPosition fromPosition,
            final Collection<Move> outMoves) {
        int landPosX = fromPosition.getX();
        int landPosY = fromPosition.getY();
        while (board.isPositionWithinBounds(++landPosX, --landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition, new BoardPosition(
                    landPosX, landPosY)));
        }
    }
    /**
     * Gets every move position that is diagonally bottom-left until either a
     * piece is reached or the bounds of the board are reached.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param outMoves      the collection any found moves are added to
     */
    public static void findFlyingMovesBottomLeft(final Board board,
            final BoardPosition fromPosition,
            final Collection<Move> outMoves) {
        int landPosX = fromPosition.getX();
        int landPosY = fromPosition.getY();
        while (board.isPositionWithinBounds(--landPosX, ++landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition, new BoardPosition(
                    landPosX, landPosY)));
        }
    }
    /**
     * Gets every move position that is diagonally bottom-right until either a
     * piece is reached or the bounds of the board are reached.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param outMoves      the collection any found moves are added to
     */
    public static void findFlyingMovesBottomRight(final Board board,
            final BoardPosition fromPosition,
            final Collection<Move> outMoves) {
        int landPosX = fromPosition.getX();
        int landPosY = fromPosition.getY();
        while (board.isPositionWithinBounds(++landPosX, ++landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition, new BoardPosition(
                    landPosX, landPosY)));
        }
    }
    /**
     * Gets every flying jump position that is diagonally above-left.
     * <p>
     * There can be more than one space between the piece performing the jump
     * and the piece being jumped. The piece performing the jump can land on
     * vacant square beyond the piece that was jumped. Only one piece can be
     * jumped over.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jumps are added to
     */
    public static void findFlyingJumpsAboveLeft(final Board board,
            final BoardPosition fromPosition, final Player owner,
            final Collection<Jump> outJumps) {
        int squareX = fromPosition.getX();
        int squareY = fromPosition.getY();
        BoardPosition foundPiecePosition = null;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(--squareX, --squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.getOwner() == owner) {
                    return;
                } else {
                    foundPiecePosition = new BoardPosition(squareX, squareY);
                    break;
                }
            }
        }
        if (foundPiecePosition != null) {
            // Create a jump for each empty space beyond it
            while (board.isPositionWithinBounds(--squareX, --squareY)
                    && !board.isPieceAt(squareX, squareY)) {
                outJumps.add(new Jump(
                        fromPosition,
                        new BoardPosition(squareX, squareY),
                        foundPiecePosition));
            }
        }
    }
    /**
     * Gets every flying jump position that is diagonally above-right.
     * <p>
     * There can be more than one space between the piece performing the jump
     * and the piece being jumped. The piece performing the jump can land on
     * vacant square beyond the piece that was jumped. Only one piece can be
     * jumped over.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jumps are added to
     */
    public static void findFlyingJumpsAboveRight(final Board board,
            final BoardPosition fromPosition, final Player owner,
            final Collection<Jump> outJumps) {
        int squareX = fromPosition.getX();
        int squareY = fromPosition.getY();
        BoardPosition foundPiecePosition = null;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(++squareX, --squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.getOwner() == owner) {
                    return;
                } else {
                    foundPiecePosition = new BoardPosition(squareX, squareY);
                    break;
                }
            }
        }
        if (foundPiecePosition != null) {
            // Create a jump for each empty space beyond it
            while (board.isPositionWithinBounds(++squareX, --squareY)
                    && !board.isPieceAt(squareX, squareY)) {
                outJumps.add(new Jump(
                        fromPosition,
                        new BoardPosition(squareX, squareY),
                        foundPiecePosition));
            }
        }
    }
    /**
     * Gets every flying jump position that is diagonally bottom-left.
     * <p>
     * There can be more than one space between the piece performing the jump
     * and the piece being jumped. The piece performing the jump can land on
     * vacant square beyond the piece that was jumped. Only one piece can be
     * jumped over.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jumps are added to
     */
    public static void findFlyingJumpsBottomLeft(final Board board,
            final BoardPosition fromPosition, final Player owner,
            final Collection<Jump> outJumps) {
        int squareX = fromPosition.getX();
        int squareY = fromPosition.getY();
        BoardPosition foundPiecePosition = null;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(--squareX, ++squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.getOwner() == owner) {
                    return;
                } else {
                    foundPiecePosition = new BoardPosition(squareX, squareY);
                    break;
                }
            }
        }
        if (foundPiecePosition != null) {
            // Create a jump for each empty space beyond it
            while (board.isPositionWithinBounds(--squareX, ++squareY)
                    && !board.isPieceAt(squareX, squareY)) {
                outJumps.add(new Jump(
                        fromPosition,
                        new BoardPosition(squareX, squareY),
                        foundPiecePosition));
            }
        }
    }
    /**
     * Gets every flying jump position that is diagonally bottom-right.
     * <p>
     * There can be more than one space between the piece performing the jump
     * and the piece being jumped. The piece performing the jump can land on
     * vacant square beyond the piece that was jumped. Only one piece can be
     * jumped over.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jumps are added to
     */
    public static void findFlyingJumpsBottomRight(final Board board,
            final BoardPosition fromPosition, final Player owner,
            final Collection<Jump> outJumps) {
        int squareX = fromPosition.getX();
        int squareY = fromPosition.getY();
        BoardPosition foundPiecePosition = null;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(++squareX, ++squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.getOwner() == owner) {
                    return;
                } else {
                    foundPiecePosition = new BoardPosition(squareX, squareY);
                    break;
                }
            }
        }
        if (foundPiecePosition != null) {
            // Create a jump for each empty space beyond it
            while (board.isPositionWithinBounds(++squareX, ++squareY)
                    && !board.isPieceAt(squareX, squareY)) {
                outJumps.add(new Jump(
                        fromPosition,
                        new BoardPosition(squareX, squareY),
                        foundPiecePosition));
            }
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
    public static void addMoveIfNotNull(final Collection<Move> moves,
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
    public static void addJumpIfNotNull(final Collection<Jump> jumps,
            final Jump possibleJump) {
        if (possibleJump != null) {
            jumps.add(possibleJump);
        }
    }
}
