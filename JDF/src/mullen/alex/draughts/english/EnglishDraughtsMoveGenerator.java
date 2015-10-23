package mullen.alex.draughts.english;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import mullen.alex.draughts.common.AbstractMoveGenerator;
import mullen.alex.draughts.common.Board;
import mullen.alex.draughts.common.BoardPosition;
import mullen.alex.draughts.common.Jump;
import mullen.alex.draughts.common.Move;
import mullen.alex.draughts.common.Piece;
import mullen.alex.draughts.common.Player;
import mullen.alex.draughts.common.Piece.MoveDirection;

/**
 * A move generator implementation that takes into account the rules of
 * English draughts.
 *
 * @author  Alex Mullen
 *
 */
public class EnglishDraughtsMoveGenerator extends AbstractMoveGenerator {
    @Override
    public final List<Move> findMoves(final Board board,
            final Player player) {
        final Collection<Jump> foundJumps = new ArrayList<>();
        final List<Move> foundSimpleMoves = new ArrayList<>();
        /*
         * Go through every square and get the moves of any pieces that belong
         * to the specified player.
         */
        for (int x = 0; x < board.getWidth(); x++) {
            // Check every 2 squares as pointless checking unused ones.
//           for (int y = (x % 2 == 0 ? 1 : 0); y < board.getHeight(); y += 2) {
            for (int y = 0; y < board.getHeight(); y++) {
                final Piece foundPiece = board.getPieceAt(x, y);
                if (foundPiece != null
                        && foundPiece.getOwner() == player) {
                    final BoardPosition piecePosition = new BoardPosition(x, y);
                    getJumpsForPiece(
                            board, foundPiece, piecePosition, foundJumps);
                    if (foundJumps.isEmpty()) {
                        /*
                         * Only bother for simple moves if jumps were not found
                         * as they cannot be taken if there are jumps available
                         * in English Draughts rules.
                         */
                         getSimpleMovesForPiece(board, foundPiece,
                                 piecePosition, foundSimpleMoves);
                    }
                }
            }
        }
        if (foundJumps.isEmpty()) {
            return foundSimpleMoves;
        } else {
            // Explore the found jumps further.
            return getJumpSequences(board, foundJumps);
        }
    }
    @Override
    public final boolean hasAnyMoves(final Board board, final Player player) {
        final List<Jump> jumps = new ArrayList<>(4);
        final List<Move> moves = new ArrayList<>(4);
        for (int x = 0; x < board.getWidth(); x++) {
            // Check every 2 squares as pointless checking unused ones.
//            for (int y = (x % 2 == 0 ? 1 : 0); y < board.getHeight(); y+=2) {
            for (int y = 0; y < board.getHeight(); y++) {
                final Piece foundPiece = board.getPieceAt(x, y);
                if (foundPiece != null
                        && foundPiece.getOwner().equals(player)) {
                    final BoardPosition piecePosition = new BoardPosition(x, y);
                    /*
                     * Check for any simple moves. Statistically, there will
                     * usually be more simple moves than jumps so this will most
                     * likely return first - thus checked first.
                     */
                    getSimpleMovesForPiece(
                            board, foundPiece, piecePosition, moves);
                    if (!moves.isEmpty()) {
                        return true;
                    }
                    // Check for any jumps.
                    getJumpsForPiece(board, foundPiece, piecePosition, jumps);
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
    private static void getJumpsForPiece(final Board board,
            final Piece piece, final BoardPosition piecePosition,
            final Collection<Jump> jumps) {
        if (piece.isCrowned()) {
            /*
             * The piece is crowned so can move in any direction.
             */
            addJumpIfNotNull(jumps,
                    getJumpAboveLeft(board, piecePosition, piece.getOwner()));
            addJumpIfNotNull(jumps,
                    getJumpAboveRight(board, piecePosition, piece.getOwner()));
            addJumpIfNotNull(jumps,
                    getJumpBottomLeft(board, piecePosition, piece.getOwner()));
            addJumpIfNotNull(jumps,
                    getJumpBottomRight(board, piecePosition, piece.getOwner()));
        } else {
            /*
             * The piece is not crowned so the direction needs to be determined.
             */
            if (piece.getMoveDirection() == MoveDirection.UP) {
                // Only get upward jumps.
                addJumpIfNotNull(jumps,
                        getJumpAboveLeft(
                                board, piecePosition, piece.getOwner()));
                addJumpIfNotNull(jumps,
                        getJumpAboveRight(
                                board, piecePosition, piece.getOwner()));
            } else if (piece.getMoveDirection() == MoveDirection.DOWN) {
                // Only get downward jumps.
                addJumpIfNotNull(jumps,
                        getJumpBottomLeft(
                                board, piecePosition, piece.getOwner()));
                addJumpIfNotNull(jumps,
                        getJumpBottomRight(
                                board, piecePosition, piece.getOwner()));
            } else {
                throw new IllegalStateException("Unhandled move direction.");
            }
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
    private static void getSimpleMovesForPiece(
            final Board board,
            final Piece piece,
            final BoardPosition piecePosition,
            final Collection<Move> moves) {
        if (piece.isCrowned()) {
            // The piece is crowned so can move in any direction.
            addMoveIfNotNull(moves,
                    getMoveAboveLeft(board, piecePosition));
            addMoveIfNotNull(moves,
                    getMoveAboveRight(board, piecePosition));
            addMoveIfNotNull(moves,
                    getMoveBottomLeft(board, piecePosition));
            addMoveIfNotNull(moves,
                    getMoveBottomRight(board, piecePosition));
        } else {
            // The piece is not crowned so the direction needs to be determined.
            if (piece.getMoveDirection() == MoveDirection.UP) {
                // Only get upward moves.
                addMoveIfNotNull(moves,
                        getMoveAboveLeft(board, piecePosition));
                addMoveIfNotNull(moves,
                        getMoveAboveRight(board, piecePosition));
            } else if (piece.getMoveDirection() == MoveDirection.DOWN) {
                // Only get downward moves.
                addMoveIfNotNull(moves,
                        getMoveBottomLeft(board, piecePosition));
                addMoveIfNotNull(moves,
                        getMoveBottomRight(board, piecePosition));
            } else {
                throw new IllegalStateException("Unhandled move direction.");
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
    private static void addMoveIfNotNull(final Collection<Move> moves,
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
    private static void addJumpIfNotNull(final Collection<Jump> jumps,
            final Jump possibleJump) {
        if (possibleJump != null) {
            jumps.add(possibleJump);
        }
    }
    /**
     * Explores and returns all possible jump sequences for the specified jumps.
     *
     * @param board  the board to use
     * @param jumps  the jumps
     * @return       a collection of {@link Move} instances for the given jumps
     */
    private static List<Move> getJumpSequences(
            final Board board,
            final Collection<Jump> jumps) {
        final List<Move> jumpSequences = new ArrayList<>();
        for (final Jump singleJump : jumps) {
            exploreJump(board,
                        board.getPieceAt(singleJump.getFrom()),
                        singleJump,
                        new ArrayList<>(),
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
    private static void exploreJump(
            final Board board,
            final Piece piece,
            final Jump jump,
            final List<Jump> path,
            final Collection<Move> sequences) {
        path.add(jump);
        final Collection<Jump> furtherJumps = new ArrayList<>();
        getJumpsForPiece(board, piece, jump.getTo(), furtherJumps);
        /*
         * Need to remove jumps that have already been done. This will happen
         * with crown pieces as they can go back and forth.
         */
        removeAlreadyJumpedPositionMoves(furtherJumps, path);
        if (furtherJumps.isEmpty()) {
            sequences.add(new Move(path.get(0).getFrom(), jump.getTo(), path));
        } else {
            for (final Jump furtherJump : furtherJumps) {
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
            final Collection<Jump> jumps, final List<Jump> previousJumps) {
        final Iterator<Jump> jumpsIterator = jumps.iterator();
        while (jumpsIterator.hasNext()) {
            final BoardPosition jumpedPosition =
                    jumpsIterator.next().getJumped();
            // Check jumps already found.
            for (final Jump jumpAlreadyMade : previousJumps) {
                if (jumpedPosition.equals(jumpAlreadyMade.getJumped())) {
                    jumpsIterator.remove();
                    // No point in searching the remaining if any.
                    break;
                }
            }
        }
    }
}
