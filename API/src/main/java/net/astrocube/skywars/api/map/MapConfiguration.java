package net.astrocube.skywars.api.map;

import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.core.virtual.gamemode.GameMode;

import java.util.List;
import java.util.Set;

public interface MapConfiguration extends GameMapConfiguration {

    /**
     * @return set of configurable teams for the map.
     */
    @Override
    Set<ConfigurableTeam> getTeams();

    Set<Chest> getChests();

    /**
     * Configurable team that extends default map team including
     * spawn coordinates.
     */
    interface ConfigurableTeam extends GameMapConfiguration.MapTeam {

        /**
         * @return coordinates of the team spawn
         */
        CoordinatePoint getSpawn();

    }

    /**
     * Configurable chest object
     */
    interface Chest {

        /**
         * @return location of the chest
         */
        CoordinatePoint getLocation();

        /**
         * @return tier of the chest
         */
        Tier getTier();

        /**
         * Chest type to be filled
         */
        enum Tier {
            NORMAL, CENTER
        }

    }

}
