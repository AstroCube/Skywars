package net.astrocube.skywars.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameModePairEvent;
import net.astrocube.api.bukkit.game.event.game.GamePairEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


public class GamePairEnableListener implements Listener {

	private @Inject Plugin plugin;

	@EventHandler
	public void onGameReady(GamePairEnableEvent event) {
		Bukkit.getPluginManager().callEvent(
				new GameModePairEvent(
						plugin.getConfig().getString("centauri.mode"),
						plugin.getConfig().getString("centauri.subMode")
				)
		);
	}

}
