package uk.ac.tees.aia.mullen.draughts.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import javax.swing.Timer;

public class PieceJumpAnimation implements Animator, ActionListener {
    private static final int VELOCITY = 5;
    private static final int INTERVAL = 10;
    private Point currentDestination;
    private final Timer timer;
    private final GamePanel gamePanel;
    private final BoardPiece movingPiece;
    private final AnimatorContinuation continuationRunnable;
    private final Queue<Point> positionsToLandAt;
    
    public PieceJumpAnimation(final GamePanel panel,
            final BoardPiece piece, final List<Point> landPositions,
            final AnimatorContinuation continuation) {
        gamePanel = panel;
        movingPiece = piece;
        positionsToLandAt = new ArrayDeque<>(landPositions);
        continuationRunnable = continuation;
        currentDestination = positionsToLandAt.remove();
        timer = new Timer(INTERVAL, this);
        timer.setRepeats(true);
        // Grow the size to make it look like it was picked up.
        movingPiece.height += 10;
        movingPiece.width += 10;
    }
    @Override
    public final void start() {
        timer.start();
    }
    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (movingPiece.x < currentDestination.x) {
            final int remaining = (currentDestination.x - movingPiece.x);
            movingPiece.x += Math.min(VELOCITY, remaining);
        } else if (movingPiece.x > currentDestination.x) {
            final int remaining = (movingPiece.x - currentDestination.x);
            movingPiece.x -= Math.min(VELOCITY, remaining);
        }
        if (movingPiece.y < currentDestination.y) {
            final int remaining = (currentDestination.y - movingPiece.y);
            movingPiece.y += Math.min(VELOCITY, remaining);
        } else if (movingPiece.y > currentDestination.y) {
            final int remaining = (movingPiece.y - currentDestination.y);
            movingPiece.y -= Math.min(VELOCITY, remaining);
        }
        if (movingPiece.getLocation().equals(currentDestination)) {
            if (positionsToLandAt.isEmpty()) {
                // No more jumps.
                timer.stop();
                // Shrink the piece again.
                movingPiece.height -= 10;
                movingPiece.width -= 10;
                continuationRunnable.onAnimationEnded(this);
            } else {
                // Do the next jump.
                currentDestination = positionsToLandAt.remove();
            }
        }
        gamePanel.repaint();
    }
}
