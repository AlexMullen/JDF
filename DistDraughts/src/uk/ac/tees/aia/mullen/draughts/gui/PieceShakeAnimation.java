package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.swing.Timer;

/**
 * An animator that makes a piece shake.
 *
 * @author  Alex Mullen
 *
 */
public class PieceShakeAnimation implements Animator, ActionListener {
    /** Holds the interval in ms between each piece shake. */
    private static final int SHAKE_INTERVAL = 20;
    /** Holds the number of times to shake the piece. */
    private static final int SHAKE_COUNT = 20;
    /** The board panel to repaint every frame. */
    private final GamePanel gamePanel;
    /** The board piece to shake. */
    private final BoardPiece boardPiece;
    /** The timer to use for performing the animation. */
    private final Timer timer;
    /** The saved original piece position. */
    private final Point originalPosition;
    /** The queue of positions to move the piece to next. */
    private final Queue<Point> positionsToMoveTo;
    /** Holds the number of shakes remaining. */
    private int shakesRemaining = SHAKE_COUNT;
    /**
     * Creates a new instance.
     *
     * @param panel  the panel to repaint every frame
     * @param piece  the piece to shake
     */
    public PieceShakeAnimation(final GamePanel panel, final BoardPiece piece) {
        gamePanel = panel;
        boardPiece = piece;
        originalPosition = piece.getLocation();
        positionsToMoveTo = new ArrayDeque<>();

        positionsToMoveTo.add(new Point(piece.x - 1, piece.y - 1));
        positionsToMoveTo.add(new Point(piece.x - 2, piece.y - 2));
        positionsToMoveTo.add(new Point(piece.x - 3, piece.y - 3));

        positionsToMoveTo.add(new Point(piece.x + 1, piece.y + 1));
        positionsToMoveTo.add(new Point(piece.x + 2, piece.y + 2));
        positionsToMoveTo.add(new Point(piece.x + 3, piece.y + 3));

        positionsToMoveTo.add(new Point(piece.x + 1, piece.y - 1));
        positionsToMoveTo.add(new Point(piece.x + 2, piece.y - 2));
        positionsToMoveTo.add(new Point(piece.x + 3, piece.y - 3));

        positionsToMoveTo.add(new Point(piece.x - 1, piece.y + 1));
        positionsToMoveTo.add(new Point(piece.x - 2, piece.y + 2));
        positionsToMoveTo.add(new Point(piece.x - 3, piece.y + 3));

        timer = new Timer(SHAKE_INTERVAL, this);
        timer.setRepeats(true);
    }
    @Override
    public final void start() {
        timer.start();
    }
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (shakesRemaining == 0) {
            timer.stop();
            boardPiece.setLocation(originalPosition);
        } else {
            final Point position = positionsToMoveTo.poll();
            boardPiece.setLocation(position);
            positionsToMoveTo.add(position);
            shakesRemaining--;
        }
        gamePanel.repaint();
    }
}
