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
     * @param outMoves  the collection any found move will be added to
     */
    public static void findMoveAboveLeft(final Board board,
            final BoardPosition position, final Collection<Move> outMoves) {
        final int spaceX = position.x - 1;
        final int spaceY = position.y - 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            outMoves.add(new Move(position,
                    board.positions[spaceY + (board.height * spaceX)]));
        }
    }
    /**
     * Gets any potential single move to the above right space of the specified
     * board position if it is valid and vacant.
     *
     * @param board     the board to use
     * @param position  the position
     * @param outMoves  the collection any found move will be added to
     */
    public static void findMoveAboveRight(
            final Board board,
            final BoardPosition position, final Collection<Move> outMoves) {
        final int spaceX = position.x + 1;
        final int spaceY = position.y - 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            outMoves.add(new Move(position,
                    board.positions[spaceY + (board.height * spaceX)]));
        }
    }
    /**
     * Gets any potential single move to the bottom left space of the specified
     * board position if it is valid and vacant.
     *
     * @param board     the board to use
     * @param position  the position
     * @param outMoves  the collection any found move will be added to
     */
    public static void findMoveBottomLeft(
            final Board board,
            final BoardPosition position, final Collection<Move> outMoves) {
        final int spaceX = position.x - 1;
        final int spaceY = position.y + 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            outMoves.add(new Move(position,
                    board.positions[spaceY + (board.height * spaceX)]));
        }
    }
    /**
     * Gets any potential single move to the bottom right space of the specified
     * board position if it is valid and vacant.
     *
     * @param board     the board to use
     * @param position  the position
     * @param outMoves  the collection any found move will be added to
     */
    public static void findMoveBottomRight(
            final Board board,
            final BoardPosition position, final Collection<Move> outMoves) {
        final int spaceX = position.x + 1;
        final int spaceY = position.y + 1;
        // First check if the top-left position exists and is vacant.
        if (board.isPositionWithinBounds(spaceX, spaceY)
                && !board.isPieceAt(spaceX, spaceY)) {
            outMoves.add(new Move(position,
                    board.positions[spaceY + (board.height * spaceX)]));
        }
    }
    /**
     * Gets any potential single jump move over any enemy piece to the above
     * left of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jump will be added to
     */
    public static void findJumpAboveLeft(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner, final Collection<Jump> outJumps) {
        final int landPosX = fromPosition.x - 2;
        final int landPosY = fromPosition.y - 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.x - 1;
            final int jumpedPosY = fromPosition.y - 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null && owner != pieceToJumpOver.owner) {
                // This is a jump move.
                outJumps.add(new Jump(
                        fromPosition,
                        board.getBoardPositionFor(landPosX, landPosY),
                        board.getBoardPositionFor(jumpedPosX, jumpedPosY)));
            }
        }
    }
    /**
     * Gets any potential single jump move over any enemy piece to the above
     * right of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jump will be added to
     */
    public static void findJumpAboveRight(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner, final Collection<Jump> outJumps) {
        final int landPosX = fromPosition.x + 2;
        final int landPosY = fromPosition.y - 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.x + 1;
            final int jumpedPosY = fromPosition.y - 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null && owner != pieceToJumpOver.owner) {
                // This is a jump move.
                outJumps.add(new Jump(
                        fromPosition,
                        board.getBoardPositionFor(landPosX, landPosY),
                        board.getBoardPositionFor(jumpedPosX, jumpedPosY)));
            }
        }
    }
    /**
     * Gets any potential single jump move over any enemy piece to the bottom
     * left of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jump will be added to
     */
    public static void findJumpBottomLeft(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner, final Collection<Jump> outJumps) {
        final int landPosX = fromPosition.x - 2;
        final int landPosY = fromPosition.y + 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.x - 1;
            final int jumpedPosY = fromPosition.y + 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null
                    && owner != pieceToJumpOver.owner) {
                // This is a jump move.
                outJumps.add(new Jump(
                        fromPosition,
                        board.getBoardPositionFor(landPosX, landPosY),
                        board.getBoardPositionFor(jumpedPosX, jumpedPosY)));
            }
        }
    }
    /**
     * Gets any potential single jump move over any enemy piece to the bottom
     * right of the specified board position.
     *
     * @param board         the board to use
     * @param fromPosition  the position
     * @param owner         the owner of the piece any found jump is for
     * @param outJumps      the collection any found jump will be added to
     */
    public static void findJumpBottomRight(
            final Board board,
            final BoardPosition fromPosition,
            final Player owner, final Collection<Jump> outJumps) {
        final int landPosX = fromPosition.x + 2;
        final int landPosY = fromPosition.y + 2;
        /*
         * Make sure the landing position exists and is vacant. If this exists
         * then the jumped position must also exist.
         */
        if (board.isPositionWithinBounds(landPosX, landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            final int jumpedPosX = fromPosition.x + 1;
            final int jumpedPosY = fromPosition.y + 1;
            final Piece pieceToJumpOver =
                    board.getPieceAt(jumpedPosX, jumpedPosY);
            // Check if the piece that can be jumped (if any) is an enemy piece.
            if (pieceToJumpOver != null && owner != pieceToJumpOver.owner) {
                // This is a jump move.
                outJumps.add(new Jump(
                        fromPosition,
                        board.getBoardPositionFor(landPosX, landPosY),
                        board.getBoardPositionFor(jumpedPosX, jumpedPosY)));
            }
        }
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
        int landPosX = fromPosition.x;
        int landPosY = fromPosition.y;
        final BoardPosition[] positions = board.positions;
        while (board.isPositionWithinBounds(--landPosX, --landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition,
                    positions[landPosY + (board.height * landPosX)]));
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
        int landPosX = fromPosition.x;
        int landPosY = fromPosition.y;
        final BoardPosition[] positions = board.positions;
        while (board.isPositionWithinBounds(++landPosX, --landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition,
                    positions[landPosY + (board.height * landPosX)]));
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
        int landPosX = fromPosition.x;
        int landPosY = fromPosition.y;
        final BoardPosition[] positions = board.positions;
        while (board.isPositionWithinBounds(--landPosX, ++landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition,
                    positions[landPosY + (board.height * landPosX)]));
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
        int landPosX = fromPosition.x;
        int landPosY = fromPosition.y;
        final BoardPosition[] positions = board.positions;
        while (board.isPositionWithinBounds(++landPosX, ++landPosY)
                && !board.isPieceAt(landPosX, landPosY)) {
            outMoves.add(new Move(fromPosition,
                    positions[landPosY + (board.height * landPosX)]));
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
        int squareX = fromPosition.x;
        int squareY = fromPosition.y;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(--squareX, --squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.owner != owner) {
                    final BoardPosition foundPiecePosition =
                            board.getBoardPositionFor(squareX, squareY);
                    // Create a jump for each empty space beyond it
                    while (board.isPositionWithinBounds(--squareX, --squareY)
                            && !board.isPieceAt(squareX, squareY)) {
                        outJumps.add(new Jump(
                                fromPosition,
                                board.getBoardPositionFor(squareX, squareY),
                                foundPiecePosition));
                    }
                }
                return;
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
        int squareX = fromPosition.x;
        int squareY = fromPosition.y;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(++squareX, --squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.owner != owner) {
                    final BoardPosition foundPiecePosition =
                            board.getBoardPositionFor(squareX, squareY);
                    // Create a jump for each empty space beyond it
                    while (board.isPositionWithinBounds(++squareX, --squareY)
                            && !board.isPieceAt(squareX, squareY)) {
                        outJumps.add(new Jump(
                                fromPosition,
                                board.getBoardPositionFor(squareX, squareY),
                                foundPiecePosition));
                    }
                }
                return;
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
        int squareX = fromPosition.x;
        int squareY = fromPosition.y;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(--squareX, ++squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.owner != owner) {
                    final BoardPosition foundPiecePosition =
                            board.getBoardPositionFor(squareX, squareY);
                    // Create a jump for each empty space beyond it
                    while (board.isPositionWithinBounds(--squareX, ++squareY)
                            && !board.isPieceAt(squareX, squareY)) {
                        outJumps.add(new Jump(
                                fromPosition,
                                board.getBoardPositionFor(squareX, squareY),
                                foundPiecePosition));
                    }
                }
                return;
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
        int squareX = fromPosition.x;
        int squareY = fromPosition.y;
        // Loop until a piece is found or the edge of board is reached.
        while (board.isPositionWithinBounds(++squareX, ++squareY)) {
            final Piece foundPiece = board.getPieceAt(squareX, squareY);
            if (foundPiece != null) {
                if (foundPiece.owner != owner) {
                    final BoardPosition foundPiecePosition =
                            board.getBoardPositionFor(squareX, squareY);
                    // Create a jump for each empty space beyond it
                    while (board.isPositionWithinBounds(++squareX, ++squareY)
                            && !board.isPieceAt(squareX, squareY)) {
                        outJumps.add(new Jump(
                                fromPosition,
                                board.getBoardPositionFor(squareX, squareY),
                                foundPiecePosition));
                    }
                }
                return;
            }
        }
    }
}
