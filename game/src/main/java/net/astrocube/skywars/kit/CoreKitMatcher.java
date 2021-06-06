package net.astrocube.skywars.kit;

import net.astrocube.api.core.virtual.user.User;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.custom.SerializableItem;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.kit.KitMatcher;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class CoreKitMatcher implements KitMatcher {

	private @Inject CustomItemRepository<Kit> customItemRepository;

	@Override
	public Kit getPlayerKit(User user) {
		return customItemRepository.getRegisteredItems().stream().findAny().orElse(new Kit() {
			@Override
			public String getIdentifier() {
				return "";
			}

			@Override
			public SerializableItem getIcon() {
				return null;
			}

			@Override
			public SerializableItem getHelmet() {
				return SerializableItem.getAir();
			}

			@Override
			public SerializableItem getChest() {
				return SerializableItem.getAir();
			}

			@Override
			public SerializableItem getLeggings() {
				return SerializableItem.getAir();
			}

			@Override
			public SerializableItem getBoots() {
				return SerializableItem.getAir();
			}

			@Override
			public boolean hasTeamSupport() {
				return true;
			}

			@Override
			public Set<Pair> getInventory() {
				return new HashSet<>();
			}

			@Override
			public int getPrice() {
				return 0;
			}
		});
	}

}
