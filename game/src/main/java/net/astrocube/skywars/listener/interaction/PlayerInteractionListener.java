package net.astrocube.skywars.listener.interaction;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.match.ActualMatchCache;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
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

		try {

			Optional<Match> match = actualMatchCache.get(event.getPlayer().getDatabaseIdentifier());

			if (match.isPresent() && match.get().getStatus() == MatchDoc.Status.RUNNING) {
				return;
			}

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error while detecing if player can place item", e);
		}

		event.setCancelled(true);

	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {

		try {

			Optional<Match> match = actualMatchCache.get(event.getPlayer().getDatabaseIdentifier());

			if (match.isPresent() && match.get().getStatus() == MatchDoc.Status.RUNNING) {
				return;
			}

		} catch (Exception e) {
			plugin.getLogger().log(Level.SEVERE, "Error while detecing if player can break item", e);
		}

		event.setCancelled(true);

	}


}
