package net.astrocube.skywars.listener.game;

import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.event.game.GameReadyEvent;
import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.game.map.MapConfigurationProvider;
import net.astrocube.api.bukkit.virtual.game.map.GameMap;
import net.astrocube.api.bukkit.virtual.game.match.Match;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.skywars.api.cage.MatchCageSpawner;
import net.astrocube.skywars.api.chest.ChestSpawner;
import net.astrocube.skywars.api.game.DisqualificationHandler;
import net.astrocube.skywars.api.game.ScoreboardModifier;
import net.astrocube.skywars.api.game.SpawnProtectionChecker;
import net.astrocube.skywars.api.map.MapConfiguration;
import net.astrocube.skywars.api.refill.RefillScheduler;
import net.astrocube.skywars.api.team.MatchStartProcessor;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.api.team.TeamMatcher;
import net.astrocube.skywars.api.team.TeamSpawner;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.logging.Level;

public class GameReadyListener implements Listener {

	private @Inject FindService<Match> findService;
	private @Inject FindService<GameMap> gameMapFindService;
	private @Inject MapConfigurationProvider configurationProvider;
	private @Inject Plugin plugin;

	private @Inject MatchCageSpawner matchCageSpawner;
	private @Inject MatchStartProcessor matchStartProcessor;
	private @Inject SpawnProtectionChecker spawnProtectionChecker;
	private @Inject ScoreboardModifier scoreboardModifier;
	private @Inject TeamSpawner teamSpawner;
	private @Inject TeamMatcher teamMatcher;
	private @Inject RefillScheduler refillScheduler;
	private @Inject DisqualificationHandler disqualificationHandler;
	private @Inject ChestSpawner chestSpawner;

	@EventHandler
	public void onGameReady(GameReadyEvent event) {

		findService.find(event.getMatch()).callback(response -> {
			try {

				if (!response.isSuccessful() || !response.getResponse().isPresent()) {
					throw new GameControlException("The requested match was not found");
				}

				MapConfiguration configuration =
						configurationProvider.parseConfiguration(event.getConfiguration(), MapConfiguration.class);

				Set<ProvisionedTeam> provisionedTeams = teamMatcher.linkTeams(event.getTeams(), configuration);

				GameMap gameMap = gameMapFindService.findSync(response.getResponse().get().getMap());

				Bukkit.getScheduler().runTask(plugin, () -> {
					matchCageSpawner.spawn(event.getMatch(), provisionedTeams);
					teamSpawner.spawn(provisionedTeams, event.getMatch());
					chestSpawner.spawnChests(event.getMatch(), configuration.getChests());
					spawnProtectionChecker.registerMatch(provisionedTeams);
					disqualificationHandler.ensureTeams(event.getMatch(), provisionedTeams);

					// Will not stop the game when failed
					try {
						scoreboardModifier.updateInitial(provisionedTeams, gameMap);
					} catch (Exception e) {
						plugin.getLogger().log(Level.WARNING, "Error while generating scoreboard", e);
					}

					matchStartProcessor.scheduleStart(provisionedTeams, event.getMatch());
				});


				Bukkit.getScheduler().runTaskLater(
						plugin,
						() -> refillScheduler.scheduleRefill(event.getMatch(), configuration.getChests(), provisionedTeams),
						200L
				);

			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "There was an error processing game ready event.", e);
				Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(event.getMatch(), false));
			}

		});

	}

}
