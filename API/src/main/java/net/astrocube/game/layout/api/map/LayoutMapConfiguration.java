package net.astrocube.game.layout.api.map;

import net.astrocube.api.bukkit.game.map.configuration.CoordinatePoint;
import net.astrocube.api.bukkit.game.map.configuration.GameMapConfiguration;
import net.astrocube.api.core.virtual.gamemode.GameMode;

public interface LayoutMapConfiguration extends GameMapConfiguration {

    /**
     * @return spawn for an awesome {@link GameMode}
     */
    CoordinatePoint getSpawn();

}
