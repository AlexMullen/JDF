package mullen.alex.jdf.variant.international;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.BoardPosition;
import mullen.alex.jdf.common.Jump;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MoveGenerator;
import mullen.alex.jdf.common.Piece;
import mullen.alex.jdf.common.Player;
import mullen.alex.jdf.common.Piece.MoveDirection;
import static mullen.alex.jdf.common.MoveGeneratorUtil.*;

/**
 * A move generator implementation that takes into account the rules of
 * International draughts.
 *
 * @author  Alex Mullen
 *
 */
public class InternationalDraughtsMoveGenerator implements MoveGenerator {
    @Override
    public final List<Move> findMoves(final Board board, final Player player) {
        final List<Jump> foundJumps = new ArrayList<>();
        final List<Move> foundSimpleMoves = new ArrayList<>(40);
        /*
         * Go through every square and get the moves of any pieces that belong
         * to the specified player.
         */
        final int boardWidth = board.width;
        final int boardHeight = board.height;
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                final Piece foundPiece = board.getPieceAt(x, y);
                if (foundPiece != null
                        && foundPiece.owner == player) {
                    final BoardPosition piecePosition =
                            board.getBoardPositionFor(x, y);
                    findJumpsForPiece(
                            board, foundPiece, piecePosition, foundJumps);
                    if (foundJumps.isEmpty()) {
                        /*
                         * Only bother for simple moves if jumps were not found
                         * as they cannot be taken if there are jumps available
                         * in International Draughts rules.
                         */
                         findSimpleMovesForPiece(board, foundPiece,
                                 piecePosition, foundSimpleMoves);
                    }
                }
            }
        }
        /*
         * Get and return jump sequences if there are any jumps otherwise return
         * any simple moves found.
         */
        return foundJumps.isEmpty() ? foundSimpleMoves : findJumpSequences(
                board, foundJumps);
    }
    @Override
    public final boolean hasAnyMoves(final Board board, final Player player) {
        final List<Jump> jumps = new ArrayList<>(4);
        final List<Move> moves = new ArrayList<>(4);
        final int boardWidth = board.width;
        final int boardHeight = board.height;
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                final Piece foundPiece = board.getPieceAt(x, y);
                if (foundPiece != null
                        && foundPiece.owner == player) {
                    final BoardPosition piecePosition =
                            board.getBoardPositionFor(x, y);
                    /*
                     * Check for any simple moves. Statistically, there will
                     * usually be more simple moves than jumps so this will most
                     * likely return first - thus checked first.
                     */
                    findSimpleMovesForPiece(
                            board, foundPiece, piecePosition, moves);
                    if (!moves.isEmpty()) {
                        return true;
                    }
                    // Check for any jumps.
                    findJumpsForPiece(board, foundPiece, piecePosition, jumps);
                    if (!jumps.isEmpty()) {
                        return true;
                    }
                }
            }
        }
        // No moves found if execution gets to here.
        return false;
    }
    /**
     * Gets all available jumps for a piece on a board.
     *
     * @param board          the board
     * @param piece          the piece
     * @param piecePosition  the position of the piece
     * @param jumps          the collection to store found jumps into
     */
    private static void findJumpsForPiece(final Board board, final Piece piece,
            final BoardPosition piecePosition, final Collection<Jump> jumps) {
        final Player owner = piece.owner;
        if (piece.isCrowned()) {
            /*
             * The piece is crowned so can 'fly' along a diagonal.
             */
            findFlyingJumpsAboveLeft(board, piecePosition, owner, jumps);
            findFlyingJumpsAboveRight(board, piecePosition, owner, jumps);
            findFlyingJumpsBottomLeft(board, piecePosition, owner, jumps);
            findFlyingJumpsBottomRight(board, piecePosition, owner, jumps);
        } else {
            /*
             * Get jumps for all directions as men can jump in any direction in
             * International Draughts.
             */
            findJumpAboveLeft(board, piecePosition, owner, jumps);
            findJumpAboveRight(board, piecePosition, owner, jumps);
            findJumpBottomLeft(board, piecePosition, owner, jumps);
            findJumpBottomRight(board, piecePosition, owner, jumps);
        }
    }
    /**
     * Gets all available simple moves for a piece.
     *
     * @param board          the board
     * @param piece          the piece
     * @param piecePosition  the position of the piece
     * @param moves          the collection to store found jumps into
     */
    private static void findSimpleMovesForPiece(final Board board,
            final Piece piece, final BoardPosition piecePosition,
            final Collection<Move> moves) {
        if (piece.isCrowned()) {
            /*
             * The piece is crowned so can 'fly' along a diagonal.
             */
            findFlyingMovesAboveLeft(board, piecePosition, moves);
            findFlyingMovesAboveRight(board, piecePosition, moves);
            findFlyingMovesBottomLeft(board, piecePosition, moves);
            findFlyingMovesBottomRight(board, piecePosition, moves);
        } else {
            // The piece is not crowned so the direction needs to be determined.
            if (piece.getMoveDirection() == MoveDirection.UP) {
                // Only get upward moves.
                findMoveAboveLeft(board, piecePosition, moves);
                findMoveAboveRight(board, piecePosition, moves);
            } else if (piece.getMoveDirection() == MoveDirection.DOWN) {
                // Only get downward moves.
                findMoveBottomLeft(board, piecePosition, moves);
                findMoveBottomRight(board, piecePosition, moves);
            } else {
                throw new IllegalStateException("Unhandled move direction.");
            }
        }
    }
    /**
     * Explores and returns all possible jump sequences for the specified jumps.
     *
     * @param board  the board to use
     * @param jumps  the jumps
     * @return       a collection of {@link Move} instances for the given jumps
     */
    private static List<Move> findJumpSequences(final Board board,
            final List<Jump> jumps) {
        final List<Move> jumpSequences = new ArrayList<>();
        final int jumpsSize = jumps.size();
        for (int i = 0; i < jumpsSize; i++) {
            final Jump singleJump = jumps.get(i);
            exploreJump(board,
                    board.getPieceAt(singleJump.from),
                    singleJump,
                    new ArrayList<Jump>(),
                    jumpSequences);
        }
        return jumpSequences;
    }
    /**
     * Recursively performs a depth-first-search of a a jump to get all possible
     * jump sequences it can be expanded to.
     *
     * @param board      the board
     * @param piece      the piece context that would be performing the jumps
     * @param jump       the initial jump
     * @param path       the current jump sequence path
     * @param sequences  the collection of found sequences
     */
    private static void exploreJump(final Board board, final Piece piece,
            final Jump jump, final List<Jump> path,
            final List<Move> sequences) {
        path.add(jump);
        final List<Jump> furtherJumps = new ArrayList<>();
        findJumpsForPiece(board, piece, jump.to, furtherJumps);
        /*
         * Need to remove jumps that have already been done. This will happen
         * with pieces as they can jump backwards.
         */
        removeAlreadyJumpedPositionMoves(furtherJumps, path);
        if (furtherJumps.isEmpty()) {
            sequences.add(new Move(path.get(0).from, jump.to, path));
//          possibly optimization to avoid memory allocation
        } else if (furtherJumps.size() == 1) {
            exploreJump(board, piece, furtherJumps.get(0), path, sequences);
        } else {
            final int furtherJumpsSize = furtherJumps.size();
            for (int i = 0; i < furtherJumpsSize; i++) {
                final Jump furtherJump = furtherJumps.get(i);
                exploreJump(board, piece, furtherJump, new ArrayList<>(path),
                        sequences);
            }
        }
    }
    /**
     * A helper method for pruning jumps that are not possible because a jump
     * has already been performed over the position.
     *
     * @param jumps          the found jumps that could be pruned
     * @param previousJumps  previous jumps
     */
    private static void removeAlreadyJumpedPositionMoves(
            final List<Jump> jumps, final List<Jump> previousJumps) {
        final Iterator<Jump> jumpsIterator = jumps.iterator();
        while (jumpsIterator.hasNext()) {
            final BoardPosition jumpedPosition =
                    jumpsIterator.next().jumped;
            // Check jumps already found.
            final int previousJumpsSize = previousJumps.size();
            for (int i = 0; i < previousJumpsSize; i++) {
                if (jumpedPosition.equals(previousJumps.get(i).jumped)) {
                    jumpsIterator.remove();
                    // No point in searching the remaining if any.
                    break;
                }
            }
        }
    }
}
