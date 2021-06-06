package net.astrocube.skywars.api.team;

import java.util.Set;

public interface MatchStartProcessor {

	/**
	 * Process a match start between multiple stages
	 * @param teams of the match
	 * @param match to be processed
	 */
	void scheduleStart(Set<ProvisionedTeam> teams, String match);

}
