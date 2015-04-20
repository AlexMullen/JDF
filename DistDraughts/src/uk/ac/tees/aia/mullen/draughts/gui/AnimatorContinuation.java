package uk.ac.tees.aia.mullen.draughts.gui;

/**
 * An interface that defines a continuation for what happens after an
 * animation has finished.
 *
 * @author  Alex Mullen
 */
public interface AnimatorContinuation {
    /**
     * Invoked by the animation when it has finished animating.
     *
     * @param animator  the animation that ended
     */
    void onAnimationEnded(Animator animator);
}
