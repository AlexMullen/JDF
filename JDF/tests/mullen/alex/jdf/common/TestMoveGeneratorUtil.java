package mullen.alex.jdf.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import static mullen.alex.jdf.common.MoveGeneratorUtil.*;
import static mullen.alex.jdf.common.Piece.*;

/**
 * Unit tests for {@link MoveGeneratorUtil}.
 *
 * @author  Alex Mullen
 */
public class TestMoveGeneratorUtil {
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findMoveAboveLeft(Board, BoardPosition,
     * Collection)} returns an expected above-left move.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveAboveLeft() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(1, 6);
        board.setPieceAt(piecePos, new Piece(LIGHT, UP));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveAboveLeft(board, piecePos, moves);
        // There should be only one move found.
        assertEquals(1, moves.size());
        final Move foundMove = moves.get(0);
        final BoardPosition expectedToPos = board.getBoardPositionFor(0, 5);
        assertEquals(piecePos, foundMove.from);
        assertEquals(expectedToPos, foundMove.to);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
    }
    /**
     * Tests {@link MoveGeneratorUtil#findMoveAboveLeft(Board, BoardPosition,
     * Collection)} works as expected when the piece
     * is on the left-most side which means no move should be returned.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveAboveLeftFromLeftMostSide() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(0, 7);
        board.setPieceAt(piecePos, new Piece(LIGHT, UP));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveAboveLeft(board, piecePos, moves);
        // No move should be found.
        assertEquals(0, moves.size());
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findMoveAboveRight(Board, BoardPosition,
     * Collection)} returns an expected above-right move.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveAboveRight() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(6, 5);
        board.setPieceAt(piecePos, new Piece(LIGHT, UP));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveAboveRight(board, piecePos, moves);
        // There should be only one move found.
        assertEquals(1, moves.size());
        final Move foundMove = moves.get(0);
        final BoardPosition expectedToPos = board.getBoardPositionFor(7, 4);
        assertEquals(piecePos, foundMove.from);
        assertEquals(expectedToPos, foundMove.to);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
    }
    /**
     * Tests {@link MoveGeneratorUtil#findMoveAboveRight(Board, BoardPosition,
     * Collection)} works as expected when the piece
     * is on the right-most side which means no move should be returned.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveAboveRightFromRightMostSide() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(7, 6);
        board.setPieceAt(piecePos, new Piece(LIGHT, UP));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveAboveRight(board, piecePos, moves);
        // No move should be found.
        assertEquals(0, moves.size());
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findMoveBottomLeft(Board, BoardPosition,
     * Collection)} returns an expected bottom-left move.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveBottomLeft() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(1, 0);
        board.setPieceAt(piecePos, new Piece(DARK, DOWN));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveBottomLeft(board, piecePos, moves);
        // There should be only one move found.
        assertEquals(1, moves.size());
        final Move foundMove = moves.get(0);
        final BoardPosition expectedToPos = board.getBoardPositionFor(0, 1);
        assertEquals(piecePos, foundMove.from);
        assertEquals(expectedToPos, foundMove.to);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
    }
    /**
     * Tests {@link MoveGeneratorUtil#findMoveBottomLeft(Board, BoardPosition,
     * Collection)} works as expected when the piece is on the left-most side
     * which means no move should be returned.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveBottomLeftFromLeftMostSide() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(0, 1);
        board.setPieceAt(piecePos, new Piece(DARK, DOWN));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveBottomLeft(board, piecePos, moves);
        // No move should be found.
        assertEquals(0, moves.size());
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findMoveBottomRight(Board, BoardPosition,
     * Collection)} returns an expected bottom-right move.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveBottomRight() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(6, 1);
        board.setPieceAt(piecePos, new Piece(DARK, DOWN));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveBottomRight(board, piecePos, moves);
        // There should be only one move found.
        assertEquals(1, moves.size());
        final Move foundMove = moves.get(0);
        final BoardPosition expectedToPos = board.getBoardPositionFor(7, 2);
        assertEquals(piecePos, foundMove.from);
        assertEquals(expectedToPos, foundMove.to);
        // There should be no jumps.
        assertEquals(0, foundMove.jumps.size());
    }
    /**
     * Tests {@link MoveGeneratorUtil#findMoveBottomRight(Board, BoardPosition,
     * Collection)} works as expected when the piece is on the right-most side
     * which means no move should be returned.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindMoveBottomRightFromRightMostSide() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(7, 2);
        board.setPieceAt(piecePos, new Piece(DARK, DOWN));
        // Generate the move.
        final List<Move> moves = new ArrayList<>();
        findMoveBottomRight(board, piecePos, moves);
        // No move should be found.
        assertEquals(0, moves.size());
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findJumpAboveLeft(Board, BoardPosition,
     * int, Collection)} returns an expected above-left jump.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindJumpAboveLeft() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition jumpFromPos = board.getBoardPositionFor(2, 7);
        final BoardPosition jumpOverPos = board.getBoardPositionFor(1, 6);
        final BoardPosition jumpLandPos = board.getBoardPositionFor(0, 5);
        board.setPieceAt(jumpFromPos, new Piece(LIGHT, UP));
        board.setPieceAt(jumpOverPos, new Piece(DARK, DOWN));
        // Generate the move.
        final List<Jump> jumps = new ArrayList<>();
        findJumpAboveLeft(board, jumpFromPos, LIGHT, jumps);
        // One jump should be found.
        assertEquals(1, jumps.size());
        final Jump foundJump = jumps.get(0);
        assertEquals(jumpFromPos, foundJump.from);
        assertEquals(jumpOverPos, foundJump.jumped);
        assertEquals(jumpLandPos, foundJump.to);
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findJumpAboveRight(Board, BoardPosition,
     * int, Collection)} returns an expected above-right jump.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindJumpAboveRight() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition jumpFromPos = board.getBoardPositionFor(4, 7);
        final BoardPosition jumpOverPos = board.getBoardPositionFor(5, 6);
        final BoardPosition jumpLandPos = board.getBoardPositionFor(6, 5);
        board.setPieceAt(jumpFromPos, new Piece(LIGHT, UP));
        board.setPieceAt(jumpOverPos, new Piece(DARK, DOWN));
        // Generate the move.
        final List<Jump> jumps = new ArrayList<>();
        findJumpAboveRight(board, jumpFromPos, LIGHT, jumps);
        // One jump should be found.
        assertEquals(1, jumps.size());
        final Jump foundJump = jumps.get(0);
        assertEquals(jumpFromPos, foundJump.from);
        assertEquals(jumpOverPos, foundJump.jumped);
        assertEquals(jumpLandPos, foundJump.to);
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findJumpBottomLeft(Board, BoardPosition,
     * int, Collection)} returns an expected bottom-left jump.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindJumpBottomLeft() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition jumpFromPos = board.getBoardPositionFor(3, 0);
        final BoardPosition jumpOverPos = board.getBoardPositionFor(2, 1);
        final BoardPosition jumpLandPos = board.getBoardPositionFor(1, 2);
        board.setPieceAt(jumpFromPos, new Piece(DARK, DOWN));
        board.setPieceAt(jumpOverPos, new Piece(LIGHT, UP));
        // Generate the move.
        final List<Jump> jumps = new ArrayList<>();
        findJumpBottomLeft(board, jumpFromPos, DARK, jumps);
        // One jump should be found.
        assertEquals(1, jumps.size());
        final Jump foundJump = jumps.get(0);
        assertEquals(jumpFromPos, foundJump.from);
        assertEquals(jumpOverPos, foundJump.jumped);
        assertEquals(jumpLandPos, foundJump.to);
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findJumpBottomRight(Board, BoardPosition,
     * int, Collection)} returns an expected bottom-right jump.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindJumpBottomRight() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition jumpFromPos = board.getBoardPositionFor(5, 0);
        final BoardPosition jumpOverPos = board.getBoardPositionFor(6, 1);
        final BoardPosition jumpLandPos = board.getBoardPositionFor(7, 2);
        board.setPieceAt(jumpFromPos, new Piece(DARK, DOWN));
        board.setPieceAt(jumpOverPos, new Piece(LIGHT, UP));
        // Generate the move.
        final List<Jump> jumps = new ArrayList<>();
        findJumpBottomRight(board, jumpFromPos, DARK, jumps);
        // One jump should be found.
        assertEquals(1, jumps.size());
        final Jump foundJump = jumps.get(0);
        assertEquals(jumpFromPos, foundJump.from);
        assertEquals(jumpOverPos, foundJump.jumped);
        assertEquals(jumpLandPos, foundJump.to);
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findFlyingMovesAboveLeft(Board,
     * BoardPosition, Collection)} returns expected moves.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingMovesAboveLeft() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(4, 7);
        board.setPieceAt(piecePos, new Piece(LIGHT, UP));
        // Generate the moves.
        final List<Move> moves = new ArrayList<>();
        findFlyingMovesAboveLeft(board, piecePos, moves);
        // Four moves should be found.
        assertEquals(4, moves.size());
        // Check each move is what we expect.
        int x = piecePos.x - 1;
        int y = piecePos.y - 1;
        for (final Move curMove : moves) {
            assertEquals(piecePos, curMove.from);
            assertEquals(board.getBoardPositionFor(x--, y--), curMove.to);
            // Each move should have no jumps.
            assertEquals(0, curMove.jumps.size());
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findFlyingMovesAboveRight(Board,
     * BoardPosition, Collection)} returns expected moves.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingMovesAboveRight() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(0, 7);
        board.setPieceAt(piecePos, new Piece(LIGHT, UP));
        // Generate the moves.
        final List<Move> moves = new ArrayList<>();
        findFlyingMovesAboveRight(board, piecePos, moves);
        // Seven moves should be found.
        assertEquals(7, moves.size());
        // Check each move is what we expect.
        int x = piecePos.x + 1;
        int y = piecePos.y - 1;
        for (final Move curMove : moves) {
            assertEquals(piecePos, curMove.from);
            assertEquals(board.getBoardPositionFor(x++, y--), curMove.to);
            // Each move should have no jumps.
            assertEquals(0, curMove.jumps.size());
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findFlyingMovesBottomLeft(Board,
     * BoardPosition, Collection)} returns expected moves.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingMovesBottomLeft() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(5, 0);
        board.setPieceAt(piecePos, new Piece(DARK, DOWN));
        // Generate the moves.
        final List<Move> moves = new ArrayList<>();
        findFlyingMovesBottomLeft(board, piecePos, moves);
        // Five moves should be found.
        assertEquals(5, moves.size());
        // Check each move is what we expect.
        int x = piecePos.x - 1;
        int y = piecePos.y + 1;
        for (final Move curMove : moves) {
            assertEquals(piecePos, curMove.from);
            assertEquals(board.getBoardPositionFor(x--, y++), curMove.to);
            // Each move should have no jumps.
            assertEquals(0, curMove.jumps.size());
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    /**
     * Tests {@link MoveGeneratorUtil#findFlyingMovesBottomRight(Board,
     * BoardPosition, Collection)} returns expected moves.
     */
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingMovesBottomRight() {
        // Setup the board.
        final Board board = new Board(8, 8);
        final BoardPosition piecePos = board.getBoardPositionFor(1, 0);
        board.setPieceAt(piecePos, new Piece(DARK, DOWN));
        // Generate the moves.
        final List<Move> moves = new ArrayList<>();
        findFlyingMovesBottomRight(board, piecePos, moves);
        // Six moves should be found.
        assertEquals(6, moves.size());
        // Check each move is what we expect.
        int x = piecePos.x + 1;
        int y = piecePos.y + 1;
        for (final Move curMove : moves) {
            assertEquals(piecePos, curMove.from);
            assertEquals(board.getBoardPositionFor(x++, y++), curMove.to);
            // Each move should have no jumps.
            assertEquals(0, curMove.jumps.size());
        }
    }
    ////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingJumpsAboveLeft() {
        fail("Not yet implemented");
    }
    ////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingJumpsAboveRight() {
        fail("Not yet implemented");
    }
    ////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingJumpsBottomLeft() {
        fail("Not yet implemented");
    }
    ////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("static-method")
    @Test
    public final void testFindFlyingJumpsBottomRight() {
        fail("Not yet implemented");
    }
}
