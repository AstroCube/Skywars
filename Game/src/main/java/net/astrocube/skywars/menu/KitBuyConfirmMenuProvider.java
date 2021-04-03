package net.astrocube.skywars.menu;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.yushust.message.MessageHandler;
import net.astrocube.skywars.api.kit.Kit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

@Singleton
public class KitBuyConfirmMenuProvider {

    private @Inject MessageHandler messageHandler;

    public void open(Player player, Kit kit) {
        GUIBuilder.builder(
                messageHandler.replacing(
                        player,
                        "menu.kit-buy.title",
                        "%%kit_name%", messageHandler.get(
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

                            return true;
                        })
                        .build()
                )
                .addItem(
                    ItemClickable.builder(5)
                        .setItemStack(

                        )
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
