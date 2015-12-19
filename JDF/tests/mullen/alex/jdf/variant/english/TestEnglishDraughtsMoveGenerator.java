package mullen.alex.jdf.variant.english;

import java.util.List;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.BoardPosition;
import mullen.alex.jdf.common.Jump;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MoveGenerator;
import mullen.alex.jdf.common.Piece;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static mullen.alex.jdf.common.Piece.*;

/**
 * Unit tests for {@link EnglishDraughtsMoveGenerator}.
 *
 * @author  Alex Mullen
 */
public class TestEnglishDraughtsMoveGenerator {
    /** The move generator to use for each test. */
    private final MoveGenerator moveGen = new EnglishDraughtsMoveGenerator();
    /** The dark piece used for testing. */
    private final Piece darkPiece = new Piece(DARK, DOWN);
    /** The dark crowned piece used for testing. */
    private final Piece darkPieceCrowned = new Piece(DARK, BOTH);
    /** The light piece used for testing. */
    private final Piece lightPiece = new Piece(LIGHT, UP);
    /** The light crowned piece used for testing. */
    private final Piece lightPieceCrowned = new Piece(LIGHT, BOTH);
    ////////////////////////////////////////////////////////////////////////////
    /**
     * A test for making sure a simple move can be properly found upwards left.
     */
    @Test
    public final void testFindMovesWithManNoJumpsUpwardsLeft() {
        final Board board = new Board(8, 8);
        board.setPieceAt(3, 6, lightPiece);
        board.setPieceAt(4, 5, darkPiece);
        board.setPieceAt(5, 4, darkPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundMove = foundMoves.get(0);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
        // Check the expected move positions.
        assertEquals(new BoardPosition(3, 6), foundMove.from);
        assertEquals(new BoardPosition(2, 5), foundMove.to);
    }
    /**
     * A test for making sure a simple move can be properly found upwards right.
     */
    @Test
    public final void testFindMovesWithManNoJumpsUpwardsRight() {
        final Board board = new Board(8, 8);
        board.setPieceAt(3, 6, lightPiece);
        board.setPieceAt(2, 5, darkPiece);
        board.setPieceAt(1, 4, darkPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundMove = foundMoves.get(0);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
        // Check the expected move positions.
        assertEquals(new BoardPosition(3, 6), foundMove.from);
        assertEquals(new BoardPosition(4, 5), foundMove.to);
    }
    /**
     * A test for making sure a simple move can be properly found downwards
     * right.
     */
    @Test
    public final void testFindMovesWithManNoJumpsDownwardsRight() {
        final Board board = new Board(8, 8);
        board.setPieceAt(4, 1, darkPiece);
        board.setPieceAt(3, 2, lightPiece);
        board.setPieceAt(2, 3, lightPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, DARK);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundMove = foundMoves.get(0);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
        // Check the expected move positions.
        assertEquals(new BoardPosition(4, 1), foundMove.from);
        assertEquals(new BoardPosition(5, 2), foundMove.to);
    }
    /**
     * A test for making sure a simple move can be properly found downwards
     * left.
     */
    @Test
    public final void testFindMovesWithManNoJumpsDownwardsLeft() {
        final Board board = new Board(8, 8);
        board.setPieceAt(4, 1, darkPiece);
        board.setPieceAt(5, 2, lightPiece);
        board.setPieceAt(6, 3, lightPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, DARK);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundMove = foundMoves.get(0);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
        // Check the expected move positions.
        assertEquals(new BoardPosition(4, 1), foundMove.from);
        assertEquals(new BoardPosition(3, 2), foundMove.to);
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests that all four surrounding non-jumping moves for a king can be
     * found.
     */
    @Test
    public final void testFindMovesWithKingNoJumps() {
        final Board board = new Board(8, 8);
        board.setPieceAt(4, 3, darkPieceCrowned);
        final List<Move> foundMoves = moveGen.findMoves(board, DARK);
        // The four moves surrounding the king should be found.
        assertEquals(4, foundMoves.size());
        // Check each move to make sure it has no jumps and is what we expect.
        // First move should be upper-left space.
        final Move firstMove = foundMoves.get(0);
        assertEquals(0, firstMove.jumps.size());
        assertEquals(new BoardPosition(4, 3), firstMove.from);
        assertEquals(new BoardPosition(3, 2), firstMove.to);
        // Second move should be upper-right space.
        final Move secondMove = foundMoves.get(1);
        assertEquals(0, secondMove.jumps.size());
        assertEquals(new BoardPosition(4, 3), secondMove.from);
        assertEquals(new BoardPosition(5, 2), secondMove.to);
        // Third move should be lower-left space.
        final Move thirdMove = foundMoves.get(2);
        assertEquals(0, thirdMove.jumps.size());
        assertEquals(new BoardPosition(4, 3), thirdMove.from);
        assertEquals(new BoardPosition(3, 4), thirdMove.to);
        // Fourth move should be lower-right space.
        final Move fourthMove = foundMoves.get(3);
        assertEquals(0, fourthMove.jumps.size());
        assertEquals(new BoardPosition(4, 3), fourthMove.from);
        assertEquals(new BoardPosition(5, 4), fourthMove.to);
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * A test for making sure a simple move can be properly found.
     */
    @Test
    public final void testFindMovesWithNoJumps2() {
        final Board board = new Board(8, 8);
        board.setPieceAt(3, 1, lightPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should only be two found.
        assertEquals(2, foundMoves.size());
        final Move firstfoundMove = foundMoves.get(0);
        // There should be no jumps.
        assertEquals(0, firstfoundMove.jumps.size());
        assertEquals(new BoardPosition(3, 1), firstfoundMove.from);
        assertEquals(new BoardPosition(2, 0), firstfoundMove.to);
        // Check the second move is what we expect.
        final Move secondfoundMove = foundMoves.get(1);
        // There should be no jumps.
        assertEquals(0, secondfoundMove.jumps.size());
        assertEquals(new BoardPosition(3, 1), secondfoundMove.from);
        assertEquals(new BoardPosition(4, 0), secondfoundMove.to);
    }
    /**
     * A test for making sure no moves are returned when there isn't any.
     */
    @Test
    public final void testFindMovesWithNoMovesOrJumps() {
        final Board board = new Board(8, 8);
        board.setPieceAt(0, 1, lightPiece);
        board.setPieceAt(1, 0, lightPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should be no moves found and null should NOT be returned.
        assertEquals(0, foundMoves.size());
    }
    /**
     * A test for making sure a simple jump move can be properly found.
     * <p>
     * This test also gives the attacking piece an opportunity to move without
     * jumping but this should not be returned as there is a jump opportunity
     * which must be taken according to English Draughts rules.
     */
    @Test
    public final void testFindMovesWithSingleJump() {
        final Board board = new Board(8, 8);
        board.setPieceAt(3, 7, lightPiece);
        board.setPieceAt(2, 6, darkPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move jumpMove = foundMoves.get(0);
        final BoardPosition expectedFromPosition = new BoardPosition(3, 7);
        final BoardPosition expectedtoPosition = new BoardPosition(1, 5);
        final BoardPosition expectedJumpPosition = new BoardPosition(2, 6);
        assertEquals(expectedFromPosition, jumpMove.from);
        assertEquals(expectedtoPosition, jumpMove.to);
        // There should be one jump in the sequence.
        assertEquals(1, jumpMove.jumps.size());
        assertEquals(expectedFromPosition, jumpMove.jumps.get(0).from);
        assertEquals(expectedtoPosition, jumpMove.jumps.get(0).to);
        assertEquals(expectedJumpPosition, jumpMove.jumps.get(0).jumped);
    }
    /**
     * A test for making sure a double jump in the same diagonal direction works
     * and returns the correct move.
     */
    @Test
    public final void testFindMovesWithDoubleJumpSameDirection() {
        final Board board = new Board(8, 8);
        board.setPieceAt(0, 7, lightPiece);
        board.setPieceAt(1, 6, darkPiece);
        board.setPieceAt(3, 4, darkPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundJumpMove = foundMoves.get(0);
        // There should be two jumps in the sequence.
        assertEquals(2, foundJumpMove.jumps.size());
        // Check the first jump in the sequence is what we expect.
        final Jump firstJump = foundJumpMove.jumps.get(0);
        assertEquals(new BoardPosition(0, 7), firstJump.from);
        assertEquals(new BoardPosition(2, 5), firstJump.to);
        assertEquals(new BoardPosition(1, 6), firstJump.jumped);
        // Check the second jump in the sequence is what we expect.
        final Jump secondJump = foundJumpMove.jumps.get(1);
        assertEquals(new BoardPosition(2, 5), secondJump.from);
        assertEquals(new BoardPosition(4, 3), secondJump.to);
        assertEquals(new BoardPosition(3, 4), secondJump.jumped);
    }
    /**
     * A test for making sure a triple jump in the same diagonal direction works
     * returns the correct move.
     */
    @Test
    public final void testFindMovesWithTripleJumpSameDirection() {
        final Board board = new Board(8, 8);
        board.setPieceAt(0, 7, lightPiece);
        board.setPieceAt(1, 6, darkPiece);
        board.setPieceAt(3, 4, darkPiece);
        board.setPieceAt(5, 2, darkPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should only be one found.
        assertEquals(1, foundMoves.size());
        final Move foundJumpMove = foundMoves.get(0);
        // There should be three jumps in the sequence.
        assertEquals(3, foundJumpMove.jumps.size());
        // Check the first jump in the sequence is what we expect.
        final Jump firstJump = foundJumpMove.jumps.get(0);
        assertEquals(new BoardPosition(0, 7), firstJump.from);
        assertEquals(new BoardPosition(2, 5), firstJump.to);
        assertEquals(new BoardPosition(1, 6), firstJump.jumped);
        // Check the second jump in the sequence is what we expect.
        final Jump secondJump = foundJumpMove.jumps.get(1);
        assertEquals(new BoardPosition(2, 5), secondJump.from);
        assertEquals(new BoardPosition(4, 3), secondJump.to);
        assertEquals(new BoardPosition(3, 4), secondJump.jumped);
        // Check the third jump in the sequence is what we expect.
        final Jump thirdJump = foundJumpMove.jumps.get(2);
        assertEquals(new BoardPosition(4, 3), thirdJump.from);
        assertEquals(new BoardPosition(6, 1), thirdJump.to);
        assertEquals(new BoardPosition(5, 2), thirdJump.jumped);
    }
    @Test
    public final void testFindMovesWithQuadDiamondJumpUsingLightKing() {
        final Board board = new Board(8, 8);
        board.setPieceAt(4, 7, lightPieceCrowned);
        board.setPieceAt(3, 6, darkPiece);
        board.setPieceAt(3, 4, darkPiece);
        board.setPieceAt(5, 4, darkPiece);
        board.setPieceAt(5, 6, darkPiece);
        final List<Move> foundMoves = moveGen.findMoves(board, LIGHT);
        // There should two moves found.
        assertEquals(2, foundMoves.size());
        ////////////////////////////////////////////////////////////////////////
        // Retrieve the first move and verify (Jumps go clockwise around).
        final Move firstMove = foundMoves.get(0);
        // This should have four jumps.
        assertEquals(4, firstMove.jumps.size());
        // Verify the jumps (First jump).
        assertEquals(new BoardPosition(4, 7), firstMove.jumps.get(0).from);
        assertEquals(new BoardPosition(2, 5), firstMove.jumps.get(0).to);
        assertEquals(new BoardPosition(3, 6), firstMove.jumps.get(0).jumped);
        // Second jump.
        assertEquals(new BoardPosition(2, 5), firstMove.jumps.get(1).from);
        assertEquals(new BoardPosition(4, 3), firstMove.jumps.get(1).to);
        assertEquals(new BoardPosition(3, 4), firstMove.jumps.get(1).jumped);
        // Third jump.
        assertEquals(new BoardPosition(4, 3), firstMove.jumps.get(2).from);
        assertEquals(new BoardPosition(6, 5), firstMove.jumps.get(2).to);
        assertEquals(new BoardPosition(5, 4), firstMove.jumps.get(2).jumped);
        // Fourth jump.
        assertEquals(new BoardPosition(6, 5), firstMove.jumps.get(3).from);
        assertEquals(new BoardPosition(4, 7), firstMove.jumps.get(3).to);
        assertEquals(new BoardPosition(5, 6), firstMove.jumps.get(3).jumped);
        ////////////////////////////////////////////////////////////////////////
        // Retrieve the second move and verify (Jumps go anti-clockwise around).
        final Move secondMove = foundMoves.get(1);
        // This should have four jumps.
        assertEquals(4, secondMove.jumps.size());
        // Verify the jumps (First jump).
        assertEquals(new BoardPosition(4, 7), secondMove.jumps.get(0).from);
        assertEquals(new BoardPosition(6, 5), secondMove.jumps.get(0).to);
        assertEquals(new BoardPosition(5, 6), secondMove.jumps.get(0).jumped);
        // Second jump.
        assertEquals(new BoardPosition(6, 5), secondMove.jumps.get(1).from);
        assertEquals(new BoardPosition(4, 3), secondMove.jumps.get(1).to);
        assertEquals(new BoardPosition(5, 4), secondMove.jumps.get(1).jumped);
        // Third jump.
        assertEquals(new BoardPosition(4, 3), secondMove.jumps.get(2).from);
        assertEquals(new BoardPosition(2, 5), secondMove.jumps.get(2).to);
        assertEquals(new BoardPosition(3, 4), secondMove.jumps.get(2).jumped);
        // Fourth jump.
        assertEquals(new BoardPosition(2, 5), secondMove.jumps.get(3).from);
        assertEquals(new BoardPosition(4, 7), secondMove.jumps.get(3).to);
        assertEquals(new BoardPosition(3, 6), secondMove.jumps.get(3).jumped);
    }
}
