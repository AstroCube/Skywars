package net.astrocube.skywars.listener.interaction;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.logging.Level;

public class PlayerInteractionListener implements Listener {

	private @Inject ActualMatchCache actualMatchCache;
	private @Inject Plugin plugin;

	@EventHandler
	public void onBlockPlaceEvent(BlockPlaceEvent event) {
		cancelIfNotPlaying(event.getPlayer(), event);
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		cancelIfNotPlaying(event.getPlayer(), event);
	}

	private void cancelIfNotPlaying(Player player, Cancellable cancellableListener) {
		try {
			Optional<Match> optionalMatch = actualMatchCache.get(player.getDatabaseIdentifier());

			if (optionalMatch.isPresent() && optionalMatch.get().getStatus() == MatchDoc.Status.RUNNING) {
				return;
			}
		} catch (Exception exception) {
			plugin.getLogger().log(
				Level.SEVERE,
				"Error while detecting if player can place item", exception
			);
		}

		cancellableListener.setCancelled(true);
	}
}