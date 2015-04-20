package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * An animator that fades out multiple pieces.
 *
 * @author  Alex Mullen
 *
 */
public class MultiPieceFadeOutAnimation implements DrawableAnimator,
        AnimatorContinuation {
    /** The pieces to fade. */
    private final List<BoardPiece> piecesToFade;
    /** Holds the continuation to run after this has finished animating. */
    private final AnimatorContinuation continuationToRun;
    /** The list of fade animations this started. */
    private final List<PieceFadeOutAnimation> fadeAnimations;
    /**
     * Creates a new instance.
     *
     * @param panel             the panel to repaint
     * @param pieces            the list of pieces to fade
     * @param initialDelay      the initial delay in milliseconds before the
     *                          first piece starts to fade
     * @param delayBetweenEach  the delay in milliseconds between successive
     *                          pieces
     * @param continuation      the continuation to run after the animation
     *                          completes - can be <code>null</code>
     */
    public MultiPieceFadeOutAnimation(final GamePanel panel,
            final List<BoardPiece> pieces, final int initialDelay,
            final int delayBetweenEach,
            final AnimatorContinuation continuation) {
        piecesToFade = Objects.requireNonNull(pieces);
        continuationToRun = continuation;
        fadeAnimations = new ArrayList<>();
        int accumulatedDelay = initialDelay;
        for (final BoardPiece piece : piecesToFade) {
            final PieceFadeOutAnimation animation =
                    new PieceFadeOutAnimation(
                            panel, piece, accumulatedDelay, this);
            accumulatedDelay += delayBetweenEach;
            fadeAnimations.add(animation);
        }
    }
    @Override
    public final void start() {
        for (final PieceFadeOutAnimation fadeAnimation : fadeAnimations) {
            fadeAnimation.start();
        }
    }
    @Override
    public final void onAnimationEnded(final Animator animator) {
        fadeAnimations.remove(animator);
        if (fadeAnimations.isEmpty() && continuationToRun != null) {
            // Finished.
            continuationToRun.onAnimationEnded(this);
        }
    }
    @Override
    public final void draw(final Graphics2D graphics) {
        for (final PieceFadeOutAnimation fadeAnimation : fadeAnimations) {
            fadeAnimation.draw(graphics);
        }
    }
}
