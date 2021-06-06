package net.astrocube.skywars.api.team;

import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.skywars.api.map.MapConfiguration;

import java.util.Set;

public interface TeamMatcher {

	/**
	 * Creates a set of configurable teams with the provided match teams.
	 * @param matchTeam     to be used
	 * @param configuration where maps will be extracted
	 * @return linked teams with configurable map
	 */
	Set<ProvisionedTeam> linkTeams(Set<MatchDoc.Team> matchTeam, MapConfiguration configuration);

}
