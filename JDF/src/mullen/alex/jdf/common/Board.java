package mullen.alex.jdf.common;

import java.util.Arrays;


/**
 * Represents a board for the game of draughts.
 * <p>
 * The flat arrays used for this can be accessed like a 2D array using the
 * following formula.
 * <p>
 * <code>y + (height * x)</code>
 *
 * @author  Alex Mullen
 *
 */
public class Board {
    /** The width of the board (left-to-right). */
    public final int width;
    /** The height of the board (top-to-bottom). */
    public final int height;
    /** The piece positions represented by a one-dimensional array. */
    public final Piece[] pieces;
    /** The cached position objects for each position on the board. */
    public final BoardPosition[] positions;
    /**
     * Creates a new draughts board that is of the specified dimensions.
     *
     * @param boardWidth              the width of the board (left-to-right)
     * @param boardHeight             the height of the board (top-to-bottom)
     *
     * @throws IllegalArgumentException  if <code>boardWidth</code> or
     *                                   <code>boardHeight</code> is less than
     *                                   <code>1</code>
     */
    public Board(final int boardWidth, final int boardHeight) {
        if (boardWidth < 1 || boardHeight < 1) {
            throw new IllegalArgumentException("board dimensions need to > 0");
        }
        width = boardWidth;
        height = boardHeight;
        final int boardArrayLength = width * height;
        pieces = new Piece[boardArrayLength];
        positions = new BoardPosition[boardArrayLength];
        for (int x = 0; x < width; x++) {
            final int heightByX = height * x;
            for (int y = 0; y < height; y++) {
                positions[y + heightByX] = new BoardPosition(x, y);
            }
        }
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
        positions = boardToCopy.positions;
        pieces = new Piece[boardToCopy.pieces.length];
        for (int i = 0; i < pieces.length; i++) {
            final Piece foundPiece = boardToCopy.pieces[i];
            if (foundPiece != null) {
                pieces[i] = new Piece(foundPiece);
            }
        }
    }
    /**
     * Gets a cached {@link BoardPosition} instance for the specified position
     * which is useful for limiting the amount of garbage produced when
     * generating moves.
     *
     * @param x  the X position (left-to-right)
     * @param y  the Y position (top-to-bottom)
     * @return   a <code>BoardPosition</code> instance for the position
     *
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     */
    public final BoardPosition getBoardPositionFor(final int x, final int y) {
        return positions[y + (height * x)];
    }
    /**
     * Gets the piece at the specified position.
     *
     * @param x  the X position (left-to-right)
     * @param y  the Y position (top-to-bottom)
     * @return   the piece at the specified position - this could be
     *           <code>null</code> if there is no piece at the specified
     *           position
     *
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     * @see #getPieceAt(BoardPosition)
     */
    public final Piece getPieceAt(final int x, final int y) {
        return pieces[y + (height * x)];
    }
    /**
     * Gets the piece at the position specified in the <code>position</code>
     * instance.
     *
     * @param position  the position
     * @return          the piece at the specified position - this could be
     *                  <code>null</code> if there is no piece at the specified
     *                  position
     *
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>position</code> is out of
     *                                         bounds on this board
     * @throws NullPointerException  if <code>position</code> is
     *                               <code>null</code>
     * @see #getPieceAt(int, int)
     */
    public final Piece getPieceAt(final BoardPosition position) {
        return pieces[position.y + (height * position.x)];
    }
    /**
     * Sets the piece at the specified position.
     *
     * @param x         the X position (left-to-right)
     * @param y         the Y position (top-to-bottom)
     * @param newPiece  the piece to place at the specified position
     *                  (this can be <code>null</code>)
     *
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     */
    public final void setPieceAt(final int x, final int y,
            final Piece newPiece) {
        pieces[y + (height * x)] = newPiece;
    }
    /**
     * Sets the piece at the specified position.
     *
     * @param position  the position
     * @param newPiece  the piece to place at the specified position
     *                  (this can be <code>null</code>)
     *
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     * @throws NullPointerException  if <code>position</code> is
     *                               <code>null</code>
     */
    public final void setPieceAt(final BoardPosition position,
            final Piece newPiece) {
        pieces[position.y + (height * position.x)] = newPiece;
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
     *
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>x</code> and <code>y</code>
     *                                         is out of bounds on this board
     */
    public final Piece setPieceAndGetAt(final int x, final int y,
            final Piece newPiece) {
        final int index = y + (height * x);
        final Piece previousPiece = pieces[index];
        pieces[index] = newPiece;
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
     *
     * @throws ArrayIndexOutOfBoundsException  if the location specified with
     *                                         <code>position</code> is out of
     *                                         bounds on this board
     * @throws NullPointerException  if <code>position</code> is
     *                               <code>null</code>
     */
    public final Piece setPieceAndGetAt(final BoardPosition position,
            final Piece newPiece) {
        final int index = position.y + (height * position.x);
        final Piece previousPiece = pieces[index];
        pieces[index] = newPiece;
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
        return pieces[y + (height * x)] != null;
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
    public final boolean isPositionWithinBounds(final int x, final int y) {
        return x >= 0 && x < width && y < height && y >= 0;
    }
    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(pieces);
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Board other = (Board) obj;
        if (width != other.width || height != other.height) {
            return false;
        }
        return Arrays.deepEquals(pieces, other.pieces);
    }
    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append("Board [width=").append(width).append(", height=")
          .append(height).append(']');
        for (int i = 0; i < pieces.length; i++) {
            final Piece foundPiece = pieces[i];
            if (foundPiece != null) {
                final BoardPosition pos = positions[i];
                sb.append("\n    (").append(pos.x).append(',')
                  .append(pos.y).append(") = ").append(foundPiece);
            }
        }
        return sb.toString();
    }
}
