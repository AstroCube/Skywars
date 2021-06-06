package net.astrocube.skywars.api.refill;

import net.astrocube.skywars.api.team.ProvisionedTeam;

import java.util.Set;

public interface RefillAnnouncer {

	void announceRefill(Set<ProvisionedTeam> teams, int seconds);

}
