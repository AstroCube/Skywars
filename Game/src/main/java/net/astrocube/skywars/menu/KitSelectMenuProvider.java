package net.astrocube.skywars.menu;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.bukkit.menu.ShapedMenuGenerator;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.custom.SerializableItem;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.perk.SkyWarsPerkManifest;
import net.astrocube.skywars.api.perk.SkyWarsPerkProvider;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

/**
 * Class responsible of creating
 * and opening the kit selection menu
 * for players
 */
public class KitSelectMenuProvider {

    private @Inject MessageHandler messageHandler;
    private @Inject ShapedMenuGenerator menuGenerator;
    private @Inject CustomItemRepository<Kit> kitRepository;
    private @Inject SkyWarsPerkProvider skyWarsPerkProvider;

    public void open(Player player) throws Exception{

        Set<ShapedMenuGenerator.BaseClickable> items =
                new HashSet<>();
        SkyWarsPerkManifest perkManifest =
                skyWarsPerkProvider.getManifest(player.getDatabaseIdentifier())
                        .orElseThrow(() -> new GameControlException("Manifest not found"));

        Set<String> boughtKits = perkManifest.getBoughtKits();
        Optional<String> selectedKit = perkManifest.getSelectedKit();
        int money = perkManifest.getMoney();

        for (Kit kit : kitRepository.getRegisteredItems()) {
            String kitId = kit.getIdentifier();
            boolean selected = selectedKit.isPresent() && selectedKit.get().equals(kitId);
            items.add(new ShapedMenuGenerator.BaseClickable() {
                @Override
                public Consumer<Player> getClick() {
                    if (boughtKits.contains(kitId)) {
                        return clicker -> {
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
                        };
                    } else if (money > kit.getPrice()) {
                        return clicker -> {
                            boughtKits.add(kitId);
                            perkManifest.setMoney(money - kit.getPrice());
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
                        };
                    }
                    // dummy
                    return clicker -> {};
                }

                @Override
                public ItemStack getStack() {
                    SerializableItem icon = kit.getIcon();

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

                    List<String> header = messageHandler.getMany(player, "menu.kit-select.kit.head");
                    List<String> description = messageHandler.getMany(
                            player,
                            "kits." + kitId + ".description"
                    );
                    List<String> footer = messageHandler.replacingMany(
                            player,
                            footerPath,
                            "%%required%%", kit.getPrice() - money,
                            "%%price%%", kit.getPrice()
                    );

                    header.addAll(description);
                    header.addAll(footer);

                    return ItemBuilder.newBuilder(
                            icon.getMaterial(),
                            icon.getNumber(),
                            icon.getNumber().byteValue()
                    )
                            .setName(messageHandler.get(
                                    player,
                                    "kits." + kitId + ".title"
                            ))
                            .setLore(header)
                            .build();
                }
            });
        }

        Inventory inventory = menuGenerator.generate(
                player,
                messageHandler.get(player, "menu.kit-select.title"),
                clicker -> {

                },
                items
        );

        player.openInventory(inventory);
    }

}
