package mullen.alex.jdf.variant.english;

import static org.junit.Assert.assertEquals;

import java.util.List;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MoveGenerator;
import mullen.alex.jdf.common.MovePerformer;
import mullen.alex.jdf.common.Piece;
import mullen.alex.jdf.common.MovePerformer.PerformedMove;

import org.junit.Test;

/**
 * Unit tests for {@link EnglishDraughtsMovePerformer}.
 *
 * @author  Alex Mullen
 */
public class TestEnglishDraughtsMovePerformer {
    /** The move performer to use for each test. */
    private final MovePerformer movePerformer =
            new EnglishDraughtsMovePerformer();
    /** The dark pieces used for testing. */
    private final Piece darkPiece = new Piece(Piece.DARK, Piece.DOWN);
    /** The light pieces used for testing. */
    private final Piece lightPiece = new Piece(Piece.LIGHT, Piece.UP);
    /** The light crowned pieces used for testing. */
    private final Piece lightPieceCrowned = new Piece(Piece.LIGHT, Piece.BOTH);
    /** The move generator to use for getting available moves to perform. */
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
        board1.setPieceAndGetAt(3, 7, lightPiece);
        board1.setPieceAndGetAt(4, 6, darkPiece);
        board1.setPieceAndGetAt(4, 4, darkPiece);
        board1.setPieceAndGetAt(4, 2, darkPiece);
        // Save the state.
        final Board board1OriginalState = new Board(board1);
        /*
         * Create an expected board state for the board to look like after
         * performing the move.
         */
        final Board board1ExpectedStateAfterMove = new Board(10, 8);
        board1ExpectedStateAfterMove.setPieceAndGetAt(5, 1, lightPiece);
        // Get the move and perform it.
        final List<Move> foundMoves =
                moveGenerator.findMoves(board1, Piece.LIGHT);
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
        board1.setPieceAndGetAt(4, 6, lightPiece);
        board1.setPieceAndGetAt(5, 5, darkPiece);
        board1.setPieceAndGetAt(5, 3, darkPiece);
        board1.setPieceAndGetAt(3, 1, darkPiece);
        // Save the state.
        final Board board1OriginalState = new Board(board1);
        /*
         * Create an expected board state for the board to look like after
         * performing the move.
         */
        final Board board1ExpectedStateAfterMove = new Board(8, 8);
        board1ExpectedStateAfterMove.setPieceAndGetAt(2, 0, lightPieceCrowned);
        // Get the move and perform it.
        final List<Move> foundMoves =
                moveGenerator.findMoves(board1, Piece.LIGHT);
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
}
