package net.astrocube.skywars.api.custom;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemSerializer {

    public static ItemStack serialize(SerializableItem item) {
        ItemStack stack = new ItemStack(item.getMaterial(), item.getNumber());
        item.getEnchantments().forEach(enchantment -> {
            Enchantment type = Enchantment.getByName(enchantment.getType());

            if (type != null) {

                stack.addUnsafeEnchantment(type, enchantment.getLevel());
            }
        });
        return stack;
    }

}
