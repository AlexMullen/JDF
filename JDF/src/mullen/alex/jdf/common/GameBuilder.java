package mullen.alex.jdf.common;

/**
 * An interface that defines a class that can build a type of draughts game.
 *
 * @author  Alex Mullen
 *
 */
public interface GameBuilder {
    /**
     * Gets the name of this builder.
     *
     * @return  the name
     */
    String getName();
    /**
     * Gets whether forced captures is configurable for this game builder.
     *
     * @return  <code>true</code> if it is configurable; <code>false</code> if
     *          not
     */
    boolean isForcedCapturesConfigurable();
    /**
     * Gets whether choosing who moves first is configurable for this game
     * builder.
     *
     * @return  <code>true</code> if it is configurable; <code>false</code>
     *          if not
     */
    boolean isFirstTurnConfigurable();
    /**
     * Builds a game using the values in the specified configuration.
     *
     * @param config  the configuration for the game
     * @return        the built {@link Game} instance
     */
    Game build(Config config);
    /**
     * A container class for holding configuration options for creating a
     * game.
     *
     * @author  Alex Mullen
     */
    class Config {
        /** Holds the value for whether forced captures is on or off. */
        private boolean forcedCaptures;
        /** Holds the player who will play the dark pieces. */
        private Player darkPlayer;
        /** Holds the player who will play the light pieces. */
        private Player lightPlayer;
        /**
         * Gets whether forced captures is enabled or disabled.
         *
         * @return  the value
         */
        public final boolean getForcedCaptures() {
            return forcedCaptures;
        }
        /**
         * Sets whether forced captures is enabled or disabled.
         *
         * @param value  the value to set
         */
        public final void setForcedCaptures(final boolean value) {
            forcedCaptures = value;
        }
        /**
         * Gets the player who will be playing with the light pieces.
         *
         * @return  the player
         */
        public final Player getLightPlayer() {
            return lightPlayer;
        }
        /**
         * Sets the player who will be playing with the light pieces.
         *
         * @param player  the player
         */
        public final void setLightPlayer(final Player player) {
            lightPlayer = player;
        }
        /**
         * Gets the player who will be playing with the dark pieces.
         *
         * @return  the player
         */
        public final Player getDarkPlayer() {
            return darkPlayer;
        }
        /**
         * Sets the player who will be playing with the dark pieces.
         *
         * @param player  the player
         */
        public final void setDarkPlayer(final Player player) {
            darkPlayer = player;
        }
    }
}
