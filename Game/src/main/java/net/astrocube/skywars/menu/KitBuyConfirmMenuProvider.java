package net.astrocube.skywars.menu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.perk.SkyWarsPerkManifest;
import net.astrocube.skywars.api.perk.SkyWarsPerkProvider;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.util.logging.Level;

@Singleton
public class KitBuyConfirmMenuProvider {

    private @Inject MessageHandler messageHandler;
    private @Inject SkyWarsPerkProvider perkProvider;

    public void open(Player player, Kit kit) {

        SkyWarsPerkManifest perkManifest;

        try {
            perkManifest = perkProvider.getManifest(player.getDatabaseIdentifier())
                    .orElseThrow(() -> new GameControlException("Perk manifest not found"));
        } catch (Exception e) {
            Bukkit.getLogger().log(
                    Level.SEVERE,
                    "Cannot get manifest for player " + player.getName(),
                    e
            );
            return;
        }

        GUIBuilder.builder(
                messageHandler.replacing(
                        player,
                        "menu.kit-buy.title",
                        "%kit_name%", messageHandler.get(
                                player,
                                "kits." + kit.getIdentifier() + ".title"
                        )
                ),
                3
        )
                .addItem(
                    // "joder claro que si" button
                    ItemClickable.builder(3)
                        .setItemStack(
                            ItemBuilder.newBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 13)
                                .setName(messageHandler.get(player, "menu.kit-buy.accept"))
                                .build()
                        )
                        .setAction(event -> {
                            perkManifest.getBoughtKits().add(kit.getIdentifier());
                            perkManifest.setSelectedKit(kit.getIdentifier());
                            perkManifest.setMoney(perkManifest.getMoney() - kit.getPrice());
                            try {
                                perkProvider.update(player.getDatabaseIdentifier(), perkManifest);
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
                        })
                        .build()
                )
                .addItem(
                    ItemClickable.builder(5)
                        .setItemStack(KitIconUtil.getBuilderFor(
                                player,
                                perkManifest,
                                kit,
                                "menu.kit-select.kit.foot-purchasable",
                                messageHandler
                        ).build())
                        .build()
                )
                .addItem(
                    // "ahorita no joven" button
                    ItemClickable.builder(7)
                        .setItemStack(
                            ItemBuilder.newBuilder(Material.STAINED_GLASS_PANE, 1, (byte) 14)
                                .setName(messageHandler.get(player, "menu.kit-buy.reject"))
                                .build()
                        )
                        .setAction(event -> {
                            event.getWhoClicked().closeInventory();
                            return true;
                        })
                        .build()
                )
                .build();
    }

}
