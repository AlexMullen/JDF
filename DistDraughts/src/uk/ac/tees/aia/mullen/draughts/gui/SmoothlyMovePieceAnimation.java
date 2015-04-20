package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * An animation that smoothly moves a piece from one position to another.
 *
 * @author  Alex Mullen
 *
 */
public class SmoothlyMovePieceAnimation implements Animator, ActionListener {
    /** Holds the velocity (pixels) to move each frame. */
    private static final int VELOCITY = 5;
    /** Holds the interval each frame occurs at in milliseconds. */
    private static final int INTERVAL = 10;
    /** The board panel to repaint for every frame. */
    private final GamePanel gamePanel;
    /** The piece to animate. */
    private final BoardPiece movingPiece;
    /** The destination to move the piece to. */
    private final Point destination;
    /** Holds the continuation to run after this has finished animating. */
    private final AnimatorContinuation continuationToRun;
    /** The timer to use for performing the animation. */
    private final Timer timer;
    /**
     * Creates a new instance.
     *
     * @param panel         the panel to repaint
     * @param piece         the piece to move
     * @param dest          the destination to move the piece to
     * @param continuation  the continuation to run once the animation has
     *                      finished - can be <code>null</code>
     */
    public SmoothlyMovePieceAnimation(final GamePanel panel,
            final BoardPiece piece, final Point dest,
            final AnimatorContinuation continuation) {
        gamePanel = panel;
        movingPiece = piece;
        destination = dest;
        continuationToRun = continuation;
        timer = new Timer(INTERVAL, this);
        timer.setRepeats(true);
    }
    @Override
    public final void start() {
        timer.start();
    }
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (movingPiece.x < destination.x) {
            final int remaining = (destination.x - movingPiece.x);
            movingPiece.x += Math.min(VELOCITY, remaining);
        } else if (movingPiece.x > destination.x) {
            final int remaining = (movingPiece.x - destination.x);
            movingPiece.x -= Math.min(VELOCITY, remaining);
        }
        if (movingPiece.y < destination.y) {
            final int remaining = (destination.y - movingPiece.y);
            movingPiece.y += Math.min(VELOCITY, remaining);
        } else if (movingPiece.y > destination.y) {
            final int remaining = (movingPiece.y - destination.y);
            movingPiece.y -= Math.min(VELOCITY, remaining);
        }
        gamePanel.repaint();
        if (movingPiece.getLocation().equals(destination)) {
            timer.stop();
            if (continuationToRun != null) {
                continuationToRun.onAnimationEnded(this);
            }
        }
    }
}
