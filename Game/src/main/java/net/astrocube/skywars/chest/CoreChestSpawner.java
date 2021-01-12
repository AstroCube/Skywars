package net.astrocube.skywars.chest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.skywars.api.chest.ChestFiller;
import net.astrocube.skywars.api.chest.ChestSpawner;
import net.astrocube.skywars.api.map.MapConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;

import java.util.Set;

@Singleton
public class CoreChestSpawner implements ChestSpawner {

    private @Inject ChestFiller chestFiller;

    @Override
    public void spawnChest(String match, MapConfiguration.Chest chest) {

        World world = Bukkit.getWorld("match_" + match);

        if (world == null) {
            return;
        }

        Location chestLocation = new Location(world, chest.getLocation().getX(), chest.getLocation().getY(), chest.getLocation().getZ());
        Block block = world.getBlockAt(chestLocation);
        block.setType(Material.CHEST);

        chestFiller.fillChest(chest, (Chest) block.getState());
    }

    @Override
    public void spawnChests(String match, Set<MapConfiguration.Chest> chests) {
        chests.forEach(chest -> spawnChest(match, chest));
    }

}
