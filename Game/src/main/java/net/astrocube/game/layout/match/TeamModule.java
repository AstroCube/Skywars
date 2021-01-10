package net.astrocube.game.layout.match;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.game.layout.api.team.TeamBalancer;

public class TeamModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(TeamBalancer.class).to(CoreTeamBalancer.class);
    }
}
