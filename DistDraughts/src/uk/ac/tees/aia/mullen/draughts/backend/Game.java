package uk.ac.tees.aia.mullen.draughts.backend;

/**
 * An abstract class that that represents a game of Draughts.
 *
 * @author  Alex Mullen
 *
 */
public abstract class Game {
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
    public abstract Player getTurnOwner();
    /**
     * Gets the move generator this game uses.
     *
     * @return  the move generator
     */
    public abstract MoveGenerator getMoveGenerator();
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
    public abstract Player getOpponent(final Player owner);
    /**
     * Performs a move for the current turn owner.
     *
     * @param move  the move to perform
     */
    public abstract void performMove(Move move);
}
