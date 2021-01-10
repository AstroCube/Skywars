package net.astrocube.game.layout.api.team;

import net.astrocube.api.bukkit.game.matchmaking.MatchAssignable;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;

import java.util.Set;

public interface TeamBalancer {

    /**
     * Obtain matches from assignable list and creates a balance
     * in order to get equal teams.
     * @param assignableList to be generated
     * @return structured teams
     */
    Set<MatchDoc.Team> generateTeams(Set<MatchAssignable> assignableList);

}
