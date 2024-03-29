package net.astrocube.skywars.listener.game;

import net.astrocube.api.bukkit.game.event.game.GameUserDisconnectEvent;
import net.astrocube.api.bukkit.game.match.UserMatchJoiner;
import net.astrocube.skywars.api.event.PlayerDisqualificationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameUserLeaveListener implements Listener {

	@EventHandler
	public void onDisconnect(GameUserDisconnectEvent event) {
		Player player = event.getPlayer();

		if (event.getOrigin() == UserMatchJoiner.Origin.PLAYING) {
			Bukkit.getPluginManager().callEvent(new PlayerDisqualificationEvent(player, null));
		}
	}
}