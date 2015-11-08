package mullen.alex.jdf.variant.english;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.BoardPosition;
import mullen.alex.jdf.common.Jump;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MoveGenerator;
import mullen.alex.jdf.common.Piece;

import static mullen.alex.jdf.common.MoveGeneratorUtil.*;

/**
 * A move generator implementation that takes into account the rules of
 * English draughts.
 *
 * @author  Alex Mullen
 *
 */
public class EnglishDraughtsMoveGenerator implements MoveGenerator {
    @Override
    public final List<Move> findMoves(final Board board, final int colour) {
        final List<Jump> foundJumps = new ArrayList<>();
        final List<Move> foundSimpleMoves = new ArrayList<>(20);
        /*
         * Go through every square and get the moves of any pieces that belong
         * to the specified player.
         */
        final Piece[] pieces = board.pieces;
        final BoardPosition[] positions = board.positions;
        final int piecesArrayLength = pieces.length;
        for (int i = 0; i < piecesArrayLength; i++) {
            final Piece foundPiece = pieces[i];
            if (foundPiece != null && foundPiece.colour == colour) {
                final BoardPosition piecePosition = positions[i];
                findJumpsForPiece(board, foundPiece, piecePosition, foundJumps);
                if (foundJumps.isEmpty()) {
                    /*
                     * Only bother for simple moves if jumps were not found
                     * as they cannot be taken if there are jumps available
                     * in English Draughts rules.
                     */
                    findSimpleMovesForPiece(
                            board, foundPiece, piecePosition, foundSimpleMoves);
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
        final int pieceColour = piece.colour;
        switch (piece.getMoveDirection()) {
            case Piece.BOTH:
                findJumpAboveLeft(board, piecePosition, pieceColour, jumps);
                findJumpAboveRight(board, piecePosition, pieceColour, jumps);
                //$FALL-THROUGH$
            case Piece.DOWN:
                findJumpBottomLeft(board, piecePosition, pieceColour, jumps);
                findJumpBottomRight(board, piecePosition, pieceColour, jumps);
                break;
            case Piece.UP:
                findJumpAboveLeft(board, piecePosition, pieceColour, jumps);
                findJumpAboveRight(board, piecePosition, pieceColour, jumps);
                break;
            default:
                break;
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
        switch (piece.getMoveDirection()) {
            case Piece.BOTH:
                findMoveAboveLeft(board, piecePosition, moves);
                findMoveAboveRight(board, piecePosition, moves);
                //$FALL-THROUGH$
            case Piece.DOWN:
                findMoveBottomLeft(board, piecePosition, moves);
                findMoveBottomRight(board, piecePosition, moves);
                break;
            case Piece.UP:
                findMoveAboveLeft(board, piecePosition, moves);
                findMoveAboveRight(board, piecePosition, moves);
                break;
            default:
                break;
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
     * @param sequences  the list of found sequences
     */
    private static void exploreJump(final Board board, final Piece piece,
            final Jump jump, final List<Jump> path,
            final List<Move> sequences) {
        path.add(jump);
        // Apply the move to the board, saving some objects so we can revert.
        final Piece jumpedRemovedPiece =
                board.setPieceAndGetAt(jump.jumped, null);
        board.setPieceAt(jump.from, null);
        board.setPieceAt(jump.to, piece);
        final List<Jump> furtherJumps = new ArrayList<>(4);
        findJumpsForPiece(board, piece, jump.to, furtherJumps);
        if (furtherJumps.isEmpty()) {
            sequences.add(new Move(path.get(0).from, jump.to, path));
        } else {
            final int furtherJumpsSize = furtherJumps.size();
            for (int i = 0; i < furtherJumpsSize; i++) {
                exploreJump(board, piece, furtherJumps.get(i),
                        new ArrayList<>(path), sequences);
            }
        }
        // Revert the board back to its original position.
        board.setPieceAt(jump.jumped, jumpedRemovedPiece);
        board.setPieceAt(jump.from, piece);
        board.setPieceAt(jump.to, null);
    }
}
