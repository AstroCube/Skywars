package net.astrocube.skywars.game;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.game.SpawnProtectionChecker;

public class GameModule extends ProtectedModule {

    @Override
    public void configure() {
        bind(SpawnProtectionChecker.class).to(CoreSpawnProtectionChecker.class);
    }

}
