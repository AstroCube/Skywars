package net.astrocube.skywars.api.perk;

import net.astrocube.api.core.virtual.perk.StorablePerk;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public interface SkyWarsPerkProvider {

	static SkyWarsPerkManifest generateDefault() {
		return new SkyWarsPerkManifest() {

			@Override
			public Set<String> getBoughtKits() {
				return Collections.emptySet();
			}

			@Override
			public String getSelectedKit() {
				return "default";
			}

			@Override
			public void setSelectedKit(String kitIdentifier) {
			}

			@Override
			public int getMoney() {
				return 0;
			}

			@Override
			public void setMoney(int money) {
			}
		};
	}

	/**
	 * Retrieves perk manifest from {@link StorablePerk}.
	 *
	 * @param playerId to retrieve
	 * @return optional containing possible manifest
	 */
	Optional<SkyWarsPerkManifest> getManifest(String playerId) throws Exception;

	/**
	 * updates a certain manifest according to player stored {@link StorablePerk}.
	 *
	 * @param playerId to update
	 * @param manifest to update
	 */
	void update(String playerId, SkyWarsPerkManifest manifest) throws Exception;
}