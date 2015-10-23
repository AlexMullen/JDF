package mullen.alex.draughts.common;

import java.util.Arrays;


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
    /** Holds whether the near right square of either player is light. */
    private final boolean nearRightSquareLight;
    /** The board represented by a two-dimensional array. */
    private final Piece[][] board;
    /**
     * Creates a new draughts board that is of the specified dimensions.
     *
     * @param boardWidth              the width of the board (left-to-right)
     * @param boardHeight             the height of the board (top-to-bottom)
     * @param isNearRightSquareLight  whether the near right square of either
     *                                player is marked as the light square
     *
     * @throws IllegalArgumentException  if <code>boardWidth</code> or
     *                                   <code>boardHeight</code> is less than
     *                                   <code>1</code>
     */
    public Board(final int boardWidth, final int boardHeight,
            final boolean isNearRightSquareLight) {
        if (boardWidth < 1 || boardHeight < 1) {
            throw new IllegalArgumentException("eesshh");
        }
        width = boardWidth;
        height = boardHeight;
        nearRightSquareLight = isNearRightSquareLight;
        board = new Piece[width][height];
    }
    /**
     * Creates a new instance that is a deep-copy of the specified board.
     *
     * @param boardToCopy  the board to copy for this instance.
     *
     * @throws NullPointerException  if <code>boardToCopy</code> is
     *                               <code>null</code>
     */
    public Board(final Board boardToCopy) {
        width = boardToCopy.width;
        height = boardToCopy.height;
        nearRightSquareLight = boardToCopy.nearRightSquareLight;
        board = new Piece[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Piece foundPiece = boardToCopy.board[x][y];
                if (foundPiece != null) {
                    board[x][y] = new Piece(foundPiece);
                }
            }
        }
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
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     */
    public final void setPieceAt(final int x, final int y,
            final Piece newPiece) {
        board[x][y] = newPiece;
    }
    /**
     * Sets the piece at the specified position.
     *
     * @param position  the position
     * @param newPiece  the piece to place at the specified position
     *                  (this can be <code>null</code>)
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     * @throws NullPointerException  if <code>position</code> is
     *                               <code>null</code>
     */
    public final void setPieceAt(final BoardPosition position,
            final Piece newPiece) {
        board[position.getX()][position.getY()] = newPiece;
    }
    /**
     * Sets the piece at the specified position and returns the previous piece
     * at the position.
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
    public final Piece setPieceAndGetAt(final int x, final int y,
            final Piece newPiece) {
        final Piece previousPiece = board[x][y];
        board[x][y] = newPiece;
        return previousPiece;
    }
    /**
     * Sets the piece at the specified position and returns the previous piece
     * at the position.
     *
     * @param position  the position
     * @param newPiece  the piece to place at the specified position
     *                  (this can be <code>null</code>)
     * @return          the piece that was replaced at the specified position
     *                  - this could be <code>null</code> if there was no piece
     *                  at the set position
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>position</code> is out of
     *                                         bounds on this board
     * @throws NullPointerException  if <code>position</code> is
     *                               <code>null</code>
     */
    public final Piece setPieceAndGetAt(final BoardPosition position,
            final Piece newPiece) {
        return setPieceAndGetAt(position.getX(), position.getY(), newPiece);
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
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        return true;
    }
    /**
     * Gets whether the near-right square of either player
     * (top left and bottom right) is marked as a light square.
     * <p>
     * Note that an even board width is required for both near-right corners to
     * be the same.
     *
     * @return  <code>true</code> or <code>false</code>
     */
    public final boolean isNearRightSquareLight() {
        return nearRightSquareLight;
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
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(board);
        result = prime * result + height;
        result = prime * result + width;
        return result;
    }
    /**
     * Indicates whether two boards are equal to each other.
     * <p>
     * Two boards are equal if they both are the same size and each
     * corresponding position is equal.
     */
    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Board other = (Board) obj;
        if (width != other.width) {
            return false;
        }
        if (height != other.height) {
            return false;
        }
        if (!Arrays.deepEquals(board, other.board)) {
            return false;
        }
        return true;
    }
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Board [width=").append(width).append(", height=")
          .append(height).append("]");
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Piece foundPiece = board[x][y];
                if (foundPiece != null) {
                    sb.append("\n    (").append(x).append(",")
                      .append(y).append(") = ").append(foundPiece);
                }
            }
        }
        return sb.toString();
    }
}
