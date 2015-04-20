package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

import uk.ac.tees.aia.mullen.draughts.common.BoardPosition;

/**
 * Represents a board square on a draughts board.
 *
 * @author  Alex Mullen
 *
 */
@SuppressWarnings("serial")
public class BoardSquare extends Rectangle {
    /** The image to use for light squares. */
    private static BufferedImage lightSquareImage;
    /** The image to use for dark squares. */
    private static BufferedImage darkSquareImage;
    /** Holds the colour of this square. */
    private Color squareColour;
    /** Holds the default colour of this square. */
    private final Color defaultColour;
    /** Holds the grid based board position of this square. */
    private final BoardPosition boardPosition;
    /** The virtual board world this square belongs in. */
    private final BoardWorld boardWorld;
    static {
        final URL lightImageURL =
                BoardSquare.class.getResource("resources/smooth-wood.jpg");
        if (lightImageURL != null) {
            try {
                lightSquareImage = ImageIO.read(lightImageURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        final URL darkImageURL =
                BoardSquare.class.getResource("resources/darker-wood.jpg");
        if (darkImageURL != null) {
            try {
                darkSquareImage = ImageIO.read(darkImageURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /**
     * Creates a new instance that is the size of a square in the specified
     * world, uses the given colour and represents the specified grid location
     * on a board.
     *
     * @param world     the world to use as a size reference
     * @param colour    the colour
     * @param position  the position this represents on the board
     */
    public BoardSquare(final BoardWorld world, final Color colour,
            final BoardPosition position) {
        boardWorld = Objects.requireNonNull(world);
        defaultColour = Objects.requireNonNull(colour);
        boardPosition = Objects.requireNonNull(position);
        squareColour = defaultColour;
        setSize(new Dimension(world.getSquareSize(), world.getSquareSize()));
        final Point worldPosition = world.boardToWorldCoords(
                boardPosition.getX(),
                boardPosition.getY());
        setLocation(worldPosition);
    }
    /**
     * Gets the colour of this square.
     *
     * @return  the colour
     */
    public final Color getColour() {
        return squareColour;
    }
    /**
     * Gets the default colour of this square.
     *
     * @return  the default colour of this square
     */
    public final Color getDefaultColour() {
        return defaultColour;
    }
    /**
     * Sets the colour of this square.
     *
     * @param colour  the colour to set it to
     */
    public final void setColour(final Color colour) {
        squareColour = Objects.requireNonNull(colour);
    }
    /**
     * Gets the board position this square represents.
     * <p>
     * This is the actual position on the board, not the position to render
     * this at.
     *
     * @return  the board position
     */
    public final BoardPosition getBoardPosition() {
        return boardPosition;
    }
    /**
     * Sets this square to its original default colour.
     */
    public final void resetColour() {
        squareColour = defaultColour;
    }
    /**
     * Draws this square to the specified graphics context.
     *
     * @param g  the graphics context
     */
    public final void draw(final Graphics2D g) {
        final Graphics2D newG = (Graphics2D) g.create();
        // Turn Anti-Aliasing on.
        newG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // We want highest rendering quality.
        newG.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        if (squareColour == Color.WHITE) {
            newG.drawImage(lightSquareImage, x, y, boardWorld.getSquareSize(),
                    boardWorld.getSquareSize(), null);
        } else {
            newG.drawImage(darkSquareImage, x, y, boardWorld.getSquareSize(),
                    boardWorld.getSquareSize(), null);
        }
        // Dispose of this context as we explicitly created it.
        newG.dispose();
    }
}
