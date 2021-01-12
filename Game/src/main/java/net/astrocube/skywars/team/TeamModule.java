package net.astrocube.skywars.team;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.team.TeamMatcher;
import net.astrocube.skywars.api.team.TeamSpawner;

public class TeamModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(TeamMatcher.class).to(CoreTeamMatcher.class);
        bind(TeamSpawner.class).to(CoreTeamSpawner.class);
    }

}
