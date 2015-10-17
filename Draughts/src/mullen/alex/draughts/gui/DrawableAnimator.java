package mullen.alex.draughts.gui;

import java.awt.Graphics2D;

/**
 * An interface for defining an animator that can be drawn.
 *
 * @author  Alex Mullen
 *
 */
public interface DrawableAnimator extends Animator {
    /**
     * Draws the animation to the specified graphics context.
     *
     * @param graphics  the graphics context to draw on
     */
    void draw(Graphics2D graphics);
}
