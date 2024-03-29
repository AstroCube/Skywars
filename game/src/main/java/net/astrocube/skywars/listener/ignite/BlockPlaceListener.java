package net.astrocube.skywars.listener.ignite;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

		Block block = event.getBlockPlaced();

		if (block.getType() == Material.TNT) {
			block.setType(Material.AIR);
			block.getWorld().spawnEntity(
				block.getLocation().clone().add(0.5, 0, 0.5),
				EntityType.PRIMED_TNT
			);
		}
	}
}