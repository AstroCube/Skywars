package net.astrocube.skywars.loader;

import com.google.inject.Scopes;
import com.google.inject.name.Names;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.core.loader.Loader;

public class LoaderModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(Loader.class).to(GameLoader.class).in(Scopes.SINGLETON);
		bind(Loader.class).annotatedWith(Names.named("listener")).to(ListenerLoader.class);
		bind(Loader.class).annotatedWith(Names.named("repository")).to(RepositoryLoader.class);
	}

}
