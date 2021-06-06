package net.astrocube.skywars.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.skywars.api.cage.Cage;
import net.astrocube.skywars.api.chest.tier.ChestTier;
import net.astrocube.skywars.api.custom.CustomItemRepository;

public class RepositoryLoader implements Loader {

	private @Inject CustomItemRepository<ChestTier> tierRepository;
	private @Inject CustomItemRepository<Cage> cageRepository;

	@Override
	public void load() {
		tierRepository.generate();
		cageRepository.generate();
	}

}
