package mullen.alex.draughts.international;

import mullen.alex.draughts.common.Game;
import mullen.alex.draughts.common.GameBuilder;

/**
 * A game builder implementation for International draughts.
 *
 * @author  Alex Mullen
 *
 */
public class InternationalDraughtsGameBuilder implements GameBuilder {
    /**
     * Creates a new instance.
     */
    public InternationalDraughtsGameBuilder() {
        // Empty.
    }
    @Override
    public final String getName() {
        return "International";
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
        return new InternationalDraughtsGame(config.getLightPlayer(),
                config.getDarkPlayer());
    }
    @Override
    public final String toString() {
        return getName();
    }
}
