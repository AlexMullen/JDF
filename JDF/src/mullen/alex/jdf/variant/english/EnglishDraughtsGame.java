package mullen.alex.jdf.variant.english;

import java.util.Objects;

import mullen.alex.jdf.common.Board;
import mullen.alex.jdf.common.BoardPattern;
import mullen.alex.jdf.common.CheckeredBoardPattern;
import mullen.alex.jdf.common.Game;
import mullen.alex.jdf.common.Move;
import mullen.alex.jdf.common.MoveGenerator;
import mullen.alex.jdf.common.MovePerformer;
import mullen.alex.jdf.common.Piece;
import mullen.alex.jdf.common.Player;
import mullen.alex.jdf.common.Piece.MoveDirection;

/**
 * A draughts game that follows the rules and conventions of English Draughts.
 *
 * @author  Alex Mullen
 *
 */
public class EnglishDraughtsGame extends Game {
    /** The width of the board. */
    private static final int BOARD_WIDTH = 8;
    /** The height of the board. */
    private static final int BOARD_HEIGHT = 8;
    /** Holds the owner of the dark pieces. */
    private final Player darkPieceOwner;
    /** Holds the owner of the light pieces. */
    private final Player lightPieceOwner;
    /** Holds the board for this game. */
    private final Board board;
    /** Holds the board pattern for this game. */
    private final BoardPattern boardPattern;
    /** The move generator for getting legal moves this uses. */
    private final MoveGenerator moveGenerator;
    /** The move performer for performing moves this uses. */
    private final MovePerformer movePerformer;
    /** Holds whoever's turn it currently is. */
    private Player turnOwner;
    /** Holds the result of the game when it has ended. */
    private GameResult result;
    /**
     * Creates a new instance and associates the specified players with the
     * light and dark pieces respectively.
     *
     * @param light  the owner of the light pieces
     * @param dark   the owner of the dark pieces
     */
    public EnglishDraughtsGame(final Player light, final Player dark) {
        super();
        lightPieceOwner = Objects.requireNonNull(light);
        darkPieceOwner = Objects.requireNonNull(dark);
        if (lightPieceOwner.equals(darkPieceOwner)) {
            throw new IllegalArgumentException("vs themself?");
        }
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        boardPattern = new CheckeredBoardPattern(BoardPattern.WHITE_SQUARE,
                BOARD_WIDTH, BOARD_HEIGHT);
        moveGenerator = new EnglishDraughtsMoveGenerator();
        movePerformer = new EnglishDraughtsMovePerformer();
        // Dark moves first.
        turnOwner = darkPieceOwner;
        initPieces();
    }
    @Override
    public final Board getBoard() {
        // Returns a copy.
        return new Board(board);
    }
    @Override
    public final BoardPattern getBoardPattern() {
        return boardPattern;
    }
    @Override
    public final Player getTurnOwner() {
        return turnOwner;
    }
    @Override
    public final Player getDarkPlayer() {
        return darkPieceOwner;
    }
    @Override
    public final Player getLightPlayer() {
        return lightPieceOwner;
    }
    @Override
    public final MoveGenerator getMoveGenerator() {
        return moveGenerator;
    }
    @Override
    public final MovePerformer getMovePerformer() {
        return movePerformer;
    }
    @Override
    public final Player getOpponent(final Player owner) {
        if (owner.equals(lightPieceOwner)) {
            return darkPieceOwner;
        } else {
            return lightPieceOwner;
        }
    }
    @Override
    public final GameResult getResult() {
        return result;
    }
    @Override
    public final void performMove(final Move move) {
        // No more moves allowed if game over.
        if (result != null) {
            throw new IllegalStateException();
        }
        movePerformer.perform(move, board);
        // Next player's turn.
        turnOwner = getOpponent(turnOwner);
        // Check if the next player has any moves left.
        if (!moveGenerator.hasAnyMoves(board, turnOwner)) {
         // Their turn but they have no moves left so the opposing player wins.
            result = new GameResult(getOpponent(turnOwner));
        }
    }
    /**
     * Initialises the pieces and places them into their correct starting
     * positions.
     */
    private void initPieces() {
//      board.setPieceAt(7, 4, new Piece(lightPieceOwner, MoveDirection.BOTH));
//
//      board.setPieceAt(0, 3, new Piece(darkPieceOwner, MoveDirection.BOTH));
//      board.setPieceAt(0, 5, new Piece(darkPieceOwner, MoveDirection.BOTH));

        board.setPieceAt(1, 0, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(3, 0, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(5, 0, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(7, 0, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(0, 1, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(2, 1, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(4, 1, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(6, 1, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(1, 2, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(3, 2, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(5, 2, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(7, 2, new Piece(darkPieceOwner, MoveDirection.DOWN));
        board.setPieceAt(0, 5, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(2, 5, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(4, 5, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(6, 5, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(1, 6, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(3, 6, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(5, 6, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(7, 6, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(0, 7, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(2, 7, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(4, 7, new Piece(lightPieceOwner, MoveDirection.UP));
        board.setPieceAt(6, 7, new Piece(lightPieceOwner, MoveDirection.UP));
    }
}
