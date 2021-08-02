package net.astrocube.skywars.api.custom;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ItemSerializer {

	public static ItemStack serialize(SerializableItem item) {

		if (item == null) {
			return new ItemStack(Material.AIR, 1);
		}

		int quantity = 0;
		short code = 0;

		if (item.getNumber() != null) {
			quantity = item.getNumber();
		}
		if (item.getCode() != null) {
			code = item.getCode();
		}

		ItemStack stack = new ItemStack(item.getMaterial(), quantity, code);
		item.getEnchantments().forEach(enchantment -> {
			Enchantment type = Enchantment.getByName(enchantment.getType());
			if (type != null) {
				stack.addUnsafeEnchantment(type, enchantment.getLevel());
			}
		});
		return stack;
	}
}