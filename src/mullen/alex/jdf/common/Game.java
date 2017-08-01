package mullen.alex.jdf.common;

/**
 * An abstract class that that represents a game of Draughts.
 *
 * @author  Alex Mullen
 *
 */
public interface Game {
    /**
     * Gets the current game board state.
     *
     * @return  the board
     */
    Board getBoard();
    /**
     * Gets the board pattern for this game type.
     *
     * @return  the pattern
     */
    BoardPattern getBoardPattern();
    /**
     * Gets whoever turn it currently is.
     *
     * @return  the owner of the turn
     */
    Player getTurn();
    /**
     * Gets the player who is playing the dark pieces.
     *
     * @return  the player
     */
    Player getDarkPlayer();
    /**
     * Gets the player who is playing the light pieces.
     *
     * @return  the player
     */
    Player getLightPlayer();
    /**
     * Gets the move generator this game uses.
     *
     * @return  the move generator
     */
    MoveGenerator getMoveGenerator();
    /**
     * Gets the move performer this game uses.
     *
     * @return  the move performer
     */
    MovePerformer getMovePerformer();
    /**
     * Gets the opponent for the specified piece owner.
     *
     * @param owner  the piece owner
     * @return       the opponent
     */
    Player getOpponent(final Player owner);
    /**
     * Gets the result of the game.
     *
     * @return  the result or <code>null</code> if the game has not ended yet.
     */
    GameResult getResult();
    /**
     * Performs a move for the current turn owner.
     *
     * @param move  the move to perform
     */
    void performMove(Move move);
    /**
     * Undoes the last move.
     */
    void undoMove();
    /**
     * Represents the result of a game.
     *
     * @author  Alex Mullen
     */
    class GameResult {
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
