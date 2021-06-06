package net.astrocube.skywars.menu;

import me.yushust.message.MessageHandler;
import net.astrocube.api.core.virtual.user.User;
import net.astrocube.skywars.api.custom.SerializableItem;
import net.astrocube.skywars.api.kit.Kit;
import net.astrocube.skywars.api.perk.SkyWarsPerkManifest;
import org.bukkit.entity.Player;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.util.List;

public class KitIconUtil {

    private KitIconUtil() {
    }

    public static ItemBuilder getBuilderFor(
            Player player,
            SkyWarsPerkManifest perk,
            Kit kit,
            String footerPath,
            MessageHandler messageHandler
    ) {
        SerializableItem icon = kit.getIcon();

        List<String> header = messageHandler.getMany(player, "menu.kit-select.kit.head");
        List<String> description = messageHandler.getMany(
                player,
                "kits." + kit.getIdentifier() + ".description"
        );
        List<String> footer = messageHandler.replacingMany(
                player,
                footerPath,
                "%required%", kit.getPrice() - perk.getMoney(),
                "%price%", kit.getPrice()
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
                        "kits." + kit.getIdentifier() + ".title"
                ))
                .setLore(header);
    }

}
