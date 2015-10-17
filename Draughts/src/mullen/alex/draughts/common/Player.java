package mullen.alex.draughts.common;

/**
 * An interface that defines a player in a game.
 *
 * @author  Alex Mullen
 *
 */
public interface Player {
    /**
     * Gets whether this is an artificial player.
     *
     * @return  <code>true</code> if artificial; <code>false</code> for human
     *          presumably.
     */
    boolean isArtificial();
}
