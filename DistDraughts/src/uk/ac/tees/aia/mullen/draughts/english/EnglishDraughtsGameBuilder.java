package uk.ac.tees.aia.mullen.draughts.english;

import uk.ac.tees.aia.mullen.draughts.common.Game;
import uk.ac.tees.aia.mullen.draughts.common.GameBuilder;

/**
 * A game builder implementation for English draughts.
 *
 * @author  Alex Mullen
 *
 */
public class EnglishDraughtsGameBuilder implements GameBuilder {
    /**
     * Creates a new instance.
     */
    public EnglishDraughtsGameBuilder() {
        // Empty.
    }
    @Override
    public final String getName() {
        return "English";
    }
    @Override
    public final boolean isForcedCapturesConfigurable() {
        return false;
    }
    @Override
    public final boolean isFirstTurnConfigurable() {
        return false;
    }
    @Override
    public final Game build(final Config config) {
        return new EnglishDraughtsGame(config.getLightPlayer(),
                config.getDarkPlayer());
    }
    @Override
    public final String toString() {
        return getName();
    }
}
