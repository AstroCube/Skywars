package net.astrocube.skywars.listener.ignite;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EntityExplodeListener implements Listener {

	@EventHandler
	public void onEntityExplodeListener(EntityExplodeEvent event) {
		if (event.getEntityType() == EntityType.PRIMED_TNT) {
			event.blockList().clear();

			event.getLocation().getWorld()
				.getNearbyEntities(event.getLocation(), 5, 5, 5)
				.stream()
				.filter(entity -> entity instanceof Player)
				.map(entity -> (Player) entity)
				.forEach(player -> player.setHealth(player.getHealth() - 2));
		}
	}
}