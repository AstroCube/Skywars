package net.astrocube.skywars.listener.damage;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerItemDamageListener implements Listener {

	@EventHandler
	public void repairWeapons(PlayerItemDamageEvent event) {
		if (event.getItem() != null) {
			event.setCancelled(true);
		}
	}
}
