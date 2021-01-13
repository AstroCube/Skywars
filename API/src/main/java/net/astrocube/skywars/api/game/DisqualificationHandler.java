package net.astrocube.skywars.api.game;

import net.astrocube.skywars.api.team.ProvisionedTeam;

import java.util.Set;

public interface DisqualificationHandler {

    void ensureTeams(String match, Set<ProvisionedTeam> teams);

    void disqualify(String user);

    interface Registry {

        String getMatch();

        String getUser();

        String getTeam();

    }

}
