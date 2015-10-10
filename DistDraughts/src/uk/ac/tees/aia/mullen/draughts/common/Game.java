package uk.ac.tees.aia.mullen.draughts.common;

/**
 * An abstract class that that represents a game of Draughts.
 *
 * @author  Alex Mullen
 *
 */
public abstract class Game {
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
     * Gets the player who is playing the dark pieces.
     *
     * @return  the player
     */
    public abstract Player getDarkPlayer();
    /**
     * Gets the player who is playing the light pieces.
     *
     * @return  the player
     */
    public abstract Player getLightPlayer();
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
     * Gets the result of the game.
     *
     * @return  the result or <code>null</code> if the game has not ended yet.
     */
    public abstract GameResult getResult();
    /**
     * Performs a move for the current turn owner.
     *
     * @param move  the move to perform
     */
    public abstract void performMove(Move move);
    /**
     * Represents the result of a game.
     *
     * @author  Alex Mullen
     */
    public static class GameResult {
        /** Holds the winning player. */
        private final Player winningPlayer;
        /**
         * Creates a new instance with the given winning player.
         *
         * @param winner  the winning player
         */
        public GameResult(final Player winner) {
            winningPlayer = winner;
        }
        /**
         * Gets the winning player.
         *
         * @return  the winner
         */
        public final Player getWinner() {
            return winningPlayer;
        }
    }
}
