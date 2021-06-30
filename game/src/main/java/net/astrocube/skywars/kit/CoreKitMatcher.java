package net.astrocube.skywars.kit;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.virtual.game.match.MatchDoc;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.kit.KitMatcher;
import net.astrocube.skywars.api.perk.SkyWarsPerkManifest;
import net.astrocube.skywars.api.perk.SkyWarsPerkProvider;
import net.astrocube.skywars.api.team.ProvisionedTeam;
import org.bukkit.Bukkit;

import javax.inject.Inject;

public class CoreKitMatcher implements KitMatcher {

	private @Inject SkyWarsPerkProvider skyWarsPerkProvider;
	private @Inject CustomItemRepository<Kit> kitRepository;

	@Override
	public Kit getPlayerKit(MatchDoc.TeamMember member) throws Exception {

		String userIdentification = member.getUser();

		SkyWarsPerkManifest perkManifest =
			skyWarsPerkProvider.getManifest(userIdentification)
				.orElseThrow(() -> new GameControlException("Perk manifest of player" + userIdentification + "not found"));

		return kitRepository.getRegisteredItems()
			.stream()
			.filter(kit -> kit.getIdentifier().equalsIgnoreCase(
				perkManifest.getSelectedKit()
					.orElse("default"))
			)
			.findAny()
			.orElseThrow(() ->
				new GameControlException(
					"An error occurred while matching the kit of the user " + userIdentification + ", the problem seems an error of the kit generation."
				)
			);
	}

	@Override
	public void applyTeamKits(ProvisionedTeam team) throws Exception {
		for(MatchDoc.TeamMember member : team.getMembers()) {
			Kit.build(Bukkit.getPlayerByIdentifier(member.getUser()), getPlayerKit(member));
		}
	}
}