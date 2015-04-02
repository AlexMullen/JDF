package uk.ac.tees.aia.mullen.draughts.backend;


/**
 * Represents a board for the game of draughts.
 *
 * @author  Alex Mullen
 *
 */
public class Board {
    /** Holds the width of the board. */
    private final int width;
    /** Holds the height of the board. */
    private final int height;
    /** The board represented by a two-dimensional array. */
    private final Piece[][] board;
    /**
     * Creates a new draughts board that is of the specified dimensions.
     *
     * @param boardWidth   the width of the board (left-to-right)
     * @param boardHeight  the height of the board (top-to-bottom)
     *
     * @throws IllegalArgumentException  if <code>boardWidth</code> or
     *                                   <code>boardHeight</code> is less than
     *                                   <code>1</code>
     */
    public Board(final int boardWidth, final int boardHeight) {
        if (boardWidth < 1 || boardHeight < 1) {
            throw new IllegalArgumentException("eesshh");
        }
        width = boardWidth;
        height = boardHeight;
        board = new Piece[width][height];
    }
    /**
     * Gets the width of the board.
     * <p>
     * The width of the board goes from left-to-right in 2D space.
     *
     * @return  the width
     */
    public final int getWidth() {
        return width;
    }
    /**
     * Gets the height of the board.
     * <p>
     * The height of the board goes from top-to-bottom in 2D space.
     *
     * @return  the height
     */
    public final int getHeight() {
        return height;
    }
    /**
     * Gets the piece at the specified position.
     *
     * @param x  the X position (left-to-right)
     * @param y  the Y position (top-to-bottom)
     * @return   the piece at the specified position - this could be
     *           <code>null</code> if there is no piece at the specified
     *           position
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     * @see #getPieceAt(BoardPosition)
     */
    public final Piece getPieceAt(final int x, final int y) {
        return board[x][y];
    }
    /**
     * Gets the piece at the position specified in the <code>position</code>
     * instance.
     *
     * @param position  the position
     * @return          the piece at the specified position - this could be
     *                  <code>null</code> if there is no piece at the specified
     *                  position
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>position</code> is out of
     *                                         bounds on this board
     * @throws NullPointerException  if <code>position</code> is
     *                               <code>null</code>
     * @see #getPieceAt(int, int)
     */
    public final Piece getPieceAt(final BoardPosition position) {
        return getPieceAt(position.getX(), position.getY());
    }
    /**
     * Sets the piece at the specified position.
     *
     * @param x         the X position (left-to-right)
     * @param y         the Y position (top-to-bottom)
     * @param newPiece  the piece to place at the specified position
     *                  (this can be <code>null</code>)
     * @return          the piece that was replaced at the specified position
     *                  - this could be <code>null</code> if there was no piece
     *                  at the set position
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     */
    public final Piece setPieceAt(final int x, final int y,
            final Piece newPiece) {
        final Piece previousPiece = board[x][y];
        board[x][y] = newPiece;
        return previousPiece;
    }
    /**
     * Gets whether there is a piece at the specified position.
     *
     * @param x  the X position (left-to-right)
     * @param y  the Y position (top-to-bottom)
     * @return   <code>true</code> if there is a piece; <code>false</code> if
     *           there is no piece
     */
    public final boolean isPieceAt(final int x, final int y) {
        return getPieceAt(x, y) != null;
    }
    /**
     * Gets whether the specified position exists on this board and is not
     * past this board's bounds.
     *
     * @param x  the X position (left-to-right)
     * @param y  the Y position (top-to-bottom)
     * @return   <code>true</code> if the position is valid; <code>false</code>
     *           if it is not valid
     */
    public final boolean isPositionValid(final int x, final int y) {
        // A negative value is always invalid.
        if (x < 0 || y < 0) {
            return false;
        }
        if (x >= board.length || y >= board[0].length) {
            return false;
        }
        return true;
    }
    /**
     * Gets whether the specified Y position is a kings row.
     *
     * @param y  the Y position (top-to-bottom)
     * @return   <code>true</code> if the specified value is a kings row;
     *           <code>false</code> if it is not
     */
    public final boolean isKingsRow(final int y) {
        return y == 0 || y == (height - 1);
    }
    @Override
    public final String toString() {
        return "Board [width=" + width + ", height=" + height + "]";
    }
}
