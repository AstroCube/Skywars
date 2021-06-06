package net.astrocube.skywars.cage;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.cage.CageMatcher;
import net.astrocube.skywars.api.cage.CageSpawner;
import net.astrocube.skywars.api.cage.MatchCageSpawner;

public class CoreCage extends ProtectedModule {

	@Override
	public void configure() {
		bind(CageMatcher.class).to(CoreCageMatcher.class);
		bind(CageSpawner.class).to(CoreCageSpawner.class);
		bind(MatchCageSpawner.class).to(CoreMatchCageSpawner.class);
	}

}
