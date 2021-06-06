package net.astrocube.skywars.api.team;

import java.util.Set;

public interface TeamSpawner {

	/**
	 * Spawn all the match teams in their respective
	 * map spawns.
	 * @param provisioned players
	 * @param match       where the players belong
	 */
	void spawn(Set<ProvisionedTeam> provisioned, String match);

}
