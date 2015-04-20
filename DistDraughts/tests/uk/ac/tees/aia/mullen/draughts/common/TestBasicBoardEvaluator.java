package uk.ac.tees.aia.mullen.draughts.common;

import static org.junit.Assert.assertEquals;
import static uk.ac.tees.aia.mullen.draughts.common.BasicBoardEvaluator.POINTS_FOR_PIECE;
import static uk.ac.tees.aia.mullen.draughts.common.BasicBoardEvaluator.POINTS_FOR_PIECE_CROWNED;

import org.junit.Test;

import uk.ac.tees.aia.mullen.draughts.common.BasicBoardEvaluator;
import uk.ac.tees.aia.mullen.draughts.common.Board;
import uk.ac.tees.aia.mullen.draughts.common.BoardEvaluator;
import uk.ac.tees.aia.mullen.draughts.common.Piece;
import uk.ac.tees.aia.mullen.draughts.common.Player;
import uk.ac.tees.aia.mullen.draughts.common.Piece.MoveDirection;

/**
 * Unit tests for {@link BasicBoardEvaluator}.
 *
 * @author  Alex Mullen
 */
public class TestBasicBoardEvaluator {
    /** The evaluator available for each test. */
    private final BoardEvaluator evaluator = new BasicBoardEvaluator();
    /** The dark piece owner used for testing. */
    private final Player darkPieceOwner = new EmptyMockPieceOwner();
    /** The light piece owner used for testing. */
    private final Player lightPieceOwner = new EmptyMockPieceOwner();
//    /** The dark pieces used for testing. */
//    private final Piece darkPiece =
//            new Piece(darkPieceOwner, MoveDirection.DOWN);
    /** The dark crowned pieces used for testing. */
    private final Piece darkPieceCrowned =
            new Piece(darkPieceOwner, MoveDirection.BOTH);
    /** The light pieces used for testing. */
    private final Piece lightPiece =
            new Piece(lightPieceOwner, MoveDirection.UP);
//    /** The light crowned pieces used for testing. */
//    private final Piece lightPieceCrowned =
//            new Piece(lightPieceOwner, MoveDirection.BOTH);
    /**
     * Tests that the evaluation gives expected scores for some typical board
     * setups.
     */
    @Test
    public final void testEvaluate() {
        final Board board = new Board(8, 8);
        // Evaluate an empty board.
        assertEquals(Integer.MIN_VALUE,
                evaluator.evaluate(board, lightPieceOwner));
        assertEquals(Integer.MIN_VALUE,
                evaluator.evaluate(board, darkPieceOwner));
        // Place one light piece on the board.
        board.setPieceAndGetAt(3, 7, lightPiece);
        assertEquals(
                Integer.MAX_VALUE,
                evaluator.evaluate(board, lightPieceOwner));
        assertEquals(
                Integer.MIN_VALUE,
                evaluator.evaluate(board, darkPieceOwner));
        // Place another light piece on the board.
        board.setPieceAndGetAt(5, 7, lightPiece);
        assertEquals(
                Integer.MAX_VALUE,
                evaluator.evaluate(board, lightPieceOwner));
        assertEquals(
                Integer.MIN_VALUE,
                evaluator.evaluate(board, darkPieceOwner));
        // Place one dark king on the board.
        board.setPieceAndGetAt(3, 3, darkPieceCrowned);
        assertEquals(
                (2 * POINTS_FOR_PIECE) - POINTS_FOR_PIECE_CROWNED,
                evaluator.evaluate(board, lightPieceOwner));
        assertEquals(
                POINTS_FOR_PIECE_CROWNED - (2 * POINTS_FOR_PIECE),
                evaluator.evaluate(board, darkPieceOwner));
    }
    /**
     * Tests that the evaluation with a 1x1 board to test it correctly iterates
     * and considers every square.
     */
    @Test
    public final void testEvaluateOnSmallestBoardPossible() {
        final Board board = new Board(1, 1);
        // Evaluate an empty one-square board.
        assertEquals(Integer.MIN_VALUE,
                evaluator.evaluate(board, lightPieceOwner));
        assertEquals(Integer.MIN_VALUE,
                evaluator.evaluate(board, darkPieceOwner));
        // Place one light piece on the square.
        board.setPieceAndGetAt(0, 0, lightPiece);
        assertEquals(
                Integer.MAX_VALUE,
                evaluator.evaluate(board, lightPieceOwner));
        assertEquals(
                Integer.MIN_VALUE,
                evaluator.evaluate(board, darkPieceOwner));
    }
    /**
     * An empty mock <code>Player</code> instance used as a place holder
     * for testing.
     *
     * @author  Alex Mullen
     */
    private static class EmptyMockPieceOwner implements Player {
        @Override
        public boolean isArtificial() {
            // TODO Auto-generated method stub
            return false;
        }
    }
}
