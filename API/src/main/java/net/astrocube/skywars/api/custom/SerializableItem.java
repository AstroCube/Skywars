package net.astrocube.skywars.api.custom;

import org.bukkit.Material;

import java.util.HashSet;
import java.util.Set;

/**
 * Item that can be built to an {@link org.bukkit.inventory.ItemStack}
 */
public interface SerializableItem {

    /**
     * @return material of the item
     */
    Material getMaterial();

    /**
     * @return item quantity
     */
    int getNumber();

    /**
     * @return enchantments of the item
     */
    Set<EnchantmentCompound> getEnchantments();

    interface EnchantmentCompound {

        /**
         * @return enchantment to be applied
         */
        String getType();

        /**
         * @return level of the enchantment
         */
        int getLevel();

    }

    static SerializableItem getAir() {
        return new SerializableItem() {
            @Override
            public Material getMaterial() {
                return Material.AIR;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public Set<EnchantmentCompound> getEnchantments() {
                return new HashSet<>();
            }
        };
    }

}
