package net.astrocube.skywars.api.game;

import org.bukkit.entity.Player;

public interface VictoryAnnouncer {

	/**
	 * Send victory title to a player
	 * @param player who will visualize title
	 */
	void sendVictoryTitle(Player player);

}
