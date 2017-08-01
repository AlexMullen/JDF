package mullen.alex.jdf.common;

/**
 * An interface that defines a player in a game.
 *
 * @author  Alex Mullen
 *
 */
public interface Player {
    /**
     * Gets the colour of pieces this player is controlling.
     *
     * @return  either {@link Piece#DARK} or {@link Piece#LIGHT}.
     */
    int getColour();
    /**
     * Gets whether this is an artificial player.
     *
     * @return  <code>true</code> if artificial; <code>false</code> for human
     *          presumably.
     */
    boolean isArtificial();
}
