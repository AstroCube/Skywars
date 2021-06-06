package net.astrocube.skywars.api.cage;

import net.astrocube.skywars.api.team.ProvisionedTeam;

import java.util.Set;

public interface MatchCageSpawner {

	void spawn(String match, Set<ProvisionedTeam> teams);

	void remove(String match, Set<ProvisionedTeam> teams);

}
