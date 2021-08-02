package net.astrocube.skywars.listener.game;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.event.game.GameUserJoinEvent;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.api.bukkit.user.inventory.nbt.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import team.unnamed.gui.core.item.type.ItemBuilder;

public class GameUserJoinListener implements Listener {

	private @Inject Plugin plugin;
	private @Inject MessageHandler messageHandler;

	@EventHandler
	public void onJoin(GameUserJoinEvent event) {
		Player player = event.getPlayer();

		if (event.getOrigin() == UserMatchJoiner.Origin.WAITING) {
			Bukkit.getScheduler().runTaskLater(
				plugin,
				() -> {
					ItemStack gadget = ItemBuilder.newBuilder(Material.STONE_SWORD)
						.setName(messageHandler.get(player, "gadget.kit-select.name"))
						.setLore(messageHandler.getMany(player, "gadget.kit-select.lore"))
						.build();
					gadget = NBTUtils.addString(gadget, "actionable", "kit_select");
					player.getInventory().setItem(
						0,
						gadget
					);
				},
				20
			);
		}
	}
}