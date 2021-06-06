package net.astrocube.skywars.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.AllArgsConstructor;
import me.fixeddev.inject.ProtectedModule;
import net.astrocube.skywars.api.cage.Cage;
import net.astrocube.skywars.api.chest.tier.ChestTier;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.kit.Kit;
import org.bukkit.plugin.Plugin;

@AllArgsConstructor
public class CustomModule extends ProtectedModule {

	@Provides
	@Singleton
	public CustomItemRepository<ChestTier> provideTierRepository(Plugin plugin, ObjectMapper mapper) {
		return new CoreItemRepository<>("tiers", ChestTier.class, plugin, mapper);
	}

	@Provides
	@Singleton
	public CustomItemRepository<Cage> provideCageRepository(Plugin plugin, ObjectMapper mapper) {
		return new CoreItemRepository<>("cage", Cage.class, plugin, mapper);
	}

	@Provides
	@Singleton
	public CustomItemRepository<Kit> provideKitRepository(Plugin plugin, ObjectMapper mapper) {
		return new CoreItemRepository<>("kit", Kit.class, plugin, mapper);
	}

}
