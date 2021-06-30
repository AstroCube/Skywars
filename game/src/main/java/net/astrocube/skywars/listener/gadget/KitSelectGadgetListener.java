package net.astrocube.skywars.listener.gadget;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.user.inventory.event.ActionableItemEvent;
import net.astrocube.skywars.menu.KitSelectMenuProvider;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

import java.util.logging.Level;

public class KitSelectGadgetListener implements Listener {

	private @Inject KitSelectMenuProvider kitSelectMenuProvider;

	@EventHandler
	public void onUse(ActionableItemEvent event) {
		if (event.getAction().equals("kit_select")
			&& (event.getClick() == Action.RIGHT_CLICK_AIR
			|| event.getClick() == Action.RIGHT_CLICK_BLOCK)) {
			try {
				kitSelectMenuProvider.open(event.getPlayer());
			} catch (Exception exception) {
				Bukkit.getLogger().log(
					Level.SEVERE,
					"An error occurred while opening kit selection menu",
					exception
				);
			}
		}
	}
}