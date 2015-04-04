package uk.ac.tees.aia.mullen.draughts.english;

import java.util.Objects;

import uk.ac.tees.aia.mullen.draughts.backend.Board;
import uk.ac.tees.aia.mullen.draughts.backend.Game;
import uk.ac.tees.aia.mullen.draughts.backend.Move;
import uk.ac.tees.aia.mullen.draughts.backend.MoveFinder;
import uk.ac.tees.aia.mullen.draughts.backend.MovePerformer;
import uk.ac.tees.aia.mullen.draughts.backend.Piece;
import uk.ac.tees.aia.mullen.draughts.backend.Player;
import uk.ac.tees.aia.mullen.draughts.backend.Piece.MoveDirection;

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
    /** The move finder for getting legal moves this uses. */
    private final MoveFinder moveFinder;
    /** The move performer for performing moves this uses. */
    private final MovePerformer movePerformer;
    /** Holds whoever's turn it currently is. */
    private Player turnOwner;
    /** Indicates whether the game has ended or not. */
    private boolean ended;
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
        // Dark starts first.
        turnOwner = darkPieceOwner;
        board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        moveFinder = new EnglishDraughtsMoveFinder();
        movePerformer = new EnglishDraughtsMovePerformer();
        initPieces();
    }
    @Override
    public final void start() {
        turnOwner.onTurn(this);
    }
    @Override
    public final Board getBoard() {
        // Returns a copy.
        return new Board(board);
    }
    @Override
    public final Player getTurnOwner() {
        return turnOwner;
    }
    @Override
    public final MoveFinder getMoveFinder() {
        return moveFinder;
    }
    @Override
    public final MovePerformer getMovePerformer() {
        return movePerformer;
    }
    @Override
    public final void performMove(final Move move) {
        if (ended) {
            throw new IllegalStateException();
        }
        if (move.getJumps() != null) {
            System.out.println(move);
        }
        movePerformer.perform(move, board);
        if (moveFinder.findMoves(board, turnOwner).isEmpty()) {
            // They moved but have no moves left so the other player wins.
            ended = true;
            final Player winningPlayer = getOpponent(turnOwner);
            lightPieceOwner.onGameEnded(this, winningPlayer);
            darkPieceOwner.onGameEnded(this, winningPlayer);
        } else if (moveFinder.findMoves(
                board, getOpponent(turnOwner)).isEmpty()) {
            // There turn but have no moves left.
            ended = true;
            final Player winningPlayer = turnOwner;
            lightPieceOwner.onGameEnded(this, winningPlayer);
            darkPieceOwner.onGameEnded(this, winningPlayer);
        } else {
            turnOwner = getOpponent(turnOwner);
            turnOwner.onTurn(this);
        }
    }
    @Override
    public final Player getOpponent(final Player owner) {
        if (owner.equals(lightPieceOwner)) {
            return darkPieceOwner;
        } else {
            return lightPieceOwner;
        }
    }
    /**
     * Initialises the pieces and places them into their correct starting
     * positions.
     */
    private void initPieces() {
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
