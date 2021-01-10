package net.astrocube.game.layout.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.game.layout.match.TeamModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
        install(new TeamModule());
    }

}
