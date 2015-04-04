package uk.ac.tees.aia.mullen.draughts.backend.english;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import uk.ac.tees.aia.mullen.draughts.backend.Board;
import uk.ac.tees.aia.mullen.draughts.backend.Game;
import uk.ac.tees.aia.mullen.draughts.backend.Move;
import uk.ac.tees.aia.mullen.draughts.backend.MoveGenerator;
import uk.ac.tees.aia.mullen.draughts.backend.MovePerformer;
import uk.ac.tees.aia.mullen.draughts.backend.MovePerformer.PerformedMove;
import uk.ac.tees.aia.mullen.draughts.backend.Piece;
import uk.ac.tees.aia.mullen.draughts.backend.Player;
import uk.ac.tees.aia.mullen.draughts.backend.Piece.MoveDirection;
import uk.ac.tees.aia.mullen.draughts.english.EnglishDraughtsMoveGenerator;
import uk.ac.tees.aia.mullen.draughts.english.EnglishDraughtsMovePerformer;

/**
 * Unit tests for {@link EnglishDraughtsMovePerformer}.
 *
 * @author  Alex Mullen
 */
public class TestEnglishDraughtsMovePerformer {
    /** The move performer to use for each test. */
    private final MovePerformer movePerformer =
            new EnglishDraughtsMovePerformer();
    /** The dark piece owner used for testing. */
    private final Player darkPieceOwner = new EmptyMockPieceOwner();
    /** The light piece owner used for testing. */
    private final Player lightPieceOwner = new EmptyMockPieceOwner();
    /** The dark pieces used for testing. */
    private final Piece darkPiece =
            new Piece(darkPieceOwner, MoveDirection.DOWN);
//    /** The dark crowned pieces used for testing. */
//    private final Piece darkPieceCrowned =
//            new Piece(darkPieceOwner, MoveDirection.BOTH);
    /** The light pieces used for testing. */
    private final Piece lightPiece =
            new Piece(lightPieceOwner, MoveDirection.UP);
    /** The light crowned pieces used for testing. */
    private final Piece lightPieceCrowned =
            new Piece(lightPieceOwner, MoveDirection.BOTH);
    /** The move finder to use for getting available moves to perform. */
    private final MoveGenerator moveGenerator =
            new EnglishDraughtsMoveGenerator();
    /**
     * Tests {@link EnglishDraughtsMovePerformer#perform(Move, Board)} works as
     * expected when performing a move then undoing it.
     */
    @Test
    public final void testPerform1() {
        // Create a board and place some pieces.
        final Board board1 = new Board(10, 8);
        board1.setPieceAt(3, 7, lightPiece);
        board1.setPieceAt(4, 6, darkPiece);
        board1.setPieceAt(4, 4, darkPiece);
        board1.setPieceAt(4, 2, darkPiece);
        // Save the state.
        final Board board1OriginalState = new Board(board1);
        /*
         * Create an expected board state for the board to look like after
         * performing the move.
         */
        final Board board1ExpectedStateAfterMove = new Board(10, 8);
        board1ExpectedStateAfterMove.setPieceAt(5, 1, lightPiece);
        // Get the move and perform it.
        final List<Move> foundMoves =
                moveGenerator.findMoves(board1, lightPieceOwner);
        // There should be one move found.
        assertEquals(1, foundMoves.size());
        final Move moveToPerform = foundMoves.get(0);
        final PerformedMove performedMove =
                movePerformer.perform(moveToPerform, board1);
        // The board should now look like the specified board.
        assertEquals(board1ExpectedStateAfterMove, board1);
        // Undoing the move should make the board look like its original state.
        performedMove.undo();
        assertEquals(board1OriginalState, board1);
    }
    /**
     * Tests {@link EnglishDraughtsMovePerformer#perform(Move, Board)} works as
     * expected when performing a move then undoing it.
     * <p>
     * This also tests to make sure a crowned piece is un-crowned when undoing a
     * move.
     */
    @Test
    public final void testPerform2() {
        // Create a board and place some pieces.
        final Board board1 = new Board(8, 8);
        board1.setPieceAt(4, 6, lightPiece);
        board1.setPieceAt(5, 5, darkPiece);
        board1.setPieceAt(5, 3, darkPiece);
        board1.setPieceAt(3, 1, darkPiece);
        // Save the state.
        final Board board1OriginalState = new Board(board1);
        /*
         * Create an expected board state for the board to look like after
         * performing the move.
         */
        final Board board1ExpectedStateAfterMove = new Board(8, 8);
        board1ExpectedStateAfterMove.setPieceAt(2, 0, lightPieceCrowned);
        // Get the move and perform it.
        final List<Move> foundMoves =
                moveGenerator.findMoves(board1, lightPieceOwner);
        // There should be one move found.
        assertEquals(1, foundMoves.size());
        final Move moveToPerform = foundMoves.get(0);
        final PerformedMove performedMove =
                movePerformer.perform(moveToPerform, board1);
        // The board should now look like the specified board.
        assertEquals(board1ExpectedStateAfterMove, board1);
        // Undoing the move should make the board look like its original state.
        performedMove.undo();
        assertEquals(board1OriginalState, board1);
    }
    /**
     * An empty mock <code>PieceOwner</code> instance used as a place holder
     * for testing.
     *
     * @author  Alex Mullen
     */
    private static class EmptyMockPieceOwner implements Player {
        @Override
        public void onTurn(final Game game) {
            // Empty.
        }
        @Override
        public void onGameEnded(final Game game, final Player winner) {
            // Empty.
        }
    }
}
