package net.astrocube.skywars.menu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.perk.SkyWarsPerkManifest;
import net.astrocube.skywars.api.perk.SkyWarsPerkProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.abstraction.item.ItemClickable;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;

/**
 * Class responsible of creating
 * and opening the kit selection menu
 * for players
 */
@Singleton
public class KitSelectMenuProvider {

	private @Inject MessageHandler messageHandler;
	private @Inject ShapedMenuGenerator menuGenerator;
	private @Inject CustomItemRepository<Kit> kitRepository;
	private @Inject SkyWarsPerkProvider skyWarsPerkProvider;
	private @Inject KitBuyConfirmMenuProvider kitBuyConfirmMenuProvider;

	public void open(Player player) throws Exception {

		SkyWarsPerkManifest perkManifest =
			skyWarsPerkProvider.getManifest(player.getDatabaseIdentifier())
				.orElseThrow(() -> new GameControlException("Manifest not found"));

		Set<String> boughtKits = perkManifest.getBoughtKits();
		Optional<String> selectedKit = perkManifest.getSelectedKit();
		int money = perkManifest.getMoney();

		Inventory inventory = menuGenerator.generate(
			player,
			messageHandler.get(player, "menu.kit-select.title"),
			() -> {
			},
			Kit.class,
			kitRepository.getRegisteredItems(),
			kit -> {
				String kitId = kit.getIdentifier();
				boolean selected = selectedKit.isPresent() && selectedKit.get().equals(kitId);
				String footerPath = "menu.kit-select.kit.foot-";

				if (selected) {
					footerPath += "selected";
				} else if (boughtKits.contains(kitId)) {
					footerPath += "bought";
				} else if (money > kit.getPrice()) {
					footerPath += "purchasable";
				} else {
					footerPath += "unavailable";
				}

				ItemStack item = KitIconUtil.getBuilderFor(player, perkManifest, kit, footerPath, messageHandler)
					.build();

				Predicate<InventoryClickEvent> action;

				if (boughtKits.contains(kitId)) {
					action = event -> {
						perkManifest.setSelectedKit(kitId);
						try {
							skyWarsPerkProvider.update(player.getDatabaseIdentifier(), perkManifest);
						} catch (Exception e) {
							Bukkit.getLogger().log(
								Level.INFO,
								"An error occurred while updating SkyWars" +
									" perks for player " + player.getName(),
								e
							);
						}
						player.closeInventory();
						return true;
					};
				} else if (money > kit.getPrice()) {
					action = event -> {
						kitBuyConfirmMenuProvider.open(player, kit);
						return true;
					};
				} else {
					action = event -> true;
				}

				return ItemClickable.builder()
					.setItemStack(item)
					.setAction(action)
					.build();
			}
		);

		player.openInventory(inventory);
	}
}