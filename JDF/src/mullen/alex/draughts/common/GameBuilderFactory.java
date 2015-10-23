package mullen.alex.draughts.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory of game builders that can build different variations of draughts
 * games.
 *
 * @author  Alex Mullen
 *
 */
public class GameBuilderFactory {
    /** The map of game builders. */
    private final Map<String, GameBuilder> gameBuilderMap;
    /**
     * Creates a new instance.
     */
    public GameBuilderFactory() {
        gameBuilderMap = new HashMap<>();
    }
    /**
     * Gets the specified builder by name.
     *
     * @param name  the name of the builder to retrieve
     * @return      the builder instance associated with that name or
     *              <code>null</code> if a builder is not associated with that
     *              name
     */
    public final GameBuilder getBuilder(final String name) {
        return gameBuilderMap.get(name);
    }
    /**
     * Gets all game builders registered with this factory.
     *
     * @return  the list of builders
     */
    public final List<GameBuilder> getBuilders() {
        return new ArrayList<>(gameBuilderMap.values());
    }
    /**
     * Registers the specified builder with this factory.
     *
     * @param builder  the builder
     * @return         <code>true</code> if it was successfully registered;
     *                 <code>false</code> otherwise if another builder was
     *                 already registered that shares the same name
     */
    public final boolean registerBuilder(
            final GameBuilder builder) {
        return gameBuilderMap.putIfAbsent(builder.getName(), builder) == null;
    }
}
