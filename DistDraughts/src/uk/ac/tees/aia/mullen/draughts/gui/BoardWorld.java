package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Point;

/**
 * Represents a draughts board in 2D space.
 * <p>
 * This simply represents a 2D board in memory rather than basing it on an
 * actual window panel as the latter can change sizes which messes the code
 * up.
 *
 * @author  Alex Mullen
 *
 */
public class BoardWorld {
    /** Holds the size of each square in units. */
    private final int squareSize;
    /** Holds the number of squares along this board has. */
    private final int boardWidth;
    /** Holds the number of squares downwards this board has. */
    private final int boardHeight;
    /**
     * Creates a new instance where each square is the specified number of
     * units in size and represents a board specified number of squares long
     * and tall.
     *
     * @param squareUnitSize  the unit size of each square
     * @param width           how many squares for the width
     * @param height          how many squares for the height
     */
    public BoardWorld(final int squareUnitSize, final int width,
            final int height) {
        squareSize = squareUnitSize;
        boardWidth = width;
        boardHeight = height;
    }
    /**
     * Gets the square unit size of this board.
     *
     * @return  the square size
     */
    public final int getSquareSize() {
        return squareSize;
    }
    /**
     * Gets the board width.
     *
     * @return  the board width
     */
    public final int getBoardWidth() {
        return boardWidth;
    }
    /**
     * Gets the board height.
     *
     * @return  the board height
     */
    public final int getBoardHeight() {
        return boardHeight;
    }
    /**
     * Gets the width of this world in units.
     *
     * @return  the width
     */
    public final int getWorldWidth() {
        return boardWidth * squareSize;
    }
    /**
     * Gets the height of this world in units.
     *
     * @return  the height
     */
    public final int getWorldHeight() {
        return boardHeight * squareSize;
    }
    /**
     * Converts board grid coordinates into world coordinates.
     * <p>
     * The returned coordinates represent the top-left of the square.
     *
     * @param x  the X grid coordinates (left-to-right)
     * @param y  the Y grid coordinates (top-to-bottom)
     * @return   the world coordinates in this world of the specified grid
     *           square
     */
    public final Point boardToWorldCoords(final int x, final int y) {
        return new Point(x * squareSize, y * squareSize);
    }
    /**
     * Converts world coordinates from this world into board grid coordinates.
     *
     * @param x  the X world coordinates (left-to-bottom)
     * @param y  the Y world coordinates (top-to-bottom)
     * @return   the board grid coordinates contained in the specified world
     * coordinates
     */
    public final Point worldToBoardCoords(final int x, final int y) {
        return new Point(x / squareSize, y / squareSize);
    }
}
