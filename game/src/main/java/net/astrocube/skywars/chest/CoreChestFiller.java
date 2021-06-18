package net.astrocube.skywars.chest;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.skywars.api.chest.ChestFiller;
import net.astrocube.skywars.api.chest.tier.ChestTier;
import net.astrocube.skywars.api.custom.CustomItemRepository;
import net.astrocube.skywars.api.custom.ItemSerializer;
import net.astrocube.skywars.api.map.MapConfiguration;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.Random;

@Singleton
public class CoreChestFiller implements ChestFiller {

	private @Inject CustomItemRepository<ChestTier> tierRepository;

	@Override
	public void fillChest(MapConfiguration.Chest chest, Chest mapChest) {

		Optional<ChestTier> tierOptional = tierRepository.getRegisteredItems()
				.stream()
				.filter(t -> t.getTier() == chest.getTier())
				.findAny();

		if (!tierOptional.isPresent()) {
			return;
		}

		ChestTier tier = tierOptional.get();

		Random generator = new Random(System.currentTimeMillis());
		Inventory inventory = mapChest.getInventory();

		inventory.clear();

		// array containing free slots, initially, elements are equal to its indexes
		int[] freeSlots = new int[inventory.getSize()];

		// count of free slots, it decrements
		int freeSlotCount = inventory.getSize();

		for (int i = 0; i < inventory.getSize(); i++) {
			freeSlots[i] = i;
		}

		for (ChestTier.Item tierItem : tier.getProbabilities()) {
			if (freeSlotCount <= 0) {
				break;
			}
			if (
					tierItem.getChance() >= 100 ||
							generator.nextInt(100) < tierItem.getChance()
			) {

				// generate number between minimum and maximum
				int amount = generator.nextInt(tierItem.getMaximum() - tierItem.getMinimum() + 1) + tierItem.getMinimum();

				ItemStack item = ItemSerializer.serialize(tierItem.getItem());
				item.setAmount(amount);

				int cursor = generator.nextInt(freeSlotCount);
				int slot = freeSlots[cursor];

				freeSlotCount--;
				// move last element to current index
				freeSlots[cursor] = freeSlots[freeSlotCount];

				inventory.setItem(slot, item);
			}
		}
	}

}
