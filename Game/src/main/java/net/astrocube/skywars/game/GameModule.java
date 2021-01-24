package net.astrocube.skywars.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.game.DisqualificationHandler;
import net.astrocube.skywars.api.game.ScoreboardModifier;
import net.astrocube.skywars.api.game.SpawnProtectionChecker;
import net.astrocube.skywars.api.game.VictoryAnnouncer;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(SpawnProtectionChecker.class).to(CoreSpawnProtectionChecker.class);
        bind(ScoreboardModifier.class).to(CoreScoreboardModifier.class);
        bind(DisqualificationHandler.class).to(CoreDisqualificationHandler.class);
        bind(VictoryAnnouncer.class).to(CoreVictoryAnnouncer.class);
    }

}
