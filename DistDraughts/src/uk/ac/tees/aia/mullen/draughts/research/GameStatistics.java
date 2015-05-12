package uk.ac.tees.aia.mullen.draughts.research;

import uk.ac.tees.aia.mullen.draughts.common.Player;

/**
 * A class for holding statistical data about a benchmarked gamed.
 *
 * @author  Alex Mullen
 *
 */
public class GameStatistics {
    /** Holds the game ID. */
    private final int gameId;
    /** Holds the time in milliseconds of the game duration. */
    private long gameTime;
    /** Holds the total number of moves made in the game. */
    private int movesMade;
    /** Holds the winner of the game. */
    private Player winner;
    /** Holds the loser of the game. */
    private Player loser;
    /**
     * Creates a new instance for the specified game iteration ID.
     *
     * @param id  the game iteration ID
     */
    GameStatistics(final int id) {
        gameId = id;
    }
    /**
     * Gets the game ID.
     * @return  the ID
     */
    public final int getGameID() {
        return gameId;
    }
    /**
     * Gets the game time.
     * @return  the time in milliseconds
     */
    public final long getGameTime() {
        return gameTime;
    }
    /**
     * Sets the game time.
     * @param time  the game time in milliseconds
     */
    public final void setGameTime(final long time) {
        gameTime = time;
    }
    /**
     * Gets the number of moves made.
     * @return  the number of moves
     */
    public final int getMovesMade() {
        return movesMade;
    }
    /**
     * Sets the number of moves made.
     * @param moveCount  number of moves made
     */
    public final void setMovesMade(final int moveCount) {
        movesMade = moveCount;
    }
    /**
     * Gets the winner of the game.
     * @return  the winning player
     */
    public final Player getWinner() {
        return winner;
    }
    /**
     * Sets the winning player.
     * @param winningPlayer  the winning player
     */
    public final void setWinner(final Player winningPlayer) {
        winner = winningPlayer;
    }
    /**
     * Gets the losing player.
     * @return  the losing player
     */
    public final Player getLoser() {
        return loser;
    }
    /**
     * Sets the losing player.
     * @param losingPlayer  the losing player
     */
    public final void setLoser(final Player losingPlayer) {
        loser = losingPlayer;
    }
    @Override
    public final String toString() {
        return "GameStatistics [gameId=" + gameId + ", gameTime=" + gameTime
                + ", movesMade=" + movesMade + ", winner=" + winner
                + ", loser=" + loser + "]";
    }
}
