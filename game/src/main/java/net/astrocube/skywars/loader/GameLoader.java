package net.astrocube.skywars.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;

import javax.inject.Named;

public class GameLoader implements Loader {

	private @Inject
	@Named("listener")
	Loader listenerLoader;
	private @Inject
	@Named("repository")
	Loader repositoryLoader;

	@Override
	public void load() {
		this.listenerLoader.load();
		this.repositoryLoader.load();
	}

}
