package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import uk.ac.tees.aia.mullen.draughts.backend.Piece.MoveDirection;

/**
 * A move finder implementation that takes into account the rules of
 * English draughts.
 *
 * @author  Alex Mullen
 *
 */
public class EnglishDraughtsMoveFinder extends AbstractMoveFinder {
    @Override
    public final List<Move> findMoves(final Board board,
            final PieceOwner owner) {
        final Collection<JumpMove> foundJumps = new ArrayList<>();
        final Collection<Move> foundSimpleMoves = new ArrayList<>();
        for (final BoardPosition piecePosition
                : getPositionsForPieces(board, owner)) {
            final Piece piece = board.getPieceAt(piecePosition);
            foundJumps.addAll(getJumpsForPiece(board, piece, piecePosition));
            if (foundJumps.isEmpty()) {
                /*
                 * Only bother for simple moves if jumps were not found as they
                 * cannot be taken if there are jumps available in English
                 * Draughts rules.
                 */
                foundSimpleMoves.addAll(
                        getSimpleMovesForPiece(board, piece, piecePosition));
            }
        }
        if (foundJumps.isEmpty()) {
            final List<Move> combinedMoves = new ArrayList<>();
            combinedMoves.addAll(foundJumps);
            combinedMoves.addAll(foundSimpleMoves);
            return combinedMoves;
        } else {
            // Explore jumps further and get their sequences.
            return new ArrayList<Move>(getJumpSequences(board, foundJumps));
        }
    }
    /**
     * Gets all available jumps for a piece on a board.
     *
     * @param board          the board
     * @param piece          the piece
     * @param piecePosition  the position of the piece
     * @return               a list of jumps available for the specified piece
     *                       or an empty list if there are no jumps available;
     *                       never <code>null</code>
     */
    private static Collection<JumpMove> getJumpsForPiece(final Board board,
            final Piece piece, final BoardPosition piecePosition) {
        final Collection<JumpMove> jumps = new ArrayList<>();
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
        return jumps;
    }
    /**
     * Gets all available simple moves for a piece.
     *
     * @param board          the board
     * @param piece          the piece
     * @param piecePosition  the position of the piece
     * @return               a list of simple moves available for the specified
     *                       piece or an empty list if there are no moves
     *                       available; never <code>null</code>
     */
    private static Collection<Move> getSimpleMovesForPiece(
            final Board board,
            final Piece piece,
            final BoardPosition piecePosition) {
        final Collection<Move> foundMoves = new ArrayList<>();
        if (piece.isCrowned()) {
            // The piece is crowned so can move in any direction.
            addMoveIfNotNull(foundMoves,
                    getMoveAboveLeft(board, piecePosition));
            addMoveIfNotNull(foundMoves,
                    getMoveAboveRight(board, piecePosition));
            addMoveIfNotNull(foundMoves,
                    getMoveBottomLeft(board, piecePosition));
            addMoveIfNotNull(foundMoves,
                    getMoveBottomRight(board, piecePosition));
        } else {
            // The piece is not crowned so the direction needs to be determined.
            if (piece.getMoveDirection() == MoveDirection.UP) {
                // Only get upward moves.
                addMoveIfNotNull(foundMoves,
                        getMoveAboveLeft(board, piecePosition));
                addMoveIfNotNull(foundMoves,
                        getMoveAboveRight(board, piecePosition));
            } else if (piece.getMoveDirection() == MoveDirection.DOWN) {
                // Only get downward moves.
                addMoveIfNotNull(foundMoves,
                        getMoveBottomLeft(board, piecePosition));
                addMoveIfNotNull(foundMoves,
                        getMoveBottomRight(board, piecePosition));
            } else {
                throw new IllegalStateException("Unhandled move direction.");
            }
        }
        return foundMoves;
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
    private static void addJumpIfNotNull(final Collection<JumpMove> jumps,
            final JumpMove possibleJump) {
        if (possibleJump != null) {
            jumps.add(possibleJump);
        }
    }
    /**
     * Explores and returns all possible jump sequences for the specified jumps.
     *
     * @param board  the board to use
     * @param jumps  the jumps
     * @return       a collection of {@link JumpMoveSequence} instances for the
     *               given jumps
     */
    private static Collection<JumpMoveSequence> getJumpSequences(
            final Board board,
            final Collection<JumpMove> jumps) {
        final Collection<JumpMoveSequence> jumpSequences = new ArrayList<>();
        for (final JumpMove singleJump : jumps) {
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
            final JumpMove jump,
            final List<JumpMove> path,
            final Collection<JumpMoveSequence> sequences) {
        path.add(jump);
        final Collection<JumpMove> furtherJumps =
                getJumpsForPiece(board, piece, jump.getTo());
        /*
         * Need to remove jumps that have already been done. This will happen
         * with crown pieces as they can go back and forth.
         */
        removeAlreadyJumpedPositionMoves(furtherJumps, path);
        if (furtherJumps.isEmpty()) {
            sequences.add(new JumpMoveSequence(
                    path.get(0).getFrom(),
                    jump.getTo(),
                    path));
        } else {
            for (final JumpMove furtherJump : furtherJumps) {
                // Check we have not already jumped over this position.
                exploreJump(board,
                            piece,
                            furtherJump,
                            new ArrayList<>(path),
                            sequences);
            }
        }
    }
    /**
     * A helper method for pruning jumps that are not possible because a jump
     * has already been performed over the position.
     *
     * @param jumps          the found jumps
     * @param previousJumps  previous jumps
     */
    private static void removeAlreadyJumpedPositionMoves(
            final Collection<JumpMove> jumps,
            final List<JumpMove> previousJumps) {
        final Iterator<JumpMove> jumpsIterator = jumps.iterator();
        while (jumpsIterator.hasNext()) {
            final BoardPosition jumpedPosition =
                    jumpsIterator.next().getJumped();
            // Check jumps already found.
            for (final JumpMove jumpAlreadyMade : previousJumps) {
                if (jumpedPosition.equals(jumpAlreadyMade.getJumped())) {
                    jumpsIterator.remove();
                    // No point in searching the remaining if any.
                    break;
                }
            }
        }
    }
}
