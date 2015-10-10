package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.MouseInputListener;

import uk.ac.tees.aia.mullen.draughts.common.ArtificialPlayer;
import uk.ac.tees.aia.mullen.draughts.common.Board;
import uk.ac.tees.aia.mullen.draughts.common.BoardPosition;
import uk.ac.tees.aia.mullen.draughts.common.Game;
import uk.ac.tees.aia.mullen.draughts.common.Jump;
import uk.ac.tees.aia.mullen.draughts.common.Move;
import uk.ac.tees.aia.mullen.draughts.common.Piece;

/**
 * Represents an interactive game display for controlling and viewing the
 * output of a game.
 *
 * @author  Alex Mullen
 *
 */
@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseInputListener {
    /** How many units each square is in the virtual board world. */
    private static final int BOARDWORLD_SQUARE_SIZE = 100;
    /** The minimum time in ms AI moves will be delayed before displaying. */
    private static final int MINIMUM_AI_THINK_TIME = 1000;
    /** The background image to render behind the board. */
    private static BufferedImage backgroundImage;
    static {
        final URL imageURL =
                GamePanel.class.getResource("resources/background.jpg");
        if (imageURL != null) {
            try {
                backgroundImage = ImageIO.read(imageURL);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /** Holds the list of board squares to render. */
    private final List<BoardSquare> boardSquares;
    /** Holds the list of drawable animators that need something rendering. */
    private final List<DrawableAnimator> drawableAnimations;
    /** Holds a map of pieces with the key being the board position of each. */
    private final Map<BoardPosition, BoardPiece> boardPieces;
    /** The game model context. */
    private final Game gameModel;
    /** The board world for scaling everything independently of display size. */
    private final BoardWorld boardWorld;

    /** The current piece being dragged - <code>null</code> if none. */
    private BoardPiece draggingPiece;
    /** The board grid position the current dragging piece was dragged from. */
    private BoardPosition draggingPieceFromPosition;
    /** The position offset the mouse is to the piece it is dragging. */
    private Point dragMouseOffset;
    /** Indicates whether the current turn is for a human. */
    private boolean humanTurn;
    /** Holds if this panel is active or not. */
    private boolean active;
    /**
     * Creates a new instance using the specified game.
     *
     * @param game  the game
     */
    public GamePanel(final Game game) {
        gameModel = game;

        boardSquares = new ArrayList<>();
        boardPieces = new HashMap<>();
        drawableAnimations = new ArrayList<>();
        addMouseListener(this);
        addMouseMotionListener(this);

        final Board board = gameModel.getBoard();
        boardWorld = new BoardWorld(BOARDWORLD_SQUARE_SIZE, board.getWidth(),
                board.getHeight());
        Color currentColour = Color.WHITE;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                final Piece foundPiece = board.getPieceAt(x, y);
                if (foundPiece != null) {
                    Color pieceColour;
                    if (foundPiece.getOwner()
                            == gameModel.getDarkPlayer()) {
                        pieceColour = Color.BLACK;
                    } else {
                        pieceColour = Color.WHITE;
                    }
                    final BoardPiece newPiece = new BoardPiece(boardWorld,
                            pieceColour, foundPiece.isCrowned());
                    boardPieces.put(new BoardPosition(x, y), newPiece);
                    // Position the piece.
                    newPiece.setLocation(getCentrePositionAt(x, y, newPiece));
                }
                boardSquares.add(
                        new BoardSquare(boardWorld, currentColour,
                                new BoardPosition(x, y)));
                currentColour = nextAlternatingSquareColour(currentColour);
            }
            currentColour = nextAlternatingSquareColour(currentColour);
        }
    }
    /**
     * Starts the interactive game display.
     */
    public final void start() {
        active = true;
        nextTurn();
    }
    /**
     * Stops the interactive game display.
     */
    public final void stop() {
        active = false;
    }
    /**
     * Invoked after a turn was made so that the next turn can be configured or
     * requested.
     */
    private void nextTurn() {
        // First make sure this panel is activated.
        if (!active) {
            return;
        }
        // Check for end game.
        if (gameModel.getResult() != null) {
            // Prevent any more human input.
            humanTurn = false;
            // Display a congratulations message if the winner is not an AI.
            if (!gameModel.getResult().getWinner().isArtificial()) {
                JOptionPane.showMessageDialog(getParent(), "Congratulations you won!");
            } else if (!gameModel.getOpponent(
                    gameModel.getResult().getWinner()).isArtificial()) {
                // The loser is not an AI so display a bad luck message.
                JOptionPane.showMessageDialog(getParent(), "Better luck next time.");
            }
        } else if (gameModel.getTurnOwner().isArtificial()) {
            // The current turn is for an artificial player.
            // Prevent any human input.
            humanTurn = false;
            /*
             * Set the cursor to a wait cursor to indicate to the user that
             * the AI is thinking.
             */
            setCursor(new Cursor(Cursor.WAIT_CURSOR));
            final ArtificialPlayer aiPlayer =
                    (ArtificialPlayer) gameModel.getTurnOwner();
            final SwingWorker<Move, Void> aiTask =
                    new SwingWorker<Move, Void>() {
                @Override
                protected Move doInBackground() throws Exception {
                    final long timeStarted = System.currentTimeMillis();
                    final Move aiMove = aiPlayer.getMove(gameModel);
                    final long timeElapsed =
                            (System.currentTimeMillis() - timeStarted);
                    if (timeElapsed < MINIMUM_AI_THINK_TIME) {
                        // Simulated delay of at least a minimum time.
                        Thread.sleep(MINIMUM_AI_THINK_TIME - timeElapsed);
                    }
                    return aiMove;
                }
                @Override
                protected void done() {
                    try {
                        final Move aiMove = get();
                        gameModel.performMove(aiMove);
                        // Update our displayable model.
                        final BoardPiece movedPiece =
                                boardPieces.get(aiMove.getFrom());
                        boardPieces.put(aiMove.getTo(),
                                boardPieces.remove(aiMove.getFrom()));
                        // Check if the move crowned a piece.
                        if (gameModel.getBoard().getPieceAt(aiMove.getTo())
                                .isCrowned() && !movedPiece.isCrowned()) {
                            // The move made the piece become crowned.
                            movedPiece.crown();
                        }
                        // Reset the cursor back to the default.
                        setCursor(Cursor.getDefaultCursor());
                        // Display and animate the move performed.
                        displayAiMove(aiMove, new AnimatorContinuation() {
                            @Override
                            public void onAnimationEnded(
                                    final Animator animator) {
                                /*
                                 * After the animation finishes perform the
                                 * next game turn.
                                 */
                                nextTurn();
                            }
                        });
                    } catch (InterruptedException | ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            aiTask.execute();
        } else {
            // Let the user move pieces.
            humanTurn = true;
        }
    }
    /**
     * Gets the next alternative square colour for a chequered board.
     *
     * @param current  the current set square colour
     * @return         the next square colour
     */
    private static Color nextAlternatingSquareColour(final Color current) {
        if (current == Color.BLACK) {
            return Color.WHITE;
        } else {
            return Color.BLACK;
        }
    }
    /**
     * Displays an AI move.
     *
     * @param move          the move
     * @param continuation  the continuation to run when the move animation
     *                      finishes
     */
    private void displayAiMove(final Move move,
            final AnimatorContinuation continuation) {
        final BoardPiece movedPiece = boardPieces.get(move.getTo());
        if (move.getJumps().isEmpty()) {
            // Animation to move and centre the piece.
            final Animator moveAnimation = new SmoothlyMovePieceAnimation(
                    GamePanel.this, movedPiece,
                    getCentrePositionAt(move.getTo().getX(),
                            move.getTo().getY(), movedPiece),
                    new AnimatorContinuation() {
                        @Override
                        public void onAnimationEnded(final Animator animator) {
                            movedPiece.height -= 10;
                            movedPiece.width -= 10;
                            nextTurn();
                        }
                    });

            movedPiece.height += 10;
            movedPiece.width += 10;

            moveAnimation.start();
        } else {
            /*
             * Remove jumped pieces and add them to a separate list for fading
             * and also calculate the centred positions for the moving piece to
             * land onto visually.
             */
            final List<BoardPiece> jumpedPieces = new ArrayList<>();
            final List<Point> centredLandPositions = new ArrayList<>();
            for (final Jump jump : move.getJumps()) {
                jumpedPieces.add(boardPieces.remove(jump.getJumped()));
                centredLandPositions.add(
                        getCentrePositionAt(jump.getTo().getX(),
                                jump.getTo().getY(), movedPiece));
            }
            // ... and fade out each piece in jump order.
            final DrawableAnimator fadeAnimation =
                    new MultiPieceFadeOutAnimation(GamePanel.this, jumpedPieces,
                            250, 750, new AnimatorContinuation() {
                        @Override
                        public void onAnimationEnded(final Animator animator) {
                            drawableAnimations.remove(animator);
                        }
            });
            drawableAnimations.add(fadeAnimation);
            fadeAnimation.start();

            // Animation to move and centre the piece.
            final PieceJumpAnimation moveAnimation =
                    new PieceJumpAnimation(GamePanel.this, movedPiece,
                            centredLandPositions, continuation);
            moveAnimation.start();
        }
    }
    /**
     * Converts display coordinates of the logical display into normalised
     * board world coordinates.
     *
     * @param viewCoords  the display coordinates
     * @return            the equivalent board world coordinates
     */
    private Point viewCoordinatesToWorldCoordinates(final Point viewCoords) {
        // Calculate the scale used.
        final double scale = Math.min(
                ((double) getWidth() / (double) boardWorld.getWorldWidth()),
                ((double) (getHeight() - 50)
                        / (double) boardWorld.getWorldHeight()));
        // Calculate the scaled offsets to take into account the centred board.
        final int scaledGridOffsetX =
                (int) ((getWidth() - (boardWorld.getWorldWidth() * scale)) / 2);
        final int scaledGridOffsetY =
                (int) ((getHeight()
                        - (boardWorld.getWorldHeight() * scale)) / 2);
        // Calculate the world board position.
        final int worldPosX =
                (int) ((viewCoords.x - scaledGridOffsetX) / scale);
        final int worldPosY =
                (int) ((viewCoords.y - scaledGridOffsetY) / scale);
        // Calculate the grid position clicked.
        return new Point(worldPosX, worldPosY);
    }
    /**
     * Gets the world coordinates for centre positioning a piece on a board
     * square.
     *
     * @param gridX  the X position of the square
     * @param gridY  the Y position of the square
     * @param piece  the piece to position
     * @return       the centre position for the specified piece at the given
     *               position
     */
    private Point getCentrePositionAt(final int gridX, final int gridY,
            final BoardPiece piece) {
        final Point pieceCentrePosition =
                boardWorld.boardToWorldCoords(gridX, gridY);
        final int squareSize = boardWorld.getSquareSize();
        pieceCentrePosition.x += (squareSize - piece.width) / 2;
        pieceCentrePosition.y += (squareSize - piece.height) / 2;
        return pieceCentrePosition;
    }
    /**
     * Performs the rendering.
     *
     * @param g  the graphics context to render to
     */
    private void paintComponent(final Graphics2D g) {
        // Draw background.
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        }
        // Calculate the scale for the board and pieces.
        // (deduct from the height a little to add a small margin).
        final double scale = Math.min(
                ((double) getWidth() / (double) boardWorld.getWorldWidth()),
                ((double) (getHeight() - 50)
                        / (double) boardWorld.getWorldHeight()));
        // Calculate the scaled offsets so we can centre align the grid.
        final int scaledGridOffsetX =
                (int) ((getWidth() - (boardWorld.getWorldWidth() * scale)) / 2);
        final int scaledGridOffsetY =
                (int) ((getHeight()
                        - (boardWorld.getWorldHeight() * scale)) / 2);
        g.scale(scale, scale);
        g.translate(scaledGridOffsetX / scale, scaledGridOffsetY / scale);
        // Draw squares.
        for (final BoardSquare square : boardSquares) {
            square.draw(g);
        }
        // Draw pieces.
        for (final BoardPiece piece : boardPieces.values()) {
            piece.draw(g);
        }
        // Render any drawable animations.
        for (final DrawableAnimator animator : drawableAnimations) {
            animator.draw(g);
        }
        // Render any dragging piece last so that it is on top.
        if (draggingPiece != null) {
            draggingPiece.draw(g);
        }
    }
    @Override
    protected final void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // We require a Graphics2D context.
        paintComponent((Graphics2D) g);
    }
    @Override
    public final void mousePressed(final MouseEvent e) {
        /*
         * Check if it is a human players turn so that they are allowed to move
         * pieces and that a piece is not currently picked up.
         */
        if (!SwingUtilities.isLeftMouseButton(e) || !humanTurn
                || draggingPiece != null) {
            return;
        }
        final Point worldLoc = viewCoordinatesToWorldCoordinates(e.getPoint());
        for (final Entry<BoardPosition, BoardPiece> pieceEntry
                : boardPieces.entrySet()) {
            /*
             * Check if a piece was clicked and the piece is owned by a
             * non-artificial player (Most likely a human but you never know...)
             */
            if (pieceEntry.getValue().contains(worldLoc.x, worldLoc.y)
                    && !gameModel.getBoard().getPieceAt(
                            pieceEntry.getKey()).getOwner().isArtificial()) {
                /*
                 * Do not allow the piece to be picked up if it is not its
                 * players' turn.
                 */
                if (gameModel.getBoard().getPieceAt(
                        pieceEntry.getKey()).getOwner()
                                != gameModel.getTurnOwner()) {
                    break;
                }
                draggingPiece = pieceEntry.getValue();
                draggingPieceFromPosition = pieceEntry.getKey();
                /*
                 * To stop the top left part of the piece snapping to the mouse
                 * cursor position.
                 */
                dragMouseOffset =
                        new Point(worldLoc.x - draggingPiece.x,
                                worldLoc.y - draggingPiece.y);
                /*
                 * Increase the size of the piece to simulate it being picked
                 * up.
                 */
                draggingPiece.width += 10;
                draggingPiece.height += 10;
                /*
                 * We found the selected piece so no point in searching the
                 * remaining pieces.
                 */
                break;
            }
        }
    }
    @Override
    public final void mouseReleased(final MouseEvent e) {
        if (draggingPiece == null) {
            return;
        }
        int currentSquareIntersectionSize = 0;
        BoardSquare currentSquare = null;
        // Find which square the dragged piece landed on.
        for (final BoardSquare square : boardSquares) {
            if (draggingPiece.intersects(square)) {
                // It landed on this one but need to find out by how much?
                final Rectangle intersection =
                        draggingPiece.intersection(square);
                final int intersectionArea =
                        intersection.height * intersection.width;
                // Is this the current largest intersected square?
                if (intersectionArea > currentSquareIntersectionSize) {
                    currentSquare = square;
                    currentSquareIntersectionSize = intersectionArea;
                }
            }
        }

        /*
         * Shrink the piece back to its original size to simulate it being
         * dropped.
         */
        draggingPiece.width -= 10;
        draggingPiece.height -= 10;

        /*
         * Check if it properly covered a square to determine which one to place
         * it on.
         */
        if (currentSquare == null) {
            /*
             * It was not dropped on a square or it is not clear which square
             * they wanted it on so reset it back to its original position.
             */
            draggingPiece.setLocation(getCentrePositionAt(
                    draggingPieceFromPosition.getX(),
                    draggingPieceFromPosition.getY(), draggingPiece));
        } else {
            final List<Move> validMoves =
                    gameModel.getMoveGenerator().findMoves(
                            gameModel.getBoard(), gameModel.getTurnOwner());
            final Move move = findMove(draggingPieceFromPosition,
                    currentSquare.getBoardPosition(), validMoves);
            if (move == null) {
                /*
                 * It was not a valid move so reset it back to its original
                 * position.
                 */
                draggingPiece.setLocation(
                        getCentrePositionAt(draggingPieceFromPosition.getX(),
                        draggingPieceFromPosition.getY(), draggingPiece));
                /*
                 * Shake the pieces that can move if they did not drop it back
                 * down on its original position.
                 */
                if (!draggingPieceFromPosition.equals(
                        currentSquare.getBoardPosition())) {
                    /*
                     * TODO Bug if the user spams bad positions - these will
                     * shake hard and out of line
                     */
                    for (final Move pieceThatCanMove : validMoves) {
                        new PieceShakeAnimation(this, boardPieces.get(
                                pieceThatCanMove.getFrom())).start();
                    }
                }
            } else {
                // Perform the move.
                gameModel.performMove(move);
                boardPieces.put(currentSquare.getBoardPosition(),
                        boardPieces.remove(draggingPieceFromPosition));
                if (gameModel.getBoard().getPieceAt(
                        currentSquare.getBoardPosition()).isCrowned()
                        && !draggingPiece.isCrowned()) {
                    // The move resulted in the piece becoming crowned.
                    draggingPiece.crown();
                }
                // Smoothly move the piece to the centre of the square.
                final Animator moveAnimation =
                        new SmoothlyMovePieceAnimation(this, draggingPiece,
                                getCentrePositionAt(
                                        currentSquare.getBoardPosition().getX(),
                                        currentSquare.getBoardPosition().getY(),
                                        draggingPiece), null);
                moveAnimation.start();
                if (!move.getJumps().isEmpty()) {
                    /*
                     * Remove jumped pieces and add them to a separate list for
                     * fading.
                     */
                    final List<BoardPiece> jumpedPieces =
                            removeAndGetJumpedPieces(move.getJumps());
                    // ... and fade out each piece in jump order.
                    final DrawableAnimator fadeAnimation =
                            new MultiPieceFadeOutAnimation(GamePanel.this,
                                    jumpedPieces, 250, 750,
                                    new AnimatorContinuation() {
                                @Override
                                public void onAnimationEnded(
                                        final Animator animator) {
                                    drawableAnimations.remove(animator);
                                }
                    });
                    drawableAnimations.add(fadeAnimation);
                    fadeAnimation.start();
                }
                nextTurn();
            }
        }
        // Release the piece and repaint the panel.
        draggingPiece = null;
        dragMouseOffset = null;
        draggingPieceFromPosition = null;
        repaint();
    }
    /**
     * Finds a move in a list of moves with a given source and destination
     * position.
     *
     * @param from        the source position
     * @param to          the destination position
     * @param validMoves  the list of valid moves
     * @return            the first found move or <code>null</code> if no move
     *                    was found
     */
    private static Move findMove(final BoardPosition from,
            final BoardPosition to, final List<Move> validMoves) {
        for (final Move move : validMoves) {
            if (move.getFrom().equals(from) && move.getTo().equals(to)) {
                return move;
            }
        }
        return null;
    }
    /**
     * Remove any jumped pieces specified from out display model and return
     * them.
     *
     * @param jumps  the jumps that were made
     * @return       any pieces that were jumped over
     */
    private List<BoardPiece> removeAndGetJumpedPieces(final List<Jump> jumps) {
        final List<BoardPiece> jumpedPieces = new ArrayList<>();
        for (final Jump jump : jumps) {
            jumpedPieces.add(boardPieces.remove(jump.getJumped()));
        }
        return jumpedPieces;
    }
    @Override
    public final void mouseDragged(final MouseEvent e) {
        if (draggingPiece == null) {
            return;
        }
        final Point worldLoc = viewCoordinatesToWorldCoordinates(e.getPoint());
        // Take into account the position of the mouse relative to the piece.
        worldLoc.x -= dragMouseOffset.x;
        worldLoc.y -= dragMouseOffset.y;
        // Move the piece to its new position.
        draggingPiece.setLocation(worldLoc);
        repaint();
    }
    @Override
    public void mouseMoved(final MouseEvent e) {
        // Not used.
    }
    @Override
    public void mouseClicked(final MouseEvent e) {
        // Not used.
    }
    @Override
    public void mouseEntered(final MouseEvent e) {
        // Not used.
    }
    @Override
    public void mouseExited(final MouseEvent e) {
        // Not used.
    }
}
