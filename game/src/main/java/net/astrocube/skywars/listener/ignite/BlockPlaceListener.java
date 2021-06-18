package net.astrocube.skywars.listener.ignite;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void blockPlaceListener(BlockPlaceEvent event) {

		Block blockPlaced = event.getBlockPlaced();
		World world = blockPlaced.getWorld();

		if (blockPlaced.getType() == Material.TNT) {
			blockPlaced.setType(Material.AIR);
			world.spawnEntity(blockPlaced.getLocation(), EntityType.PRIMED_TNT);
		}
	}
}