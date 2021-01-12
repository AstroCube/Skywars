package net.astrocube.skywars.api.kit;

import net.astrocube.skywars.api.custom.Customizable;
import net.astrocube.skywars.api.custom.ItemSerializer;
import net.astrocube.skywars.api.custom.SerializableItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Set;

public interface Kit extends Customizable {

    /**
     * @return identifier of the kit
     */
    String getIdentifier();

    /**
     * @return serializable helmet to be placed
     */
    SerializableItem getHelmet();

    /**
     * @return serializable chest to be placed
     */
    SerializableItem getChest();

    /**
     * @return serializable leggings to be placed
     */
    SerializableItem getLeggings();

    /**
     * @return serializable boots to be placed
     */
    SerializableItem getBoots();

    /**
     * @return if kit has team support
     */
    boolean hasTeamSupport();

    /**
     * @return serializable chest to be placed
     */
    Set<Pair> getInventory();

    interface Pair {

        /**
         * @return serializable item to be placed
         */
        SerializableItem getItem();

        /**
         * @return position where item will be placed
         */
        int getPosition();

    }

    /**
     * Build kit at a player inventory
     * @param player to be used
     * @param kit to be built
     */
    static void build(Player player, Kit kit) {
        player.getInventory().setHelmet(ItemSerializer.serialize(kit.getHelmet()));
        player.getInventory().setChestplate(ItemSerializer.serialize(kit.getChest()));
        player.getInventory().setLeggings(ItemSerializer.serialize(kit.getLeggings()));
        player.getInventory().setBoots(ItemSerializer.serialize(kit.getBoots()));
        kit.getInventory().forEach(item ->
                player.getInventory().setItem(item.getPosition(), ItemSerializer.serialize(item.getItem())));
    }

}
