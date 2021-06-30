package net.astrocube.skywars.api.kit;

import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.skywars.api.team.ProvisionedTeam;

public interface KitMatcher {

	Kit getPlayerKit(MatchDoc.TeamMember member) throws Exception;

	void applyTeamKits(ProvisionedTeam team) throws Exception;
}