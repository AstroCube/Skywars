package net.astrocube.skywars.chest;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.chest.ChestFiller;
import net.astrocube.skywars.api.chest.ChestSpawner;

public class ChestModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(ChestFiller.class).to(CoreChestFiller.class);
		bind(ChestSpawner.class).to(CoreChestSpawner.class);
	}

}
