package net.astrocube.skywars.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.cage.CoreCage;
import net.astrocube.skywars.chest.ChestModule;
import net.astrocube.skywars.custom.CustomModule;
import net.astrocube.skywars.game.GameModule;
import net.astrocube.skywars.team.TeamModule;
import net.astrocube.skywars.translation.TranslationModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new TranslationModule());
        install(new TeamModule());
        install(new CoreCage());
        install(new ChestModule());
        install(new GameModule());
        install(new CustomModule());
    }

}
