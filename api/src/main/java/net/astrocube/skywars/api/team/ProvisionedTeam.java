package net.astrocube.skywars.api.team;

import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.skywars.api.map.MapConfiguration;

import java.util.Set;

public interface ProvisionedTeam extends MapConfiguration.ConfigurableTeam {

    /**
     * @return members of the provisioned team
     */
    Set<MatchDoc.TeamMember> getMembers();

}
