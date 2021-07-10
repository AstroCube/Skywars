package net.astrocube.skywars.perk;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.perk.PerkManifestProvider;
import net.astrocube.api.core.service.update.UpdateService;
import net.astrocube.api.core.virtual.perk.StorablePerk;
import net.astrocube.api.core.virtual.perk.StorablePerkDoc;
import net.astrocube.skywars.api.perk.SkyWarsPerkManifest;
import net.astrocube.skywars.api.perk.SkyWarsPerkProvider;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;

public class CoreSkyWarsPerkProvider implements SkyWarsPerkProvider {

	private @Inject ObjectMapper mapper;
	private @Inject Plugin plugin;
	private @Inject PerkManifestProvider perkManifestProvider;
	private @Inject UpdateService<StorablePerk, StorablePerkDoc.Partial> updateService;

	@Override
	public Optional<SkyWarsPerkManifest> getManifest(String playerId) throws Exception {
		return getFromUser(playerId)
			.stream()
			.map(manifest -> (SkyWarsPerkManifest) manifest.getStored())
			.findFirst();
	}

	@Override
	public void update(String playerId, SkyWarsPerkManifest manifest) throws Exception {
		StorablePerk perk = getFromUser(playerId).stream().findAny().orElseThrow(
			() -> new GameControlException("Not found any storable perk to be updated.")
		);

		perk.setStored(manifest);
		updateService.updateSync(perk);
	}

	private Set<StorablePerk> getFromUser(String playerId) throws Exception {
		ObjectNode node = mapper.createObjectNode();

		node.put("responsible", playerId);
		node.put("type", "skywars_manifest");
		node.put("subGamemode", plugin.getConfig().getString("centauri.subMode"));

		Set<StorablePerk> perks = perkManifestProvider.query(node, SkyWarsPerkManifest.class);

		if (perks == null || perks.size() == 0) {

			perkManifestProvider.createRegistry(
				playerId,
				plugin.getConfig().getString("centauri.mode"),
				plugin.getConfig().getString("centauri.subMode"),
				"skywars_manifest",
				SkyWarsPerkProvider.generateDefault()
			);

			return getFromUser(playerId);
		}

		return perks;
	}
}