package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.Timer;

/**
 * An animator that gradually fades out a piece until it cannot be seen.
 *
 * @author  Alex Mullen
 *
 */
public class PieceFadeOutAnimation implements DrawableAnimator, ActionListener {
    /** Holds the interval we fade out the piece a little more in ms. */
    private static final int INTERVAL = 10;
    /** How much to reduce the alpha value by each frame. */
    private static final float ALPHA_FADE_AMOUNT = 0.01f;
    /** The board panel to repaint every frame. */
    private final GamePanel gamePanel;
    /** The piece to fade. */
    private final BoardPiece boardPiece;
    /** Holds the continuation to run after this has finished animating. */
    private final AnimatorContinuation continuationToRun;
    /** The timer to use for performing the animation. */
    private final Timer timer;
    /**
     * Creates a new instance.
     *
     * @param panel         the panel to repaint
     * @param piece         the piece to fade
     * @param initialDelay  the initial delay to wait before starting the fade
     * @param continuation  the continuation to run once the animation
     *                      has finished - can be <code>null</code>
     */
    public PieceFadeOutAnimation(final GamePanel panel, final BoardPiece piece,
            final int initialDelay, final AnimatorContinuation continuation) {
        gamePanel = Objects.requireNonNull(panel);
        boardPiece = Objects.requireNonNull(piece);
        continuationToRun = continuation;
        timer = new Timer(INTERVAL, this);
        timer.setInitialDelay(initialDelay);
        timer.setRepeats(true);
    }
    @Override
    public final void start() {
        timer.start();
    }
    @Override
    public final void draw(final Graphics2D g) {
        boardPiece.draw(g);
    }
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (boardPiece.getAlpha() > 0) {
            boardPiece.setAlpha(Math.max(0,
                    (boardPiece.getAlpha() - ALPHA_FADE_AMOUNT)));
            gamePanel.repaint();
        } else {
            timer.stop();
            if (continuationToRun != null) {
                continuationToRun.onAnimationEnded(this);
            }
        }
    }
}
