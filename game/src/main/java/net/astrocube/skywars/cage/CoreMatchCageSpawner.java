package net.astrocube.skywars.cage;

import net.astrocube.api.bukkit.game.event.match.MatchInvalidateEvent;
import net.astrocube.skywars.api.cage.CageMatcher;
import net.astrocube.skywars.api.cage.CageSpawner;
import net.astrocube.skywars.api.cage.MatchCageSpawner;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import net.astrocube.skywars.team.TeamUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class CoreMatchCageSpawner implements MatchCageSpawner {

	private @Inject CageSpawner cageSpawner;
	private @Inject CageMatcher cageMatcher;
	private @Inject Plugin plugin;

	@Override
	public void spawn(String match, Set<ProvisionedTeam> teams) {
		executeOperation(match, teams, true);
	}

	@Override
	public void remove(String match, Set<ProvisionedTeam> teams) {
		executeOperation(match, teams, false);
	}

	private void executeOperation(String match, Set<ProvisionedTeam> teams, boolean place) {

		World world = Bukkit.getWorld("match_" + match);
		int area = plugin.getConfig().getInt("wars.cage-area");

		if (world == null) {
			Bukkit.getPluginManager().callEvent(new MatchInvalidateEvent(match, false));
			return;
		}

		teams.forEach(provisionedTeam ->
				cageSpawner.spawnCage(
						place ? cageMatcher.getHighestCage(provisionedTeam) : CageMatcher.getEmptyCage(),
						TeamUtils.generateSpawn(provisionedTeam.getSpawn(), world, 0),
						area
				)
		);
	}

}
