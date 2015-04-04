package uk.ac.tees.aia.mullen.draughts.backend;

import java.util.ArrayList;
import java.util.Collection;

/**
 * An abstract class that that represents a game of Draughts.
 *
 * @author  Alex Mullen
 *
 */
public abstract class Game {
    /** The collection of observers for this game. */
    private final Collection<GameObserver> observers;
    /**
     * Creates a new instance.
     */
    protected Game() {
        observers = new ArrayList<>();
    }
    /**
     * Attaches the specified observer to this.
     *
     * @param observer  the observer
     */
    public final void addObserver(final GameObserver observer) {
        observers.add(observer);
    }
    /**
     * Gets all attached game observers.
     *
     * @return  all observers
     */
    protected final Collection<GameObserver> getObservers() {
        return observers;
    }
    /**
     * Starts the game.
     */
    public abstract void start();
    /**
     * Gets the current game board state.
     *
     * @return  the board
     */
    public abstract Board getBoard();
    /**
     * Gets whoever turn it currently is.
     *
     * @return  the owner of the turn
     */
    public abstract PieceOwner getTurnOwner();
    /**
     * Gets the move finder this game uses.
     *
     * @return  the move finder
     */
    public abstract MoveFinder getMoveFinder();
    /**
     * Gets the move performer this game uses.
     *
     * @return  the move performer
     */
    public abstract MovePerformer getMovePerformer();
    /**
     * Gets the opponent for the specified piece owner.
     *
     * @param owner  the piece owner
     * @return       the opponent
     */
    public abstract PieceOwner getOpponent(final PieceOwner owner);
    /**
     * Performs a move for the current turn owner.
     *
     * @param move  the move to perform
     */
    public abstract void performMove(Move move);
}
