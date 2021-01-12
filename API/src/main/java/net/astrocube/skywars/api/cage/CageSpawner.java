package net.astrocube.skywars.api.cage;

import org.bukkit.Location;

import java.util.Set;

public interface CageSpawner {

    /**
     * Spawns a cage where user(s) will be placed.
     * @param cage material to be spawned
     * @param location where cage will be spawned
     * @param area of the cage
     */
    void spawnCage(Cage cage, Location location, int area);

}
