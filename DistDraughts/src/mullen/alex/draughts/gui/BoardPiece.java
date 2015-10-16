package mullen.alex.draughts.gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import javax.imageio.ImageIO;

/**
 * Represents a board piece on a board.
 *
 * @author  Alex Mullen
 *
 */
@SuppressWarnings("serial")
public class BoardPiece extends Rectangle {
    /** How much is the size of a piece relative to a square. */
    private static final float PIECE_SIZE_TO_SQUARE_SIZE = 0.8f;
    /** The image to use for light pieces. */
    private static BufferedImage lightPieceImage;
    /** The image to use for dark pieces. */
    private static BufferedImage darkPieceImage;
    /** The image to use for crowned light pieces. */
    private static BufferedImage lightPieceCrownedImage;
    /** The image to use for crowned dark pieces. */
    private static BufferedImage darkPieceCrownedImage;
    static {
        final URL lightPieceImageURL =
                BoardPiece.class.getResource("resources/light-piece.png");
        if (lightPieceImageURL != null) {
            try {
                lightPieceImage = ImageIO.read(lightPieceImageURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    static {
        final URL darkPieceImageURL =
                BoardPiece.class.getResource("resources/dark-piece.png");
        if (darkPieceImageURL != null) {
            try {
                darkPieceImage = ImageIO.read(darkPieceImageURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    static {
        final URL lightPieceCrownedImageURL =
                BoardPiece.class.getResource(
                        "resources/light-piece-crowned.png");
        if (lightPieceCrownedImageURL != null) {
            try {
                lightPieceCrownedImage =
                        ImageIO.read(lightPieceCrownedImageURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    static {
        final URL darkPieceCrownedImageURL =
                BoardPiece.class.getResource(
                        "resources/dark-piece-crowned.png");
        if (darkPieceCrownedImageURL != null) {
            try {
                darkPieceCrownedImage = ImageIO.read(darkPieceCrownedImageURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    /** Holds the colour of this piece. */
    private final Color pieceColour;
    /** Determines whether this piece is crowned. */
    private boolean crowned;
    /** Holds the alpha amount for this piece. */
    private float alpha = 1.0f;
    /**
     * Creates a new instance that belongs on the specified board world at the
     * initial board grid position set to the specified colour.
     *
     * @param world      the world
     * @param colour     the colour of the piece
     * @param isCrowned  whether the piece is crowned or not
     */
    public BoardPiece(final BoardWorld world, final Color colour,
            final boolean isCrowned) {
        pieceColour = Objects.requireNonNull(colour);
        crowned = isCrowned;
        final int size =
                (int) (world.getSquareSize() * PIECE_SIZE_TO_SQUARE_SIZE);
        setSize(size, size);
    }
    /**
     * Gets the colour of this piece.
     *
     * @return  the colour
     */
    public final Color getColour() {
        return pieceColour;
    }
    /**
     * Gets whether this piece is crowned.
     *
     * @return  <code>true</code> if crowned; <code>false</code> if not
     */
    public final boolean isCrowned() {
        return crowned;
    }
    /**
     * Sets this piece as crowned.
     */
    public final void crown() {
        crowned = true;
    }
    /**
     * Gets the alpha colour value of this piece.
     *
     * @return  the alpha value
     */
    public final float getAlpha() {
        return alpha;
    }
    /**
     * Sets the alpha colour value of this piece.
     *
     * @param value  the new alpha value
     */
    public final void setAlpha(final float value) {
        alpha = value;
    }
    /**
     * Draws this square to the specified graphics context.
     *
     * @param g  the graphics context
     */
    public final void draw(final Graphics2D g) {
        final Graphics2D newG = (Graphics2D) g.create();
        // Anti-Aliasing
        newG.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // Quality rendering.
        newG.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        // Set the alpha.
        newG.setComposite(
                AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        newG.setColor(pieceColour);
        if (crowned) {
            if (pieceColour == Color.WHITE) {
                newG.drawImage(
                        lightPieceCrownedImage, x, y, width, height, null);
            } else {
                newG.drawImage(
                        darkPieceCrownedImage, x, y, width, height, null);
            }
        } else {
            if (pieceColour == Color.WHITE) {
                newG.drawImage(lightPieceImage, x, y, width, height, null);
            } else {
                newG.drawImage(darkPieceImage, x, y, width, height, null);
            }
        }
        // Dispose of this context as we explicitly created it.
        newG.dispose();
    }
}
