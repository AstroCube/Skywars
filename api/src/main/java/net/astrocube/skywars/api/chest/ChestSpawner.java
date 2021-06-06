package net.astrocube.skywars.api.chest;

import net.astrocube.skywars.api.map.MapConfiguration;

import java.util.Set;

public interface ChestSpawner {

    /**
     * Spawn some chest for a match.
     * @param match where the chest will be located
     * @param chest to be spawned
     */
    void spawnChest(String match, MapConfiguration.Chest chest);

    /**
     * Spawn a certain set of chests for a match.
     * @param match where the chest will be located
     * @param chests to be spawned
     */
    void spawnChests(String match, Set<MapConfiguration.Chest> chests);

}
